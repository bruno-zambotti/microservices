package br.com.zambotti.order.service.impl;

import br.com.zambotti.order.advice.ResponseError;
import br.com.zambotti.order.model.Inventory;
import br.com.zambotti.order.model.Order;
import br.com.zambotti.order.model.OrderItem;
import br.com.zambotti.order.model.enums.OrderStatus;
import br.com.zambotti.order.repository.OrderRepository;
import br.com.zambotti.order.service.CustomerService;
import br.com.zambotti.order.service.InventoryService;
import br.com.zambotti.order.service.OrderService;
import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private CustomerService customerService;

	@Override
	public List<Order> getAllOrders() {
		List<Order> order = StreamSupport.stream(orderRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return order;
	}

	@Override
	public Order getOrderById(Integer id) {
		return orderRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
	}

	@Override
	public Order createOrder(Integer customerId) {
		verifyCustomer(customerId);

		Order order = new Order(customerId, new Date(), OrderStatus.NOVO);

		return orderRepository.save(order);
	}

	@Override
	public void validateInventoryToCreate(Set<OrderItem> items){
		List<Inventory> inventoryList = verifyInventoryForSale(items);
		updateInventory(inventoryList);
	}

	@Override
	public void updateOrder(Integer id, Integer customerId, OrderStatus status, Set<OrderItem> items, Boolean updateInventory) {
		Order order = getOrderById(id);

		List<Inventory> inventoryList = verifyInventoryForUpdate(order, items);

		verifyCustomer(customerId);

		order.setCustomerId(customerId);
		order.setStatus(status);
		order.setItems(items);

		orderRepository.save(order);

		if (updateInventory)
			updateInventory(inventoryList);
	}

	@Override
	public void deleteOrder(Integer id) {
		orderRepository.delete(orderRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Pedido não encontrado")));
	}

	private void updateInventory(List<Inventory> items) {
		for (Inventory inventoryItem : items) {
			inventoryService.updateInventory(inventoryItem);
		}
	}

	private void verifyCustomer(Integer customerId){
		customerService.getCustomerById(customerId);
	}

	private List<Inventory> verifyInventoryForUpdate(Order storedOrder, Set<OrderItem> items) {
		List<Inventory> inventoryList = inventoryService.getInventory();
		List<Inventory> modifiedInventory = new ArrayList<>();

		for (Inventory inventory : inventoryList) {
			Boolean quantityGreaterThanLimit;
			Integer quantity;
			Optional<OrderItem> product = items.stream().filter(item ->
					item.getId().getProductId().equals(inventory.getProduct().getId())).findFirst();

			Optional<OrderItem> storedProduct = storedOrder.getItems().stream().filter(item ->
					item.getId().getProductId().equals(inventory.getProduct().getId())).findFirst();

			quantity = storedProduct.map(
					orderItem ->
							product.get().getQuantity() - orderItem.getQuantity())
					.orElseGet(() ->
							product.get().getQuantity());

			quantityGreaterThanLimit = false;
			Integer newQuantity = 0;
			if (quantity > inventory.getAmount()){
				quantityGreaterThanLimit = true;
			} else if (quantity < 0) {
				newQuantity = quantity;
			} else {
				newQuantity = product.get().getQuantity();
			}

			if (!product.isPresent() || quantityGreaterThanLimit){
				throw new ResponseError(
						HttpStatus.UNPROCESSABLE_ENTITY,
						"Não é possível realizar o pedido pois um mais produtos informados não possui a quantidade suficiente em estoque.");
			} else {
				Inventory updatedInventory = new Inventory();
				updatedInventory.setInventoryId(inventory.getInventoryId());
				updatedInventory.setAmount(inventory.getAmount() - newQuantity);
				updatedInventory.setProduct(inventory.getProduct());
				modifiedInventory.add(updatedInventory);
			}
		}

		return modifiedInventory;
	}

	private List<Inventory> verifyInventoryForSale(Set<OrderItem> items) {
		List<Inventory> inventoryList = inventoryService.getInventory();
		List<Inventory> modifiedInventory = new ArrayList<>();

		for (OrderItem item : items) {
			Optional<Inventory> inventory = inventoryList.stream().filter(inventoryItem ->
					item.getId().getProductId().equals(inventoryItem.getProduct().getId())).findFirst();

			if (!inventory.isPresent() || item.getQuantity() > inventory.get().getAmount()){
				throw new ResponseError(
						HttpStatus.UNPROCESSABLE_ENTITY,
						"Não é possível realizar o pedido pois um mais produtos informados não possui a quantidade suficiente em estoque.");
			} else {
				Inventory newInventory = new Inventory();
				newInventory.setInventoryId(inventory.get().getInventoryId());
				newInventory.setAmount(inventory.get().getAmount() - item.getQuantity());
				newInventory.setProduct(inventory.get().getProduct());
				modifiedInventory.add(newInventory);
			}
		}

		return modifiedInventory;
	}
}

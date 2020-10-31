package br.com.zambotti.order.service;

import java.util.List;
import java.util.Set;

import br.com.zambotti.order.model.Order;
import br.com.zambotti.order.model.OrderItem;
import br.com.zambotti.order.model.enums.OrderStatus;

public interface OrderService {
	List<Order> getAllOrders();
	Order getOrderById(Integer id);
	Order createOrder(Integer customerId);
	void updateOrder(Integer id, Integer customerId, OrderStatus status, Set<OrderItem> items, Boolean updateInventory);
	void deleteOrder(Integer id);
	void validateInventoryToCreate(Set<OrderItem> items);
}

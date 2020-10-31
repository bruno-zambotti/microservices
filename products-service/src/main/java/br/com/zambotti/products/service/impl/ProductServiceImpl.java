package br.com.zambotti.products.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import br.com.zambotti.products.advice.ResponseError;
import br.com.zambotti.products.model.Order;
import br.com.zambotti.products.model.OrderItem;
import br.com.zambotti.products.service.InventoryService;
import br.com.zambotti.products.service.OrderService;
import br.com.zambotti.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.zambotti.products.model.Product;
import br.com.zambotti.products.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private OrderService orderService;
	private InventoryService inventoryService;

	@Autowired
	public ProductServiceImpl(final ProductRepository productRepository,
							  final OrderService orderService,
							  final InventoryService inventoryService){
		this.productRepository = productRepository;
		this.orderService = orderService;
		this.inventoryService = inventoryService;
	};

	@Override
	public List<Product> getAllProduct() {
		List<Product> product = StreamSupport.stream(productRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return product;
	}

	@Override
	public Product getProductById(Integer id) {
		return productRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}

	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void updateProduct(Product product) {
		Product storedProduct = productRepository.findById(product.getId()).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado"));

		storedProduct.setName(product.getName());
		storedProduct.setDescription(product.getDescription());
		storedProduct.setPrice(product.getPrice());

		productRepository.save(storedProduct);
	}

	@Override
	public void deleteProduct(Integer id) {
		Product product = productRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado"));

		if (Optional.ofNullable(inventoryService.getInventoryItemByProductId(product.getId())).isPresent()) {
			throw new ResponseError(HttpStatus.PRECONDITION_FAILED, "O produto informado possui estoque associado.");
		}

		List<Order> orders = orderService.getAllOrders();

		for (Order order : orders) {
			for (OrderItem orderItem : order.getItems())
				if (orderItem.getProductId().equals(product.getId()))
					throw new ResponseError(HttpStatus.PRECONDITION_FAILED, "O produto informado possui ao menos um pedido de venda associado.");
		}

		productRepository.delete(product);
	}
}

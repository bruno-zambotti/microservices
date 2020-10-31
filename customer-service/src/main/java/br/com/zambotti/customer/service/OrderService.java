package br.com.zambotti.customer.service;

import br.com.zambotti.customer.model.Order;

import java.util.List;

public interface OrderService {
	List<Order> getAllOrders();
}

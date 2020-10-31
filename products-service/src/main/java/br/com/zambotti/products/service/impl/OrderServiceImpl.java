package br.com.zambotti.products.service.impl;

import br.com.zambotti.products.model.Order;
import br.com.zambotti.products.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${order.service.endpoint}")
	private String orderServiceEndpoint;

	@Override
	public List<Order> getAllOrders() {
		RestTemplate restTemplate = new RestTemplate();
        Order[] orders = restTemplate.getForObject(orderServiceEndpoint, Order[].class);

		if (orders == null || orders.length == 0) {
			return new ArrayList<>();
		}
		return Arrays.asList(orders);
	}
}

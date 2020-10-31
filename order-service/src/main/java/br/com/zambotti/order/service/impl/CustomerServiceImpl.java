package br.com.zambotti.order.service.impl;

import br.com.zambotti.order.advice.ResponseError;
import br.com.zambotti.order.model.Customer;
import br.com.zambotti.order.service.CustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Value("${customer.service.endpoint}")
	private String customerServiceEndpoint;

	@Override
	public Customer getCustomerById(Integer id) {
		RestTemplate restTemplate = new RestTemplate();

		String url = String.format("%s/%s", customerServiceEndpoint, id);
		Customer customer = restTemplate.getForObject(url, Customer.class);

		if (!Optional.ofNullable(customer).isPresent())
			throw new ResponseError(HttpStatus.NOT_FOUND, "Cliente não encontrado");

		return customer;
	}
}

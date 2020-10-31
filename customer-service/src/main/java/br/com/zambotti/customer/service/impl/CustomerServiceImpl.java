package br.com.zambotti.customer.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import br.com.zambotti.customer.advice.ResponseError;
import br.com.zambotti.customer.model.Order;
import br.com.zambotti.customer.service.CustomerService;
import br.com.zambotti.customer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.zambotti.customer.model.Customer;
import br.com.zambotti.customer.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderService orderService;

	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> customers = StreamSupport.stream(customerRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return customers;
	}

	@Override
	public Customer getCustomerById(Integer id) {
		return customerRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		Customer storedCustomer = customerRepository.findById(customer.getId()).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

		storedCustomer.setName(customer.getName());
		storedCustomer.setSurname(customer.getSurname());
		storedCustomer.setGender(customer.getGender());
		storedCustomer.setBirthDate(customer.getBirthDate());
		storedCustomer.setAddress(customer.getAddress());
		storedCustomer.setPhones(customer.getPhones());

		customerRepository.save(storedCustomer);
	}

	@Override
	public void deleteCustomer(Integer id) {

		List<Order> orders = orderService.getAllOrders();

		for (Order order : orders)
			if (order.getCustomerId().equals(id))
				throw new ResponseError(HttpStatus.PRECONDITION_FAILED,
						"O cliente informado possui ao menos um pedido de venda associado.");

		customerRepository.delete(customerRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Cliente não encontrado")));
	}
}

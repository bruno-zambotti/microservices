package br.com.zambotti.customer.service;

import java.util.List;

import br.com.zambotti.customer.model.Customer;

public interface CustomerService {
	List<Customer> getAllCustomer();
	Customer getCustomerById(Integer id);
	Customer createCustomer(Customer customer);
	void updateCustomer(Customer customer);
	void deleteCustomer(Integer id);
}

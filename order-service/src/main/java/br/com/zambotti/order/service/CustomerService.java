package br.com.zambotti.order.service;

import br.com.zambotti.order.model.Customer;

public interface CustomerService {
    Customer getCustomerById(Integer id);
}

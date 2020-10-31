package br.com.zambotti.customer.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.zambotti.customer.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}

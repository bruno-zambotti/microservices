package br.com.zambotti.order.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.zambotti.order.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>  {

}

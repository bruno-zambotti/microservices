package br.com.zambotti.products.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.zambotti.products.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>{

}

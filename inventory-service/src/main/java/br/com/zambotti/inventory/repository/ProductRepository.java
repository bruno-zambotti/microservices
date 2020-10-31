package br.com.zambotti.inventory.repository;

import br.com.zambotti.inventory.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>{

}

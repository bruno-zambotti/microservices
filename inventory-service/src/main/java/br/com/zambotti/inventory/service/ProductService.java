package br.com.zambotti.inventory.service;

import br.com.zambotti.inventory.model.Product;

public interface ProductService {
    Product getProductById(Integer id);
}

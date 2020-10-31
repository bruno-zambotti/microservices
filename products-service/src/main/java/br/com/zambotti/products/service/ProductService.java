package br.com.zambotti.products.service;

import java.util.List;

import br.com.zambotti.products.model.Product;

public interface ProductService {
	
	List<Product> getAllProduct();
	Product getProductById(Integer id);
	Product createProduct(Product customer);
	void updateProduct(Product customer);
	void deleteProduct(Integer id);

}

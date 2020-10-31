package br.com.zambotti.inventory.service.impl;

import br.com.zambotti.inventory.advice.ResponseError;
import br.com.zambotti.inventory.model.Product;
import br.com.zambotti.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${products.service.endpoint}")
    private String productsServiceEndpoint;

    @Override
    public Product getProductById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("%s/%s", productsServiceEndpoint, id);
        Product product = null;

        try {
             product = restTemplate.getForObject(url, Product.class);
        } catch (Exception ex){
            throw new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }

        if (!Optional.ofNullable(product).isPresent())
                throw new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado");

        return product;
    }
}

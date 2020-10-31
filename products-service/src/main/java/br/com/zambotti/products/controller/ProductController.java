package br.com.zambotti.products.controller;

import br.com.zambotti.products.dto.ProductRequest;
import br.com.zambotti.products.dto.ProductResponse;
import br.com.zambotti.products.model.Product;
import br.com.zambotti.products.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/microservices/v1/product")
@Api(value = "Gerenciamento de Produtos")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;

    @Autowired
    public ProductController(final ProductService productService){
        this.productService = productService;
    };

    @ApiOperation(value = "Consultar todos os produtos")
    @GetMapping(produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        LOGGER.info("Getting all products ... ");
        List<Product> products = productService.getAllProduct();
        List<ProductResponse> productResponse = new ArrayList<>();
        products.stream().forEach(product -> productResponse.add(
                getProductResponse(product)));

        return ResponseEntity.ok(productResponse);
    }

    @ApiOperation(value = "Consultar um produto específico")
    @GetMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Integer id){
        LOGGER.info("Getting a specific product ... ");
        Product productResponse = productService.getProductById(id);

        return ResponseEntity.ok(getProductResponse(productResponse));
    }

    @ApiOperation(value = "Excluir um produto")
    @DeleteMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id){
        LOGGER.info("Deleting a specific product ... ");
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Alterar um produto")
    @PutMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id,
                                           @Valid @RequestBody ProductRequest productRequest){
        LOGGER.info("Updating a specific product ... ");
        productService.updateProduct(new Product(id, productRequest.getName(), productRequest.getDescription(),
                productRequest.getPrice()));

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Cadastro de produtos")
    @PostMapping(produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest){
        LOGGER.info("Creating a specific product ... ");
        Product product = productService.createProduct(new Product(productRequest.getName(), productRequest.getDescription(),
                productRequest.getPrice()));

        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/microservices/v1/product", product.getId())))
                .body(getProductResponse(product));
    }

    private ProductResponse getProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }
}
package br.com.zambotti.products.service;

import br.com.zambotti.products.advice.ResponseError;
import br.com.zambotti.products.model.Inventory;
import br.com.zambotti.products.model.Order;
import br.com.zambotti.products.model.Product;
import br.com.zambotti.products.repository.ProductRepository;
import br.com.zambotti.products.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private ProductServiceImpl service;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAProductWithSuccess() {
        String name = "name";
        String description = "description";
        Double price = 122.44;

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        when(productRepository.save(product)).thenReturn(product);

        assertNotNull(this.service.createProduct(product));
    }

    @Test
    public void shouldUpdateAProductWithSuccess() {
        Integer productId = 1;
        String name = "name";
        String description = "description";
        Double price = 122.44;

        Product product = new Product();
        product.setId(productId);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        product.setName("other_name");
        product.setDescription("other_description");

        this.service.updateProduct(product);
    }

    @Test
    public void shouldThrowAExceptionWhenUpdateAProductThatNotExists() {
        Integer productId = 1;

        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        try {
            this.service.updateProduct(product);
        } catch (ResponseError error){
            Assert.assertEquals(HttpStatus.NOT_FOUND, error.getStatus());
            Assert.assertEquals("Produto não encontrado", error.getMessage());
        }
    }

    @Test
    public void shouldGetAllStoredCustomersWithSuccess() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(new Product(), new Product()));
        Assert.assertEquals(2, this.service.getAllProduct().size());
    }

    @Test
    public void shouldDeleteAProductWithSuccess() {
        Integer id = 1;

        when(productRepository.findById(id)).thenReturn(Optional.of(new Product()));
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>());

        this.service.deleteProduct(id);
    }

    @Test
    public void shouldGetProductByIdWithSuccess() {
        Integer id = 1;

        when(productRepository.findById(id)).thenReturn(Optional.of(new Product()));

        assertNotNull(this.service.getProductById(id));
    }

    @Test
    public void shouldThrowAExceptionWhenDeleteAProductThatNotExists() {
        Integer id = 1;

        when(productRepository.findById(id)).thenReturn(Optional.empty());
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>());

        try {
            this.service.deleteProduct(id);
        } catch (ResponseError error){
            Assert.assertEquals(HttpStatus.NOT_FOUND, error.getStatus());
            Assert.assertEquals("Produto não encontrado", error.getMessage());
        }
    }

    @Test
    public void shouldThrowAExceptionWhenDeleteAProductWithAAssociatedOrder() {
        Integer customerId = 1;
        Integer productId = 1;
        Integer orderId = 1;

        when(productRepository.findById(productId)).thenReturn(Optional.of(new Product()));
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(
                new Order(orderId, customerId, "NOVO", new HashSet<>())));

        try {
            this.service.deleteProduct(productId);
        } catch (ResponseError error){
            Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, error.getStatus());
            Assert.assertEquals("O produto informado possui ao menos um pedido de venda associado.",
                    error.getMessage());
        }
    }

    @Test
    public void shouldThrowAExceptionWhenDeleteAProductWithInventory() {
        Integer productId = 1;
        String name = "name";
        String description = "description";
        Double price = 122.44;

        Product product = new Product();
        product.setId(productId);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        when(productRepository.findById(productId)).thenReturn(Optional.of(new Product()));
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>());
        when(inventoryService.getInventoryItemByProductId(productId)).thenReturn(new Inventory(20));

        try {
            this.service.deleteProduct(productId);
        } catch (ResponseError error){
            Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, error.getStatus());
            Assert.assertEquals("O produto informado possui estoque associado.",
                    error.getMessage());
        }
    }
}

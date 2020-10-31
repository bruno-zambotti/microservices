package br.com.zambotti.inventory.services;

import br.com.zambotti.inventory.advice.ResponseError;
import br.com.zambotti.inventory.model.Inventory;
import br.com.zambotti.inventory.model.Product;
import br.com.zambotti.inventory.repository.InventoryRepository;
import br.com.zambotti.inventory.repository.ProductRepository;
import br.com.zambotti.inventory.service.impl.InventoryServiceImpl;
import br.com.zambotti.inventory.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class InventoryServiceTest {
    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private InventoryServiceImpl service;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAnInventoryWithSuccess() {
        Integer productId = 1;
        String name = "name";
        String description = "description";
        Double price = 122.44;

        Product product = new Product(productId, name, description, price);
        Inventory inventory = new Inventory(10, product);
        when(productService.getProductById(productId)).thenReturn(product);
        when(inventoryRepository.save(inventory)).thenReturn(inventory);
        when(productRepository.save(product)).thenReturn(product);

        assertNotNull(this.service.createInventoryItem(productId, inventory));
    }

    @Test
    public void shouldUpdateAnInventoryWithSuccess() {
        Integer productId = 1;
        Integer inventoryId = 1;
        String name = "name";
        String description = "description";
        Double price = 122.44;

        Product product = new Product(productId, name, description, price);
        Inventory inventory = new Inventory(10, product);
        inventory.setInventoryId(inventoryId);

        when(inventoryRepository.findById(inventory.getInventoryId())).thenReturn(Optional.of(inventory));

        this.service.updateInventoryItem(inventory);
    }

    @Test
    public void shouldThrowAExceptionWhenUpdateAnInventoryThatNotExists() {
        Integer inventoryId = 1;

        Inventory inventory = new Inventory();
        inventory.setInventoryId(inventoryId);

        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.empty());

        try {
            this.service.updateInventoryItem(inventory);
        } catch (ResponseError error){
            Assert.assertEquals(HttpStatus.NOT_FOUND, error.getStatus());
            Assert.assertEquals("Inventário não encontrado", error.getMessage());
        }
    }

    @Test
    public void shouldGetAllStoredInventoriesWithSuccess() {
        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(new Inventory(), new Inventory()));
        Assert.assertEquals(2, this.service.getInventory().size());
    }

    @Test
    public void shouldDeleteAnInventoryWithSuccess() {
        Integer id = 1;

        when(inventoryRepository.findById(id)).thenReturn(Optional.of(new Inventory()));


        this.service.deleteInventoryItem(id);
    }

    @Test
    public void shouldGetInventoryByProductIdWithSuccess() {
        Integer id = 1;

        when(inventoryRepository.findByProductId(id)).thenReturn(Optional.of(new Inventory()));

        assertNotNull(this.service.getInventoryItemByProductId(id));
    }

    @Test
    public void shouldThrowAExceptionWhenDeleteAnInventoryThatNotExists() {
        Integer id = 1;

        when(inventoryRepository.findById(id)).thenReturn(Optional.empty());

        try {
            this.service.deleteInventoryItem(id);
        } catch (ResponseError error){
            Assert.assertEquals(HttpStatus.NOT_FOUND, error.getStatus());
            Assert.assertEquals("Item do estoque não encontrado", error.getMessage());
        }
    }
}

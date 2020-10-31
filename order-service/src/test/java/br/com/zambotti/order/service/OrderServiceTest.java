package br.com.zambotti.order.service;

import br.com.zambotti.order.advice.ResponseError;
import br.com.zambotti.order.model.Customer;
import br.com.zambotti.order.model.Inventory;
import br.com.zambotti.order.model.Order;
import br.com.zambotti.order.model.OrderItem;
import br.com.zambotti.order.model.OrderItemPK;
import br.com.zambotti.order.model.Product;
import br.com.zambotti.order.model.enums.OrderStatus;
import br.com.zambotti.order.repository.OrderRepository;
import br.com.zambotti.order.service.impl.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private OrderServiceImpl service;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAOrderWithSuccess() {
        Integer customerId = 1;
        Order order = new Order(customerId, new Date(), OrderStatus.NOVO);

        when(customerService.getCustomerById(customerId)).thenReturn(new Customer());
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order orderResult = this.service.createOrder(customerId);

        assertNotNull(orderResult);
        assertEquals(OrderStatus.NOVO, orderResult.getStatus());
        assertEquals(customerId, orderResult.getCustomerId());
    }

    @Test
    public void shouldUpdateAOrderWithSuccess() {
        Integer orderId = 1;
        Integer customerId = 1;
        Integer productId = 1;
        Integer orderItemId = 1;
        String name = "name";
        String description = "description";
        Double price = 122.44;

        OrderItem orderItem = new OrderItem(new OrderItemPK(orderItemId, productId), 12);

        Order order = new Order(customerId, new Date(), OrderStatus.NOVO, new HashSet<>(Arrays.asList(orderItem)));
        Product product = new Product(productId, name, description, price);
        Inventory inventory = new Inventory(15, product);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(inventoryService.getInventory()).thenReturn(Arrays.asList(inventory));

        this.service.updateOrder(orderId, customerId, OrderStatus.AGUARDANDO_PAGAMENTO, new HashSet<>(Arrays.asList(orderItem)), Boolean.TRUE);
    }

    @Test
    public void shouldThrowAExceptionWhenUpdateAOrderThatNotExists() {
        Integer orderId = 1;
        Integer customerId = 1;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        try {
            this.service.updateOrder(orderId, customerId, OrderStatus.AGUARDANDO_PAGAMENTO, new HashSet<>(), Boolean.TRUE);
        } catch (ResponseError error) {
            Assert.assertEquals(HttpStatus.NOT_FOUND, error.getStatus());
            Assert.assertEquals("Pedido não encontrado", error.getMessage());
        }
    }

    @Test
    public void shouldGetAllStoredOrdersWithSuccess() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(new Order(), new Order()));
        Assert.assertEquals(2, this.service.getAllOrders().size());
    }

    @Test
    public void shouldDeleteAOrderWithSuccess() {
        Integer id = 1;

        when(orderRepository.findById(id)).thenReturn(Optional.of(new Order()));

        this.service.deleteOrder(id);
    }

    @Test
    public void shouldGetCustomerByIdWithSuccess() {
        Integer id = 1;

        when(orderRepository.findById(id)).thenReturn(Optional.of(new Order()));

        assertNotNull(this.service.getOrderById(id));
    }

    @Test
    public void shouldValidateInventoryToCreateAnOrderWithSucess() {
        Integer productId = 1;
        Integer orderItemId = 1;
        String name = "name";
        String description = "description";
        Double price = 122.44;

        OrderItem orderItem = new OrderItem(new OrderItemPK(orderItemId, productId), 12);
        Product product = new Product(productId, name, description, price);
        Inventory inventory = new Inventory(15, product);

        when(inventoryService.getInventory()).thenReturn(Arrays.asList(inventory));

        this.service.validateInventoryToCreate(new HashSet<>(Arrays.asList(orderItem)));
    }

    @Test
    public void shouldThrowAExceptionWhenValidateInventoryToCreateAndInventoryIsInsufficient() {
        Integer productId = 1;
        Integer orderItemId = 1;
        String name = "name";
        String description = "description";
        Double price = 122.44;

        OrderItem orderItem = new OrderItem(new OrderItemPK(orderItemId, productId), 12);
        Product product = new Product(productId, name, description, price);
        Inventory inventory = new Inventory(10, product);

        when(inventoryService.getInventory()).thenReturn(Arrays.asList(inventory));

        try {
            this.service.validateInventoryToCreate(new HashSet<>(Arrays.asList(orderItem)));
        } catch (ResponseError error) {
            Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, error.getStatus());
            Assert.assertEquals("Não é possível realizar o pedido pois um mais produtos informados não possui a quantidade suficiente em estoque.", error.getMessage());
        }
    }
}

package br.com.zambotti.order.controller;

import br.com.zambotti.order.dto.ProductDTO;
import br.com.zambotti.order.dto.OrderRequest;
import br.com.zambotti.order.dto.OrderRequestUpdate;
import br.com.zambotti.order.dto.OrderResponse;
import br.com.zambotti.order.model.Order;
import br.com.zambotti.order.model.OrderItem;
import br.com.zambotti.order.model.OrderItemPK;
import br.com.zambotti.order.model.enums.OrderStatus;
import br.com.zambotti.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/microservices/v1/order")
@Api(value = "Gerenciamento de Pedidos")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService){
        this.orderService = orderService;
    };

    @ApiOperation(value = "Consultar todos os pedidos")
    @GetMapping(produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<OrderResponse>> getOrders(){
        LOGGER.info("Getting orders ... ");
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponse> orderResponse = new ArrayList<>();
        orders.forEach(order -> orderResponse.add(
                getOrderResponse(order)));

        return ResponseEntity.ok(orderResponse);
    }

    @ApiOperation(value = "Consultar um pedido específicos")
    @GetMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("id") Integer id){
        LOGGER.info("Getting a order ... ");
        Order orderResponse = orderService.getOrderById(id);

        return ResponseEntity.ok(getOrderResponse(orderResponse));
    }

    @ApiOperation(value = "Excluir um pedido")
    @DeleteMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Integer id){
        LOGGER.info("Deleting a order ... ");
        orderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Alterar um pedido")
    @PutMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateOrder(@PathVariable("id") Integer id,
                                              @Valid @RequestBody OrderRequestUpdate orderRequest){
        LOGGER.info("Updating a order ... ");
        orderService.updateOrder(id,
                orderRequest.getCustomerId(),
                OrderStatus.toEnum(orderRequest.getStatus()),
                toOrderItem(id, orderRequest.getItems()), Boolean.TRUE);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Realizar um pedido")
    @PostMapping(produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest){
        LOGGER.info("Creating a order ... ");
        Order order = orderService.createOrder(orderRequest.getCustomerId());

        orderService.validateInventoryToCreate(toOrderItem(order.getId(), orderRequest.getItems()));

        Set<OrderItem> orderItems = toOrderItem(order.getId(), orderRequest.getItems());

        orderService.updateOrder(order.getId(), order.getCustomerId(), order.getStatus(), orderItems, Boolean.FALSE);

        order.setItems(orderItems);

        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/microservices/v1/order", order.getId())))
                .body(getOrderResponse(order));
    }

    private List<ProductDTO> toProductDTO(Set<OrderItem> itemsModel) {
        List<ProductDTO> productsDTO = new ArrayList<>();
        itemsModel.forEach(products -> productsDTO.add(
                new ProductDTO(products.getId().getProductId(), products.getQuantity())));
        return productsDTO;
    }

    private Set<OrderItem> toOrderItem(Integer id, List<ProductDTO> products){
        Set<OrderItem> orderItem = new HashSet<>();

        products.forEach(product -> orderItem.add(new OrderItem(
                new OrderItemPK(id, product.getProductId()),
                product.getQuantity())));
        return orderItem;
    }

    private OrderResponse getOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getStatus().getDescription(),
                toProductDTO(order.getItems()));
    }
}
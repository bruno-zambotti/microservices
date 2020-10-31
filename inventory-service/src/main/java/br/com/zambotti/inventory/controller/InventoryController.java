package br.com.zambotti.inventory.controller;

import br.com.zambotti.inventory.dto.InventoryRequest;
import br.com.zambotti.inventory.dto.InventoryResponse;
import br.com.zambotti.inventory.dto.InventoryUpdateRequest;
import br.com.zambotti.inventory.model.Inventory;
import br.com.zambotti.inventory.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/microservices/v1/inventory")
@Api(value = "Gerenciamento de Estoque")
public class InventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(final InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @ApiOperation(value = "Consultar o estoque")
    @GetMapping(produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<InventoryResponse>> getInventory(){
        LOGGER.info("Getting the all inventory items ... ");
        List<InventoryResponse> inventoryResponse = new ArrayList<>();
        inventoryService.getInventory().forEach(inventoryItem -> inventoryResponse.add(
                new InventoryResponse(inventoryItem.getProduct().getId(), inventoryItem.getAmount())));

        return ResponseEntity.ok(inventoryResponse);
    }

    @ApiOperation(value = "Consultar um produto do estoque")
    @GetMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<InventoryResponse> getInventoryByProductId(@PathVariable("id") Integer id){
        LOGGER.info("Getting the inventory for a specific product ... ");
        Inventory inventory = inventoryService.getInventoryItemByProductId(id);

        return ResponseEntity.ok(new InventoryResponse(
                inventory.getProduct().getId(), inventory.getAmount()));
    }

    @ApiOperation(value = "Excluir um produto do estoque")
    @DeleteMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteInventoryByProductId(@PathVariable("id") Integer id){
        LOGGER.info("Deleting the inventory for a specific product ... ");
        Inventory inventory = inventoryService.getInventoryItemByProductId(id);
        inventoryService.deleteInventoryItem(inventory.getInventoryId());

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Alterar um produto do estoque")
    @PutMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateInventoryByProductId(@PathVariable("id") Integer id,
                                                        @Valid @RequestBody InventoryUpdateRequest inventoryUpdateRequest){
        LOGGER.info("Updating the inventory for a specific product ... ");
        Inventory inventory = inventoryService.getInventoryItemByProductId(id);
        inventory.setAmount(inventoryUpdateRequest.getAmount());
        inventoryService.updateInventoryItem(inventory);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Adicionar um produto ao estoque")
    @PostMapping(produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> createInventory(@Valid @RequestBody InventoryRequest inventoryRequest){
        LOGGER.info("Creating the inventory for a specific product ... ");
        inventoryService.createInventoryItem(inventoryRequest.getProductId(),
                new Inventory(inventoryRequest.getAmount()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
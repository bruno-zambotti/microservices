package br.com.zambotti.inventory.service.impl;

import br.com.zambotti.inventory.advice.ResponseError;
import br.com.zambotti.inventory.model.Inventory;
import br.com.zambotti.inventory.model.Product;
import br.com.zambotti.inventory.repository.InventoryRepository;
import br.com.zambotti.inventory.repository.ProductRepository;
import br.com.zambotti.inventory.service.InventoryService;
import br.com.zambotti.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class InventoryServiceImpl implements InventoryService {
	@Autowired
	private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

	@Autowired
	private ProductService productsService;

	@Override
	public List<Inventory> getInventory() {
		return StreamSupport.stream(inventoryRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public Inventory getInventoryItemByProductId(Integer id) {
		return inventoryRepository.findByProductId(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado no estoque"));
	}

	@Override
	public Inventory createInventoryItem(Integer productId, Inventory inventory) {
		Product product = productsService.getProductById(productId);
        product.setInventory(inventory);
        Inventory savedInventory = inventoryRepository.save(inventory);
        savedInventory.setProduct(productRepository.save(product));

		return savedInventory;
	}

	@Override
	public void updateInventoryItem(Inventory inventory) {
		Inventory inventoryCustomer = inventoryRepository.findById(inventory.getInventoryId())
				.orElseThrow(() -> new ResponseError(HttpStatus.NOT_FOUND, "Inventário não encontrado"));

		inventoryCustomer.setAmount(inventory.getAmount());

		inventoryRepository.save(inventoryCustomer);
	}

	@Override
	public void deleteInventoryItem(Integer id) {
		inventoryRepository.delete(inventoryRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Item do estoque não encontrado")));
	}
}

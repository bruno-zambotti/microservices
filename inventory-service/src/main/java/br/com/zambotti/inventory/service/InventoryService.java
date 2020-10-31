package br.com.zambotti.inventory.service;

import br.com.zambotti.inventory.model.Inventory;

import java.util.List;

public interface InventoryService {
	List<Inventory> getInventory();
	Inventory getInventoryItemByProductId(Integer id);
	Inventory createInventoryItem(Integer productId, Inventory inventory);
	void updateInventoryItem(Inventory inventory);
	void deleteInventoryItem(Integer id);
}

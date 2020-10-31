package br.com.zambotti.order.service;

import br.com.zambotti.order.model.Inventory;

import java.util.List;

public interface InventoryService {
	List<Inventory> getInventory();
	Inventory getInventoryItemByProductId(Integer id);
	void updateInventory(Inventory inventory);
}

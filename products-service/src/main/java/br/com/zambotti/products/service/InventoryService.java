package br.com.zambotti.products.service;

import br.com.zambotti.products.model.Inventory;

public interface InventoryService {
	Inventory getInventoryItemByProductId(Integer id);
}

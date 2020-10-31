package br.com.zambotti.order.service.impl;

import br.com.zambotti.order.model.Inventory;
import br.com.zambotti.order.model.InventoryModel;
import br.com.zambotti.order.model.InventoryUpdate;
import br.com.zambotti.order.model.Product;
import br.com.zambotti.order.service.InventoryService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Value("${inventory.service.endpoint}")
	private String inventoryServiceEndpoint;

	@Override
	public List<Inventory> getInventory() {
		RestTemplate restTemplate = new RestTemplate();
		InventoryModel[] inventories = restTemplate.getForObject(inventoryServiceEndpoint, InventoryModel[].class);

		if (inventories == null || inventories.length == 0) {
			return new ArrayList<>();
		}

		List<Inventory> inventory = new ArrayList<>();
        for (InventoryModel inventoryModel : inventories) {
            inventory.add(new Inventory(inventoryModel.getAmount(), new Product(inventoryModel.getProductId())));
        }

		return inventory;
	}

	@Override
	public Inventory getInventoryItemByProductId(Integer id) {
		RestTemplate restTemplate = new RestTemplate();

		String url = String.format("%s/%s", inventoryServiceEndpoint, id);

		return restTemplate.getForObject(url, Inventory.class);
	}

	@Override
	public void updateInventory(Inventory inventory) {
		RestTemplate restTemplate = new RestTemplate();

		String url = String.format("%s/%s", inventoryServiceEndpoint, inventory.getProduct().getId());
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<InventoryUpdate> request = new HttpEntity<>(new InventoryUpdate(inventory.getAmount()), headers);

		restTemplate.exchange(url, HttpMethod.PUT, request, Object.class);
	}
}

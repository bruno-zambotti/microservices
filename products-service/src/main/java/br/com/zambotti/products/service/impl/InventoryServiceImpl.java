package br.com.zambotti.products.service.impl;

import br.com.zambotti.products.model.Inventory;
import br.com.zambotti.products.service.InventoryService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
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
	public Inventory getInventoryItemByProductId(Integer id) {
		RestTemplate restTemplate = new RestTemplate();

		String url = String.format("%s/%s", inventoryServiceEndpoint, id);

		return restTemplate.getForObject(url, Inventory.class);
	}
}

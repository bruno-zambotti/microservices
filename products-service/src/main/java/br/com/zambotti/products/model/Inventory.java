package br.com.zambotti.products.model;

public class Inventory {

	private Integer id;

	private Integer amount;

	private Product product;

	public Inventory() {
	}

	public Inventory(Integer amount) {
		this.amount = amount;
	}

	public Inventory(Integer amount, Product product) {
		this.amount = amount;
		this.product = product;
	}

	public Integer getInventoryId() {
		return id;
	}

	public void setInventoryId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}

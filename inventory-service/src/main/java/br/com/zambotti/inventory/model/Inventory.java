package br.com.zambotti.inventory.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Inventory {

	@Id
	@GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(initialValue = 1, name = "generator", sequenceName = "inventory_sequence")
	@Column
	private Integer id;

	private Integer amount;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "inventory", cascade = CascadeType.ALL)
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

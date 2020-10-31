package br.com.zambotti.products.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Product {

	@Id
	@GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(initialValue = 1, name = "generator", sequenceName = "product_sequence")
	@Column
	private Integer id;

	private String name;
	private String description;
	private Double price;

	public Product(String name, String description, Double price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public Product(Integer id, String name, String description, Double price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public Product() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() { return price; }

	public void setPrice(Double price) { this.price = price; }
}

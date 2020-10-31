package br.com.zambotti.order.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItem {

	@EmbeddedId
	private OrderItemPK id = new OrderItemPK();

	private Integer quantity;

	public OrderItem() {
	}

	public OrderItem(Integer quantity) {
		this.quantity = quantity;
	}

	public OrderItem(OrderItemPK id, Integer quantity) {
		this.id = id;
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public OrderItemPK getId() {
		return id;
	}

	public void setId(OrderItemPK id) {
		this.id = id;
	}
}

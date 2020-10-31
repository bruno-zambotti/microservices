package br.com.zambotti.order.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.zambotti.order.model.enums.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(generator = "order_generator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(initialValue = 1, name = "order_generator", sequenceName = "order_sequence")
	private Integer id;

	private Integer customerId;

	private Date createdDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@OneToMany(mappedBy = "id.id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<OrderItem> items;

	public Order(Integer customerId, Date createdDate, OrderStatus status) {
		this.customerId = customerId;
		this.createdDate = createdDate;
		this.status = status;
	}

	public Order(Integer customerId, Date createdDate, OrderStatus status, Set<OrderItem> items) {
		this.customerId = customerId;
		this.createdDate = createdDate;
		this.status = status;
		this.items = items;
	}

	public Order(Integer id, Integer customerId, Date createdDate, OrderStatus status, Set<OrderItem> items) {
		this.id = id;
		this.customerId = customerId;
		this.createdDate = createdDate;
		this.status = status;
		this.items = items;
	}

	public Order() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Set<OrderItem> getItems() {
		return items;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}

}

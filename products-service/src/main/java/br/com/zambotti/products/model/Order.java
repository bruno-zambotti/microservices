package br.com.zambotti.products.model;

import java.util.Set;

public class Order {

    private Integer id;
    private Integer customerId;
    private String status;
    private Set<OrderItem> items;

    public Order() {
    }

    public Order(Integer id, Integer customerId, String status, Set<OrderItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.items = items;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }
}
package br.com.zambotti.order.dto;

import java.util.List;

public class OrderResponse {
    private Integer id;
    private Integer customerId;
    private String status;
    private List<ProductDTO> items;

    public OrderResponse(Integer id, Integer customerId, String status, List<ProductDTO> items) {
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

    public List<ProductDTO> getItems() {
        return items;
    }

    public void setItems(List<ProductDTO> items) {
        this.items = items;
    }
}

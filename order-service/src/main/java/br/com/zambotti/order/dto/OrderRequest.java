package br.com.zambotti.order.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {
    @NotNull
    private Integer customerId;

    @NotNull
    private List<ProductDTO> items;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<ProductDTO> getItems() {
        return items;
    }
    public void setItems(List<ProductDTO> items) { this.items = items; }
}
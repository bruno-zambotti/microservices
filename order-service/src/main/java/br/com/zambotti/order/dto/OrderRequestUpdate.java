package br.com.zambotti.order.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequestUpdate {
    @NotNull
    private Integer customerId;

    @NotNull
    private Integer status;

    @NotNull
    private List<ProductDTO> items;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public List<ProductDTO> getItems() {
        return items;
    }

    public void setItems(List<ProductDTO> items) {
        this.items = items;
    }
}
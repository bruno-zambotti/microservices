package br.com.zambotti.inventory.dto;

import javax.validation.constraints.NotNull;

public class InventoryRequest {

    @NotNull
    private Integer productId;

    @NotNull
    private Integer amount;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

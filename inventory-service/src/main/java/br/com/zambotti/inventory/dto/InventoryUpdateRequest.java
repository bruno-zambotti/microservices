package br.com.zambotti.inventory.dto;

import javax.validation.constraints.NotNull;

public class InventoryUpdateRequest {
    @NotNull
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

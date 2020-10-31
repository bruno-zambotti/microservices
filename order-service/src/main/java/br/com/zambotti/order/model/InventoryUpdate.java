package br.com.zambotti.order.model;

public class InventoryUpdate {
    private Integer amount;

    public InventoryUpdate() {
    }

    public InventoryUpdate(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

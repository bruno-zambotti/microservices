package br.com.zambotti.order.model;

public class InventoryModel {
    private Integer productId;
    private Integer amount;

    public InventoryModel(){

    }

    public InventoryModel(Integer productId, Integer amount) {
        this.productId = productId;
        this.amount = amount;
    }

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

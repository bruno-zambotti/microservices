package br.com.zambotti.products.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductRequest {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}

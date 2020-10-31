package br.com.zambotti.order.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderItemPK implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer productId;

    public OrderItemPK() {
    }

    public OrderItemPK(Integer id, Integer productId) {
        this.id = id;
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}

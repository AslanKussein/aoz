package kz.aoz.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created by amanzhol-ak on 11.12.2016.
 */
@Entity
@Table(name = "ORDER_DETAIL")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "OrderDetail.findAll", query = "SELECT u FROM OrderDetail u order by u.id desc"),
        @NamedQuery(name = "OrderDetail.countAll", query = "SELECT count(u) FROM OrderDetail u"),
        @NamedQuery(name = "OrderDetail.findById", query = "SELECT u FROM OrderDetail u WHERE u.id = :id")
})
public class OrderDetail {

    @Id
    @NotNull
    @Column(name = "ID")
    private String id;

    @NotNull
    @Column(name = "order_id")
    private String orderId;

    @NotNull
    @Column(name = "PRODUCT_ID")
    private String productId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

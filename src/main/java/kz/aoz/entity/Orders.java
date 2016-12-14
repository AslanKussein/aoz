package kz.aoz.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created by amanzhol-ak on 11.12.2016.
 */
@Entity
@Table(name = "ORDERS")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Orders.findAll", query = "SELECT u FROM Orders u order by u.id desc"),
        @NamedQuery(name = "Orders.countAll", query = "SELECT count(u) FROM Orders u"),
        @NamedQuery(name = "Orders.findById", query = "SELECT u FROM Orders u WHERE u.id = :id")
})
public class Orders {

    @Id
    @NotNull
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "BEG_DATE")
    private Timestamp begDate;

    @NotNull
    @Column(name = "END_DATE")
    private Timestamp endDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getBegDate() {
        return begDate;
    }

    public void setBegDate(Timestamp begDate) {
        this.begDate = begDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
}

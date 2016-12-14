/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author kusein-at
 */
@Entity
@Table(name = "parse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parse.findAll", query = "SELECT p FROM Parse p")
    , @NamedQuery(name = "Parse.findById", query = "SELECT p FROM Parse p WHERE p.id = :id")
    , @NamedQuery(name = "Parse.findByProductsId", query = "SELECT p FROM Parse p WHERE p.productsId = :productsId")
})
public class Parse implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "PR_PRICE")
    private Double prPrice;
    @JoinColumn(name = "PRODUCTS_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Products productsId;
    @JoinColumn(name = "UNIT_CODE", referencedColumnName = "CODE")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unit unitCode;

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "id")
    private String id;
    @JoinColumn(name = "PROVIDERS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Providers providersId;
    @Column(name = "CUR_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentDt;

    public Parse() {
    }

    public Parse(String id) {
        this.id = id;
    }

    public Double getPrPrice() {
        return prPrice;
    }

    public void setPrPrice(Double prPrice) {
        this.prPrice = prPrice;
    }

    public Products getProductsId() {
        return productsId;
    }

    public void setProductsId(Products productsId) {
        this.productsId = productsId;
    }

    public Unit getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(Unit unitCode) {
        this.unitCode = unitCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Providers getProvidersId() {
        return providersId;
    }

    public void setProvidersId(Providers providersId) {
        this.providersId = providersId;
    }

    public Date getCurrentDt() {
        return currentDt;
    }

    public void setCurrentDt(Date currentDt) {
        this.currentDt = currentDt;
    }
}

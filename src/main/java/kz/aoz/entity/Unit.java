/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by kusein-at on 17.11.2016.
 */
@Entity
@Table(name = "UNIT")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Unit.findAll", query = "SELECT u FROM Unit u"),
        @NamedQuery(name = "Unit.findById", query = "SELECT u FROM Unit u WHERE u.id = :id"),
        @NamedQuery(name = "Unit.findByName", query = "SELECT u FROM Unit u WHERE u.name = :name")})
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODE")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "unitCode", fetch = FetchType.LAZY)
    private Collection<Parse> parseCollection;
    @OneToMany(mappedBy = "unitId", fetch = FetchType.LAZY)
    private Collection<Products> productsCollection;

    public Unit() {
    }

    public Unit(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Parse> getParseCollection() {
        return parseCollection;
    }

    public void setParseCollection(Collection<Parse> parseCollection) {
        this.parseCollection = parseCollection;
    }

    public Collection<Products> getProductsCollection() {
        return productsCollection;
    }

    public void setProductsCollection(Collection<Products> productsCollection) {
        this.productsCollection = productsCollection;
    }
}

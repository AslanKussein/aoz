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
 * @author kusein-at
 */
@Entity
@Table(name = "products")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p")
        , @NamedQuery(name = "Products.findById", query = "SELECT p FROM Products p WHERE p.id = :id")
        , @NamedQuery(name = "Products.findByCode", query = "SELECT p FROM Products p WHERE p.code like :code")
        , @NamedQuery(name = "Products.findByName", query = "SELECT p FROM Products p WHERE p.name = :name")
        , @NamedQuery(name = "Products.findByUnitId", query = "SELECT p FROM Products p WHERE p.unitId = :unitId")})
public class Products implements Serializable {

    @JoinColumn(name = "UNIT_ID", referencedColumnName = "CODE")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unit unitId;

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(min = 1, max = 222)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "CODE")
    private String code;
    @Size(max = 12)
    @Column(name = "PARENT_CODE")
    private String parentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 222)
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "productsId", fetch = FetchType.LAZY)
    private Collection<Parse> parseCollection;

    public Products() {
    }

    public Products(String id) {
        this.id = id;
    }

    public Products(String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kz.aoz.entity.Products[ id=" + id + " ]";
    }

    public Unit getUnitId() {
        return unitId;
    }

    public void setUnitId(Unit unitId) {
        this.unitId = unitId;
    }
}

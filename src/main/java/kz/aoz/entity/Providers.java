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
 *
 * @author kusein-at
 */
@Entity
@Table(name = "providers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Providers.findAll", query = "SELECT p FROM Providers p")
    , @NamedQuery(name = "Providers.findById", query = "SELECT p FROM Providers p WHERE p.id = :id")
    , @NamedQuery(name = "Providers.findByName", query = "SELECT p FROM Providers p WHERE p.name = :name")})
public class Providers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NAME")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "providersId", fetch = FetchType.LAZY)
    private Collection<Parse> parseCollection;

    public Providers() {
    }

    public Providers(Long id) {
        this.id = id;
    }

    public Providers(String name) {
        this.name = name;
    }

    public Providers(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Providers)) {
            return false;
        }
        Providers other = (Providers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kz.aoz.entity.Providers[ id=" + id + " ]";
    }
    
}

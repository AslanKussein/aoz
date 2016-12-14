package kz.aoz.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by amanzhol-ak on 01.09.2016.
 */
@Entity
@Table(name = "EMAIL_DETAIL")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "EmailDetail.findAll", query = "SELECT d FROM EmailDetail d"),
        @NamedQuery(name = "EmailDetail.findById", query = "SELECT d FROM EmailDetail d WHERE d.id = :id"),
        @NamedQuery(name = "EmailDetail.findByType", query = "SELECT d FROM EmailDetail d WHERE d.type = :type")
})
public class EmailDetail {

    @Id
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ID")
    private String id ;

    @Size(min = 1, max = 255)
    @Column(name = "USERNAME")
    private String username ;

    @Size(min = 1, max = 255)
    @Column(name = "PASSWORD")
    private String password ;

    @Size(min = 1, max = 255)
    @Column(name = "HOST")
    private String host ;

    @Size(min = 1, max = 20)
    @Column(name = "TYPE")
    private String type ;

    @Column(name = "PORT")
    private Long port ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPort() {
        return port;
    }

    public void setPort(Long port) {
        this.port = port;
    }
}

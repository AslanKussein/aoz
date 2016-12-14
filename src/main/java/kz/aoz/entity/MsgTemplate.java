package kz.aoz.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by amanzhol-ak on 01.09.2016.
 */
@Entity
@Table(name = "MSG_TEMPLATE")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "MsgTemplate.findAll", query = "SELECT d FROM MsgTemplate d"),
        @NamedQuery(name = "MsgTemplate.findById", query = "SELECT d FROM MsgTemplate d WHERE d.id = :id"),
        @NamedQuery(name = "MsgTemplate.findByCode", query = "SELECT d FROM MsgTemplate d WHERE d.code = :code")
})
public class MsgTemplate {

    @Id
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ID")
    private String id ;

    @Size(min = 1, max = 1024)
    @Column(name = "TEMPLATE")
    private String template ;

    @Size(min = 1, max = 255)
    @Column(name = "TITLE")
    private String title ;


    @Column(name = "CODE")
    private Long code ;

    public MsgTemplate() {

    }

    public MsgTemplate(String id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

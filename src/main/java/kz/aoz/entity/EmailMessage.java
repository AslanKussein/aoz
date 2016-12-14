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
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author a.amanzhol
 */
@Entity
@Table(name = "EMAIL_MESSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmailMessage.findAll", query = "SELECT d FROM EmailMessage d"),
    @NamedQuery(name = "EmailMessage.findById", query = "SELECT d FROM EmailMessage d WHERE d.id = :id")})
public class EmailMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ID")
    private String id;
    @Column(name = "ID_TEMPLATE")
    private String idTemplate;
    @JoinColumn(name = "U_NAME", referencedColumnName = "U_NAME")
    private Users uName;
    @Column(name = "MSG_DATE")
    private Timestamp msgDate;
    @Column(name = "STATE")
    private Integer state;
    @Size(min = 1, max = 500)
    @Column(name = "DESCRIPTION")
    private String description;

    public EmailMessage() {
    }

    public EmailMessage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getuName() {
        return uName;
    }

    public void setuName(Users uName) {
        this.uName = uName;
    }

    public Timestamp getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(Timestamp msgDate) {
        this.msgDate = msgDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }
}

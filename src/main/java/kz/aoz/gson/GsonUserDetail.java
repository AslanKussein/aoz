package kz.aoz.gson;

import java.util.List;

public class GsonUserDetail {
    private String id;
    private String uName;
    private String firstname;
    private String lastname;
    private String middlename;
    private Integer locked;
    private List<GsonGroupmembers> roles;

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
        this.id = uName;
    }

    public List<GsonGroupmembers> getRoles() {
        return roles;
    }

    public void setRoles(List<GsonGroupmembers> roles) {
        this.roles = roles;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }
}
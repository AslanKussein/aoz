/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.gson;

import kz.aoz.entity.Groupmembers;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by a.amanzhol on 20.11.2016.
 */
public class GsonGroups implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String gName;
    private String gDescription;

    public GsonGroups() {

    }

    public GsonGroups(String gName) {
        this.gName = gName;
    }

    public String getGName() {
        return gName;
    }

    public void setGName(String gName) {
        this.gName = gName;
        this.id = gName;
    }

    public String getGDescription() {
        return gDescription;
    }

    public void setGDescription(String gDescription) {
        this.gDescription = gDescription;
    }

}
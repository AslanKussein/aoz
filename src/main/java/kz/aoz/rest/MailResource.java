/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.rest;

import kz.aoz.gson.GsonEmail;
import kz.aoz.gson.GsonEmailDetail;
import kz.aoz.gson.GsonMsgTemplate;
import kz.aoz.session.MailSession;
import kz.aoz.wrapper.Serialization;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;

import static kz.aoz.util.Util.objectToJson;
import static kz.aoz.wrapper.Serialization.wrapToGsonEmailDetailByJsonString;
import static kz.aoz.wrapper.Serialization.wrapToGsonMsgTemplateByJsonString;


/**
 * REST Web Service
 *
 * @author a.amanzhol
 */

@Stateless
@Path("mail")
public class MailResource {

    @EJB
    MailSession mailSession;

    @GET
    @Produces("application/json")
    @Path("sendMail")
    public String sendMail() {
        mailSession.sendMessage("admin","dc6b72e9-4875-4979-ad82-56e0c07d6f30");
        return "true";
    }

    @POST
    @Produces("application/json")
    @Path("saveAttrs")
    public String saveAttrs(String json) {
        GsonEmailDetail emailDetail = wrapToGsonEmailDetailByJsonString(json);
        return objectToJson(mailSession.saveAttrs(emailDetail));
    }

    @POST
    @Produces("application/json")
    @Path("testMailSubmit")
    public String testMailSubmit(String json) {
        GsonEmail gson = Serialization.wrapToGsonEmailByJsonString(json);
        return objectToJson(mailSession.testMailSubmit(gson));
    }

    @POST
    @Produces("application/json")
    @Path("saveTemplate")
    public String saveTemplate(String json) {
        GsonMsgTemplate gson = wrapToGsonMsgTemplateByJsonString(json);
        return objectToJson(mailSession.saveTemplate(gson));
    }

    @GET
    @Produces("application/json")
    @Path("getContent")
    public String getContent() {
        return objectToJson(mailSession.getContent());
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.rest;

import kz.aoz.gson.GsonDics;
import kz.aoz.gson.GsonImportTov;
import kz.aoz.session.DictionarySession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

import static kz.aoz.util.Util.isNullOrEmpty;
import static kz.aoz.util.Util.objectToJson;
import static kz.aoz.wrapper.Serialization.wrapToGsonDicsByJsonString;
import static kz.aoz.wrapper.Serialization.wrapToGsonImportTovByJsonString;

/**
 * REST Web Service
 *
 * @author a.kussein
 */
@Stateless
@Path("dic")
public class DictionaryResource {

    @Context
    private HttpServletRequest request;

    @EJB
    DictionarySession dictionarySession;

    @GET
    @Produces("application/json")
    public String load(@QueryParam("name") String dicName) {
        if (!isNullOrEmpty(dicName)) {
            switch (dicName) {
                case "productsList":
                    return objectToJson(dictionarySession.getProductsList());
                case "unit":
                    return objectToJson(dictionarySession.getUnitList());
                case "providers":
                    return objectToJson(dictionarySession.getProviderList());
                default:
                    break;
            }
        }
        throw new WebApplicationException(400);
    }

    @POST
    @Produces("application/json")
    @Path("saveProducts")
    public String saveProducts(String json) {
        GsonImportTov gson = wrapToGsonImportTovByJsonString(json);
        return objectToJson(dictionarySession.saveProducts(gson));
    }

    @POST
    @Produces("application/json")
    @Path("removeProducts")
    public String removeProducts(String ids) {
        return objectToJson(dictionarySession.removeProducts(ids));
    }

    @POST
    @Produces("application/json")
    @Path("saveUnit")
    public String saveUnit(String json) {
        GsonDics gson = wrapToGsonDicsByJsonString(json);
        return objectToJson(dictionarySession.saveUnit(gson));
    }

    @GET
    @Produces("application/json")
    @Path("removeUnit")
    public String removeUnit(@QueryParam("id") String id) {
        return objectToJson(dictionarySession.removeUnits(id));
    }

    @POST
    @Produces("application/json")
    @Path("saveProviders")
    public String saveProviders(String json) {
        GsonDics gson = wrapToGsonDicsByJsonString(json);
        return objectToJson(dictionarySession.saveProviders(gson));
    }

    @GET
    @Produces("application/json")
    @Path("removeProviders")
    public String removeProviders(@QueryParam("id") Long id) {
        return objectToJson(dictionarySession.removeProviders(id));
    }
}

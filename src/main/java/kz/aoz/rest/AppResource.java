/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.rest;


import kz.aoz.session.AppSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.math.BigDecimal;

import static kz.aoz.util.Util.objectToJson;

/*
* REST Web Service
*
* @author a.amanzhol
*/
@Stateless
@Path("app")
public class AppResource {

    @Context
    private HttpServletRequest request;

    @Context
    SecurityContext sc;

    private String getUserName() {
        return sc.getUserPrincipal().getName();
    }

    @EJB
    AppSession appSession;

    @GET
    @Produces("application/json")
    @Path("beforeCreateApp")
    public String beforeCreateApp(@QueryParam("id") BigDecimal id, @QueryParam("personid") BigDecimal sicid) {
        return null;
    }

    @GET
    @Produces("application/json")
    @Path("getImportList")
    public String getProductsList(@QueryParam("start") int start,
                                  @QueryParam("count") int count,
                                  @QueryParam("provider") String provider,
                                  @QueryParam("impDate") String impDate,
                                  @QueryParam("srcText") String src) {
        return objectToJson(appSession.getImportList(getUserName(), start, count, provider, impDate, src));
    }

    @GET
    @Produces("application/json")
    @Path("getMainImport")
    public String getMainImport() {
        return objectToJson(appSession.getMainImport());
    }
}
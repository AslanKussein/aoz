/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.rest;


import kz.aoz.session.KitchenSession;

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
@Path("kitchen")
public class KitchenResource {

    @Context
    private HttpServletRequest request;

    @Context
    SecurityContext sc;

    private String getUserName() {
        return sc.getUserPrincipal().getName();
    }

    @EJB
    KitchenSession kitchenSession;

    @GET
    @Produces("application/json")
    @Path("getKitchenTreeData")
    public String getKitchenTreeData(@QueryParam("id") String id) {
        return objectToJson(kitchenSession.getKitchenTreeData(id));
    }

    @GET
    @Produces("application/json")
    @Path("getOrdersList")
    public String getOrdersList(@QueryParam("start") int start,
                                 @QueryParam("count") int count,
                                 @QueryParam("srcText") String src) {
        return objectToJson(kitchenSession.getOrdersList(  start, count, src));
    }
    @GET
    @Produces("application/json")
    @Path("getOrderListById")
    public String getOrderListById(@QueryParam("start") int start,
                                 @QueryParam("count") int count,
                                 @QueryParam("id") Integer id) {
        return objectToJson(kitchenSession.getOrderListById(start, count, id));
    }
}
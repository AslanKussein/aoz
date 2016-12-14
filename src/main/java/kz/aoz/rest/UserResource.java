/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.rest;


import kz.aoz.session.UserSession;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import java.math.BigDecimal;

import static kz.aoz.util.Util.objectToJson;

/*
* REST Web Service
*
* @author a.amanzhol
*/
@Stateless
@Path("user")
public class UserResource {

    @Context
    private HttpServletRequest request;


    @EJB
    UserSession userSession;

    @Context
    SecurityContext sc;

    @Resource
    private javax.transaction.UserTransaction utx;

    private String getUserName() {
        return sc.getUserPrincipal().getName();
    }

    @GET
    @Produces("application/json")
    @Path("getUsersDetailList")
    public String getUsersDetailList(@QueryParam("start") Integer start, @QueryParam("limit") Integer limit) {
        return objectToJson(userSession.getUsersDetailList(start, limit));
    }

    @GET
    @Produces("application/json")
    @Path("getGroupMembersByUName")
    public String getGroupMembersByUName(@QueryParam("uName") String uName) {
        return objectToJson(userSession.getGroupMembersByUName(uName));
    }


    @POST
    @Produces("application/json")
    @Path("editUser")
    @Consumes("application/x-www-form-urlencoded")
    public String editUser(MultivaluedMap<String, String> formParams) {
        return objectToJson(userSession.editUser(formParams));
    }

}
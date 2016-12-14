/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.rest;


import kz.aoz.session.AdminSession;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;

import static kz.aoz.util.Util.getGsonResult;
import static kz.aoz.util.Util.objectToJson;

/*
* REST Web Service
*
* @author a.amanzhol
*/
@Stateless
@Path("admin")
public class AdminResource {

    @Context
    private HttpServletRequest request;


    @EJB
    AdminSession adminSession;

    @Context
    SecurityContext sc;

    @Resource
    private javax.transaction.UserTransaction utx;

    private String getUserName() {
        return sc.getUserPrincipal().getName();
    }

    @GET
    @Produces("application/json")
    @Path("getGroups")
    public String getGroups() {
        return objectToJson(adminSession.getGroups());
    }

    @POST
    @Produces("application/json")
    @Path("lockUser")
    @Consumes("application/x-www-form-urlencoded")
    public String lockUser(MultivaluedMap<String, String> formParams) {
        if (!sc.isUserInRole("admin_role"))
            return objectToJson(getGsonResult(false, "У вас не достаточно прав"));
        String uName = formParams.getFirst("uName");
        Integer lock = Integer.parseInt(formParams.getFirst("lock"));
        return objectToJson(adminSession.lockUser(uName, lock));
    }

    @POST
    @Produces("application/json")
    @Path("createUser")
    @Consumes("application/x-www-form-urlencoded")
    public String createUser(MultivaluedMap<String, String> formParams) {
        return objectToJson(adminSession.createUser(formParams.getFirst("uName"), formParams.getFirst("newPass"), formParams.getFirst("confirmPass"), formParams.getFirst("uDesc")));
    }

    @POST
    @Produces("application/json")
    @Path("resetPassword")
    @Consumes("application/x-www-form-urlencoded")
    public String resetPassword(MultivaluedMap<String, String> formParams) {
        return objectToJson(adminSession.resetPassword(formParams.getFirst("uName"), formParams.getFirst("oldPass"), formParams.getFirst("newPass"), formParams.getFirst("confirmPass")));
    }
}
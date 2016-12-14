/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.rest;

import com.sun.jersey.multipart.FormDataParam;
import kz.aoz.gson.GsonUsers;
import kz.aoz.session.ImportSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

import static kz.aoz.login.AuthServlet.USER;
import static kz.aoz.util.Util.objectToJson;

/**
 * REST Web Service
 *
 * @author a.kussein
 */
@Stateless
@Path("imp")
public class ImportResource {

    @Context
    private HttpServletRequest request;

    @EJB
    ImportSession importSession;

    private GsonUsers getUser() {
        HttpSession session = request.getSession(true);
        return (GsonUsers) session.getAttribute(USER);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    @Path("uploadFile")
    public String handleFileUpload(@FormDataParam("upload") InputStream ins) {
        return objectToJson(importSession.uploadFile(ins));
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    @Path("uploadFileTov")
    public String handleFileUploadTov(@FormDataParam("upload") InputStream ins) {
        return objectToJson(importSession.uploadFileTov(ins));
    }
}

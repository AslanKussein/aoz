/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.rest;

import javax.ws.rs.core.Application;
import java.util.Set;

/**
 *
 * @author a.amanzhol
 */
@javax.ws.rs.ApplicationPath("wr")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(kz.aoz.rest.AdminResource.class);
        resources.add(kz.aoz.rest.AppResource.class);
        resources.add(kz.aoz.rest.DictionaryResource.class);
        resources.add(kz.aoz.rest.ImportResource.class);
        resources.add(kz.aoz.rest.KitchenResource.class);
        resources.add(kz.aoz.rest.UserResource.class);
    }

}

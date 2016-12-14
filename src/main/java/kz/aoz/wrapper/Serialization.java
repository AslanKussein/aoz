/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.wrapper;

import com.google.gson.Gson;
import kz.aoz.gson.*;

/**
 *
 * @author a.amanzhol
 */
public class Serialization {

    public static GsonUserDetail wrapToGsonUserDetailByJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GsonUserDetail.class);
    }

    public static GsonImportTov wrapToGsonImportTovByJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GsonImportTov.class);
    }

    public static GsonDics wrapToGsonDicsByJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GsonDics.class);
    }

    public static GsonDicsId wrapToGsonDicsIdByJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GsonDicsId.class);
    }


    public static GsonEmailDetail wrapToGsonEmailDetailByJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GsonEmailDetail.class);
    }

    public static GsonEmail wrapToGsonEmailByJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GsonEmail.class);
    }

    public static GsonMsgTemplate wrapToGsonMsgTemplateByJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GsonMsgTemplate.class);
    }
}

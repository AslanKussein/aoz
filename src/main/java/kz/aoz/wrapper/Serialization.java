/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.wrapper;

import com.google.gson.Gson;
import kz.aoz.gson.GsonDics;
import kz.aoz.gson.GsonDicsId;
import kz.aoz.gson.GsonImportTov;
import kz.aoz.gson.GsonUserDetail;

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
}

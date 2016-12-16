package kz.aoz.wrapper;

import kz.aoz.entity.*;
import kz.aoz.gson.*;

import java.util.ArrayList;
import java.util.List;

import static kz.aoz.util.DateUtil.dateTimeToString;
import static kz.aoz.util.DateUtil.dateToString;

/**
 * Created by kusein-at on 17.11.2016.
 */
public class Wrapper {

    public static GsonUsers wrapToGsonUsers(Users user) {
        if (user != null) {
            GsonUsers gson = new GsonUsers();
            gson.setuName(user.getuName());
            gson.setUserDetail(wrapToGsonUserDetail(user.getUserDetail()));
            return gson;
        }
        return null;
    }

    public static List<GsonUserDetail> wrapToGsonUserDetailList(List<UserDetail> list) {
        List<GsonUserDetail> result = new ArrayList<>();
        for (UserDetail detail : list) {
            result.add(wrapToGsonUserDetail(detail));
        }
        return result;
    }

    private static GsonUserDetail wrapToGsonUserDetail(UserDetail user) {
        if (user != null) {
            GsonUserDetail gson = new GsonUserDetail();
            gson.setuName(user.getuName().getuName());
            gson.setFirstname(user.getFirstname());
            gson.setLastname(user.getLastname());
            gson.setMiddlename(user.getMiddlename());
            gson.setEmail(user.getEmail());
            gson.setLocked(user.getLocked());
            return gson;
        }
        return null;
    }

    public static UserDetail wrapToGsonUserDetail(GsonUserDetail gson) {
        if (gson != null) {
            UserDetail userDetail = new UserDetail();
            userDetail.setuName(new Users(gson.getuName()));
            userDetail.setFirstname(gson.getFirstname());
            userDetail.setLastname(gson.getLastname());
            userDetail.setMiddlename(gson.getMiddlename());
            userDetail.setEmail(gson.getEmail());
            userDetail.setLocked(gson.getLocked() == null ? 0 : gson.getLocked());
            return userDetail;
        }
        return null;
    }


    public static List<GsonGroupmembers> wrapToGsonGroupmembersList(List<Groupmembers> list) {
        List<GsonGroupmembers> result = new ArrayList<>();
        for (Groupmembers groupmembers : list) {
            result.add(wrapToGsonGroupmembers(groupmembers));
        }
        return result;
    }


    private static GsonGroupmembers wrapToGsonGroupmembers(Groupmembers g) {
        if (g != null) {
            GsonGroupmembers gson = new GsonGroupmembers();
            gson.setgMember(g.getGroupmembersPK().getGMember());
            gson.setgName(g.getGroupmembersPK().getGName());
            return gson;
        }
        return null;
    }


    public static List<GsonGroups> wrapToGsonGroupsList(List<Groups> list) {
        List<GsonGroups> result = new ArrayList<>();
        for (Groups group : list) {
            result.add(wrapToGsonGroup(group));
        }
        return result;
    }

    private static GsonGroups wrapToGsonGroup(Groups group) {
        if (group != null) {
            GsonGroups gson = new GsonGroups();
            gson.setGDescription(group.getGDescription());
            gson.setGName(group.getGName());
            return gson;
        }
        return null;
    }


    public static List<Groupmembers> wrapToGroupmembersList(List<GsonGroupmembers> gsonList, String uName) {
        List<Groupmembers> result = new ArrayList<>();
        for (GsonGroupmembers gson : gsonList) {
            result.add(wrapToGroupmembers(gson, uName));
        }
        return result;
    }


    private static Groupmembers wrapToGroupmembers(GsonGroupmembers g, String uName) {
        if (g != null) {
            Groupmembers gson = new Groupmembers();
            gson.setGroupmembersPK(new GroupmembersPK(g.getgName(), uName));
            return gson;
        }
        return null;
    }

    public static GsonImportTov wrapToGsonImportTov(Products obj) {
        if (obj != null) {
            GsonImportTov gson = new GsonImportTov();
            gson.setId(obj.getId());
            gson.setCode(obj.getCode());
            gson.setProductName(obj.getName());
            if (obj.getParentId() != null) {
                gson.setParentId(obj.getParentId());
            }
            if (obj.getUnitId() != null)
                gson.setUnitId(obj.getUnitId().getId());
            return gson;
        }
        return null;
    }


    public static List<GsonImportTov> wrapToGsonImportTovList(List<Products> list) {
        List<GsonImportTov> uList = new ArrayList<>();
        for (Products o : list) {
            uList.add(wrapToGsonImportTov(o));
        }
        return uList;
    }

    public static List<GsonProducts> wrapToGsonProductsList(List<Products> list) {
        List<GsonProducts> uList = new ArrayList<>();
        for (Products o : list) {
            uList.add(wrapToGsonProducts(o));
        }
        return uList;
    }



    public static GsonProducts wrapToGsonProducts(Products obj) {
        if (obj != null) {
            GsonProducts gson = new GsonProducts();
            gson.setId(obj.getId());
            gson.setCode(obj.getCode());
            gson.setName(obj.getName());
            if (obj.getParentId() != null) {
                gson.setParentId(obj.getParentId());
            }
            if (obj.getUnitId() != null)
                gson.setUnit(wrapToGsonUnit(obj.getUnitId()));
            return gson;
        }
        return null;
    }

    public static GsonUnit wrapToGsonUnit(Unit obj) {
        if (obj != null) {
            GsonUnit gson = new GsonUnit();
            gson.setId(obj.getId());
            gson.setName(obj.getName());

            return gson;
        }
        return null;
    }

    public static List<GsonUnit> wrapToGsonUnitList(List<Unit> list) {
        List<GsonUnit> uList = new ArrayList<>();
        for (Unit o : list) {
            uList.add(wrapToGsonUnit(o));
        }
        return uList;
    }

    public static GsonDicsId wrapToGsonProviders(Providers obj) {
        if (obj != null) {
            GsonDicsId gson = new GsonDicsId();
            gson.setId(obj.getId());
            gson.setName(obj.getName());
            return gson;
        }
        return null;
    }

    public static List<GsonDicsId> wrapToGsonProviderList(List<Providers> list) {
        List<GsonDicsId> uList = new ArrayList<>();
        for (Providers o : list) {
            uList.add(wrapToGsonProviders(o));
        }
        return uList;
    }

    public static GsonImport wrapToGsonParse(Parse obj) {
        if (obj != null) {
            GsonImport gson = new GsonImport();
            if (obj.getProductsId() != null) {
                gson.setId(obj.getProductsId().getCode());
                gson.setProductName(obj.getProductsId().getName());
            }
            if (obj.getUnitCode() != null) {
                gson.setUnit(obj.getUnitCode().getName());
            }
            gson.setPrice(obj.getPrPrice());
            gson.setImportDate(dateToString(obj.getCurrentDt()));
            gson.setCompanyName(obj.getProvidersId().getName());
            return gson;
        }
        return null;
    }

    public static List<GsonImport> wrapToGsonImportList(List<Parse> list) {
        List<GsonImport> uList = new ArrayList<>();
        for (Parse o : list) {
            uList.add(wrapToGsonParse(o));
        }
        return uList;
    }

    public static List<GsonOrders> wrapToGsonOrdersList(List<Orders> list) {
        List<GsonOrders> uList = new ArrayList<>();
        for (Orders o : list) {
            uList.add(wrapToGsonOrders(o));
        }
        return uList;
    }


    public static GsonOrders wrapToGsonOrders(Orders obj) {
        if (obj != null) {
            GsonOrders gson = new GsonOrders();
            gson.setId(obj.getId());
            gson.setBegDate(dateTimeToString(obj.getBegDate(), "dd.MM.yyyy HH.mm"));
            gson.setEndDate(dateTimeToString(obj.getEndDate(), "dd.MM.yyyy HH.mm"));

            return gson;
        }
        return null;
    }


    public static GsonMsgTemplate wrapToGsonMsgTemplate(MsgTemplate obj) {
        GsonMsgTemplate gson = new GsonMsgTemplate();
        if (obj != null) {
            gson.setId(obj.getId());
            gson.setCode(obj.getCode());
            gson.setTemplate(obj.getTemplate());
            gson.setTitle(obj.getTitle());
        }
        return gson;
    }

    public static MsgTemplate wrapToMsgTemplate(GsonMsgTemplate obj) {
        MsgTemplate template = new MsgTemplate();
        template.setId(obj.getId());
        template.setTitle(obj.getTitle());
        template.setTemplate(obj.getTemplate());
        template.setCode(obj.getCode());
        return template;
    }


    public static GsonEmailDetail wrapToGsonEmailDetail(EmailDetail obj) {
        GsonEmailDetail gson = new GsonEmailDetail();
        if (obj != null) {
            gson.setId(obj.getId());
            gson.setHost(obj.getHost());
            gson.setPort(obj.getPort());
            gson.setPassword("****");
            gson.setUsername(obj.getUsername());
            gson.setType(obj.getType());
        }
        return gson;
    }
}

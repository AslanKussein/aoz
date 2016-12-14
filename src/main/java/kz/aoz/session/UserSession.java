package kz.aoz.session;

import kz.aoz.entity.Groupmembers;
import kz.aoz.entity.UserDetail;
import kz.aoz.entity.Users;
import kz.aoz.gson.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

import static kz.aoz.util.Util.*;
import static kz.aoz.wrapper.Serialization.wrapToGsonUserDetailByJsonString;
import static kz.aoz.wrapper.Wrapper.*;

/**
 * Created by kusein-at on 17.11.2016.
 */
@Stateless
public class UserSession {

    @PersistenceContext(unitName = "aoz_jdbc")
    private EntityManager em;

    public GsonUsers getGsonUser(String uName) {
        try {
            Users user = getUser(uName);
            return wrapToGsonUsers(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public  Users getUser(String uName) {
        try {
            Users user = (Users) getSingleResultOrNull(em.createNamedQuery("Users.findByUName").setParameter("uName", uName));
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GsonDatatableData getUsersDetailList(Integer start, Integer limit) {
        if (start == null) {
            start = 0;
        }
        if (limit == null) {
            limit = 15;
        }
        GsonDatatableData data = new GsonDatatableData();
        try {
            List<UserDetail> list = em.createNamedQuery("UserDetail.findAll")
                    .setFirstResult(start)
                    .setMaxResults(limit)
                    .getResultList();
            data.setData(wrapToGsonUserDetailList(list));
            data.setPos(start);
            Query q = em.createQuery(" SELECT count(u)  FROM UserDetail u ");
            Long recordSize  = (Long) q.getSingleResult();
            data.setTotal_count(recordSize.intValue());

        } catch (NoResultException e) {
        }
        return data;
    }

    public List<GsonGroupmembers> getGroupMembersByUName(String uName) {
        List<GsonGroupmembers> result;
        try {
            List<Groupmembers> list = em.createNamedQuery("Groupmembers.findByGMember")
                    .setParameter("gMember",uName)
                    .getResultList();
            result = wrapToGsonGroupmembersList(list);
        } catch (NoResultException e) {
            result = new ArrayList<>();
        }

        return result;
    }



    public GsonResult editUser(MultivaluedMap<String, String> formParams) {
        GsonUserDetail gsonUserDetail = wrapToGsonUserDetailByJsonString(formParams.getFirst("json"));

        UserDetail userDetail = wrapToGsonUserDetail(gsonUserDetail);
        em.merge(userDetail);

        Query q = em.createQuery("DELETE FROM Groupmembers g where g.groupmembersPK.gMember  = :gMember")
                .setParameter("gMember", gsonUserDetail.getuName());
        q.executeUpdate();
        List<Groupmembers> list= wrapToGroupmembersList(gsonUserDetail.getRoles(),gsonUserDetail.getuName());
        for(Groupmembers g :list) {
            em.persist(g);
        }
        em.flush();
        return getGsonResult(true, null);
    }
}
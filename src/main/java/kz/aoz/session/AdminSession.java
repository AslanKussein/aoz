package kz.aoz.session;

import kz.aoz.entity.Groups;
import kz.aoz.entity.UserDetail;
import kz.aoz.entity.Users;
import kz.aoz.gson.GsonGroups;
import kz.aoz.gson.GsonResult;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static kz.aoz.util.Crypt.MD5;
import static kz.aoz.util.Util.*;
import static kz.aoz.wrapper.Wrapper.wrapToGsonGroupsList;

/**
 * Created by a.amanzhol on 17.11.2016.
 */
@Stateless
public class AdminSession {

    @PersistenceContext(unitName = "aoz_jdbc")
    private EntityManager em;

    @EJB
    UserSession userSession;

    public List<GsonGroups> getGroups() {

        List<GsonGroups> result;
        try {
            List<Groups> list = em.createNamedQuery("Groups.findAll")
                    .getResultList();
            result = wrapToGsonGroupsList(list);
        } catch (NoResultException e) {
            result = new ArrayList<>();
        }

        return result;
    }

    public GsonResult createUser(String uName, String newPass, String confirmPass, String uDesc) {

        if (isNullOrEmpty(uName)) {
            return getGsonResult(false, "Вы не ввели логин");
        } else if (isNullOrEmpty(newPass)) {
            return getGsonResult(false, "Вы не ввели пароль");
        } else if (isNullOrEmpty(confirmPass)) {
            return getGsonResult(false, "Вы не ввели пароль подтверждение");
        } else if (!newPass.equals(confirmPass)) {
            return getGsonResult(false, "Пароли не совпадают");
        }
        try {
            Users user = (Users) getSingleResultOrNull(em.createNamedQuery("Users.findByUName").setParameter("uName", uName));
            if (user != null) {
                return getGsonResult(false, "Пользователь с таким логином существует");
            }
            user = new Users();
            user.setuName(uName);
            user.setuPassword(MD5(newPass));
            user.setuDescription(getValueOrEmpty(uDesc));

            UserDetail detail = new UserDetail();
            detail.setuName(user);
            detail.setLocked(0);

            em.persist(user);
            em.persist(detail);
            return getGsonResult(true, uName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getGsonResult(false, "Ошибка при сохранений");
    }

    public GsonResult lockUser(String uName, Integer lock) {
        try {

            UserDetail userDetail = (UserDetail) getSingleResultOrNull(em.createNamedQuery("UserDetail.findByUName").setParameter("uName", new Users(uName)));
            if (userDetail == null) {
                return getGsonResult(false, "Пользователь не найден");
            }
            userDetail.setLocked(lock);
            //em.remove(users);
            return getGsonResult(true, null);
        } catch (NoResultException e) {
            e.printStackTrace();
            return getGsonResult(false, "Ошибка");
        }

    }

    public GsonResult resetPassword(String uName, String oldPass, String newPass, String confirmPass) {
        try {

            Users user = (Users) getSingleResultOrNull(em.createNamedQuery("Users.findByUName").setParameter("uName", uName));
            if (user == null) {
                return getGsonResult(false, "Пользователь " + uName + " не найден");
            }
            if (!user.getuPassword().equals(MD5(oldPass))) {
                return getGsonResult(false, "Ввели не правильный пароль");
            }
            if (!newPass.equals(confirmPass)) {
                return getGsonResult(false, "Пароли не совпадают");
            }
            user.setuPassword(MD5(newPass));
            return getGsonResult(true, null);
        } catch (NoResultException e) {
            e.printStackTrace();
            return getGsonResult(false, "Ошибка");
        }

    }

}
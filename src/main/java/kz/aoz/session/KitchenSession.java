/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.session;

import kz.aoz.entity.Orders;
import kz.aoz.entity.Products;
import kz.aoz.gson.GsonDatatableData;
import kz.aoz.gson.GsonKithenTreeData;
import kz.aoz.gson.GsonOrders;
import kz.aoz.gson.GsonResult;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import static kz.aoz.util.Util.*;
import static kz.aoz.wrapper.Wrapper.wrapToGsonOrders;
import static kz.aoz.wrapper.Wrapper.wrapToGsonOrdersList;
import static kz.aoz.wrapper.Wrapper.wrapToGsonProductsList;


/**
 * @author a.amanzhol
 */
@Stateless
public class KitchenSession {


    private static final Logger logger = Logger.getLogger(KitchenSession.class);

    @PersistenceContext(unitName = "aoz_jdbc")
    private EntityManager em;

    private static final int PER_DEF_START = 0;
    private static final int PER_DEF_COUNT = 30;

    public GsonKithenTreeData getKitchenTreeData(String id) {
        GsonKithenTreeData gson = new GsonKithenTreeData();

        if (isNullOrEmpty(id)) {
            GsonResult gsonResult = createOrder();
            if (gsonResult.getResult()) {
                gson.setResult(true);
                gson.setOrder((GsonOrders) gsonResult.getMessage());
                id = gson.getOrder().getId();
            } else {
                gson.setResult(false);
                gson.setMessage(gsonResult.getMessage());
            }

        } else {
            gson.setResult(true);
            gson.setProductList(getVSelectedProductList(id));
        }
        gson.setvProductList(getVProductList(id));

        return gson;
    }

    private GsonResult createOrder() {
        Orders orders = (Orders) getSingleResultOrNull(em.createQuery("select o from Orders o where o.endDate is null"));
        if (orders != null) {
            return getGsonResult(false, "Уже есть созданная заявка!");
        }
        orders = new Orders();
        orders.setId(createGuid());
        orders.setBegDate(new Timestamp(new Date().getTime()));
        em.persist(orders);
        return getGsonResult(true, wrapToGsonOrders(orders));
    }


    public GsonDatatableData getVProductList(String id) {

        try {

            StringBuilder query = new StringBuilder("SELECT p ");
            StringBuilder countQuery = new StringBuilder("SELECT count(p) ");
            StringBuilder sql = new StringBuilder(" FROM Products p where p.id not in (SELECT d.productId  FROM OrderDetail d ");

            if (id != null) {
                sql.append("where d.orderId = ?1");
            }
            sql.append(")");
            query.append(sql.toString());
            countQuery.append(sql.toString());
            Query q = em.createQuery(query.toString());

            Query cQuery = em.createQuery(countQuery.toString());
            if (id != null) {
                q.setParameter(1, id);
                cQuery.setParameter(1, id);
            }

            List<Products> list = q.getResultList();

            GsonDatatableData data = new GsonDatatableData();
            data.setData(wrapToGsonProductsList(list));
            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public GsonDatatableData getVSelectedProductList(String id) {

        try {

            StringBuilder query = new StringBuilder("SELECT p ");
            StringBuilder countQuery = new StringBuilder("SELECT count(p) ");
            StringBuilder sql = new StringBuilder(" FROM Products p where p.id  in (SELECT d.productId  FROM OrderDetail d ");

            if (id != null) {
                sql.append("where d.orderId = ?1");
            }
            sql.append(")");
            query.append(sql.toString());
            countQuery.append(sql.toString());
            Query q = em.createQuery(query.toString());

            Query cQuery = em.createQuery(countQuery.toString());
            if (id != null) {
                q.setParameter(1, id);
                cQuery.setParameter(1, id);
            }

            List<Products> list = q.getResultList();

            GsonDatatableData data = new GsonDatatableData();
            data.setData(wrapToGsonProductsList(list));
            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public GsonDatatableData getOrderListById(Integer start, Integer count, Integer id) {

        if (start == 0 || count == 0) {
            start = PER_DEF_START;
            count = PER_DEF_COUNT;
        }
        StringBuilder query = new StringBuilder("SELECT p ");
        StringBuilder countQuery = new StringBuilder("SELECT count(p) ");
        String sql = " FROM Products p where p.id  in (SELECT d.productId  FROM OrderDetail d where d.orderId = ?1)";

        query.append(sql);
        countQuery.append(sql);
        List<Products> list = em.createQuery(query.toString())
                .setFirstResult(start)
                .setMaxResults(count)
                .setParameter(1, id)
                .getResultList();


        Long countAll = (Long) getSingleResultOrNull(em.createQuery(countQuery.toString())
                .setParameter(1, id));
        GsonDatatableData data = new GsonDatatableData();
        data.setPos(start);
        if (countAll == null) countAll = 0L;
        data.setTotal_count(countAll.intValue());
        data.setData(wrapToGsonProductsList(list));
        return data;
    }

    public GsonDatatableData getOrdersList(Integer start, Integer count, String src) {

        if (start == 0 || count == 0) {
            start = PER_DEF_START;
            count = PER_DEF_COUNT;
        }

        List<Orders> list = em.createNamedQuery("Orders.findAll")
                .setFirstResult(start)
                .setMaxResults(count)
                .getResultList();

        Long countAll = (Long) getSingleResultOrNull(em.createNamedQuery("Orders.countAll"));

        GsonDatatableData data = new GsonDatatableData();
        data.setPos(start);
        if (countAll == null) countAll = 0L;
        data.setTotal_count(countAll.intValue());
        data.setData(wrapToGsonOrdersList(list));
        return data;
    }
}
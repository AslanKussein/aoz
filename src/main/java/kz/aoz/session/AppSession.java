/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.session;

import kz.aoz.entity.Parse;
import kz.aoz.gson.GsonDatatableData;
import kz.aoz.gson.GsonImport;
import kz.aoz.util.Utx;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static kz.aoz.util.DateUtil.stringToDate;
import static kz.aoz.util.Util.isNullOrEmpty;
import static kz.aoz.wrapper.Wrapper.wrapToGsonImportList;


/**
 * @author a.amanzhol
 */
@Stateless
public class AppSession extends Utx {


    private static final Logger logger = Logger.getLogger(AppSession.class);

    @PersistenceContext(unitName = "aoz_jdbc")
    private EntityManager em;

    private static final int PER_DEF_START = 0;
    private static final int PER_DEF_COUNT = 30;

    public GsonDatatableData getImportList(String user, Integer start, Integer count, String provider, String impDate, String src) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Parse> queryFilter = builder.createQuery(Parse.class);
        Root<Parse> productsRoot = queryFilter.from(Parse.class);
        queryFilter.orderBy(builder.asc(productsRoot.get("id")));
        queryFilter.select(productsRoot);

        List<Predicate> predicateList = new ArrayList<>();
        Predicate filterApps;

        if (!isNullOrEmpty(provider)) {

            filterApps = builder.or(
                    builder.like(builder.upper(productsRoot.<String>get("providersId").<String>get("name")), "%" + provider.toUpperCase() + "%")
            );

            predicateList.add(filterApps);
        }

        if (!isNullOrEmpty(impDate)) {
            filterApps = builder.equal(productsRoot.<Date>get("currentDt"), stringToDate(impDate));
            predicateList.add(filterApps);
        }

        if (!isNullOrEmpty(src)) {

            filterApps = builder.or(
                    builder.like(builder.upper(productsRoot.<String>get("productsId").<String>get("name")), "%" + src.toUpperCase() + "%")
            );

            predicateList.add(filterApps);
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        queryFilter.where(predicates);

        if (start == 0 || count == 0) {
            start = PER_DEF_START;
            count = PER_DEF_COUNT;
        }

        // Вычисляем общее количество записей, для разбивки по страницам
        CriteriaBuilder countBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        countQuery.select(countBuilder.count(productsRoot)).where(predicates);
        int recordSize = (em.createQuery(countQuery).getSingleResult()).intValue();

        List<Parse> parses = em.createQuery(queryFilter)
                .setFirstResult(start)
                .setMaxResults(count)
                .getResultList();

        GsonDatatableData data = new GsonDatatableData();
        data.setPos(start);
        data.setTotal_count(recordSize);
        data.setData(wrapToGsonImportList(parses));
        return data;
    }

    public List<GsonImport> getMainImport() {
        List<Parse> parse = em.createQuery("SELECT v from Parse v group by v.currentDt, v.providersId").getResultList();
        return wrapToGsonImportList(parse);
    }
}
package kz.aoz.session;

import kz.aoz.entity.Products;
import kz.aoz.entity.Providers;
import kz.aoz.entity.Unit;
import kz.aoz.gson.*;
import org.eclipse.persistence.config.QueryHints;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static kz.aoz.util.Util.*;
import static kz.aoz.wrapper.Wrapper.*;

/**
 * Created by a.amanzhol on 17.11.2016.
 */
@Stateless
public class DictionarySession {

    @PersistenceContext(unitName = "aoz_jdbc")
    private EntityManager em;

    @EJB
    UserSession userSession;

    public List<GsonImportTov> getProductsList() {
        List<Products> list = em.createNamedQuery("Products.findAll")
                .setHint(QueryHints.CACHE_STORE_MODE, CacheStoreMode.REFRESH)
                .getResultList();
        return wrapToGsonImportTovList(list);
    }

    public List<GsonUnit> getUnitList() {
        List<Unit> list = em.createNamedQuery("Unit.findAll")
                .setHint(QueryHints.CACHE_STORE_MODE, CacheStoreMode.REFRESH)
                .getResultList();
        return wrapToGsonUnitList(list);
    }

    public List<GsonDicsId> getProviderList() {
        List<Providers> list = em.createNamedQuery("Providers.findAll")
                .setHint(QueryHints.CACHE_STORE_MODE, CacheStoreMode.REFRESH)
                .getResultList();
        return wrapToGsonProviderList(list);
    }

    public GsonResult saveProducts(GsonImportTov gson) {
        if (isNullOrEmpty(gson.getId())) {
            return createProducts(gson);
        }
        return editProducts(gson);
    }

    private GsonResult createProducts(GsonImportTov gson) {
        try {
            Products products = new Products();
            products.setId(createGuid());

            if (getProductsCode(gson.getCode()) != null) {
                return getResultGson(Boolean.FALSE, "Нарушено уникальность");
            } else {
                products.setCode(gson.getCode());
            }
            products.setName(gson.getProductName());

            if (!isNullOrEmpty(gson.getParentId())) {
                products.setParentId(getProducts(gson.getParentId()).getCode());
            }

            if (gson.getUnitId() != null) {
                products.setUnitId(new Unit(gson.getUnitId()));
            }

            em.persist(products);
            return getResultGson(Boolean.TRUE, wrapToGsonProducts(products));
        } catch (Exception e) {
            e.printStackTrace();
            return getResultGson(Boolean.FALSE, null);
        }
    }


    private Products getProducts(String id) {
        return (Products) getSingleResultOrNull(em.createNamedQuery("Products.findById")
                .setHint(QueryHints.CACHE_STORE_MODE, CacheStoreMode.REFRESH)
                .setParameter("id", id));
    }

    private Products getProductsCode(String code) {
        return (Products) getSingleResultOrNull(em.createNamedQuery("Products.findByCode")
                .setHint(QueryHints.CACHE_STORE_MODE, CacheStoreMode.REFRESH)
                .setParameter("code", code));
    }

    private GsonResult editProducts(GsonImportTov gson) {
        try {
            Products products = getProducts(gson.getId());
            products.setCode(gson.getCode());
            products.setName(gson.getProductName());

            if (!isNullOrEmpty(gson.getParentId())) {
                products.setParentId(getProducts(gson.getParentId()).getCode());
            }

            if (gson.getUnitId() != null) {
                products.setUnitId(new Unit(gson.getUnitId()));
            }

            em.persist(products);
            return getResultGson(Boolean.TRUE, wrapToGsonProducts(products));
        } catch (Exception e) {
            e.printStackTrace();
            return getResultGson(Boolean.FALSE, null);
        }
    }

    public GsonResult removeProducts(String ids) {
        try {
            Products products = getProducts(ids);
            if (products != null) {
                em.remove(products);
            }

            return getResultGson(Boolean.TRUE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return getResultGson(Boolean.FALSE, null);
        }
    }

    public GsonResult saveUnit(GsonDics gson) {
        if (getUnit(gson.getId()) == null) {
            return createUnits(gson);
        }
        return editUnits(gson);
    }

    private GsonResult createUnits(GsonDics gson) {
        try {
            Query q = em.createNativeQuery("INSERT INTO UNIT (CODE, NAME) VALUES(?1,?2)").setParameter(1, gson.getId()).setParameter(2,gson.getName());
            q.executeUpdate();

            Unit unit = (Unit) getSingleResultOrNull(em.createNamedQuery("Unit.findById").setParameter("id", gson.getId()));
            return getResultGson(Boolean.TRUE, wrapToGsonUnit(unit));

        } catch (Exception e) {
            e.printStackTrace();
            return getResultGson(Boolean.FALSE, null);
        }
    }

    private GsonResult editUnits(GsonDics gson) {
        try {

            Unit unit = getUnit(gson.getId());
            unit.setName(gson.getName());
            em.persist(unit);

            return getResultGson(Boolean.TRUE, wrapToGsonUnit(unit));
        } catch (Exception e) {
            e.printStackTrace();
            return getResultGson(Boolean.FALSE, null);
        }
    }

    private Unit getUnit(String id) {
        return (Unit) getSingleResultOrNull(em.createNamedQuery("Unit.findById")
                .setHint(QueryHints.CACHE_STORE_MODE, CacheStoreMode.REFRESH)
                .setParameter("id", id));
    }

    public GsonResult removeUnits(String ids) {
        try {
            Unit unit = getUnit(ids);
            if (unit != null) {
                em.remove(unit);
            }

            return getResultGson(Boolean.TRUE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return getResultGson(Boolean.FALSE, null);
        }
    }

    public GsonResult saveProviders(GsonDics gson) {
        if (isNullOrEmpty(gson.getId())) {
            return createProviders(gson);
        }
        return editProviders(gson);
    }

    private GsonResult createProviders(GsonDics gson) {
        try {
            Providers providers = new Providers();
            providers.setName(gson.getName());
            em.persist(providers);

            return getResultGson(Boolean.TRUE, wrapToGsonProviders(providers));

        } catch (Exception e) {
            e.printStackTrace();
            return getResultGson(Boolean.FALSE, null);
        }
    }

    private GsonResult editProviders(GsonDics gson) {
        try {

            Providers providers = getProviders(Long.parseLong(gson.getId()));
            providers.setName(gson.getName());
            em.persist(providers);

            return getResultGson(Boolean.TRUE, wrapToGsonProviders(providers));
        } catch (Exception e) {
            e.printStackTrace();
            return getResultGson(Boolean.FALSE, null);
        }
    }

    private Providers getProviders(Long id) {
        return (Providers) getSingleResultOrNull(em.createNamedQuery("Providers.findById")
                .setHint(QueryHints.CACHE_STORE_MODE, CacheStoreMode.REFRESH)
                .setParameter("id", id));
    }

    public GsonResult removeProviders(Long ids) {
        try {
            Providers providers = getProviders(ids);
            if (providers != null) {
                em.remove(providers);
            }

            return getResultGson(Boolean.TRUE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return getResultGson(Boolean.FALSE, null);
        }
    }
}
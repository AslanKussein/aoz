/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.aoz.session;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import kz.aoz.entity.Parse;
import kz.aoz.entity.Products;
import kz.aoz.entity.Providers;
import kz.aoz.entity.Unit;
import kz.aoz.gson.GsonImport;
import kz.aoz.gson.GsonImportResult;
import kz.aoz.gson.GsonImportTov;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.persistence.config.QueryHints;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static kz.aoz.util.Util.*;

/**
 * @author a.kussein
 */
@Stateless
public class ImportSession {

    @PersistenceContext(unitName = "aoz_jdbc")
    private EntityManager em;

    public GsonImportResult uploadFile(InputStream ins) {
        List<GsonImport> importList = new ArrayList<>();

        GsonImport gsonImport = new GsonImport();
        try {
            XSSFWorkbook wb = new XSSFWorkbook(ins);

            for (int k = 0; k < wb.getNumberOfSheets(); k++) {
                XSSFSheet ws = wb.getSheetAt(k);
                int rowNum = ws.getLastRowNum() + 2;
                if (rowNum >= 3) {
                    for (int i = 3; i <= rowNum; i++) {
                        String s = "00000000000";
                        int n = 11;
                        Double productCount = null;
                        Double price = null;
                        String unit  = null;
                        gsonImport = new GsonImport();
                        XSSFRow row = ws.getRow(i);
                        if (row != null) {

                            if (row.getCell(0) != null) {
                                BigDecimal code = new BigDecimal(row.getCell(0).getNumericCellValue());

                                if (row.getCell(2) != null) {
                                    unit = row.getCell(2).getStringCellValue();
                                }
                                if (row.getCell(3) != null) {
                                    price = row.getCell(3).getNumericCellValue();
                                }

                                n = n - code.toString().length();
                                s = s.substring(0, n);

                                gsonImport.setCode(s + code);
                                gsonImport.setPrice(price);
                                gsonImport.setUnit(unit);

                                importList.add(gsonImport);
                            }


                        }
                    }
                } else {
                    return getGsonImportResult(false, null, "Загружаемый файл пуст");
                }
            }

            return importData(importList, "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return getGsonImportResult(false, null, "Не удалось загрузить файл");
    }


    private GsonImportResult importData(List<GsonImport> importList, String companyName) throws ParseException {
        Providers providers = getProvidersId(companyName);
        if (importList.size() > 0) {
            for (GsonImport gson : importList) {

                if (!gson.getCode().equals("00000000000")) {
                    Parse parse = new Parse();
                    parse.setId(createGuid());
                    parse.setProvidersId(providers);
                    parse.setProductsId(getProductsCode(gson.getCode()));
                    parse.setUnitCode(getUnitbyName(gson.getUnit()));
                    parse.setPrPrice(gson.getPrice());
                    parse.setCurrentDt(new Date());
                    em.persist(parse);
                }

            }
            return getGsonImportResult(true, importList, null);
        }
        return getGsonImportResult(false, "Ошибка", null);
    }

    private Providers getProvidersId(String name) {
        return (Providers) getSingleResultOrNull(em.createNamedQuery("Providers.findByName")
                .setHint(QueryHints.CACHE_STORE_MODE, CacheStoreMode.REFRESH)
                .setParameter("name", name));
    }

    private Unit getUnitbyName(String name) {
        return (Unit) getSingleResultOrNull(em.createNamedQuery("Unit.findByName")
                .setHint(QueryHints.CACHE_STORE_MODE, CacheStoreMode.REFRESH)
                .setParameter("name", name));
    }

    private Products getProductsCode(String code) {
        return (Products) getSingleResultOrNull(em.createNamedQuery("Products.findByCode")
                .setHint(QueryHints.CACHE_STORE_MODE, CacheStoreMode.REFRESH)
                .setParameter("code", "%" + code + "%"));
    }

    public GsonImportResult uploadFileTov(InputStream ins) {
        List<GsonImportTov> importList = new ArrayList<>();
        try {

            XSSFWorkbook wb = new XSSFWorkbook(ins);

            for (int k = 0; k < wb.getNumberOfSheets(); k++) {
                XSSFSheet ws = wb.getSheetAt(k);
                int rowNum = ws.getLastRowNum() + 2;
                GsonImportTov gson;
                if (rowNum >= 0) {
                    for (int i = 0; i <= rowNum; i++) {

                        gson = new GsonImportTov();
                        String s = "00000000000", s1 = "00000000000";
                        int n = 11, n1 = 11;
                        Double parentId = null;
                        String parentName = null;

                        XSSFRow row = ws.getRow(i);
                        if (row != null) {

                            BigDecimal code = new BigDecimal(row.getCell(0).getNumericCellValue());
                            String productName = row.getCell(1).getStringCellValue();

                            String unit = row.getCell(3).getStringCellValue();

                            if (row.getCell(4) != null) {
                                parentId = row.getCell(4).getNumericCellValue();
                            }

                            n = n - code.toString().length();
                            s = s.substring(0, n);

                            gson.setCode(s + code);
                            gson.setProductName(productName);

                            if (parentId != null) {
                                n1 = n1 - new BigDecimal(parentId).toString().replace(".0", "").length();
                                s1 = s1.substring(0, n1);
                                parentName = s1 + parentId.toString().replace(".0", "");
                            }

                            gson.setParentId(parentName);
                            gson.setUnitName(unit);

                            importList.add(gson);
                        }
                    }
                } else {
                    return getGsonImportResult(false, null, "Загружаемый файл пуст");
                }
            }

            return importDataTovar(importList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return getGsonImportResult(false, null, "Не удалось загрузить файл");
    }

    private GsonImportResult importDataTovar(List<GsonImportTov> importList) throws ParseException, MySQLIntegrityConstraintViolationException {
        if (importList.size() > 0) {
            try {
                for (GsonImportTov gson : importList) {
                    Products products = new Products();
                    products.setId(createGuid());
                    products.setCode(gson.getCode());
                    products.setName(gson.getProductName());
                    products.setParentId(gson.getParentId());
                    products.setUnitId(new Unit(getUnit(gson.getUnitName())));
                    em.persist(products);
                }
                return getGsonImportResult(true, importList, null);
            } catch (Exception ex) {
                ex.printStackTrace();
                return getGsonImportResult(false, "Ошибка", null);
            }
        }
        return getGsonImportResult(false, "Ошибка", null);
    }

    private String getUnit(String name) {
        Unit unit = (Unit) getSingleResultOrNull(em.createNamedQuery("Unit.findByName").setParameter("name", name));
        if (unit != null) {
            return unit.getId();
        } else
            return null;
    }

}

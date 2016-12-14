package kz.aoz.test;

/**
 * Created by kusein-at on 18.11.2016.
 */

import kz.aoz.gson.GsonImport;
import kz.aoz.gson.GsonImportTov;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<GsonImport> parse(String name) {
        List<GsonImport> importList = new ArrayList<>();
        GsonImport gsonImport = new GsonImport();
        InputStream in;
        XSSFWorkbook wb = null;
        try {
            in = new FileInputStream(name);
            wb = new XSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert wb != null;
        for (int k = 0; k < wb.getNumberOfSheets(); k++) {
            XSSFSheet ws = wb.getSheetAt(k);
            int rowNum = ws.getLastRowNum() + 2;
            if (rowNum > 3) {
                for (int i = 3; i <= rowNum; i++) {
                    String s = "00000000000";
                    int n = 11;
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

                            System.out.println(s + code + " " + price + " " + unit);
                        }

                    }
                }
            }
        }

        return importList;
    }

    public static List<GsonImportTov> parseTov(String name) {

        InputStream in;
        XSSFWorkbook wb = null;
        try {
            in = new FileInputStream(name);
            wb = new XSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert wb != null;

        List<GsonImportTov> importList = new ArrayList<>();

        for (int k = 0; k < wb.getNumberOfSheets(); k++) {
            XSSFSheet ws = wb.getSheetAt(k);
            int rowNum = ws.getLastRowNum() + 2;
            if (rowNum >= 0) {
                for (int i = 0; i <= rowNum; i++) {
                    String s = "00000000000";
                    String s1 = "00000000000";
                    int n = 11;
                    int n1 = 11;
                    Double parentId = null;
                    String parentName = null;
                    XSSFRow row = ws.getRow(i);
                    if (row != null) {
                        BigDecimal code = new BigDecimal(row.getCell(0).getNumericCellValue());
                        String productName = row.getCell(1).getStringCellValue();

                        String unit =  row.getCell(3).getStringCellValue();

                        if (row.getCell(4) != null){
                            parentId = row.getCell(4).getNumericCellValue();
                        }


                        n = n - code.toString().length();
                        s = s.substring(0, n);

                        if (parentId != null) {
                            n1 = n1 - new BigDecimal(parentId).toString().replace(".0","").length();
                            s1 = s1.substring(0, n1);
                            parentName = s1 + parentId.toString().replace(".0","");
                        }


//

                        System.out.println(s + code + " " + productName + "  " + unit + "  " + parentName);
                    }
                }
            }
        }


        return importList;
    }

    public static void main(String[] args) {
        String s = "00000007513.0";
        s = s.replace(".0","");
        System.out.println(s);
    }

}
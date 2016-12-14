package kz.aoz.test;

/**
 * Created by kusein-at on 18.11.2016.
 */
public class Main {
    public static void main(String[] args) {
//        Parser.parseTov("src/main/java/kz/aoz/test/321.xlsx");
        Parser.parse("src/main/java/kz/aoz/test/test.xlsx");
//        for (GsonImport gson : Parser.parse("src/main/java/kz/aoz/test/test.xlsx")) {
//            System.out.println(gson.getCompanyName());
//            System.out.println(gson.getCode() + " " + gson.getProductName() + " " + gson.getUnit() + " " + gson.getPrice());
//        }
    }
}

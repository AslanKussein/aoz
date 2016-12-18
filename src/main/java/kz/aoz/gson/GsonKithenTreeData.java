package kz.aoz.gson;

import java.util.Objects;

/**
 * Created by a.amanzhol on 20.11.2016.
 */


public class GsonKithenTreeData {
    private boolean result;
    private Object message;
    private GsonOrders order;
    private GsonDatatableData vProductList;
    private GsonDatatableData productList;


    public GsonOrders getOrder() {
        return order;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setOrder(GsonOrders order) {
        this.order = order;
    }

    public GsonDatatableData getvProductList() {
        return vProductList;
    }

    public void setvProductList(GsonDatatableData vProductList) {
        this.vProductList = vProductList;
    }

    public GsonDatatableData getProductList() {
        return productList;
    }

    public void setProductList(GsonDatatableData productList) {
        this.productList = productList;
    }
}

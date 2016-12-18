package kz.aoz.gson;

/**
 * Created by amanzhol-ak on 11.12.2016.
 */
public class GsonOrders {

    private String id;
    private String begDate;
    private String endDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBegDate() {
        return begDate;
    }

    public void setBegDate(String begDate) {
        this.begDate = begDate;
    }
}

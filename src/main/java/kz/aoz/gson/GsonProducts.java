package kz.aoz.gson;

/**
 * Created by amanzhol-ak on 12.12.2016.
 */
public class GsonProducts {

    private String id;
    private String code;
    private String parentId;
    private String name;
    private GsonUnit unit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public GsonUnit getUnit() {
        return unit;
    }

    public void setUnit(GsonUnit unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

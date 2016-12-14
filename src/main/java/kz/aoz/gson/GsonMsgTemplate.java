package kz.aoz.gson;

/**
 * Created by amanzhol-ak on 03.09.2016.
 */
public class GsonMsgTemplate {

    private String id ;

    private Long code ;

    private String title ;

    private String template ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}

package kz.aoz.gson;

import kz.aoz.entity.MsgTemplate;

import java.util.List;

/**
 * Created by amanzhol-ak on 03.09.2016.
 */
public class GsonEmailDetailContent {
    private GsonEmailDetail gsonEmailDetail;
    private List<MsgTemplate> msgTemplateList;


    public GsonEmailDetail getGsonEmailDetail() {
        return gsonEmailDetail;
    }

    public void setGsonEmailDetail(GsonEmailDetail gsonEmailDetail) {
        this.gsonEmailDetail = gsonEmailDetail;
    }

    public List<MsgTemplate> getMsgTemplateList() {
        return msgTemplateList;
    }

    public void setMsgTemplateList(List<MsgTemplate> msgTemplateList) {
        this.msgTemplateList = msgTemplateList;
    }
}

package kz.aoz.session;

import kz.aoz.entity.EmailDetail;
import kz.aoz.entity.EmailMessage;
import kz.aoz.entity.MsgTemplate;
import kz.aoz.entity.Users;
import kz.aoz.gson.*;
import kz.aoz.util.DesEncrypter;
import kz.aoz.util.Utx;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static kz.aoz.util.MailUtil.sendSSL;
import static kz.aoz.util.Util.createGuid;
import static kz.aoz.util.Util.getResultGson;
import static kz.aoz.util.Util.getSingleResultOrNull;
import static kz.aoz.wrapper.Wrapper.wrapToGsonEmailDetail;
import static kz.aoz.wrapper.Wrapper.wrapToGsonMsgTemplate;
import static kz.aoz.wrapper.Wrapper.wrapToMsgTemplate;


/**
 * @author a.amanzhol
 */
@Stateless
public class MailSession  extends Utx {

    private static final Logger logger = Logger.getLogger(MailSession.class);

    @PersistenceContext(unitName = "aoz_jdbc")
    private EntityManager em;

    public boolean sendMail(String messageId) {
        Boolean res = false;
        try {
            EmailMessage emailMessage = (EmailMessage) getSingleResultOrNull(em.createNamedQuery("EmailMessage.findById").setParameter("id", messageId));
            GsonMsgTemplate msgTemplate = wrapToGsonMsgTemplate((MsgTemplate) getSingleResultOrNull(em.createNamedQuery("MsgTemplate.findById")
                    .setParameter("id", emailMessage.getIdTemplate())));

            GsonMsgTemplate newTemplate = setTemplateAttr(msgTemplate, emailMessage.getuName());
            res = sendSSL(getEmailDetail("smtp"), "email", newTemplate.getTitle(), newTemplate.getTemplate());
            emailMessage.setState(1);
        } catch (MessagingException mEx) {
            mEx.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean sendMessage(String uName, String idTemplate) {
        try {
            EmailMessage message = new EmailMessage();
            message.setId(createGuid());
            message.setIdTemplate(idTemplate);
            message.setuName(new Users(uName));
            message.setState(0);
            message.setMsgDate(new Timestamp(new Date().getTime()));
            em.persist(message);
            try {
             /*   SendMail sendMail = new SendMail();
                sendMail.sendMessage(message.getId());*/
            } catch (Exception e) {
                message.setDescription("Ошибка при добавлений в очередь: " + e.toString());
                em.merge(message);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private EmailDetail getEmailDetail(String type) {
        return (EmailDetail) getSingleResultOrNull(em.createNamedQuery("EmailDetail.findByType").setParameter("type", type));
    }

    private GsonMsgTemplate setTemplateAttr(GsonMsgTemplate template, Users user) {
        String temp = template.getTemplate()
                .replace("#firstName#", user.getUserDetail().getFirstname())
                .replace("#lastName#", user.getUserDetail().getLastname());
        template.setTemplate(temp);
        return template;
    }

    public GsonResult saveAttrs(GsonEmailDetail gson) {
        try {
            EmailDetail emailDetail = new EmailDetail();
            if (gson.getPassword().equals("****")) {
                emailDetail = (EmailDetail) getSingleResultOrNull(em.createNamedQuery("EmailDetail.findById").setParameter("id", gson.getId()));
            } else {
                emailDetail.setPassword(DesEncrypter.defKeyEnCrypt(gson.getPassword()));
            }
            emailDetail.setPort(gson.getPort());
            emailDetail.setHost(gson.getHost());
            emailDetail.setType(gson.getType());
            emailDetail.setUsername(gson.getUsername());
            emailDetail.setId(gson.getId());
            em.merge(emailDetail);
            return getResultGson(true, null);
        } catch (Exception e) {
            return getResultGson(false, null);
        }
    }

    public GsonResult testMailSubmit(GsonEmail gson) {
        try {
            sendSSL(getEmailDetail("smtp"), gson.getUsername(), gson.getTitle(), gson.getText());
            return getResultGson(true, null);
        } catch (MessagingException e) {
            return getResultGson(false, "Ошибка при отправке");
        } catch (Exception e) {
            return getResultGson(false, "Ошибка при отправке");
        }
    }

    public GsonResult saveTemplate(GsonMsgTemplate gson) {
        MsgTemplate template = wrapToMsgTemplate(gson);
        try {
            em.merge(template);
            return getResultGson(true, null);
        } catch (Exception e) {
            return getResultGson(false, null);
        }
    }

    public GsonEmailDetailContent getContent(String type) {
        GsonEmailDetailContent c = new GsonEmailDetailContent();
        GsonEmailDetail gsonEmailDetail = wrapToGsonEmailDetail(getEmailDetail("smtp"));
        List<MsgTemplate> msgTemplateList = em.createNamedQuery("MsgTemplate.findAll").getResultList();
        c.setGsonEmailDetail(gsonEmailDetail);
        c.setMsgTemplateList(msgTemplateList);
        return c;
    }
}
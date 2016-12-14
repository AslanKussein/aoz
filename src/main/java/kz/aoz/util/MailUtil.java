package kz.aoz.util;

/**
 * Created by amanzhol-ak on 02.09.2016.
 */



import kz.aoz.entity.EmailDetail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 * @author a.amanzhol
 */
public class MailUtil {

    public static boolean sendSSL(EmailDetail emailDetail, String to, String subject, String msg) throws MessagingException,Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host",  emailDetail.getHost());
        props.put("mail.smtp.socketFactory.port", emailDetail.getPort().toString());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", emailDetail.getPort().toString());

        final String userName = emailDetail.getUsername();
        final String password = DesEncrypter.defKeyDeCrypt(emailDetail.getPassword());

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(msg);
            Transport.send(message);
            return true;

    }

}

package enivarmail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import objectos.notificaciones;

public class EnivarMail {

    public static List<Long> colNotiFil;
    public static List<Long> colNotiOk;

    /**
     * @param args the command line arguments
     */
    public final static void main(String[] args) {
        Controller ctr = new Controller();
        List<notificaciones> colNotificaciones = ctr.getNotificaciones();

        if (colNotificaciones != null && colNotificaciones.size() > 0) {
            EnviarMail(colNotificaciones);
        }

        if (colNotiFil != null && colNotiFil.size() > 0) {
            ctr.updateNotificaciones(colNotiFil, "E");
        }

        if (colNotiOk != null && colNotiOk.size() > 0) {
            ctr.updateNotificaciones(colNotiOk, "S");
        }

        if (colNotiOk != null && colNotiOk.size() > 0) {
            ctr.updateNotificaciones(colNotiOk, "S");
        }
    }

    public static void EnviarMail(List<notificaciones> colNotificaciones) {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "atuservicioidm@gmail.com");
        props.put("mail.smtp.password", "proyectoidm");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);

        colNotiFil = new ArrayList<Long>();
        colNotiOk = new ArrayList<Long>();

        for (notificaciones noti : colNotificaciones) {

            Session session = Session.getInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            // Create the email addresses involved
            try {
                InternetAddress from = new InternetAddress("atuservicioidm@gmail.com");
                message.setSubject("ATuServicio IDM");
                message.setFrom(from);
                message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(noti.correo));

                String html = noti.mensaje;
                message.setSubject("IDM - ATuServicio");
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(html, "text/html; charset=utf-8");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                message.setContent(multipart);

                // Send message
                Transport transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", "atuservicioidm@gmail.com", "proyectoidm");
                System.out.println("Transport: " + transport.toString());
                transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
                colNotiOk.add(noti.idNotificaciones);

            } catch (AddressException e) {
                colNotiFil.add(noti.idNotificaciones);

            } catch (MessagingException e) {
                colNotiFil.add(noti.idNotificaciones);

            }
        }

    }

}

package es.caib.portafib.logic.utils;

import java.util.Date;
import java.util.List;

import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.fundaciobit.genapp.common.i18n.I18NArgumentString;
import org.fundaciobit.genapp.common.i18n.I18NException;

import es.caib.portafib.utils.Constants;

/**
 * 
 * @author anadal
 * 
 */
public class EmailUtil {

 /**
   * Envia un email a un {@link List} de emails
   * 
   * @param subject
   *          Asunto del Mensaje
   * @param message
   *          Contenido a enviar
   * @param from
   *          Indica la procedencia del mensaje
   * @param type
   *          Indica el con que de que tipo es el destinatario, Copia, Copia
   *          Oculta, etc
   * @param isHtml
   *          Decide si el contenido del mensaje a de ser visualizado en html o
   *          no
   * @param recipients
   *          Conjunto de emails para los que va dirigido el mensaje
   * @throws Exception
   */
  public static void postMail(String subject, String message, boolean isHtml,
      String from, String ... recipients) throws Exception {
    
    

    Context ctx = new InitialContext();
    Session session = (javax.mail.Session) ctx.lookup(Constants.MAIL_SERVICE);

    // Creamos el mensaje
    MimeMessage msg = new MimeMessage(session);

    InternetAddress addressFrom = new InternetAddress(from);
    msg.setFrom(addressFrom);

    // Indicamos los destinatarios
    InternetAddress[] addressTo = new InternetAddress[recipients.length];
    for (int i = 0; i < recipients.length; i++) {
      addressTo[i] = new InternetAddress(recipients[i]);
    }
    
    final RecipientType type = RecipientType.TO;

    msg.setRecipients(type, addressTo);

    // Configuramos el asunto
    msg.setSubject(subject, "UTF-8");
    msg.setSentDate(new Date());

    // Configuramos el contenido
    if (isHtml) {
      msg.setHeader("Content-Type", "text/html;charset=utf-8");
      /*
      URL urlToAdd = new URL(url);
      msg.setDataHandler(new DataHandler(urlToAdd));
      */
      msg.setContent(message, "text/html;charset=utf-8");
    } else {
      msg.setContent(message, "text/plain" /*; charset=UTF-8"*/);
    }

    // Mandamos el mail
    Transport.send(msg);

  }




  public static void enviarMails(List<EmailInfo> emailInfos) throws I18NException {
    try {
      if (emailInfos == null || emailInfos.size() == 0) {
        return;
      }
  
      // Esperarem a començar l'enviament mig segon per cada email 
      long date = new Date().getTime() + emailInfos.size() * 500;
  
      InitialContext ic = new InitialContext();
      final Queue queue = (Queue) ic.lookup(Constants.MAIL_QUEUE);
      final QueueConnectionFactory factory;
      factory = (QueueConnectionFactory) ic.lookup("java:/ConnectionFactory");
      final QueueConnection connection = factory.createQueueConnection();
      final QueueSession session = connection.createQueueSession(false,
          QueueSession.AUTO_ACKNOWLEDGE);
  
      // TODO Cridar configuracio
      // Temps entre enviaments de correu, per no saturar el servidor
      final Integer sleep = 5;
  
      int counter = 0;
      for (EmailInfo emailInfo : emailInfos) {
        counter++;
  
        ObjectMessage message = session.createObjectMessage();
  
        // Esperamos x segundos entre cada mensaje
        message.setLongProperty("JMS_JBOSS_SCHEDULED_DELIVERY", date + sleep * counter);
        message.setObject(emailInfo);
        final QueueSender sender = session.createSender(queue);
        sender.send(message);
      }
  
      session.close();
      connection.close();
      
    } catch(Exception e) {
      throw new I18NException(e, "error.unknown",
          new I18NArgumentString("Error enviant mail: " + e.getMessage()));
    }

  }

}
package STARSapp;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Contains functions to send e-mails to users
 * @author Samuel
 * @version 1.0
 * @since 2020-11-22
 */
public class SendEmail implements Notification{
	
	/**
	 * The e-mail account that sends out notification e-mails.
	 */
	final String username = "cz2002test@gmail.com";
	
	/**
	 * The password for the e-mail account that sends out notification e-mails.
	 */
	final String password = "C2002testZ"; 
	
	/**
	 * Sends an e-mail.
	 * @param email Email address of the recipient.
	 * @param topic Topic of the e-mail to send.
	 * @param s Text content in the e-mail to send.
	 */
	public void sendMessage(String email, String topic, String s) /*throws FileNotFoundException*/
	{
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });

				try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(username));
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(email));
					message.setSubject(topic);
					message.setText(s);

					Transport.send(message);

					System.out.println("Notification email has been sent");

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
	}
		
	
}
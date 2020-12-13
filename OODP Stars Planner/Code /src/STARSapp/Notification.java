package STARSapp;
/**
 * Course information
 * @author Samuel
 * @version 1.0
 * @since 2020-11-22
 */
interface Notification {
	/**
	 * Function to be implemented to send messgaes to recipients
	 * using different means of contact.
	 * @param recipient_contact The contact information of the recipient (phone number/email/...).
	 * @param topic The topic of the message.
	 * @param content The content of the message.
	 */
	void sendMessage(String recipient_contact, String topic, String content);
}

package online.renanlf.miaudote.exceptions;

public class EmailNotFoundException extends RuntimeException {

	public EmailNotFoundException(String email) {
		super("User not found for email " + email);
	}
	
}

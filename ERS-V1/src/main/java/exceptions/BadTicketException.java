package exceptions;

public class BadTicketException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadTicketException(String errMsg) {
		super(errMsg);
	}
}


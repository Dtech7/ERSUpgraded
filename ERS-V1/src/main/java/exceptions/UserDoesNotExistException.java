package exceptions;

public class UserDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserDoesNotExistException(String errMsg) {
		super(errMsg);
	}

}

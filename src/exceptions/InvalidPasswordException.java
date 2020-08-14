package exceptions;

public class InvalidPasswordException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5877398777770166904L;

	public InvalidPasswordException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidPasswordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidPasswordException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidPasswordException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidPasswordException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}

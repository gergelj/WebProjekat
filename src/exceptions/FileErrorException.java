package exceptions;

public class FileErrorException extends DatabaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4403648172551340022L;

	public FileErrorException() {
		// TODO Auto-generated constructor stub
	}

	public FileErrorException(String message) {
		super(message);
		this.message = message;
	}

	public FileErrorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public FileErrorException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public FileErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.message = message;
	}

}

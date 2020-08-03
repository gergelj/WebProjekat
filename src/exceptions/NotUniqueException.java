package exceptions;

public class NotUniqueException extends DatabaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7454411080250713026L;

	public NotUniqueException() {
	}

	public NotUniqueException(String arg0) {
		super(arg0);
		this.message = arg0;
	}
}

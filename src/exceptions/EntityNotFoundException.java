package exceptions;

public class EntityNotFoundException extends DatabaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public EntityNotFoundException() {
		super();
	}

	public EntityNotFoundException(String arg0) {
		super(arg0);
		this.message = arg0;
	}

	public EntityNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.message = arg0;
	}


}

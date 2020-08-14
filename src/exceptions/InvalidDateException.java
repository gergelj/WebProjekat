package exceptions;

public class InvalidDateException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4827414573157354510L;
	protected String message;
	
	public InvalidDateException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidDateException(String arg0) {
		super(arg0);
		this.message = arg0;
	}

	public InvalidDateException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InvalidDateException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.message = arg0;
	}

	public InvalidDateException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		this.message = arg0;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}

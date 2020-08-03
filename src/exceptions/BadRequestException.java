package exceptions;

public class BadRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3781839325201831284L;
	
	protected String message = "";

	public BadRequestException() {
		// TODO Auto-generated constructor stub
	}

	public BadRequestException(String arg0) {
		super(arg0);
		this.message = arg0;
	}

	public BadRequestException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BadRequestException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.message = arg0;
	}

	public BadRequestException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		this.message = arg0;
	}

	public String getMessage() {
		return message;
	}

}

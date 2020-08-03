package exceptions;

public class InvalidUserException extends BadRequestException {

	private static final long serialVersionUID = -1203310741509577680L;

	public InvalidUserException() {
	}

	public InvalidUserException(String arg0) {
		super(arg0);
	}

	public InvalidUserException(Throwable arg0) {
		super(arg0);
	}

	public InvalidUserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidUserException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
}

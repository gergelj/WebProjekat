package dto;

public class ErrorMessageDTO {
	
	private String message;

	public ErrorMessageDTO() {
		
	}

	public ErrorMessageDTO(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
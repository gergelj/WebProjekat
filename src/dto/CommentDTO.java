package dto;

public class CommentDTO {

	private long reservationId;
	private String text;
	private int rating;
	
	public CommentDTO() {
		
	}

	public CommentDTO(long reservationId, String text, int rating) {
		super();
		this.reservationId = reservationId;
		this.text = text;
		this.rating = rating;
	}

	public long getReservationId() {
		return reservationId;
	}

	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	

}

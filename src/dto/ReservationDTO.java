package dto;

import beans.DateRange;

public class ReservationDTO {
	
	private DateRange dateRange;
	private int nights;
	private String message;
	private long apartmentId;

	public ReservationDTO() {

	}

	public ReservationDTO(DateRange dateRange, int nights, String message, long apartmentId) {
		super();
		this.dateRange = dateRange;
		this.nights = nights;
		this.message = message;
		this.apartmentId = apartmentId;
	}

	public DateRange getDateRange() {
		return dateRange;
	}

	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(long apartmentId) {
		this.apartmentId = apartmentId;
	}
	
	

}

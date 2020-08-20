package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDatesDTO {

	private List<Date> checkInDates;
	private List<Date> checkOutDates;
	private List<Date> checkInOutDates;
	
	public BookingDatesDTO() {
		// TODO Auto-generated constructor stub
	}
	
	

	public BookingDatesDTO(List<Date> checkInDates, List<Date> checkOutDates, List<Date> checkInOutDates) {
		super();
		this.checkInDates = checkInDates;
		this.checkOutDates = checkOutDates;
		this.checkInOutDates = checkInOutDates;
	}


	public List<Date> getCheckInDates() {
		if(this.checkInDates == null)
			return new ArrayList<Date>();
		return checkInDates;
	}

	public void setCheckInDates(List<Date> checkInDates) {
		this.checkInDates = checkInDates;
	}

	public List<Date> getCheckOutDates() {
		if(this.checkOutDates == null)
			return new ArrayList<Date>();
		return checkOutDates;
	}

	public void setCheckOutDates(List<Date> checkOutDates) {
		this.checkOutDates = checkOutDates;
	}

	public List<Date> getCheckInOutDates() {
		if(this.checkInOutDates == null)
			return new ArrayList<Date>();
		return checkInOutDates;
	}

	public void setCheckInOutDates(List<Date> checkInOutDates) {
		this.checkInOutDates = checkInOutDates;
	}
	
	

}

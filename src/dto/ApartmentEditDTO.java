package dto;

import java.util.Date;
import java.util.List;

import beans.Apartment;

public class ApartmentEditDTO {
	
	private Apartment apartment;
	private List<Date> dates;

	public ApartmentEditDTO() {

	}

	public ApartmentEditDTO(Apartment apartment, List<Date> dates) {
		super();
		this.apartment = apartment;
		this.dates = dates;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

}

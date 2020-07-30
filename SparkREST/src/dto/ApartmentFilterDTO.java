/***********************************************************************
 * Module:  ApartmentFilterDTO.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentFilterDTO
 ***********************************************************************/

package dto;

import beans.DateRange;
import beans.PriceRange;

public class ApartmentFilterDTO {
   private String city;
   private int numberOfRooms;
   private int numberOfGuests;
   
   private DateRange dateRange;
   private PriceRange priceRange;
   
 //Constructors
	public ApartmentFilterDTO(String city, int numberOfRooms, int numberOfGuests, DateRange dateRange,
			PriceRange priceRange) {
		super();
		this.city = city;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.dateRange = dateRange;
		this.priceRange = priceRange;
	}
	
	public ApartmentFilterDTO() {
		super();
		
		this.dateRange = new DateRange();
		this.priceRange = new PriceRange();
	}

//Getters and Setters
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getNumberOfRooms() {
		return numberOfRooms;
	}
	
	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	
	public int getNumberOfGuests() {
		return numberOfGuests;
	}
	
	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}
	
	public DateRange getDateRange() {
		return dateRange;
	}
	
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}
	
	public PriceRange getPriceRange() {
		return priceRange;
	}
	
	public void setPriceRange(PriceRange priceRange) {
		this.priceRange = priceRange;
	}   
}
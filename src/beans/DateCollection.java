/***********************************************************************
 * Module:  DateCollection.java
 * Author:  Geri
 * Purpose: Defines the Class DateCollection
 ***********************************************************************/

package beans;

import java.util.*;

public class DateCollection implements IIdentifiable, IDeletable {
   private List<DateRange> dates;
   private long id;
   private boolean deleted;
   
   private Apartment apartment;
   

 //Constructors
	public DateCollection(long id, Apartment apartment, boolean deleted, List<DateRange> dates) {
		super();
		this.dates = dates;
		this.id = id;
		this.deleted = deleted;
		this.apartment = apartment;
	}
	
	public DateCollection() {
		super();
		
		this.dates = new ArrayList<DateRange>();
	}
	
	public DateCollection(Apartment apartment, boolean deleted, List<DateRange> dates) {
		super();
		this.dates = dates;
		this.deleted = deleted;
		this.apartment = apartment;
	}

//Getters and Setters
	public List<DateRange> getDates() {
		return dates;
	}
	
	public void setDates(List<DateRange> dates) {
		this.dates = dates;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public Apartment getApartment() {
		return apartment;
	}
	
	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}
	
	public long getId() {
	    return this.id;
	 }
	 
	 public void setId(long id) {
		  this.id = id;
	 }

//Methods
	 public void delete() {
	      // TODO: implement
	 }
}
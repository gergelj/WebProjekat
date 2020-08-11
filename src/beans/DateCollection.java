/***********************************************************************
 * Module:  DateCollection.java
 * Author:  Geri
 * Purpose: Defines the Class DateCollection
 ***********************************************************************/

package beans;

import java.util.*;

import beans.interfaces.IDeletable;
import beans.interfaces.IIdentifiable;

public class DateCollection implements IIdentifiable, IDeletable {
   private List<Date> dates;
   private long id;
   private boolean deleted;
   
   private Apartment apartment;
   

 //Constructors
	public DateCollection(long id, Apartment apartment, boolean deleted, List<Date> dates) {
		super();
		this.dates = dates;
		this.id = id;
		this.deleted = deleted;
		this.apartment = apartment;
	}
	
	public DateCollection() {
		super();
		
		this.dates = new ArrayList<Date>();
	}
	
	public DateCollection(Apartment apartment, boolean deleted, List<Date> dates) {
		super();
		this.dates = dates;
		this.deleted = deleted;
		this.apartment = apartment;
	}

//Getters and Setters	
	public List<Date> getDates() {
		if (dates == null)
			dates = new ArrayList<Date>();
		return dates;
	}

	public void setDates(List<Date> newDates) {
		removeAllDates();
		for (Iterator<Date> iter = newDates.iterator(); iter.hasNext();)
			addDates((Date)iter.next());
	}
	   
	public void addDates(Date newDate) {
		if (newDate == null)
			return;
		if (this.dates == null)
			this.dates = new ArrayList<Date>();
		if (!this.dates.contains(newDate))
			this.dates.add(newDate);
	   }
	   
	public void removeDates(Date oldDate) {
		if (oldDate == null)
			return;
		if (this.dates != null)
			if (this.dates.contains(oldDate))
				this.dates.remove(oldDate);
	}
	   
	public void removeAllDates() {
		if (dates != null)
			dates.clear();
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
		 this.setDeleted(true);
	 }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DateCollection other = (DateCollection) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
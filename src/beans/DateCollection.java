/***********************************************************************
 * Module:  DateCollection.java
 * Author:  Geri
 * Purpose: Defines the Class DateCollection
 ***********************************************************************/

package beans;

import java.text.DateFormat;
import java.util.*;

import beans.enums.DateStatus;
import beans.interfaces.IDeletable;
import beans.interfaces.IIdentifiable;
import java.util.stream.Collectors;

public class DateCollection implements IIdentifiable, IDeletable {
   private Map<Date, DateStatus> dates;
   private long id;
   private boolean deleted;
   
   private Apartment apartment;
   

 //Constructors
	public DateCollection(long id, Apartment apartment, boolean deleted, Map<Date, DateStatus> dates) {
		super();
		this.dates = dates;
		this.id = id;
		this.deleted = deleted;
		this.apartment = apartment;
	}
	
	public DateCollection() {
		super();
		
		this.dates = new HashMap<Date, DateStatus>();
	}
	
	public DateCollection(Apartment apartment, boolean deleted, Map<Date, DateStatus> dates) {
		super();
		this.dates = dates;
		this.deleted = deleted;
		this.apartment = apartment;
	}

//Getters and Setters	
	public Map<Date, DateStatus> getDates() {
		if (dates == null)
			dates = new HashMap<Date, DateStatus>();
		return dates;
	}

	public void setDates(Map<Date, DateStatus> newDates) {
		removeAllDates();
		for (Date key : newDates.keySet())
			addDate(key, newDates.get(key));
	}
	   
	public void addDate(Date newDate, DateStatus newStatus) {
		if (newDate == null)
			return;
		if (this.dates == null)
			this.dates = new HashMap<Date, DateStatus>();
		if (!this.dates.containsKey(newDate))
			this.dates.put(newDate, newStatus);
	   }
	   
	public void removeDate(Date oldDate) {
		if (oldDate == null)
			return;
		if (this.dates != null)
			if (this.dates.containsKey(oldDate))
				this.dates.remove(oldDate);
	}
	   
	public void removeAllDates() {
		if (dates != null)
			dates.clear();
	}
	
	public void addAvailableForBookingDate(Date date) throws IllegalArgumentException {
		if(dates.containsKey(date)) {
			if(dates.get(date) == DateStatus.booked) {
				throw new IllegalArgumentException("Date " + DateFormat.getInstance().format(date) + " can't be added, it is already booked.");
			}
		}
		else {
			dates.put(date, DateStatus.free);
		}
	}
	
	public void removeAvailableForBookingDate(Date date) throws NoSuchElementException, IllegalArgumentException {
		if(dates.containsKey(date)) {
			if(dates.get(date) == DateStatus.free) {
				dates.remove(date);
			}
			else {
				throw new IllegalArgumentException("Date " + DateFormat.getInstance().format(date) + " can't be removed, it is already booked.");
			}
		}
		else {
			throw new NoSuchElementException("Date " + DateFormat.getInstance().format(date) + " can't be removed, it was previously not added as available for booking.");
		}
	}
	
	public void addBookedDate(Date date) throws IllegalArgumentException, NoSuchElementException {
		if(dates.containsKey(date)) {
			if(dates.get(date) == DateStatus.free) {
				dates.put(date, DateStatus.booked);
			}
			else {
				throw new IllegalArgumentException("Date " + DateFormat.getInstance().format(date) + " is already booked.");
			}
		}
		else {
			throw new NoSuchElementException("Date " + DateFormat.getInstance().format(date) + " is not available for booking.");
		}
	}
	
	public void removeBookedDate(Date date) {
		if(dates.containsKey(date)) {
			if(dates.get(date) == DateStatus.booked) {
				dates.put(date, DateStatus.free);
			}
			else {
				throw new IllegalArgumentException("Date " + DateFormat.getInstance().format(date) + " can't be unbooked, it was previously not booked.");
			}
		}
		else {
			throw new NoSuchElementException("Date " + DateFormat.getInstance().format(date) + " can't be unbooked, it was prevoiusly not found in the system.");			
		}
	}
	
	public List<Date> getAvailableDates(){
		return dates.entrySet().stream().filter(e -> DateStatus.free.equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
	}
	
	public List<Date> getBookedDates(){
		return dates.entrySet().stream().filter(e -> DateStatus.booked.equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
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
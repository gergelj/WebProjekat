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
import exceptions.InvalidDateException;

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
	   
	private void addDate(Date newDate, DateStatus newStatus) {
		if (newDate == null)
			return;
		if (this.dates == null)
			this.dates = new HashMap<Date, DateStatus>();
		if (!this.dates.containsKey(newDate))
			this.dates.put(newDate, newStatus);
	   }
	   
	private void removeDate(Date oldDate) {
		if (oldDate == null)
			return;
		if (this.dates != null)
			if (this.dates.containsKey(oldDate))
				this.dates.remove(oldDate);
	}
	   
	private void removeAllDates() {
		if (dates != null)
			dates.clear();
	}
	
	public void addAvailableForBookingDate(Date date) throws InvalidDateException {
		if(dates.containsKey(date)) {
			if(dates.get(date) != DateStatus.free && dates.get(date) != DateStatus.undefined) {
				throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " can't be added, it is already booked.");
			}
		}
		else {
			dates.put(date, DateStatus.free);
		}
	}
	
	public void removeAvailableForBookingDate(Date date) throws InvalidDateException {
		if(dates.containsKey(date)) {
			if(dates.get(date) == DateStatus.free)
				dates.remove(date);
			else
				throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " can't be removed, it is already booked.");
		}
		else {
			throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " can't be removed, it was previously not added as available for booking.");
		}
	}
	
	private void addBookedDate(Date date) throws InvalidDateException {
		if(dates.containsKey(date)) {
			if(dates.get(date) == DateStatus.free)
				dates.put(date, DateStatus.booked);
			else
				throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " is already booked.");
		}
		else {
			throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " is not available for booking.");
		}
	}
	
	private void addCheckInDate(Date date) throws InvalidDateException {
		if(dates.containsKey(date)) {
			if(dates.get(date) == DateStatus.free) 
				dates.put(date, DateStatus.checkIn);
			else if(dates.get(date) == DateStatus.checkOut)
				dates.put(date, DateStatus.checkInOut);
			else
				throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " is already booked.");
		}
		else {
			throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " is not available for booking.");
		}
	}
	
	private void addCheckOutDate(Date date) throws InvalidDateException {
		if(dates.containsKey(date)) {
			if(dates.get(date) == DateStatus.free)
				dates.put(date, DateStatus.checkOut);
			else if(dates.get(date) == DateStatus.checkIn)
				dates.put(date, DateStatus.checkInOut);
			else {
				throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " is already booked.");
			}
		}
		else {
			throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " is not available for booking.");
		}
	}
	
	private void removeBookedDate(Date date) throws InvalidDateException {
		if(dates.containsKey(date)) {
			if(dates.get(date) == DateStatus.booked)
				dates.put(date, DateStatus.free);
			else if(dates.get(date) == DateStatus.free)
				throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " can't be unbooked, it was previously not booked.");
			else
				throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " can't be unbooked, it is not part of the same booking.");
		}
		else {
			throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " can't be unbooked, it was prevoiusly not found in the system.");
		}
	}
	
	private void removeCheckInDate(Date date) throws InvalidDateException {
		if(dates.containsKey(date)) {
			if(dates.get(date) == DateStatus.checkIn)
				dates.put(date, DateStatus.free);
			else if(dates.get(date) == DateStatus.checkInOut)
				dates.put(date, DateStatus.checkOut);
			else if(dates.get(date) == DateStatus.booked || dates.get(date) == DateStatus.checkOut)
				throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " can't be unbooked, it is not a check-in date.");
			else
				throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " can't be unbooked, it was previously not booked.");
		}
		else {
			throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " can't be unbooked, it was prevoiusly not found in the system.");
		}
	}
	
	private void removeCheckOutDate(Date date) throws InvalidDateException {
		if(dates.containsKey(date)) {
			if(dates.get(date) == DateStatus.checkOut)
				dates.put(date, DateStatus.free);
			else if(dates.get(date) == DateStatus.checkInOut)
				dates.put(date, DateStatus.checkIn);
			else if(dates.get(date) == DateStatus.booked || dates.get(date) == DateStatus.checkIn)
				throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " can't be unbooked, it is not a check-out date.");
			else
				throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " can't be unbooked, it was previously not booked.");				
		}
		else {
			throw new InvalidDateException("Date " + DateFormat.getDateInstance(DateFormat.LONG).format(date) + " can't be unbooked, it was prevoiusly not found in the system.");			
		}
	}
	
	public void addBooking(DateRange range) throws InvalidDateException {
		List<Date> dates = range.toList();
		
		if(dates.isEmpty())
			throw new InvalidDateException("Booking interval is empty");
		
		Date checkInDate = dates.get(0);
		Date checkOutDate = dates.get(dates.size() - 1);
		
		addCheckInDate(checkInDate);
		addCheckOutDate(checkOutDate);
		
		for(int i=1; i<dates.size()-1; i++)
			addBookedDate(dates.get(i));
	}
	
	public void removeBooking(DateRange range) throws InvalidDateException {
		List<Date> dates = range.toList();
		
		if(dates.isEmpty())
			throw new InvalidDateException("Booking interval is empty");
		
		Date checkInDate = dates.get(0);
		Date checkOutDate = dates.get(dates.size() - 1);
		
		removeCheckInDate(checkInDate);
		removeCheckOutDate(checkOutDate);
		
		for(int i=1; i<dates.size()-1; i++)
			removeBookedDate(dates.get(i));
	}
	
	// Note: List of dates which the HOST marked as available for booking
	public List<Date> getAvailableDates(){
		return dates.entrySet().stream().filter(e -> DateStatus.free.equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
	}
	
	// Note: List of dates that the HOST cannot unmark as available for booking
	public List<Date> getUnavailableDates(){
		return dates.entrySet().stream().filter(e -> !DateStatus.free.equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
	}
	
	// Note: List of dates that the GUEST can book
	public List<Date> getAvailableBookingDates(){
		return dates.entrySet().stream().filter(e -> DateStatus.free.equals(e.getValue()) || DateStatus.checkIn.equals(e.getValue()) || DateStatus.checkOut.equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
	}
	
	// Note: List of dates that the GUEST cannot book
	public List<Date> getUnavailableBookingDates(){
		return dates.entrySet().stream().filter(e -> DateStatus.booked.equals(e.getValue()) || DateStatus.checkInOut.equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
	}
	
	public List<Date> getCheckInDays(){		
		return dates.entrySet().stream().filter(e -> DateStatus.checkIn.equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
	}
	
	public List<Date> getCheckOutDays(){		
		return dates.entrySet().stream().filter(e -> DateStatus.checkOut.equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
	}
	
	public List<Date> getCheckInOutDays(){		
		return dates.entrySet().stream().filter(e -> DateStatus.checkInOut.equals(e.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
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

	public boolean isBookingAvailableFor(DateRange dateRange) {
		List<Date> dates = dateRange.toList();
		
		if(dates.isEmpty())
			return true;
		
		Date checkInDate = dates.get(0);
		Date checkOutDate = dates.get(dates.size() - 1);
		
		if(this.dates.containsKey(checkInDate)) {
			if(this.dates.get(checkInDate) != DateStatus.free && this.dates.get(checkInDate) != DateStatus.checkOut)
				return false;
		}
		else {
			return false;
		}
		
		if(this.dates.containsKey(checkOutDate)) {
			if(this.dates.get(checkOutDate) != DateStatus.free && this.dates.get(checkOutDate) != DateStatus.checkIn)
				return false;
		}
		else {
			return false;
		}
		
		for(int i=1; i<dates.size()-1; i++) {
			Date d = dates.get(i);
			if(this.dates.containsKey(d)) {
				if(this.dates.get(d) != DateStatus.free)
					return false;
			}
			else {
				return false;
			}
		}
		
		return true;
	}

}
/***********************************************************************
 * Module:  Reservation.java
 * Author:  Geri
 * Purpose: Defines the Class Reservation
 ***********************************************************************/

package beans;

import java.util.*;

import beans.enums.ReservationStatus;
import beans.interfaces.IDeletable;
import beans.interfaces.IIdentifiable;

public class Reservation implements IDeletable, IIdentifiable {
   private Date checkIn;
   private int nights;
   private double totalPrice;
   private String message;
   private long id;
   private boolean deleted;
   
   private ReservationStatus reservationStatus;
   private Apartment apartment;
   private User guest;
   
//Constructors
	public Reservation(long id, Apartment apartment, User guest, Date checkIn, int nights, double totalPrice, String message, boolean deleted,
			ReservationStatus reservationStatus) {
		super();
		this.checkIn = checkIn;
		this.nights = nights;
		this.totalPrice = totalPrice;
		this.message = message;
		this.id = id;
		this.deleted = deleted;
		this.reservationStatus = reservationStatus;
		this.apartment = apartment;
		this.guest = guest;
	}
	
	public Reservation() {
		super();
		
		this.apartment = new Apartment();
		this.guest = new User();
	}
	
	public Reservation(long id) {
		this.id = id;
		
		this.apartment = new Apartment();
		this.guest = new User();
	}
	
	public Reservation(Apartment apartment, User guest, Date checkIn, int nights, double totalPrice, String message, boolean deleted,
			ReservationStatus reservationStatus) {
		super();
		this.checkIn = checkIn;
		this.nights = nights;
		this.totalPrice = totalPrice;
		this.message = message;
		this.deleted = deleted;
		this.reservationStatus = reservationStatus;
		this.apartment = apartment;
		this.guest = guest;
	}
	
//Getters and Setters
	public Date getCheckIn() {
		return checkIn;
	}
	
	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}
	
	public int getNights() {
		return nights;
	}
	
	public void setNights(int nights) {
		this.nights = nights;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}
	
	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	public Apartment getApartment() {
		return apartment;
	}
	
	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}
	
	public User getGuest() {
		return guest;
	}
	
	public void setGuest(User guest) {
		this.guest = guest;
	}   
	
	public long getId() {
	    return this.id;
	}
			   
	public void setId(long id) {
	    this.id = id;
    }
	
	public DateRange getDateRange() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(checkIn);
		cal.add(Calendar.DAY_OF_MONTH, nights);
		Date checkOut = cal.getTime();
		return new DateRange(checkIn, checkOut);
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
		Reservation other = (Reservation) obj;
		if (id != other.id)
			return false;
		return true;
	}

}

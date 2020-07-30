/***********************************************************************
 * Module:  Reservation.java
 * Author:  Geri
 * Purpose: Defines the Class Reservation
 ***********************************************************************/

package beans;

import java.util.*;

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
   
   public long getId() {
	      return this.id;
   }
	   
   public void setId(long id) {
	  this.id = id;
   }
   
   public void delete() {
      // TODO: implement
   }

}
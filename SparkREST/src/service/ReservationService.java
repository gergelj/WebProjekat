/***********************************************************************
 * Module:  ReservationService.java
 * Author:  Geri
 * Purpose: Defines the Class ReservationService
 ***********************************************************************/

package service;

import repository.ReservationRepository;
import repository.AvailableDateCollectionRepository;
import repository.BookingDateCollectionRepository;
import beans.User;
import beans.Reservation;
import beans.Apartment;
import java.util.*;

public class ReservationService {
   private ReservationRepository reservationRepository;
   private AvailableDateCollectionRepository availableDateCollectionRepository;
   private BookingDateCollectionRepository bookingDateCollectionRepository;
   
   public List<Reservation> getReservationByGuest(User guest) {
      // TODO: implement
      return null;
   }
   
   public void cancelReservation(Reservation reservation) {
      // TODO: implement
   }
   
   public List<Reservation> getReservationByHost(User host) {
      // TODO: implement
      return null;
   }
   
   public void acceptReservation(Reservation reservation) {
      // TODO: implement
   }
   
   public void rejectReservation(Reservation reservation) {
      // TODO: implement
   }
   
   public void finishReservation(Reservation reservation) {
      // TODO: implement
   }
   
   public List<Reservation> getAll() {
      // TODO: implement
      return null;
   }
   
   public Reservation create(Reservation reservation) {
      // TODO: implement
      return null;
   }
   
   public List<Date> getAvailableDatesForApartment(Apartment apartment) {
      // TODO: implement
      return null;
   }
   
   public List<Reservation> getReservationByUsername(String username) {
      // TODO: implement
      return null;
   }

}
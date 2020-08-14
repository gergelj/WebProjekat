/***********************************************************************
 * Module:  ReservationService.java
 * Author:  Geri
 * Purpose: Defines the Class ReservationService
 ***********************************************************************/

package service;

import repository.ReservationRepository;
import repository.ApartmentRepository;
import repository.DateCollectionRepository;
import beans.User;
import beans.enums.ReservationStatus;
import beans.enums.UserType;
import exceptions.DatabaseException;
import exceptions.InvalidUserException;
import beans.Reservation;
import beans.Apartment;
import beans.DateCollection;

import java.util.*;

public class ReservationService {
   private ReservationRepository reservationRepository;
   private DateCollectionRepository dateCollectionRepository;
   private ApartmentRepository apartmentRepository;


   
//Constructors 
   public ReservationService(ReservationRepository reservationRepository, DateCollectionRepository dateCollectionRepository, ApartmentRepository apartmentRepository) {
	super();
	this.reservationRepository = reservationRepository;
	this.dateCollectionRepository = dateCollectionRepository;
	this.apartmentRepository = apartmentRepository;
   }

//Methods
   
   /** Returns a list of Reservations by guest<br><br>
    *  
    *  <b>Called by:</b> guest, admin or host<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public List<Reservation> getReservationByGuest(User guest) throws DatabaseException, InvalidUserException {
      if(guest.getUserType() != UserType.undefined)
      {
    	  List<Reservation> allReservations = reservationRepository.getAllEager();
    	  List<Reservation> retVal = new ArrayList<Reservation>();
    	  
    	  for(Reservation reservation: allReservations)
    	  {
    		  if(reservation.getGuest().getId() == guest.getId())
    		  {
    			  retVal.add(reservation);
    		  }
    	  }
    	  
    	  return retVal;
      }
      throw new InvalidUserException();
   }
   
   /** Changes status <b>reservation status</b> of reservation <br><br>
    *  
    *  <b>Called by:</b> host or guest<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   
   public void cancelReservation(Reservation reservation, UserType userType) throws DatabaseException, InvalidUserException {
	   if(reservation.getReservationStatus() == ReservationStatus.accepted || reservation.getReservationStatus() == ReservationStatus.created)
	   {
		   if(userType == UserType.host)
		   {
			   rejectReservation(reservation, userType);
		   }
		   else if(userType == UserType.guest)
		   {
			   reservation.setReservationStatus(ReservationStatus.cancelled);
		   }
		   else
		   {
			   throw new InvalidUserException();
		   }
		   reservationRepository.update(reservation);
	   }
	   //TODO: proveriti da li treba da baci neki excpetion ako nije accepted ili created
   }
   
   
   /** Returns a list of reservation by <b>host</b> <br><br>
    *  
    *  <b>Called by:</b> host or admin<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public List<Reservation> getReservationByHost(User host, UserType userType) throws InvalidUserException, DatabaseException {
	   if(userType == UserType.admin || userType == UserType.host)
	   {		  
		   List<Reservation> reservations = reservationRepository.getAllEager();
		   List<Reservation> retVal = new ArrayList<Reservation>();
		   
		   for(Reservation reservation: reservations)
		   {
			   if(reservation.getApartment().getHost().getId() == host.getId())
			   {
				   retVal.add(reservation);
			   }
		   }
		   return retVal;
	   }
	   throw new InvalidUserException();
   }
   
   /** Changes status <b>reservation status</b> to <b>accepted</b> <br><br>
    *  
    *  <b>Called by:</b> host<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public void acceptReservation(Reservation reservation, UserType userType) throws DatabaseException, InvalidUserException {
	   if(userType == UserType.host)
	   {
		   if(reservation.getReservationStatus() == ReservationStatus.created)
		   {
			   reservation.setReservationStatus(ReservationStatus.accepted);
			   reservationRepository.update(reservation);
		   }
		   //TODO: proveriti da li u suprotnom slcuaju treba da baci neki exception
	   }
	   throw new InvalidUserException();
   }
   
   
   /** Changes status <b>reservation status</b> to <b>rejected</b> <br><br>
    *  
    *  <b>Called by:</b> host<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public void rejectReservation(Reservation reservation, UserType userType) throws DatabaseException, InvalidUserException {
	   if(userType == UserType.host)
	   {
		   if(reservation.getReservationStatus() == ReservationStatus.created || reservation.getReservationStatus() == ReservationStatus.accepted)
		   {
			   reservation.setReservationStatus(ReservationStatus.rejected);
			   reservationRepository.update(reservation);
		   }
		   //TODO: provetiti da li u suprotnom terba da baci neki exception
	   }
	   throw new InvalidUserException();
	   
   }
   
   /** Changes status <b>reservation status</b> to <b>finished</b> <br><br>
    *  
    *  <b>Called by:</b> host<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public void finishReservation(Reservation reservation, UserType userType) throws DatabaseException, InvalidUserException {
	   if(userType == UserType.host)
	   {
		   Date checkOutDate = reservation.getCheckIn();
		   checkOutDate.setDate(reservation.getCheckIn().getDate()+reservation.getNights());
		   
		   if(checkOutDate.compareTo(new Date()) <= 0);
		   {
			  reservation.setReservationStatus(ReservationStatus.finished);
			  reservationRepository.update(reservation);
		   }
		   //TODO: proveriti da li baca exception ako nije prosao datum
	   }
	   
	   throw new InvalidUserException();
   }
   
   public List<Reservation> getAll() throws DatabaseException {
      return reservationRepository.getAllEager();
   }
   
   /** Creates a new <b>reservation</b> <br><br>
    *  
    *  <b>Called by:</b> guest<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public void create(Reservation reservation, UserType userType) throws InvalidUserException, DatabaseException {
      if(userType == UserType.guest)
      {
    	 reservationRepository.create(reservation);
      }
      throw new InvalidUserException();
   }
   
   
   /** Returns all <b>available dates</b> for specified apartment<br><br>
    *  
    *  <b>Called by:</b> guest, host, admin or undefined user<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public List<Date> getAvailableDatesForApartment(Apartment apartment) throws DatabaseException {
      return apartmentRepository.getAvailableDatesForApartment(apartment);
   }
   
   
   /** Returns a list of <b>reservations</b> made by specific <b>username</b><br><br>
    *  
    *  <b>Called by:</b> host, admin<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public List<Reservation> getReservationByUsername(String username, UserType userType) throws DatabaseException, InvalidUserException {
	   if(userType == UserType.host || userType == UserType.admin)
	   {
		   List<Reservation> allReservations = reservationRepository.getAllEager();
		   List<Reservation> retVal = new ArrayList<Reservation>();
		   
		   for(Reservation reservation: allReservations)
		   {
			   if(reservation.getGuest().getAccount().getUsername().equals(username))
				   retVal.add(reservation);
		   }
		   
		   return retVal;
	   }
	   throw new InvalidUserException();
   }

}
/***********************************************************************
 * Module:  ReservationService.java
 * Author:  Geri
 * Purpose: Defines the Class ReservationService
 ***********************************************************************/

package service;

import repository.ReservationRepository;
import repository.abstractrepository.IApartmentRepository;
import repository.abstractrepository.IDateCollectionRepository;
import repository.abstractrepository.IPricingCalendarRepository;
import repository.abstractrepository.IUserRepository;
import beans.User;
import beans.enums.DayOfWeek;
import beans.enums.ReservationStatus;
import beans.enums.UserType;
import dto.BookingDatesDTO;
import dto.ReservationDTO;
import exceptions.BadRequestException;
import exceptions.DatabaseException;
import exceptions.InvalidDateException;
import exceptions.InvalidUserException;
import beans.Apartment;
import beans.DateCollection;
import beans.DateRange;
import beans.PricingCalendar;
import beans.Reservation;
import java.util.*;

public class ReservationService {
   private ReservationRepository reservationRepository;
   private IDateCollectionRepository dateCollectionRepository;
   private IPricingCalendarRepository pricingCalendarRepository;
   private IApartmentRepository apartmentRepository;
   private IUserRepository userRepository;
   
   //Constructors 
   public ReservationService(ReservationRepository reservationRepository, IDateCollectionRepository dateCollectionRepository, IPricingCalendarRepository pricingCalendarRepository, IApartmentRepository apartmentRepository, IUserRepository userRepository) {
	   super();
	   this.reservationRepository = reservationRepository;
	   this.dateCollectionRepository = dateCollectionRepository;
	   this.pricingCalendarRepository = pricingCalendarRepository;
	   this.apartmentRepository = apartmentRepository;
	   this.userRepository = userRepository;
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
    * @throws BadRequestException 
    */
   public void create(ReservationDTO reservation, User user) throws InvalidUserException, DatabaseException, BadRequestException {
      if(user.getUserType() == UserType.guest) {
    	  DateCollection dc = dateCollectionRepository.getByApartmentId(reservation.getApartmentId());
    	  dc.addBooking(reservation.getDateRange());
    	  dateCollectionRepository.update(dc);
    	  
    	  Apartment apartment = apartmentRepository.getById(reservation.getApartmentId());
    	  
    	  PricingCalendar calendar = pricingCalendarRepository.getAll().get(0);
    	  double totalPrice = calendar.getTotalPrice(reservation.getDateRange(), apartment.getPricePerNight());
    	  
    	  User guest = userRepository.getById(user.getId());
    	  
    	  Reservation res = new Reservation(apartment, guest, reservation.getDateRange().getStart(), reservation.getNights(), totalPrice, reservation.getMessage(), false, ReservationStatus.created);
    	  reservationRepository.create(res);
      }
      else {    	  
    	  throw new InvalidUserException();
      }
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
   
   public List<Date> getAvailableDatesByApartment(long apartmentId, User user) throws InvalidUserException, DatabaseException{
	   if(user.getUserType() != UserType.undefined) {
		   DateCollection dc = dateCollectionRepository.getByApartmentId(apartmentId);
		   if(user.getUserType() == UserType.guest)
			   return dc.getAvailableBookingDates();
		   else
			   return dc.getAvailableDates();
	   }
	   else {
		   throw new InvalidUserException();
	   }

   }
   
   public List<Date> getUnavailableDatesByApartment(long apartmentId, User user) throws InvalidUserException, DatabaseException{
	   if(user.getUserType() != UserType.undefined) {
		   DateCollection dc = dateCollectionRepository.getByApartmentId(apartmentId);
		   if(user.getUserType() == UserType.guest)
			   return dc.getUnavailableBookingDates();
		   else
			   return dc.getUnavailableDates();
	   }
	   else {
		   throw new InvalidUserException();
	   }

   }

   public PricingCalendar getPricingCalendar(User user) throws InvalidUserException, DatabaseException {
	   if(user.getUserType() == UserType.admin) {
		   
		   List<PricingCalendar> list = pricingCalendarRepository.getAll();
		   if(list.isEmpty()) {
			   Map<DayOfWeek, Double> map = new HashMap<DayOfWeek, Double>();
			   map.put(DayOfWeek.monday, 1.0);
			   map.put(DayOfWeek.tuesday, 1.0);
			   map.put(DayOfWeek.wednesday, 1.0);
			   map.put(DayOfWeek.thursday, 1.0);
			   map.put(DayOfWeek.friday, 1.0);
			   map.put(DayOfWeek.saturday, 1.0);
			   map.put(DayOfWeek.sunday, 1.0);
			   PricingCalendar pc = new PricingCalendar(false, map, null, 1);
			   pc = pricingCalendarRepository.create(pc);
			   return pc;
		   }
		   else {
			   return list.get(0);
		   }
		   
	   }
	   else {
		   throw new InvalidUserException();
	   }
   }

   public void updatePricingCalendar(PricingCalendar pricingCalendar, User user) throws InvalidUserException, DatabaseException {
	   if(user.getUserType() == UserType.admin) {
		   pricingCalendarRepository.update(pricingCalendar);
	   }
	   else
		   throw new InvalidUserException();
   }
   
   public List<Reservation> getReservationsByApartment(long apartmentId, User user) throws InvalidUserException, DatabaseException
   {
	   if(user.getUserType() != UserType.undefined || user != null)
	   {
		   List<Reservation> allReservations = reservationRepository.getAllEager();
		   List<Reservation> retVal = new ArrayList<Reservation>();
		   if(user.getUserType() == UserType.admin || user.getUserType() == UserType.host)
		   {
			   for(Reservation res: allReservations)
			   {
				   if(res.getApartment().getId() == apartmentId)
					   retVal.add(res);
			   }
		   }
		   else if(user.getUserType() == UserType.guest)
		   {
			   for(Reservation res: allReservations)
			   {
				   if(res.getApartment().getId() == apartmentId && res.getGuest().getId() == user.getId())
					   retVal.add(res);
			   }
		   }
		   
		   for(Reservation res: retVal)
		   {
			   res.getGuest().getAccount().setPassword("");
		   }
		   
		   return retVal;
	   }
	   else
		   throw new InvalidUserException();
   }

   public BookingDatesDTO getBookingDatesInfo(long apartmentId, User user) throws InvalidUserException, DatabaseException {
	   if(user.getUserType() != UserType.undefined) {
		   DateCollection dc = dateCollectionRepository.getByApartmentId(apartmentId);
		   return new BookingDatesDTO(dc.getCheckInDays(), dc.getCheckOutDays(), dc.getCheckInOutDays());
	   }
	   else {
		   throw new InvalidUserException();
	   }
   }

	public double getTotalPrice(User user, long apartmentId, DateRange dateRange) throws BadRequestException, DatabaseException {
		if(user.getUserType() == UserType.guest) {
			DateCollection dc = dateCollectionRepository.getByApartmentId(apartmentId);
			
			dc.addBooking(dateRange);
			
			PricingCalendar cal = pricingCalendarRepository.getAll().get(0);
			Apartment a = apartmentRepository.getById(apartmentId);
			return cal.getTotalPrice(dateRange, a.getPricePerNight());
		}
		else {
			throw new InvalidUserException();
		}
	}

}
/***********************************************************************
 * Module:  ReservationRepository.java
 * Author:  Geri
 * Purpose: Defines the Class ReservationRepository
 ***********************************************************************/

package repository;

import java.util.*;

import beans.Apartment;
import beans.Reservation;
import beans.User;
import exceptions.DatabaseException;
import repository.abstractrepository.IReservationRepository;
import repository.csv.CSVRepository;
import repository.csv.IEagerCsvRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

public class ReservationRepository extends CSVRepository<Reservation> implements IReservationRepository, IEagerCsvRepository<Reservation> {
   
	private IEagerCsvRepository<Apartment> apartmentRepository;
	private IEagerCsvRepository<User> userRepository;
	
	public ReservationRepository(ICsvStream<Reservation> stream, LongSequencer sequencer, IEagerCsvRepository<Apartment> apartmentRepository, IEagerCsvRepository<User> userRepository) throws DatabaseException {
		super("Reservation", stream, sequencer);
		this.apartmentRepository = apartmentRepository;
		this.userRepository = userRepository;
	}
   
   private void bind(List<Reservation> reservations) throws DatabaseException {
	   for(Reservation reservation: reservations)
	   {
		   reservation.setApartment(getApartmentById(reservation.getApartment()));
		   reservation.setGuest(getGuestById(reservation.getGuest()));
	   }
   }
   
   public Reservation getEager(long id) throws DatabaseException {
	   Reservation reservation = getById(id);
	   
	   reservation.setApartment(getApartmentById(reservation.getApartment()));
	   reservation.setGuest(getGuestById(reservation.getGuest()));
	   
	   return reservation;
   }
   
   public List<Reservation> getAllEager() throws DatabaseException {
	   List<Reservation> reservations = getAll();
	   
	   bind(reservations);
	   
	   return reservations;
   }
   
   private Apartment getApartmentById(Apartment apartment) throws DatabaseException {
	   return apartment == null ? null : apartmentRepository.getEager(apartment.getId());
   }

   private User getGuestById(User guest) throws DatabaseException {
	   return guest == null? null : userRepository.getEager(guest.getId()); 
   }
}
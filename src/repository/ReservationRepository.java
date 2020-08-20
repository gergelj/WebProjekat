/***********************************************************************
 * Module:  ReservationRepository.java
 * Author:  Geri
 * Purpose: Defines the Class ReservationRepository
 ***********************************************************************/

package repository;

import java.util.*;

import beans.Apartment;
import beans.Comment;
import beans.Reservation;
import beans.User;
import exceptions.DatabaseException;
import exceptions.EntityNotFoundException;
import repository.abstractrepository.IReservationRepository;
import repository.csv.CSVRepository;
import repository.csv.IEagerCsvRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

public class ReservationRepository extends CSVRepository<Reservation> implements IReservationRepository, IEagerCsvRepository<Reservation> {
   
	private IEagerCsvRepository<Apartment> apartmentRepository;
	private IEagerCsvRepository<User> userRepository;
	private IEagerCsvRepository<Comment> commentRepository;
	
	public ReservationRepository(ICsvStream<Reservation> stream, LongSequencer sequencer, IEagerCsvRepository<Apartment> apartmentRepository, IEagerCsvRepository<User> userRepository, IEagerCsvRepository<Comment> commentRepository) throws DatabaseException {
		super("Reservation", stream, sequencer);
		this.apartmentRepository = apartmentRepository;
		this.userRepository = userRepository;
		this.commentRepository = commentRepository;
	}
   
   private void bind(List<Reservation> reservations) throws DatabaseException {
	   for(Reservation reservation: reservations)
	   {
		   reservation.setApartment(getApartmentById(reservation.getApartment()));
		   reservation.setGuest(getGuestById(reservation.getGuest()));
		   reservation.setComment(getCommentById(reservation.getComment()));
	   }
   }
   
   public Reservation getEager(long id) throws DatabaseException {
	   Reservation reservation = getById(id);
	   
	   reservation.setApartment(getApartmentById(reservation.getApartment()));
	   reservation.setGuest(getGuestById(reservation.getGuest()));
	   reservation.setComment(getCommentById(reservation.getComment()));
	   
	   return reservation;
   }
   
   private Comment getCommentById(Comment comment) throws DatabaseException {
	   if(comment == null)
		   return new Comment(0);
	   
	   if(comment.getId() == 0)
		   return comment;
	   
	   return commentRepository.getEager(comment.getId());
   }

   public List<Reservation> getAllEager() throws DatabaseException {
	   List<Reservation> reservations = getAll();
	   
	   bind(reservations);
	   
	   return reservations;
   }
   
   private Apartment getApartmentById(Apartment apartment) throws DatabaseException {
	   if(apartment == null)
		   return null;
	   
	   try {
		   return apartmentRepository.getEager(apartment.getId());
	   }catch(EntityNotFoundException e) {
		   apartment.getLocation().getAddress().setStreet("Deleted apartment");
		   apartment.getLocation().getAddress().setHouseNumber("");
		   apartment.getLocation().getAddress().setCity("");
		   apartment.getLocation().getAddress().setPostalCode("");
		   return apartment;
	   }
	   
   }

   private User getGuestById(User guest) throws DatabaseException {
	   return guest == null? null : userRepository.getEager(guest.getId()); 
   }
}
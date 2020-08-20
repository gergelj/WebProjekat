/***********************************************************************
 * Module:  CommentService.java
 * Author:  Geri
 * Purpose: Defines the Class CommentService
 ***********************************************************************/

package service;

import repository.ApartmentRepository;
import repository.CommentRepository;
import repository.ReservationRepository;
import repository.UserRepository;
import beans.Apartment;
import beans.Comment;
import beans.Reservation;
import beans.User;
import beans.enums.UserType;
import exceptions.BadRequestException;
import exceptions.DatabaseException;
import exceptions.InvalidUserException;

import java.util.*;
import java.util.stream.Collectors;

public class CommentService {
   private CommentRepository commentRepository;
   private ApartmentRepository apartmentRepository;
   private UserRepository userRepository;
   private ReservationRepository reservationRepository;

//Constructors
   public CommentService(CommentRepository commentRepository, ApartmentRepository apartmentRepository, UserRepository userRepository, ReservationRepository reservationRepository) {
	   super();
	   this.commentRepository = commentRepository;
	   this.apartmentRepository = apartmentRepository;
	   this.userRepository = userRepository;
	   this.reservationRepository = reservationRepository;
   }

 //Methods
   /** Creates a new comment<br><br>
    *  
    *  <b>Called by:</b> guest<br>
    *  
    * @throws InvalidUserException
    * @throws DatabaseException 
    */
   public void create(Comment comment, long reservationId, User user) throws InvalidUserException, DatabaseException {
	   //TODO: proveriti da li treba da se proverava i status rezervacije
	   //      strana 8 - ostavaljanej komentara
	   //      return type je bio Comment?
	   //	   komentar treba da ude neodobren kad se kreira? - Da [approved = false]
	   if(user.getUserType() == UserType.guest) {
		   user = userRepository.getById(user.getId());
		   comment.setUser(user);
		   comment = commentRepository.create(comment);
		   Reservation res = reservationRepository.getById(reservationId);
		   res.setComment(comment);
		   reservationRepository.update(res);
	   }
	   else {		   
		   throw new InvalidUserException();
	   }
   }
   
   
   /** Changes approval status of comment<br><br>
    *  
    *  <b>Called by:</b> host<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public Comment approveComment(Comment comment, boolean toApprove, User host, long apartmentId) throws DatabaseException, InvalidUserException {
	   if(host.getUserType() == UserType.host) {
		   Apartment a = apartmentRepository.getById(apartmentId);
		   if(!a.getHost().equals(host))
			   throw new InvalidUserException();
		   
		   comment = commentRepository.getById(comment.getId());
		   comment.setApproved(toApprove);
		   commentRepository.update(comment);
		   return comment;
	   }
	   else {
		   throw new InvalidUserException();		   
	   }

   }

   //seldece metode su dodate
   
   /** Returns a list of all Comments for apartment<br><br>
    *  
    *  <b>Called by:</b> host or admin<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public List<Comment> getCommentsByApartment(long apartmentId, User user) throws DatabaseException, InvalidUserException {
	   
	   switch(user.getUserType()) {
	   case host:{
		   Apartment apartment = apartmentRepository.getById(apartmentId);
		   if(user.getUserType() == UserType.host && !apartment.getHost().equals(user)) {
			   throw new InvalidUserException();
		   }
	   }
	   case admin:{		   
		   return commentRepository.getCommentsByApartment(apartmentId);
	   } 
	   default: {
		   return commentRepository.getCommentsByApartment(apartmentId).stream().filter(c -> c.isApproved()).collect(Collectors.toList());		   
	   }
			   
	   }
	   
   }
   
   
   /** Returns a list of all <b>approved</b> Comments<br><br>
    *  
    *  <b>Called by:</b> host, guest, admin or undefined user<br><br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public List<Comment> getAllApprovedCommentsByApartment(Apartment apartment, User user)
   {
	   /*
	   List<Comment> retVal = new ArrayList<Comment>();
	
	   List<Comment> allComments = apartmentRepository.getAllComments(apartment);
	   
	   for(Comment comment: allComments)
	   {
		   if(comment.isApproved())
			   retVal.add(comment);
	   }
	   
	   return retVal;
	   */
	   return null;
   }
}
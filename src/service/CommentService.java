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
import beans.enums.ReservationStatus;
import beans.enums.UserType;
import dto.CommentDTO;
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
    * @throws DatabaseException 
 * @throws BadRequestException 
    */
   public Comment create(CommentDTO comment, User user) throws DatabaseException, BadRequestException {

	   if(user.getUserType() == UserType.guest) {
		   user = userRepository.getById(user.getId());
		   Reservation res = reservationRepository.getById(comment.getReservationId());
		   
		   if(res.getReservationStatus() == ReservationStatus.rejected || res.getReservationStatus() == ReservationStatus.finished) {			   
			   Comment newComment = new Comment(comment.getText(), comment.getRating(), false, false, user, res.getApartment().getId());
			   newComment = commentRepository.create(newComment);
			   res.setComment(newComment);
			   reservationRepository.update(res);
			   return newComment;
		   }
		   else {
			   throw new BadRequestException("Reservation status is not 'rejected' or 'finished'");
		   }
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
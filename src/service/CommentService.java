/***********************************************************************
 * Module:  CommentService.java
 * Author:  Geri
 * Purpose: Defines the Class CommentService
 ***********************************************************************/

package service;

import repository.ApartmentRepository;
import repository.CommentRepository;
import beans.Apartment;
import beans.Comment;
import beans.User;
import beans.enums.UserType;
import exceptions.DatabaseException;
import exceptions.InvalidUserException;

import java.util.*;

public class CommentService {
   private CommentRepository commentRepository;
   private ApartmentRepository apartmentRepository;

//Constructors
   public CommentService(CommentRepository commentRepository, ApartmentRepository apartmentRepository) {
	   super();
	   this.commentRepository = commentRepository;
	   this.apartmentRepository = apartmentRepository;
   }

 //Methods
   /** Creates a new comment<br><br>
    *  
    *  <b>Called by:</b> guest<br>
    *  
    * @throws InvalidUserException
    * @throws DatabaseException 
    */
   public void create(Comment comment, UserType userType) throws InvalidUserException, DatabaseException {
	   //TODO: proveriti da li treba da se proverava i status rezervacije
	   //      strana 8 - ostavaljanej komentara
	   //      return type je bio Comment?
	   //	   komentar treba da ude neodobren kad se kreira?
	   if(userType == UserType.guest)
	   {
		   commentRepository.create(comment);
	   }
	   throw new InvalidUserException();
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
	   throw new InvalidUserException();
	   
   }

   //seldece metode su dodate
   
   /** Returns a list of all Comments for apartment<br><br>
    *  
    *  <b>Called by:</b> host or admin<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public List<Comment> getAllCommentsByApartment(Apartment apartment, User user) throws DatabaseException, InvalidUserException
   {
	   List<Comment> retVal = new ArrayList<Comment>();
	   if(user.getUserType() == UserType.host && apartment.getHost().getId() == user.getId())
	   {
		   retVal =  apartment.getComments();
		   return retVal;
	   }
	   else if(user.getUserType() == UserType.admin)
	   {
		   retVal = apartment.getComments();
		   return retVal;
	   }
	   
	   throw new InvalidUserException();
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
	   List<Comment> retVal = new ArrayList<Comment>();
	
	   List<Comment> allComments = apartmentRepository.getAllComments(apartment);
	   
	   for(Comment comment: allComments)
	   {
		   if(comment.isApproved())
			   retVal.add(comment);
	   }
	   
	   return retVal;
   }
}
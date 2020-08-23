/***********************************************************************
 * Module:  CommentRepository.java
 * Author:  Geri
 * Purpose: Defines the Class CommentRepository
 ***********************************************************************/

package repository;

import java.util.*;
import java.util.stream.Collectors;

import beans.Comment;
import beans.User;
import exceptions.DatabaseException;
import exceptions.EntityNotFoundException;
import repository.abstractrepository.ICommentRepository;
import repository.abstractrepository.IUserRepository;
import repository.csv.CSVRepository;
import repository.csv.IEagerCsvRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

public class CommentRepository extends CSVRepository<Comment> implements ICommentRepository, IEagerCsvRepository<Comment> {
   
	private IUserRepository userRepository;
	
	public CommentRepository(ICsvStream<Comment> stream, LongSequencer sequencer, IUserRepository userRepository) throws DatabaseException {
		super("Comment", stream, sequencer);
		this.userRepository =  userRepository;
	}
   
   private void bind(List<Comment> comments) throws DatabaseException {
      for(Comment comment: comments)
      {
    	  comment.setUser(getUserById(comment.getUser()));
      }
   }
   
   public Comment getEager(long id) throws DatabaseException {
      Comment comment = getById(id);
      
      comment.setUser(getUserById(comment.getUser()));
      
      return comment;
   }
   
   public List<Comment> getAllEager() throws DatabaseException {
	  List<Comment> comments = getAll();
	  
	  bind(comments);
	  
      return comments;
   }

   
   private User getUserById(User user) throws DatabaseException {
	   if(user == null)
		   return null;
	   
	   try {
		   return userRepository.getById(user.getId());		   
	   }catch(EntityNotFoundException e) {
		   user.setName("Deleted user");
		   user.setDeleted(true);
		   return user;
	   }
   }

	@Override
	public List<Comment> getCommentsByApartment(long apartmentId) throws DatabaseException {
		return getAllEager().stream().filter(comment -> comment.getApartmentId() == apartmentId).collect(Collectors.toList());
	}
}
/***********************************************************************
 * Module:  CommentRepository.java
 * Author:  Geri
 * Purpose: Defines the Class CommentRepository
 ***********************************************************************/

package repository;

import java.util.*;

import beans.Comment;
import beans.User;
import exceptions.DatabaseException;
import repository.abstractrepository.ICommentRepository;
import repository.abstractrepository.IUserRepository;
import repository.csv.CSVRepository;
import repository.csv.IEagerCsvRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

public class CommentRepository extends CSVRepository<Comment> implements ICommentRepository, IEagerCsvRepository<Comment> {
   
	private IUserRepository userRepository;
	
	public CommentRepository(ICsvStream<Comment> stream, LongSequencer sequencer, IUserRepository userRepository) {
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
	   return user == null ? null : userRepository.getById(user.getId());
   }
}
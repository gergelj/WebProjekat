/***********************************************************************
 * Module:  CommentService.java
 * Author:  Geri
 * Purpose: Defines the Class CommentService
 ***********************************************************************/

package service;

import repository.CommentRepository;
import beans.Comment;
import java.util.*;

public class CommentService {
   private CommentRepository commentRepository;

//Constructors
   public CommentService(CommentRepository commentRepository) {
	   super();
	   this.commentRepository = commentRepository;
   }

 //Methods
   public Comment create(Comment comment) {
      // TODO: implement
      return null;
   }
   
   public void approveComment(Comment comment) {
      // TODO: implement
   }

}
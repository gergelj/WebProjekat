/***********************************************************************
 * Module:  CommentRepository.java
 * Author:  Geri
 * Purpose: Defines the Class CommentRepository
 ***********************************************************************/

package repository;

import java.util.*;

import beans.Comment;
import repository.abstractrepository.ICommentRepository;
import repository.csv.CSVRepository;
import repository.csv.IEagerCsvRepository;

public class CommentRepository extends CSVRepository<Comment,Long> implements ICommentRepository, IEagerCsvRepository<Comment,Long> {
   private String entityName;
   
   private void bind() {
      // TODO: implement
   }
   
   public Comment getEager(Long id) {
      // TODO: implement
      return null;
   }
   
   public List<Comment> getAllEager() {
      // TODO: implement
      return null;
   }

}
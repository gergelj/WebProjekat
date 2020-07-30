/***********************************************************************
 * Module:  CommentRepository.java
 * Author:  Geri
 * Purpose: Defines the Class CommentRepository
 ***********************************************************************/

package repository;

import java.util.*;

public class CommentRepository extends repository.csv.CSVRepository<Comment,Long> implements repository.abstract.ICommentRepository, repository.csv.IEagerCsvRepository<Comment,Long> {
   private String entityName;
   
   private void bind() {
      // TODO: implement
   }
   
   /** @param id */
   public T getEager(ID id) {
      // TODO: implement
      return null;
   }
   
   public List<T> getAllEager() {
      // TODO: implement
      return null;
   }

}
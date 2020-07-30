/***********************************************************************
 * Module:  CommentCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class CommentCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.util.*;

import beans.Comment;

public class CommentCsvConverter implements ICsvConverter<Comment> {
   private String delimiter;
   
   /** @param entity */
   public String toCsv(Comment entity) {
      // TODO: implement
      return null;
   }
   
   /** @param entityCsv */
   public Comment fromCsv(String entityCsv) {
      // TODO: implement
      return null;
   }

}
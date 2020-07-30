/***********************************************************************
 * Module:  CommentCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class CommentCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import beans.Comment;
import beans.User;

public class CommentCsvConverter implements ICsvConverter<Comment> {
   private String delimiter;
   
   
   /*
    * private long id;
   private String text;
   private int rating;
   private boolean deleted;
   private boolean approved;
   
   private User user;
    */
   
   public String toCsv(Comment entity){
      return String.join(delimiter, String.valueOf(entity.getId()), entity.getText(), String.valueOf(entity.getRating()), String.valueOf(entity.isDeleted()), String.valueOf(entity.isApproved()), String.valueOf(entity.getUser().getId()));
   }
   
   public Comment fromCsv(String entityCsv) {
      // TODO: implement
      return null;
   }

}
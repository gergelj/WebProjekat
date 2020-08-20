/***********************************************************************
  * Module:  CommentCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class CommentCsvConverter
 ***********************************************************************/

package repository.csv.converter;


import java.util.StringJoiner;

import beans.Comment;
import beans.User;

public class CommentCsvConverter implements ICsvConverter<Comment> {
   private String delimiter = "~";
   private String newLine = "`";
   
   public String toCsv(Comment entity){
	   StringJoiner joiner = new StringJoiner(delimiter);
	   
	   joiner.add(String.valueOf(entity.getId())); //0
	   joiner.add(entity.getText().replace("\n", newLine)); //1
	   joiner.add(String.valueOf(entity.getRating())); //2
	   joiner.add(String.valueOf(entity.isDeleted())); //3
	   joiner.add(String.valueOf(entity.isApproved())); //4
	   joiner.add(String.valueOf(entity.getUser() == null ? "" : entity.getUser().getId())); //5
	   joiner.add(String.valueOf(entity.getApartmentId())); //6
	   
      return joiner.toString();
   }
   
   public Comment fromCsv(String entityCsv) {
	  String[] tokens = entityCsv.split(delimiter);
	  
	  long id = Long.valueOf(tokens[0]);
	  String text = tokens[1].replace(newLine, "\n");
	  int rating = Integer.valueOf(tokens[2]);
	  boolean deleted = Boolean.valueOf(tokens[3]);
	  boolean approved = Boolean.valueOf(tokens[4]);
	  User user = new User(Long.valueOf(tokens[5]));
	  long apartmentId = Long.valueOf(tokens[6]);
	  
	  Comment retVal = new Comment(id, text, rating, deleted, approved, user, apartmentId);
	  
      return retVal;
   }

}
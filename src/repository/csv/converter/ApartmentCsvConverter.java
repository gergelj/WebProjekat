/***********************************************************************
 * Module:  ApartmentCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.util.List;
import java.util.StringJoiner;

import beans.Amenity;
import beans.Apartment;
import beans.ApartmentType;
import beans.Comment;
import beans.Location;
import beans.Picture;
import beans.User;

public class ApartmentCsvConverter implements ICsvConverter<Apartment> {
   private String delimiter;
   private String listDelimiter;
  
   public String toCsv(Apartment entity) {
      StringJoiner joiner = new StringJoiner(delimiter);
      
      joiner.add(String.valueOf(entity.getNumberOfRooms()));
      joiner.add(String.valueOf(entity.getNumberOfGuests()));
      joiner.add(String.valueOf(entity.getId()));
      joiner.add(String.valueOf(entity.getPricePerNight()));
      joiner.add(String.valueOf(entity.isDeleted()));
      joiner.add(String.valueOf(entity.isActive()));
      joiner.add(String.valueOf(entity.getCheckInHour()));
      joiner.add(String.valueOf(entity.getCheckOutHour()));
      joiner.add(String.valueOf(entity.getApartmentType()));
      joiner.add(String.valueOf(entity.getLocation()));
      joiner.add(String.valueOf(entity.getHost().getId()));
      
      joiner.add(getPictureListString(entity.getPictures()));
      joiner.add(getAmenityListString(entity.getAmenities()));
      joiner.add(getCommentListString(entity.getComments()));
      
      
      return joiner.toString();
   }
   
   public Apartment fromCsv(String entityCsv) {
      // TODO: implement
      return null;
   }

   
   private String getPictureListString(List<Picture> pictures)
   {
	   StringJoiner joiner = new StringJoiner(listDelimiter);
	   
	   for(Picture p: pictures)
	   {
		   joiner.add(p.getName());
	   }
	   
	   return joiner.toString();
   }
   
   private String getAmenityListString(List<Amenity> amenities)
   {
	   StringJoiner joiner = new StringJoiner(listDelimiter);
	   
	   for(Amenity a: amenities)
	   {
		   joiner.add(String.valueOf(a.getId()));
	   }
	   
	   return joiner.toString();
   }
   
   private String getCommentListString(List<Comment> comments)
   {
	   StringJoiner joiner = new StringJoiner(listDelimiter);
	   
	   for(Comment c: comments)
	   {
		   joiner.add(String.valueOf(c.getId()));
	   }
	   
	   return joiner.toString();
   }
}
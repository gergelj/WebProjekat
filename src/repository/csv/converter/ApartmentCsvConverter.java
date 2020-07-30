/***********************************************************************
 * Module:  ApartmentCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import beans.Address;
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
      //TODO: proveriti
      joiner.add(String.valueOf(entity.getLocation().getLatitude()));
      joiner.add(String.valueOf(entity.getLocation().getLongitude()));
      //joiner.add(String.valueOf(entity.getLocation().getAddress()));
      
      joiner.add(String.valueOf(entity.getHost() == null? "" : entity.getHost().getId()));
      
      joiner.add(getPictureListString(entity.getPictures()));
      joiner.add(getAmenityListString(entity.getAmenities()));
      joiner.add(getCommentListString(entity.getComments()));
      
      
      return joiner.toString();
   }
   
   /*
    *  private int numberOfRooms;
   private int numberOfGuests;
   private long id;
   private double pricePerNight;
   private boolean deleted;
   private boolean active;
   private int checkInHour = 14;
   private int checkOutHour = 10;
   
   private ApartmentType apartmentType;
   private Location location;
   private User host;
   private List<Picture> pictures;
   private List<Amenity> amenities;
   private List<Comment> comments;
    * 
    */
   
   public Apartment fromCsv(String entityCsv) {
      String[] tokens = entityCsv.split(delimiter);
      
      int numberOfRooms = Integer.valueOf(tokens[0]);
      int numberOfGuests = Integer.valueOf(tokens[1]);
      long id = Long.valueOf(tokens[2]);
      double pricePerNight = Double.valueOf(tokens[3]);
      boolean deleted = Boolean.valueOf(tokens[4]);
      boolean active = Boolean.valueOf(tokens[5]);
      int checkInHour = Integer.valueOf(tokens[6]);
      int checkOutHour = Integer.valueOf(tokens[7]);
      ApartmentType apartmentType = ApartmentType.valueOf(tokens[8]);
      //TODO: proveriti 
	  Location location = new Location(Double.valueOf(tokens[9]), Double.valueOf(tokens[10]), new Address());
	  User host = new User(Long.valueOf(tokens[11]));
	  
	  List<Picture> pictures = getPictureList(tokens[12]);
	  List<Amenity> amenities = getAmenityList(tokens[13]);
	  List<Comment> comments = getCommentList(tokens[14]);
	  
	  Apartment retVal = new Apartment(numberOfRooms, numberOfGuests, pricePerNight, deleted, active, apartmentType, location, host, pictures, amenities, comments);
	  
      return retVal;
   }

   private List<Picture> getPictureList(String pictureString)
   {
	   List<Picture> retVal = new ArrayList<Picture>();
	   
	   for(String s: pictureString.split(listDelimiter))
	   {
		   retVal.add(new Picture(s));
	   }
	   
	   return retVal;
   }
   
   private List<Amenity> getAmenityList(String amenityString)
   {
	   List<Amenity> retVal = new ArrayList<Amenity>();
	   
	   for(String s: amenityString.split(listDelimiter))
	   {
		   retVal.add(new Amenity(Long.valueOf(s)));
	   }
	   
	   return retVal;
   }
   
   private List<Comment> getCommentList(String commentString)
   {
	   List<Comment> retVal = new ArrayList<Comment>();
	   
	   for(String s: commentString.split(listDelimiter))
	   {
		   retVal.add(new Comment(Long.valueOf(s)));
	   }
	   
	   return retVal;
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
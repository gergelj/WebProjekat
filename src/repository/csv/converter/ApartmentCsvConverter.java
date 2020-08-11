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
import beans.Comment;
import beans.Location;
import beans.Picture;
import beans.User;
import beans.enums.ApartmentType;

public class ApartmentCsvConverter implements ICsvConverter<Apartment> {
   private String delimiter = "~";
   private String listDelimiter = ";";
   private String emptyChar = "♥";
  
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
      
      joiner.add(String.valueOf(entity.getLocation().getLatitude()));   //Lokacija
      joiner.add(String.valueOf(entity.getLocation().getLongitude()));
      joiner.add(String.valueOf(getAddressString(entity.getLocation().getAddress())));	//Adresa
      
      joiner.add(String.valueOf(entity.getHost() == null? "" : entity.getHost().getId()));
      
      joiner.add(getPictureListString(entity.getPictures()));
      joiner.add(getAmenityListString(entity.getAmenities()));
      joiner.add(getCommentListString(entity.getComments()));
      
      
      return joiner.toString();
   }
   
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
      
	  Location location = new Location(Double.valueOf(tokens[9]), Double.valueOf(tokens[10]), getAddressFromString(tokens[11]));
	  User host = new User(Long.valueOf(tokens[12]));
	  
	  List<Picture> pictures = getPictureList(tokens[13]);
	  List<Amenity> amenities = getAmenityList(tokens[14]);
	  List<Comment> comments = getCommentList(tokens[15]);
	  
	  Apartment retVal = new Apartment(id, numberOfRooms, numberOfGuests, pricePerNight, deleted, active, checkInHour, checkOutHour, apartmentType, location, host, pictures, amenities, comments);
	  
      return retVal;
   }

   private Address getAddressFromString(String addressString)
   {
	   Address retVal = new Address();
	   
	   String[] tokens = addressString.split(listDelimiter);
	   
	   retVal.setStreet(tokens[0]);
	   retVal.setHouseNumber(tokens[1]);
	   retVal.setCity(tokens[2]);
	   retVal.setPostalCode(tokens[3]);
	   
	   return retVal;
   }
   
   private List<Picture> getPictureList(String pictureString)
   {
	   List<Picture> retVal = new ArrayList<Picture>();
	   
	   if(pictureString.equals(this.emptyChar))
		   return retVal;
	   
	   for(String s: pictureString.split(listDelimiter))
	   {
		   retVal.add(new Picture(s));
	   }
	   
	   return retVal;
   }
   
   private List<Amenity> getAmenityList(String amenityString)
   {
	   List<Amenity> retVal = new ArrayList<Amenity>();
	   
	   if(amenityString.equals(this.emptyChar))
		   return retVal;
	   
	   for(String s: amenityString.split(listDelimiter))
	   {
		   retVal.add(new Amenity(Long.valueOf(s)));
	   }
	   
	   return retVal;
   }
   
   private List<Comment> getCommentList(String commentString)
   {
	   List<Comment> retVal = new ArrayList<Comment>();
	   
	   if(commentString.equals(this.emptyChar))
		   return retVal;
	   
	   for(String s: commentString.split(listDelimiter))
	   {
		   retVal.add(new Comment(Long.valueOf(s)));
	   }
	   
	   return retVal;
   }
   
   private String getPictureListString(List<Picture> pictures)
   {
	   StringJoiner joiner = new StringJoiner(listDelimiter);
	   
	   if(pictures.isEmpty())
		   return this.emptyChar;
	   
	   for(Picture p: pictures)
	   {
		   joiner.add(p.getName());
	   }
	   
	   return joiner.toString();
   }
   
   private String getAddressString(Address address)
   {
	   StringJoiner joiner = new StringJoiner(listDelimiter);
	   
	   joiner.add(address.getStreet());
	   joiner.add(address.getHouseNumber());
	   joiner.add(address.getCity());
	   joiner.add(address.getPostalCode());
	   
	   return joiner.toString();
   }
   
   private String getAmenityListString(List<Amenity> amenities)
   {
	   StringJoiner joiner = new StringJoiner(listDelimiter);
	   
	   if(amenities.isEmpty())
		   return this.emptyChar;
	   
	   for(Amenity a: amenities)
	   {
		   joiner.add(String.valueOf(a.getId()));
	   }
	   
	   return joiner.toString();
   }
   
   private String getCommentListString(List<Comment> comments)
   {
	   StringJoiner joiner = new StringJoiner(listDelimiter);
	   
	   if(comments.isEmpty())
		   return this.emptyChar;
	   
	   for(Comment c: comments)
	   {
		   joiner.add(String.valueOf(c.getId()));
	   }
	   
	   return joiner.toString();
   }
}
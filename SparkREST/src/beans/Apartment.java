/***********************************************************************
 * Module:  Apartment.java
 * Author:  Geri
 * Purpose: Defines the Class Apartment
 ***********************************************************************/

package beans;

import java.util.*;

public class Apartment implements IIdentifiable, IDeletable {
   private int numberOfRooms;
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
   private List<Comment> comment;
   
   
   public List<Picture> getPictures() {
      if (pictures == null)
         pictures = new ArrayList<Picture>();
      return pictures;
   }
   
   public void setPictures(List<Picture> newPictures) {
      removeAllPictures();
      for (Iterator<Picture> iter = newPictures.iterator(); iter.hasNext();)
         addPictures((Picture)iter.next());
   }
   
   public void addPictures(Picture newPicture) {
      if (newPicture == null)
         return;
      if (this.pictures == null)
         this.pictures = new ArrayList<Picture>();
      if (!this.pictures.contains(newPicture))
         this.pictures.add(newPicture);
   }
   
   public void removePictures(Picture oldPicture) {
      if (oldPicture == null)
         return;
      if (this.pictures != null)
         if (this.pictures.contains(oldPicture))
            this.pictures.remove(oldPicture);
   }
   
   public void removeAllPictures() {
      if (pictures != null)
         pictures.clear();
   }

   public List<Amenity> getAmenities() {
      if (amenities == null)
         this.amenities = new ArrayList<Amenity>();
      return amenities;
   }
   
   public void setAmenities(List<Amenity> newAmenities) {
      removeAllAmenities();
      for (Iterator<Amenity> iter = newAmenities.iterator(); iter.hasNext();)
         addAmenities((Amenity)iter.next());
   }

   public void addAmenities(Amenity newAmenity) {
      if (newAmenity == null)
         return;
      if (this.amenities == null)
         this.amenities = new ArrayList<Amenity>();
      if (!this.amenities.contains(newAmenity))
         this.amenities.add(newAmenity);
   }

   public void removeAmenities(Amenity oldAmenity) {
      if (oldAmenity == null)
         return;
      if (this.amenities != null)
         if (this.amenities.contains(oldAmenity))
            this.amenities.remove(oldAmenity);
   }
   
   public void removeAllAmenities() {
      if (amenities != null)
         amenities.clear();
   }

   
   public List<Comment> getComment() {
      if (comment == null)
         comment = new ArrayList<Comment>();
      return comment;
   }

   public void setComment(List<Comment> newComment) {
      removeAllComment();
      for (Iterator<Comment> iter = newComment.iterator(); iter.hasNext();)
         addComment((Comment)iter.next());
   }
   
   public void addComment(Comment newComment) {
      if (newComment == null)
         return;
      if (this.comment == null)
         this.comment = new ArrayList<Comment>();
      if (!this.comment.contains(newComment))
         this.comment.add(newComment);
   }
   
   public void removeComment(Comment oldComment) {
      if (oldComment == null)
         return;
      if (this.comment != null)
         if (this.comment.contains(oldComment))
            this.comment.remove(oldComment);
   }
   
   public void removeAllComment() {
      if (comment != null)
         comment.clear();
   }
   
   public long getId() {
      return id;
   }
   
   /** @param id */
   public void setId(long id) {
      this.id = id;
   }
   
   public void delete() {
      // TODO: implement
   }

}
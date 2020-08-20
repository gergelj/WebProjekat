/***********************************************************************
 * Module:  Apartment.java
 * Author:  Geri
 * Purpose: Defines the Class Apartment
 ***********************************************************************/

package beans;

import java.util.*;

import beans.enums.ApartmentType;
import beans.interfaces.IDeletable;
import beans.interfaces.IIdentifiable;

public class Apartment implements IIdentifiable, IDeletable {
   private int numberOfRooms;
   private int numberOfGuests;
   private long id;
   private String name;
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
   
   public Apartment() {
	   this.location = new Location();
	   this.host = new User();
	   this.pictures = new ArrayList<Picture>();
	   this.amenities = new ArrayList<Amenity>();
	   this.comments = new ArrayList<Comment>();
   }
   
   public Apartment(long id, String name, int numberOfRooms, int numberOfGuests, double pricePerNight, boolean deleted, boolean active,
		int checkInHour, int checkOutHour, ApartmentType apartmentType, Location location, User host,
		List<Picture> pictures, List<Amenity> amenities, List<Comment> comments) {
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.id = id;
		this.name = name;
		this.pricePerNight = pricePerNight;
		this.deleted = deleted;
		this.active = active;
		this.checkInHour = checkInHour;
		this.checkOutHour = checkOutHour;
		this.apartmentType = apartmentType;
		this.location = location;
		this.host = host;
		this.pictures = pictures;
		this.amenities = amenities;
		this.comments = comments;
   }
   
   public Apartment(String name, int numberOfRooms, int numberOfGuests, double pricePerNight, boolean deleted, boolean active,
		int checkInHour, int checkOutHour, ApartmentType apartmentType, Location location, User host,
		List<Picture> pictures, List<Amenity> amenities, List<Comment> comments) {
	    this.name = name;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.pricePerNight = pricePerNight;
		this.deleted = deleted;
		this.active = active;
		this.checkInHour = checkInHour;
		this.checkOutHour = checkOutHour;
		this.apartmentType = apartmentType;
		this.location = location;
		this.host = host;
		this.pictures = pictures;
		this.amenities = amenities;
		this.comments = comments;
   }
   
   public Apartment(String name, int numberOfRooms, int numberOfGuests, double pricePerNight, boolean deleted, boolean active,
		ApartmentType apartmentType, Location location, User host,
		List<Picture> pictures, List<Amenity> amenities, List<Comment> comments) {
	    this.name = name;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.pricePerNight = pricePerNight;
		this.deleted = deleted;
		this.active = active;
		this.apartmentType = apartmentType;
		this.location = location;
		this.host = host;
		this.pictures = pictures;
		this.amenities = amenities;
		this.comments = comments;
   }

   public Apartment(long id) {
	   this.id = id;
   }

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

   
   public List<Comment> getComments() {
      if (comments == null)
         comments = new ArrayList<Comment>();
      return comments;
   }

   public void setComments(List<Comment> newComment) {
      removeAllComments();
      for (Iterator<Comment> iter = newComment.iterator(); iter.hasNext();)
         addComments((Comment)iter.next());
   }
   
   public void addComments(Comment newComment) {
      if (newComment == null)
         return;
      if (this.comments == null)
         this.comments = new ArrayList<Comment>();
      if (!this.comments.contains(newComment))
         this.comments.add(newComment);
   }
   
   public void removeComments(Comment oldComment) {
      if (oldComment == null)
         return;
      if (this.comments != null)
         if (this.comments.contains(oldComment))
            this.comments.remove(oldComment);
   }
   
   public void removeAllComments() {
      if (comments != null)
         comments.clear();
   }
   
   public long getId() {
      return id;
   }
   
   public int getNumberOfRooms() {
	return numberOfRooms;
   }

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	
	public int getNumberOfGuests() {
		return numberOfGuests;
	}
	
	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}
	
	public double getPricePerNight() {
		return pricePerNight;
	}
	
	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getCheckInHour() {
		return checkInHour;
	}
	
	public void setCheckInHour(int checkInHour) {
		this.checkInHour = checkInHour;
	}
	
	public int getCheckOutHour() {
		return checkOutHour;
	}
	
	public void setCheckOutHour(int checkOutHour) {
		this.checkOutHour = checkOutHour;
	}
	
	public ApartmentType getApartmentType() {
		return apartmentType;
	}
	
	public void setApartmentType(ApartmentType apartmentType) {
		this.apartmentType = apartmentType;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public User getHost() {
		return host;
	}
	
	public void setHost(User host) {
		this.host = host;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}
   
	public void delete() {
		this.setDeleted(true);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Apartment other = (Apartment) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
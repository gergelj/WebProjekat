/***********************************************************************
 * Module:  AmenityService.java
 * Author:  Geri
 * Purpose: Defines the Class AmenityService
 ***********************************************************************/

package service;

import repository.AmenityRepository;
import beans.Amenity;
import beans.User;
import beans.enums.UserType;
import exceptions.DatabaseException;
import exceptions.InvalidUserException;

import java.util.*;

public class AmenityService {
   private AmenityRepository amenityRepository;
   
//Constructors
   public AmenityService(AmenityRepository amenityRepository) {
	super();
		this.amenityRepository = amenityRepository;
   }

//Methods
   public Amenity create(String amenityName, User user) throws DatabaseException, InvalidUserException {
	  if(user.getUserType() == UserType.admin) {
		  Amenity amenity = new Amenity(amenityName, false);
		  amenity = amenityRepository.create(amenity);
		  return amenity;
	  }
	  else {
		  throw new InvalidUserException();
	  }
   }
   
   public void update(Amenity amenity, User user) throws DatabaseException, InvalidUserException {
	   if(user.getUserType() == UserType.admin) {		   
		   amenityRepository.update(amenity);
	   }
	   else {
		   throw new InvalidUserException();
	   }
   }
   
   public void delete(Amenity amenity, User user) throws DatabaseException, InvalidUserException {
	   if(user.getUserType() == UserType.admin) {
		   amenityRepository.delete(amenity.getId());		   
	   }
	   else {
		   throw new InvalidUserException();
	   }
   }
   
   public List<Amenity> getAll() throws DatabaseException {
      return amenityRepository.getAll();
   }

}
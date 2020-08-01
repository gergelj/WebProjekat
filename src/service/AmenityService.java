/***********************************************************************
 * Module:  AmenityService.java
 * Author:  Geri
 * Purpose: Defines the Class AmenityService
 ***********************************************************************/

package service;

import repository.AmenityRepository;
import beans.Amenity;
import beans.User;
import beans.UserType;
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
   public Amenity create(Amenity amenity, UserType userType) throws DatabaseException, InvalidUserException {
	  if(userType == UserType.admin)
	  {
		  return amenityRepository.create(amenity);
	  }
	  throw new InvalidUserException();
   }
   
   public void update(Amenity amenity, UserType userType) throws DatabaseException, InvalidUserException {
      if(userType == UserType.admin)
      {
    	  amenityRepository.update(amenity);
      }
      throw new InvalidUserException();
   }
   
   public void delete(Amenity amenity, UserType userType) throws DatabaseException, InvalidUserException {
	   if(userType == UserType.admin)
	   {
		   amenityRepository.delete(amenity.getId());
	   }
	   throw new InvalidUserException();
   }
   
   public Amenity getById(long id) throws DatabaseException {
      return amenityRepository.getById(id);
   }
   
   public List<Amenity> getAll() {
      return amenityRepository.getAll();
   }

}
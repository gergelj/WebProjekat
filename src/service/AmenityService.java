/***********************************************************************
 * Module:  AmenityService.java
 * Author:  Geri
 * Purpose: Defines the Class AmenityService
 ***********************************************************************/

package service;

import repository.AmenityRepository;
import beans.Amenity;
import exceptions.DatabaseException;
import java.util.*;

public class AmenityService {
   private AmenityRepository amenityRepository;
   
//Constructors
   public AmenityService(AmenityRepository amenityRepository) {
	super();
		this.amenityRepository = amenityRepository;
   }

//Methods
   public Amenity create(Amenity amenity) throws DatabaseException {
	  
	  return amenityRepository.create(amenity);

   }
   
   public void update(Amenity amenity) throws DatabaseException {
	   amenityRepository.update(amenity); 
   }
   
   public void delete(Amenity amenity) throws DatabaseException {
	   amenityRepository.delete(amenity.getId());
   }
   
   public List<Amenity> getAll() throws DatabaseException {
      return amenityRepository.getAll();
   }

}
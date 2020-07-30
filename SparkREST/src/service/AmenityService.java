/***********************************************************************
 * Module:  AmenityService.java
 * Author:  Geri
 * Purpose: Defines the Class AmenityService
 ***********************************************************************/

package service;

import repository.AmenityRepository;
import beans.Amenity;
import java.util.*;

public class AmenityService {
   private AmenityRepository amenityRepository;
   
//Constructors
   public AmenityService(AmenityRepository amenityRepository) {
	super();
		this.amenityRepository = amenityRepository;
   }

//Methods
   public Amenity create(Amenity amenity) {
      // TODO: implement
      return null;
   }
   
   public void update(Amenity amenity) {
      // TODO: implement
   }
   
   public void delete(Amenity amenity) {
      // TODO: implement
   }
   
   public Amenity getById(long id) {
      // TODO: implement
      return null;
   }
   
   public List<Amenity> getAll() {
      // TODO: implement
      return null;
   }

}
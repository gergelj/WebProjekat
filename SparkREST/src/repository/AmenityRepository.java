/***********************************************************************
 * Module:  AmenityRepository.java
 * Author:  Geri
 * Purpose: Defines the Class AmenityRepository
 ***********************************************************************/

package repository;

import java.util.*;

import beans.Amenity;
import repository.abstractrepository.IAmenityRepository;
import repository.csv.CSVRepository;

public class AmenityRepository extends CSVRepository<Amenity,Long> implements IAmenityRepository {
   private String entityName;

}
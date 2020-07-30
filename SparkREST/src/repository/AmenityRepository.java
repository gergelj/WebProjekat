/***********************************************************************
 * Module:  AmenityRepository.java
 * Author:  Geri
 * Purpose: Defines the Class AmenityRepository
 ***********************************************************************/

package repository;

import java.util.*;

public class AmenityRepository extends repository.csv.CSVRepository<Amenity,Long> implements repository.abstract.IAmenityRepository {
   private String entityName;

}
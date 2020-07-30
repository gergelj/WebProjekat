/***********************************************************************
 * Module:  AmenityCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class AmenityCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.util.*;

import beans.Amenity;

public class AmenityCsvConverter implements ICsvConverter<Amenity> {
   private String delimiter;
   
   public String toCsv(Amenity entity) {
      return null;
   }
  
   public Amenity fromCsv(String entityCsv) {
      return null;
   }

}
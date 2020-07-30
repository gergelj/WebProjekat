/***********************************************************************
 * Module:  AmenityCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class AmenityCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import beans.Amenity;

public class AmenityCsvConverter implements ICsvConverter<Amenity> {
   private String delimiter;
   
   public String toCsv(Amenity entity) {
      return String.join(delimiter, String.valueOf(entity.getId()), entity.getName(), String.valueOf(entity.isDeleted()));
   }
  
   public Amenity fromCsv(String entityCsv) {
      return null;
   }

}
/***********************************************************************
 * Module:  AmenityCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class AmenityCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.util.StringJoiner;

import beans.Amenity;

public class AmenityCsvConverter implements ICsvConverter<Amenity> {
   private String delimiter = "~";
   
   public String toCsv(Amenity entity) {
      StringJoiner joiner = new StringJoiner(delimiter);
      
      joiner.add(String.valueOf(entity.getId()));
      joiner.add(entity.getName());
      joiner.add(String.valueOf(entity.isDeleted()));
      
      return joiner.toString();
   }
   
   public Amenity fromCsv(String entityCsv) {
       String[] tokens = entityCsv.split(delimiter);
	   
       long id = Long.valueOf(tokens[0]);
       String name = tokens[1];
       boolean deleted = Boolean.valueOf(tokens[2]);
       
       Amenity retVal = new Amenity(id, name, deleted);
	   
	   return retVal;
   }

}
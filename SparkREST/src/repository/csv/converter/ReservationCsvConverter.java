/***********************************************************************
 * Module:  ReservationCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class ReservationCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.util.*;

import beans.Reservation;

public class ReservationCsvConverter implements ICsvConverter<Reservation> {
   private String delimiter;
   
   /** @param entity */
   public String toCsv(Reservation entity) {
      // TODO: implement
      return null;
   }
   
   /** @param entityCsv */
   public Reservation fromCsv(String entityCsv) {
      // TODO: implement
      return null;
   }

}
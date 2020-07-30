/***********************************************************************
 * Module:  ReservationCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class ReservationCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import beans.Reservation;

public class ReservationCsvConverter implements ICsvConverter<Reservation> {
   private String delimiter;
   
   public String toCsv(Reservation entity) {
      // TODO: implement
      return null;
   }
   
   public Reservation fromCsv(String entityCsv) {
      // TODO: implement
      return null;
   }

}
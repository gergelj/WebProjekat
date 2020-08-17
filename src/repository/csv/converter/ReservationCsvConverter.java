/***********************************************************************
 * Module:  ReservationCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class ReservationCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringJoiner;

import beans.Apartment;
import beans.Reservation;
import beans.User;
import beans.enums.ReservationStatus;

public class ReservationCsvConverter implements ICsvConverter<Reservation> {
   private String delimiter = "~";
   private String dateFormat = "dd.MM.yyyy. HH:mm";
   private String newLine = "`";
   
   public ReservationCsvConverter() {
   }
   
   public String toCsv(Reservation entity) {
      StringJoiner joiner = new StringJoiner(delimiter);
      
      SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
      
      joiner.add(String.valueOf(entity.getId()));
      joiner.add(String.valueOf(entity.getApartment() == null ? "" : entity.getApartment().getId()));
      joiner.add(String.valueOf(entity.getGuest() == null ? "" : entity.getGuest().getId()));
      joiner.add(formatter.format(entity.getCheckIn()));
      joiner.add(String.valueOf(entity.getNights()));
      joiner.add(String.valueOf(entity.getTotalPrice()));
      joiner.add(String.valueOf(entity.getMessage().replace("\n", newLine)));
      joiner.add(String.valueOf(entity.isDeleted()));
      joiner.add(String.valueOf(entity.getReservationStatus()));
      
      return joiner.toString();
   }
   
   public Reservation fromCsv(String entityCsv) {
      String[] tokens = entityCsv.split(delimiter);
      
      long id = Long.valueOf(tokens[0]);
      Apartment apartment = new Apartment(Long.valueOf(tokens[1]));
      User guest = new User(Long.valueOf(tokens[2]));
      Date checkIn = new Date();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			checkIn = formatter.parse(tokens[3]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      int nights = Integer.valueOf(tokens[4]);
      double totalPrice = Double.valueOf(tokens[5]);
      String message = tokens[6].replace(newLine, "\n");
      boolean deleted = Boolean.valueOf(tokens[7]);
      ReservationStatus reservationStatus = ReservationStatus.valueOf(tokens[8]);
      
      return new Reservation(id, apartment, guest, checkIn, nights, totalPrice, message, deleted, reservationStatus);
   }

}
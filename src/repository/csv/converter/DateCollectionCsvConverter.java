/***********************************************************************
 * Module:  DateCollectionCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class DateCollectionCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import beans.Apartment;
import beans.DateCollection;
import beans.enums.DateStatus;

public class DateCollectionCsvConverter implements ICsvConverter<DateCollection> {
   private String delimiter = "~";
   private String listDelimiter = ";";
   private String listDelimiter2 = "_";
   private String dateFormat = "dd.MM.yyyy.";
   private String emptyChar = "♥";
   
   public DateCollectionCsvConverter() {
	   
   }
   
   public String toCsv(DateCollection entity) {
      StringJoiner joiner = new StringJoiner(delimiter);
      
      joiner.add(String.valueOf(entity.getId()));
      joiner.add(String.valueOf(entity.getApartment() == null ? "" : entity.getApartment().getId()));
      joiner.add(String.valueOf(entity.isDeleted()));
      joiner.add(getDateList(entity.getDates()));
      
      return joiner.toString();
   }
   
   private CharSequence getDateList(Map<Date, DateStatus> dates) {
	   if(dates == null) 
		   return emptyChar;
	   
	   if(dates.isEmpty()) 
		   return emptyChar;
	   
	   StringJoiner joiner = new StringJoiner(listDelimiter);
	   
	   SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
	   
	   for(Date date : dates.keySet()){
		   joiner.add(formatter.format(date) + listDelimiter2 + dates.get(date));
	   }
	   
	   return joiner.toString();
   }

   public DateCollection fromCsv(String entityCsv) {
      String[] tokens = entityCsv.split(delimiter);
      
      long id = Long.valueOf(tokens[0]);
      Apartment apartment = tokens[1].equals("") ? null : new Apartment(Long.valueOf(tokens[1]));
      boolean deleted = Boolean.valueOf(tokens[2]);
      Map<Date, DateStatus> dates = getDateFromCsv(tokens[3]);
      
      return new DateCollection(id, apartment, deleted, dates);
   }

	private Map<Date, DateStatus> getDateFromCsv(String list) {
		Map<Date, DateStatus> retVal = new HashMap<Date, DateStatus>();
		
		if(list.equals(emptyChar)) 
			return retVal;
		
		String[] tokens = list.split(listDelimiter);
		
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		
		for(String token : tokens) {
			
			try {
				String[] parts = token.split(listDelimiter2);
				Date date = formatter.parse(parts[0]);
				DateStatus status = DateStatus.valueOf(parts[1]);
				
				retVal.put(date, status);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return retVal;
	}

}
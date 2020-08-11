/***********************************************************************
 * Module:  DateCollectionCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class DateCollectionCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import beans.Apartment;
import beans.DateCollection;

public class DateCollectionCsvConverter implements ICsvConverter<DateCollection> {
   private String delimiter = "~";
   private String listDelimiter = ";";
   private String dateFormat = "dd.MM.yyyy.";
   private String emptyChar = "♥";
   private SimpleDateFormat formatter;
   
   public DateCollectionCsvConverter() {
	   this.formatter = new SimpleDateFormat(this.dateFormat);
   }
   
   public String toCsv(DateCollection entity) {
      StringJoiner joiner = new StringJoiner(delimiter);
      
      joiner.add(String.valueOf(entity.getId()));
      joiner.add(String.valueOf(entity.getApartment() == null ? "" : entity.getApartment().getId()));
      joiner.add(String.valueOf(entity.isDeleted()));
      joiner.add(getDateList(entity.getDates()));
      
      return joiner.toString();
   }
   
   private CharSequence getDateList(List<Date> dates) {
	   if(dates == null) 
		   return emptyChar;
	   
	   if(dates.isEmpty()) 
		   return emptyChar;
	   
	   StringJoiner joiner = new StringJoiner(listDelimiter);
	   
	   for(Date date : dates){
		   joiner.add(formatter.format(date));
	   }
	   
	   return joiner.toString();
   }

   public DateCollection fromCsv(String entityCsv) {
      String[] tokens = entityCsv.split(delimiter);
      
      long id = Long.valueOf(tokens[0]);
      Apartment apartment = tokens[1].equals("") ? null : new Apartment(Long.valueOf(tokens[1]));
      boolean deleted = Boolean.valueOf(tokens[2]);
      List<Date> dates = getDateFromCsv(tokens[3]);
      
      return new DateCollection(id, apartment, deleted, dates);
   }

	private List<Date> getDateFromCsv(String list) {
		List<Date> retVal = new ArrayList<Date>();
		
		if(list.equals(emptyChar)) 
			return retVal;
		
		String[] tokens = list.split(listDelimiter);
		
		for(String token : tokens) {
			
			try {
				Date date = formatter.parse(token);
				
				retVal.add(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return retVal;
	}

}
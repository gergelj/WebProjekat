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
import beans.DateRange;

public class DateCollectionCsvConverter implements ICsvConverter<DateCollection> {
   private String delimiter = "~";
   private String listDelimiter = "^";
   private String listDelimiter2 = "_";
   private String dateFormat = "dd.MM.yyyy.";
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
   
   private CharSequence getDateList(List<DateRange> dates) {
	   if(dates == null) return "";
	   
	   StringJoiner joiner = new StringJoiner(listDelimiter);
	   
	   for(DateRange date : dates){
		   joiner.add(formatter.format(date.getStart()) + listDelimiter2 + formatter.format(date.getEnd()));
	   }
	   
	   return joiner.toString();
   }

   public DateCollection fromCsv(String entityCsv) {
      String[] tokens = entityCsv.split(delimiter);
      
      long id = Long.valueOf(tokens[0]);
      Apartment apartment = tokens[1].equals("") ? null : new Apartment(Long.valueOf(tokens[1]));
      boolean deleted = Boolean.valueOf(tokens[2]);
      List<DateRange> dates = getDateFromCsv(tokens[3]);
      
      return new DateCollection(id, apartment, deleted, dates);
   }

	private List<DateRange> getDateFromCsv(String list) {
		List<DateRange> retVal = new ArrayList<DateRange>();
		
		if(list.equals("")) return retVal;
		
		String[] tokens = list.split(listDelimiter);
		
		for(String token : tokens) {
			String[] parts = token.split(listDelimiter2);
			
			try {
				Date start = formatter.parse(parts[0]);
				Date end = formatter.parse(parts[1]);
				
				DateRange dateRange = new DateRange(start, end);
				retVal.add(dateRange);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return retVal;
	}

}
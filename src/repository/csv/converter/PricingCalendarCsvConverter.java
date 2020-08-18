package repository.csv.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import beans.PricingCalendar;
import beans.enums.DayOfWeek;

public class PricingCalendarCsvConverter implements ICsvConverter<PricingCalendar> {

	private String delimiter = "~";
	private String listDelimiter = ";";
	private String listDelimiter2 = "_";
	private String dateFormat = "dd.MM.yyyy.";
	private String emptyChar = "♥";
	
	@Override
	public String toCsv(PricingCalendar entity) {
		StringJoiner joiner = new StringJoiner(delimiter);
		
		joiner.add(String.valueOf(entity.getId()));
		joiner.add(String.valueOf(entity.isDeleted()));
		joiner.add(getPriceByWeekdayMapString(entity.getPriceByWeekday()));
		joiner.add(getListOfDatesString(entity.getHolidays()));
		joiner.add(String.valueOf(entity.getHolidayPercentage()));
		
		return joiner.toString();
	}

	private CharSequence getListOfDatesString(List<Date> holidays) {
		StringJoiner joiner = new StringJoiner(listDelimiter);
		
		if(holidays == null) return emptyChar;
		
		if(holidays.isEmpty()) return emptyChar;
		
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		 
		for (Date date : holidays) {
			joiner.add(formatter.format(date));
		}
		
		return joiner.toString();
	}

	private CharSequence getPriceByWeekdayMapString(Map<DayOfWeek, Double> priceByWeekday) {
		StringJoiner joiner = new StringJoiner(listDelimiter);
		
		if(priceByWeekday == null)
			return emptyChar;
		
		if(priceByWeekday.isEmpty())
			return emptyChar;
		
		for (DayOfWeek day : priceByWeekday.keySet()) {
			joiner.add(day + listDelimiter2 + priceByWeekday.get(day));
		}
		
		return joiner.toString();
	}

	@Override
	public PricingCalendar fromCsv(String entityCsv) {
		String[] tokens = entityCsv.split(delimiter);
		
		long id = Long.valueOf(tokens[0]);
		boolean deleted = Boolean.valueOf(tokens[1]);
		Map<DayOfWeek, Double> priceByWeekday = getPriceByWeekdayMap(tokens[2]);
		List<Date> holidays = getListOfDate(tokens[3]);
		double holidayPercentage = Double.valueOf(tokens[4]);
		
		return new PricingCalendar(id, deleted, priceByWeekday, holidays, holidayPercentage);
	}

	private List<Date> getListOfDate(String listString) {
		List<Date> retVal = new ArrayList<Date>();
		
		if(listString.equals(emptyChar))
			return retVal;
		
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		
		for(String token : listString.split(listDelimiter)) {
			try {
				Date date = formatter.parse(token);
				retVal.add(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return retVal;
	}

	private Map<DayOfWeek, Double> getPriceByWeekdayMap(String mapString) {
		Map<DayOfWeek, Double> retVal = new HashMap<DayOfWeek, Double>();
		
		if(mapString.equals(emptyChar))
			return retVal;
		
		for (String token : mapString.split(listDelimiter)) {
			String[] parts = token.split(listDelimiter2);
			DayOfWeek day = DayOfWeek.valueOf(parts[0]);
			double percentage = Double.valueOf(parts[1]);
			retVal.put(day, percentage);
		}
		
		return retVal;
	}

}

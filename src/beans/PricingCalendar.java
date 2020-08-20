package beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import beans.enums.DayOfWeek;
import beans.interfaces.IDeletable;
import beans.interfaces.IIdentifiable;

public class PricingCalendar implements IIdentifiable, IDeletable {
	
	private long id;
	private boolean deleted;
	private Map<DayOfWeek, Double> priceByWeekday;
	private List<Date> holidays;
	private double holidayPercentage;

	public PricingCalendar() {
		
	}

	public PricingCalendar(boolean deleted, Map<DayOfWeek, Double> priceByWeekday, List<Date> holidays,
			double holidayPercentage) {
		super();
		this.deleted = deleted;
		this.priceByWeekday = priceByWeekday;
		this.holidays = holidays;
		this.holidayPercentage = holidayPercentage;
	}
	
	public PricingCalendar(long id, boolean deleted, Map<DayOfWeek, Double> priceByWeekday, List<Date> holidays,
			double holidayPercentage) {
		super();
		this.id = id;
		this.deleted = deleted;
		this.priceByWeekday = priceByWeekday;
		this.holidays = holidays;
		this.holidayPercentage = holidayPercentage;
	}
	
	public double getTotalPrice(DateRange dateRange, double basePrice) {
		List<Date> dates = dateRange.toList();
		
		double retVal = 0;
		
		for(int i=0; i<dates.size()-1; i++) {
			Date d = dates.get(i);
			
			if(holidays.contains(d)) {
				retVal += basePrice * holidayPercentage;
			}
			else {
				DayOfWeek day = getDayOfWeek(d);
				if(priceByWeekday.containsKey(day)) {
					retVal += basePrice * priceByWeekday.get(day);
				}
				else {
					retVal += basePrice;
				}
			}
		}
		
		return retVal;
	}
	
	private DayOfWeek getDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int intDay = c.get(Calendar.DAY_OF_WEEK);
		return DayOfWeek.fromInteger(intDay);
	}

	public Map<DayOfWeek, Double> getPriceByWeekday() {
		if(this.priceByWeekday == null)
			return new HashMap<DayOfWeek, Double>();
		return priceByWeekday;
	}

	public void setPriceByWeekday(Map<DayOfWeek, Double> priceByWeekday) {
		removeAllPrices();
		for (DayOfWeek key : priceByWeekday.keySet())
			addPrice(key, priceByWeekday.get(key));
	}

	private void addPrice(DayOfWeek newDay, Double newPrice) {
		if (newDay == null)
			return;
		if (this.priceByWeekday == null)
			this.priceByWeekday = new HashMap<DayOfWeek, Double>();
		if (!this.priceByWeekday.containsKey(newDay))
			this.priceByWeekday.put(newDay, newPrice);
		
	}

	private void removeAllPrices() {
		if(priceByWeekday != null)
			priceByWeekday.clear();
	}

	public List<Date> getHolidays() {
		if(this.holidays == null)
			return new ArrayList<Date>();
		return holidays;
	}

	public void setHolidays(List<Date> holidays) {
		removeAllHolidays();
	      for (Iterator<Date> iter = holidays.iterator(); iter.hasNext();)
	         addHoliday((Date)iter.next());
	}
	
	public void addHoliday(Date newHoliday) {
	      if (newHoliday == null)
	         return;
	      if (this.holidays == null)
	         this.holidays = new ArrayList<Date>();
	      if (!this.holidays.contains(newHoliday))
	         this.holidays.add(newHoliday);
	}
	
	public void removeAllHolidays() {
	      if (holidays != null)
	    	  holidays.clear();
	}

	public double getHolidayPercentage() {
		return holidayPercentage;
	}

	public void setHolidayPercentage(double holidayPercentage) {
		this.holidayPercentage = holidayPercentage;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public boolean isDeleted() {
		return this.deleted;
	}

	@Override
	public void delete() {
		this.setDeleted(true);
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PricingCalendar other = (PricingCalendar) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

}

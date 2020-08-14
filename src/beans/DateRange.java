/***********************************************************************
 * Module:  DateRange.java
 * Author:  Geri
 * Purpose: Defines the Class DateRange
 ***********************************************************************/

package beans;

import java.util.*;

public class DateRange {
   private Date start;
   private Date end;
  
//Constructors
	public DateRange(Date start, Date end) {
		if(end.after(start)) {
			this.start = start;			
			this.end = end;
		}
		else {
			this.start = end;
			this.end = start;
		}
	}
	public DateRange() {
		super();
		this.start = new Date();
		this.end = new Date();
	}
	
//Getters and Setters
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	public List<Date> toList() {
	    List<Date> datesInRange = new ArrayList<>();
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(this.start);
	    
	    Calendar endCalendar = new GregorianCalendar();
	    endCalendar.setTime(this.end);
	 
	    while (calendar.before(endCalendar)) {
	        Date result = calendar.getTime();
	        datesInRange.add(result);
	        calendar.add(Calendar.DATE, 1);
	    }
	    
	    datesInRange.add(this.end);

	    return datesInRange;
	}
  
}
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
		super();
		this.start = start;
		this.end = end;
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
  
}
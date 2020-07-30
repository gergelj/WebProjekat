/***********************************************************************
 * Module:  DateCollection.java
 * Author:  Geri
 * Purpose: Defines the Class DateCollection
 ***********************************************************************/

package beans;

import java.util.*;

public class DateCollection implements IIdentifiable, IDeletable {
   private List<DateRange> dates;
   private long id;
   private boolean deleted;
   
   private Apartment apartment;
   
   public long getId() {
      return this.id;
   }
   
   public void setId(long id) {
	  this.id = id;
   }
   
   public void delete() {
      // TODO: implement
   }

}
/***********************************************************************
 * Module:  Amenity.java
 * Author:  Geri
 * Purpose: Defines the Class Amenity
 ***********************************************************************/

package beans;

import java.util.*;

public class Amenity implements IIdentifiable, IDeletable {
   private long id;
   private String name;
   private boolean deleted;
   
   public long getId() {
      return id;
   }
   
   public void setId(long id) {
	   this.id = id;
   }
   
   public void delete() {
      // TODO: implement
   }

}
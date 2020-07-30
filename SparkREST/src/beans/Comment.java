/***********************************************************************
 * Module:  Comment.java
 * Author:  Geri
 * Purpose: Defines the Class Comment
 ***********************************************************************/

package beans;

import java.util.*;

public class Comment implements IDeletable, IIdentifiable {
   private long id;
   private String text;
   private int rating;
   private boolean deleted;
   private boolean approved;
   
   private User user;
   
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
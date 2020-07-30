/***********************************************************************
 * Module:  User.java
 * Author:  Geri
 * Purpose: Defines the Class User
 ***********************************************************************/

package beans;

import java.util.*;

public class User implements IDeletable, IIdentifiable {
   private String username;
   private String password;
   private String name;
   private String surname;
   private long id;
   private boolean deleted;
   private boolean blocked;
   
   private Gender gender;
   private UserType userType;
   
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
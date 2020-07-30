/***********************************************************************
 * Module:  User.java
 * Author:  Geri
 * Purpose: Defines the Class User
 ***********************************************************************/

package beans;

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

public User(String username, String password, String name, String surname, long id, boolean deleted, boolean blocked,
		Gender gender, UserType userType) {
	super();
	this.username = username;
	this.password = password;
	this.name = name;
	this.surname = surname;
	this.id = id;
	this.deleted = deleted;
	this.blocked = blocked;
	this.gender = gender;
	this.userType = userType;
}

public User() {
	super();
}

public User(String username, String password, String name, String surname, boolean deleted, boolean blocked,
		Gender gender, UserType userType) {
	super();
	this.username = username;
	this.password = password;
	this.name = name;
	this.surname = surname;
	this.deleted = deleted;
	this.blocked = blocked;
	this.gender = gender;
	this.userType = userType;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getSurname() {
	return surname;
}

public void setSurname(String surname) {
	this.surname = surname;
}

public boolean isDeleted() {
	return deleted;
}

public void setDeleted(boolean deleted) {
	this.deleted = deleted;
}

public boolean isBlocked() {
	return blocked;
}

public void setBlocked(boolean blocked) {
	this.blocked = blocked;
}

public Gender getGender() {
	return gender;
}

public void setGender(Gender gender) {
	this.gender = gender;
}

public UserType getUserType() {
	return userType;
}

public void setUserType(UserType userType) {
	this.userType = userType;
}
   
   

}
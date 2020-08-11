/***********************************************************************
 * Module:  UserFilterDTO.java
 * Author:  Geri
 * Purpose: Defines the Class UserFilterDTO
 ***********************************************************************/

package dto;

import beans.enums.Gender;
import beans.enums.UserType;


public class UserFilterDTO {
   private String username;
   
   private UserType userType;
   private Gender gender;
   
//Constructors 
	public UserFilterDTO(String username, UserType userType, Gender gender) {
		super();
		this.username = username;
		this.userType = userType;
		this.gender = gender;
	}
	public UserFilterDTO() {
		super();
	}
	
//Getters and Setters
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	  
}
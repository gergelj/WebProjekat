/***********************************************************************
 * Module:  UserDTO.java
 * Author:  Geri
 * Purpose: Defines the Class UserDTO
 ***********************************************************************/

package dto;

import beans.enums.Gender;

public class UserDTO {
   private String username;
   private String name;
   private String surname;
   private Gender gender;
   private String oldPassword;
   private String password;
   private String controlPassword;
   
   
 //Constructors  
	public UserDTO(String username, String name, String surname, Gender gender, String password, String controlPassword, String oldPassword) {
		super();
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.password = password;
		this.controlPassword = controlPassword;
		this.oldPassword = oldPassword;
	}
	
	public UserDTO() {
		super();
	}
	
//Getters and Setters
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getControlPassword() {
		return controlPassword;
	}

	public void setControlPassword(String controlPassword) {
		this.controlPassword = controlPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	
	

}
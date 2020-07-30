/***********************************************************************
 * Module:  UserDTO.java
 * Author:  Geri
 * Purpose: Defines the Class UserDTO
 ***********************************************************************/

package dto;

public class UserDTO {
   private String username;
   private String name;
   private String surname;
   private String gender;
   private String password;
   
   
 //Constructors  
	public UserDTO(String username, String name, String surname, String gender, String password) {
		super();
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.password = password;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
/***********************************************************************
 * Module:  Address.java
 * Author:  Geri
 * Purpose: Defines the Class Address
 ***********************************************************************/

package beans;

import java.util.*;

public class Address {
   private String street;
   private String houseNumber;
   private String city;
   private String postalCode;
   
   
   
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getHouseNumber() {
		return houseNumber;
	}
	
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	} 
}
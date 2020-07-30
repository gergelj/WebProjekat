/***********************************************************************
 * Module:  Address.java
 * Author:  Geri
 * Purpose: Defines the Class Address
 ***********************************************************************/

package beans;

public class Address {
	private String street;
	private String houseNumber;
	private String city;
	private String postalCode;
	
	public Address() {
		
	}
	
	public Address(String street, String houseNumber, String city, String postalCode) {
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.postalCode = postalCode;
	}

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
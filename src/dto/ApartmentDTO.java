package dto;

import java.util.Date;
import java.util.List;

import beans.Amenity;
import beans.enums.ApartmentType;

public class ApartmentDTO {
	
	private int numberOfRooms;
	private int numberOfGuests;
	private double pricePerNight;
	private ApartmentType apartmentType;
	
	private double latitude;
	private double longitude;
	private String street;
	private String houseNumber;
	private String city;
	private String postalCode;
	
	private int checkInHour;
	private int checkOutHour;
	
	private List<String> pictures;
	private List<Amenity> amenities;
	private List<Date> bookingDates;

	
	
	public int getCheckInHour() {
		return checkInHour;
	}

	public void setCheckInHour(int checkInHour) {
		this.checkInHour = checkInHour;
	}

	public int getCheckOutHour() {
		return checkOutHour;
	}

	public void setCheckOutHour(int checkOutHour) {
		this.checkOutHour = checkOutHour;
	}

	public ApartmentType getApartmentType() {
		return apartmentType;
	}

	public void setApartmentType(ApartmentType apartmentType) {
		this.apartmentType = apartmentType;
	}

	public List<Date> getBookingDates() {
		return bookingDates;
	}

	public void setBookingDates(List<Date> bookingDates) {
		this.bookingDates = bookingDates;
	}

	public ApartmentDTO() {
	
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
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

	public List<Amenity> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenity> amenities) {
		this.amenities = amenities;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}
	

}

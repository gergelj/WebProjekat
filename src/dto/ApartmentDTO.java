package dto;

import java.util.List;

public class ApartmentDTO {
	
	//TODO
	
	private int numberOfRooms;
	private List<String> pictures;

	public ApartmentDTO() {
		// TODO Auto-generated constructor stub
	}

	public ApartmentDTO(int numberOfRooms, List<String> pictures) {
		super();
		this.numberOfRooms = numberOfRooms;
		this.pictures = pictures;
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

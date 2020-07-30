/***********************************************************************
 * Module:  ApartmentRoomsSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentRoomsSpecification
 ***********************************************************************/

package specification.specificationimpl;

import beans.Apartment;
import specification.AbstractSpecification;

public class ApartmentRoomsSpecification extends AbstractSpecification<Apartment> {

	private int numOfRooms;
	
	public ApartmentRoomsSpecification(int numOfRooms) {
		super();
		this.numOfRooms = numOfRooms;
	}

	@Override
	public boolean isSatisfiedBy(Apartment candidate) {
		// TODO Auto-generated method stub
		return false;
	}
}
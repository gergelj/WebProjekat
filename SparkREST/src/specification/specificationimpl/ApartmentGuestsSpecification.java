/***********************************************************************
 * Module:  ApartmentGuestsSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentGuestsSpecification
 ***********************************************************************/

package specification.specificationimpl;


import beans.Apartment;
import specification.AbstractSpecification;

public class ApartmentGuestsSpecification extends AbstractSpecification<Apartment> {

	private int numOfGuests;
	
	public ApartmentGuestsSpecification(int numOfGuests) {
		super();
		this.numOfGuests = numOfGuests;
	}

	@Override
	public boolean isSatisfiedBy(Apartment candidate) {
		// TODO Auto-generated method stub
		return false;
	}
}
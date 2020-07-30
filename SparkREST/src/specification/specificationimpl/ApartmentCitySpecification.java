/***********************************************************************
 * Module:  ApartmentCitySpecification.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentCitySpecification
 ***********************************************************************/

package specification.specificationimpl;

import beans.Apartment;
import specification.AbstractSpecification;

public class ApartmentCitySpecification extends AbstractSpecification<Apartment> {

	private String city;
	
	public ApartmentCitySpecification(String city) {
		super();
		this.city = city;
	}


	@Override
	public boolean isSatisfiedBy(Apartment candidate) {
		// TODO Auto-generated method stub
		return false;
	}
}
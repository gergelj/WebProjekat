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
		if(candidate.getLocation() != null) 
			if(candidate.getLocation().getAddress() != null)
				return candidate.getLocation().getAddress().getCity().toLowerCase().contains(city.toLowerCase());
		
		return false;
	}
}
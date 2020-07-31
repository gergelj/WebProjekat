/***********************************************************************
 * Module:  ApartmentPriceSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentPriceSpecification
 ***********************************************************************/

package specification.specificationimpl;

import beans.Apartment;
import beans.PriceRange;
import specification.AbstractSpecification;

public class ApartmentPriceSpecification extends AbstractSpecification<Apartment> {

	private PriceRange priceRange;
	
	public ApartmentPriceSpecification(PriceRange priceRange) {
		super();
		this.priceRange = priceRange;
	}

	@Override
	public boolean isSatisfiedBy(Apartment candidate) {
		return priceRange.isIn(candidate.getPricePerNight());
	}
}
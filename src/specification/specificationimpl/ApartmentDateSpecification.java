/***********************************************************************
 * Module:  ApartmentDateSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentDateSpecification
 ***********************************************************************/

package specification.specificationimpl;


import beans.Apartment;
import beans.DateRange;
import specification.AbstractSpecification;

public class ApartmentDateSpecification extends AbstractSpecification<Apartment> {

	private DateRange dateRange;
	
	public ApartmentDateSpecification(DateRange dateRange) {
		super();
		this.dateRange = dateRange;
	}

	@Override
	public boolean isSatisfiedBy(Apartment candidate) {
		//TODO: Get available dates for apartment
		return false;
	}
}
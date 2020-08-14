/***********************************************************************
 * Module:  ApartmentDateSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentDateSpecification
 ***********************************************************************/

package specification.specificationimpl;


import beans.Apartment;
import beans.DateCollection;
import beans.DateRange;
import exceptions.DatabaseException;
import repository.abstractrepository.IDateCollectionRepository;
import specification.AbstractSpecification;

public class ApartmentDateSpecification extends AbstractSpecification<Apartment> {

	private DateRange dateRange;
	private IDateCollectionRepository dateCollectionRepository;
	
	public ApartmentDateSpecification(DateRange dateRange, IDateCollectionRepository dateCollectionRepository) {
		super();
		this.dateRange = dateRange;
		this.dateCollectionRepository = dateCollectionRepository;
	}

	@Override
	public boolean isSatisfiedBy(Apartment candidate) {
		try {
			DateCollection dc = dateCollectionRepository.getByApartmentId(candidate.getId());
			return dc.isBookingAvailableFor(this.dateRange);
		} catch (DatabaseException e) {
			return false;
		}
	}
}
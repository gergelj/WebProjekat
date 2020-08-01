/***********************************************************************
 * Module:  ApartmentFilterConverter.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentFilterConverter
 ***********************************************************************/

package specification.filterconverter;

import dto.ApartmentFilterDTO;
import specification.ISpecification;
import specification.specificationimpl.ApartmentCitySpecification;
import specification.specificationimpl.ApartmentDateSpecification;
import specification.specificationimpl.ApartmentGuestsSpecification;
import specification.specificationimpl.ApartmentPriceSpecification;
import specification.specificationimpl.ApartmentRoomsSpecification;
import specification.specificationimpl.BooleanSpecification;
import beans.Apartment;

public class ApartmentFilterConverter {

   public static ISpecification<Apartment> getSpecification(ApartmentFilterDTO filter) {
	   ISpecification<Apartment> specification = new BooleanSpecification<Apartment>(true);
	   
	   if(!filter.getCity().isEmpty())
		   specification = specification.and(new ApartmentCitySpecification(filter.getCity()));
	   
	   if(filter.getNumberOfGuests() > 0)
		   specification = specification.and(new ApartmentGuestsSpecification(filter.getNumberOfGuests()));
	   
	   if(filter.getNumberOfRooms() > 0)
		   specification = specification.and(new ApartmentRoomsSpecification(filter.getNumberOfRooms()));
	   
	   if(filter.getDateRange() != null)
		   specification = specification.and(new ApartmentDateSpecification(filter.getDateRange()));
	   
	   if(filter.getPriceRange() != null)
		   specification = specification.and(new ApartmentPriceSpecification(filter.getPriceRange()));
	   
	   return specification;
   }

}
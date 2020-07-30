/***********************************************************************
 * Module:  IApartmentRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IApartmentRepository
 ***********************************************************************/

package repository.abstract;

import java.util.*;

public interface IApartmentRepository extends IRepository<Apartment,Long> {
   /** @param specification */
   List<Apartment> find(ISpecification<Apartment> specification);

}
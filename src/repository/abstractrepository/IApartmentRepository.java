/***********************************************************************
 * Module:  IApartmentRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IApartmentRepository
 ***********************************************************************/

package repository.abstractrepository;

import java.util.*;

import beans.Apartment;
import specification.ISpecification;

public interface IApartmentRepository extends IRepository<Apartment,Long> {
   /** @param specification */
   List<Apartment> find(ISpecification<Apartment> specification);

}
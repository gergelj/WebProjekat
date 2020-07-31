/***********************************************************************
 * Module:  IApartmentRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IApartmentRepository
 ***********************************************************************/

package repository.abstractrepository;

import java.util.*;

import beans.Apartment;
import exceptions.EntityNotFoundException;
import specification.ISpecification;

public interface IApartmentRepository extends IRepository<Apartment> {
   /** @param specification */
   List<Apartment> find(ISpecification<Apartment> specification) throws EntityNotFoundException;

}
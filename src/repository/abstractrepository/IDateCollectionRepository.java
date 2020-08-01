/***********************************************************************
 * Module:  IDateCollectionRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IDateCollectionRepository
 ***********************************************************************/

package repository.abstractrepository;


import beans.DateCollection;
import exceptions.DatabaseException;

public interface IDateCollectionRepository extends IRepository<DateCollection> {
	
	void deleteByApartment(long apartmentId) throws DatabaseException;
	
	DateCollection getByApartmentId(long apartmentId) throws DatabaseException;
}
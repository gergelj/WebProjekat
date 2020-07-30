/***********************************************************************
 * Module:  BookingDateCollectionRepository.java
 * Author:  Geri
 * Purpose: Defines the Class BookingDateCollectionRepository
 ***********************************************************************/

package repository;

import java.util.*;

import beans.DateCollection;
import repository.abstractrepository.IDateCollectionRepository;
import repository.csv.CSVRepository;

public class BookingDateCollectionRepository extends CSVRepository<DateCollection,Long> implements IDateCollectionRepository {
   private String entityName;

}
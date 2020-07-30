/***********************************************************************
 * Module:  AvailableDateCollectionRepository.java
 * Author:  Geri
 * Purpose: Defines the Class AvailableDateCollectionRepository
 ***********************************************************************/

package repository;

import java.util.*;

import beans.DateCollection;
import repository.abstractrepository.IDateCollectionRepository;
import repository.csv.CSVRepository;

public class AvailableDateCollectionRepository extends CSVRepository<DateCollection,Long> implements IDateCollectionRepository {
   private String entityName;

}
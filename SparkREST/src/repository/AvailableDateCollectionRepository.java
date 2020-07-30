/***********************************************************************
 * Module:  AvailableDateCollectionRepository.java
 * Author:  Geri
 * Purpose: Defines the Class AvailableDateCollectionRepository
 ***********************************************************************/

package repository;

import java.util.*;

public class AvailableDateCollectionRepository extends repository.csv.CSVRepository<DateCollection,Long> implements repository.abstract.IDateCollectionRepository {
   private String entityName;

}
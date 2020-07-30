/***********************************************************************
 * Module:  BookingDateCollectionRepository.java
 * Author:  Geri
 * Purpose: Defines the Class BookingDateCollectionRepository
 ***********************************************************************/

package repository;

import java.util.*;

public class BookingDateCollectionRepository extends repository.csv.CSVRepository<DateCollection,Long> implements repository.abstract.IDateCollectionRepository {
   private String entityName;

}
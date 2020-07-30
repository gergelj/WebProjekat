/***********************************************************************
 * Module:  ReservationRepository.java
 * Author:  Geri
 * Purpose: Defines the Class ReservationRepository
 ***********************************************************************/

package repository;

import java.util.*;

public class ReservationRepository extends repository.csv.CSVRepository<Reservation,Long> implements repository.abstract.IReservationRepository, repository.csv.IEagerCsvRepository<Reservation,Long> {
   private String entityName;
   
   private void bind() {
      // TODO: implement
   }
   
   /** @param id */
   public T getEager(ID id) {
      // TODO: implement
      return null;
   }
   
   public List<T> getAllEager() {
      // TODO: implement
      return null;
   }

}
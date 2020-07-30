/***********************************************************************
 * Module:  ReservationRepository.java
 * Author:  Geri
 * Purpose: Defines the Class ReservationRepository
 ***********************************************************************/

package repository;

import java.util.*;

import beans.Reservation;
import repository.abstractrepository.IReservationRepository;
import repository.csv.CSVRepository;
import repository.csv.IEagerCsvRepository;

public class ReservationRepository extends CSVRepository<Reservation,Long> implements IReservationRepository, IEagerCsvRepository<Reservation,Long> {
   private String entityName;
   
   private void bind() {
      // TODO: implement
   }
   
   public Reservation getEager(Long id) {
      // TODO: implement
      return null;
   }
   
   public List<Reservation> getAllEager() {
      // TODO: implement
      return null;
   }

}
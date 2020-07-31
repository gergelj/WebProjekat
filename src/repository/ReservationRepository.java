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
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

public class ReservationRepository extends CSVRepository<Reservation> implements IReservationRepository, IEagerCsvRepository<Reservation> {
   
	public ReservationRepository(ICsvStream<Reservation> stream, LongSequencer sequencer) {
		super("Reservation", stream, sequencer);
	}
   
   private void bind() {
      // TODO: implement
   }
   
   public Reservation getEager(long id) {
      // TODO: implement
      return null;
   }
   
   public List<Reservation> getAllEager() {
      // TODO: implement
      return null;
   }

}
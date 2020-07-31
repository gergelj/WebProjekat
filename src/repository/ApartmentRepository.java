/***********************************************************************
 * Module:  ApartmentRepository.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentRepository
 ***********************************************************************/

package repository;

import java.util.*;

import beans.Apartment;
import repository.abstractrepository.IApartmentRepository;
import repository.csv.CSVRepository;
import repository.csv.IEagerCsvRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;
import specification.ISpecification;

public class ApartmentRepository extends CSVRepository<Apartment> implements IApartmentRepository, IEagerCsvRepository<Apartment> {
   
   public ApartmentRepository(ICsvStream<Apartment> stream, LongSequencer sequencer) {
	   super("Apartment", stream, sequencer);
   }
   
   private void bind() {
      // TODO: implement
   }

   public List<Apartment> find(ISpecification<Apartment> specification) {
      // TODO: implement
      return null;
   }

   public Apartment getEager(long id) {
      // TODO: implement
      return null;
   }
   
   public List<Apartment> getAllEager() {
      // TODO: implement
      return null;
   }

}
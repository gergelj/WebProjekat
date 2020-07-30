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
import repository.sequencer.ISequencer;
import specification.ISpecification;

public class ApartmentRepository extends CSVRepository<Apartment,Long> implements IApartmentRepository, IEagerCsvRepository<Apartment,Long> {
   
   public ApartmentRepository(ICsvStream<Apartment> stream, ISequencer<Long> sequencer) {
	   super("Apartment", stream, sequencer);
   }
   
   private void bind() {
      // TODO: implement
   }

   public List<Apartment> find(ISpecification<Apartment> specification) {
      // TODO: implement
      return null;
   }

   public Apartment getEager(Long id) {
      // TODO: implement
      return null;
   }
   
   public List<Apartment> getAllEager() {
      // TODO: implement
      return null;
   }

}
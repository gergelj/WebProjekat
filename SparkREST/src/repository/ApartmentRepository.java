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
import specification.ISpecification;

public class ApartmentRepository extends CSVRepository<Apartment,Long> implements IApartmentRepository, IEagerCsvRepository<Apartment,Long> {
   private String entityName;
   
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
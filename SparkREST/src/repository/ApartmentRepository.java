/***********************************************************************
 * Module:  ApartmentRepository.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentRepository
 ***********************************************************************/

package repository;

import java.util.*;

public class ApartmentRepository extends repository.csv.CSVRepository<Apartment,Long> implements repository.abstract.IApartmentRepository, repository.csv.IEagerCsvRepository<Apartment,Long> {
   private String entityName;
   
   private void bind() {
      // TODO: implement
   }
   
   /** @param specification */
   public List<Apartment> find(ISpecification<Apartment> specification) {
      // TODO: implement
      return null;
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
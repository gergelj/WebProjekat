/***********************************************************************
 * Module:  CSVRepository.java
 * Author:  Geri
 * Purpose: Defines the Class CSVRepository
 ***********************************************************************/

package repository.csv;

import repository.abstractrepository.IRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.ISequencer;
import java.util.*;

public class CSVRepository <T,ID> implements IRepository<T,ID> {
   private String notFoundError;
   private String entityName;
   
   private ICsvStream<T> stream;
   private ISequencer<ID> sequencer;
   
   public CSVRepository(String entityName, ICsvStream<T> stream, ISequencer<ID> sequencer){
	   this.entityName = entityName;
	   this.stream = stream;
	   this.sequencer = sequencer;
	   initializeId();
   }
   
   private ID getMaxId(List<T> entities) {
      // TODO: implement
      return null;
   }
   
   protected void initializeId() {
      // TODO: implement
   }
   
   public T getById(ID id) {
      // TODO: implement
      return null;
   }
   
   public List<T> getAll() {
      // TODO: implement
      return null;
   }
   
   public T create(T entity) {
      // TODO: implement
      return null;
   }
   
   public void update(T entity) {
      // TODO: implement
   }
   
   public void delete(T entity) {
      // TODO: implement
   }

}
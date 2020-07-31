/***********************************************************************
 * Module:  CSVRepository.java
 * Author:  Geri
 * Purpose: Defines the Class CSVRepository
 ***********************************************************************/

package repository.csv;

import repository.abstractrepository.IRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

import java.util.*;

public class CSVRepository <T> implements IRepository<T> {
   private String notFoundError;
   private String entityName;
   
   private ICsvStream<T> stream;
   private LongSequencer sequencer;
   
   public CSVRepository(String entityName, ICsvStream<T> stream, LongSequencer sequencer){
	   this.entityName = entityName;
	   this.stream = stream;
	   this.sequencer = sequencer;
	   initializeId();
   }
   
   private long getMaxId(List<T> entities) {
      if(entities.isEmpty()) return 0;
      return 0;
   }
   
   protected void initializeId() {
      this.sequencer.initialize(getMaxId(this.stream.readAll()));
   }
   
   public T getById(long id) {
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
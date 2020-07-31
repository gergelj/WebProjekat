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

import beans.IDeletable;
import beans.IIdentifiable;
import exceptions.EntityNotFoundException;

public class CSVRepository <T extends IIdentifiable & IDeletable> implements IRepository<T> {
   private String notFoundError = "%s with %s:%s can not be found!";
   private String entityName;
   
   private ICsvStream<T> stream;
   private LongSequencer sequencer;
   
   public CSVRepository(String entityName, ICsvStream<T> stream, LongSequencer sequencer){
	   this.entityName = entityName;
	   this.stream = stream;
	   this.sequencer = sequencer;
	   initializeId();
   }
   
   public T create(T entity) {
	   entity.setId(this.sequencer.generateId());
	   this.stream.appendToFile(entity);
	   return entity;
   }
   
   public void update(T entity) throws EntityNotFoundException {
	   
	   List<T> entities = this.stream.readAll();
	   
	   int index = entities.indexOf(entity);
	   
	   if(index == -1)
		   throw new EntityNotFoundException(String.format(this.notFoundError, this.entityName, "id", entity.getId()));
	   
	   entities.set(index, entity);
	   this.stream.saveAll(entities);
   }
   
   private long getMaxId(List<T> entities) {
	   
      if(entities.isEmpty()) return 0;
      
      return entities.stream().max(Comparator.comparing(T::getId)).get().getId();
   }
   
   protected void initializeId() {
      this.sequencer.initialize(getMaxId(this.stream.readAll()));
   }
   
   public T getById(long id) throws EntityNotFoundException {
	   try {
		   
		   return stream.readAll().stream().filter(entity -> entity.getId() == id).findFirst().get();
	   }
	   catch(NoSuchElementException e) {
		   throw new EntityNotFoundException(String.format(this.notFoundError, this.entityName, "id", id));
	   }
   }

   public List<T> getAll() {
      return this.stream.readAll();
   }
   
   public void delete(long id) throws EntityNotFoundException {
	   T entity = getById(id);
	   entity.delete();
	   this.update(entity);
   }

}
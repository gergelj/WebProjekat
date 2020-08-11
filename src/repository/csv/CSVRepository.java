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
import java.util.stream.Collectors;

import beans.interfaces.IDeletable;
import beans.interfaces.IIdentifiable;
import exceptions.DatabaseException;
import exceptions.EntityNotFoundException;


public class CSVRepository <T extends IIdentifiable & IDeletable> implements IRepository<T> {
   protected String notFoundError = "%s with %s:%s can not be found!";
   protected String entityName;
   
   private ICsvStream<T> stream;
   private LongSequencer sequencer;
   
   public CSVRepository(String entityName, ICsvStream<T> stream, LongSequencer sequencer) throws DatabaseException{
	   this.entityName = entityName;
	   this.stream = stream;
	   this.sequencer = sequencer;
	   initializeId();
   }
   
   public T create(T entity) throws DatabaseException {
	   entity.setId(this.sequencer.generateId());
	   this.stream.appendToFile(entity);
	   return entity;
   }
   
   public void update(T entity) throws DatabaseException {
	   
	   List<T> entities = this.stream.readAll();
	   
	   int index = entities.indexOf(entity);
	   
	   if(index == -1)
		   throw new EntityNotFoundException(String.format(this.notFoundError, this.entityName, "id", entity.getId()));
	   
	   if(entities.get(index).isDeleted())
		   throw new EntityNotFoundException(String.format(this.notFoundError, this.entityName, "id", entity.getId()));
		   
	   
	   entities.set(index, entity);
	   this.stream.saveAll(entities);
   }
   
   private long getMaxId(List<T> entities) {
	   
      if(entities.isEmpty()) return 0;
      
      return entities.stream().max(Comparator.comparing(T::getId)).get().getId();
   }
   
   protected void initializeId() throws DatabaseException {
      this.sequencer.initialize(getMaxId(this.stream.readAll()));
   }
   
   public T getById(long id) throws DatabaseException {
	   try {
		   
		   T entity = stream.readAll().stream().filter(ent -> ent.getId() == id).findFirst().get();
		   
		   if(entity.isDeleted())
			   throw new EntityNotFoundException(String.format(this.notFoundError, this.entityName, "id", id));
		   
		   return entity;
	   }
	   catch(NoSuchElementException e) {
		   throw new EntityNotFoundException(String.format(this.notFoundError, this.entityName, "id", id));
	   }
   }

   public List<T> getAll() throws DatabaseException {
	  List<T> entities = this.stream.readAll();
      return entities.stream().filter(e -> !e.isDeleted()).collect(Collectors.toList());
   }
   
   public void delete(long id) throws DatabaseException {
	   T entity = getById(id);
	   entity.delete();
	   this.update(entity);
   }

}
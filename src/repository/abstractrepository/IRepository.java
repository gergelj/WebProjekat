/***********************************************************************
 * Module:  IRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IRepository
 ***********************************************************************/

package repository.abstractrepository;

import java.util.*;

import exceptions.DatabaseException;

public interface IRepository <T> {
   T getById(long id) throws DatabaseException;
   List<T> getAll();
   T create(T entity) throws DatabaseException;
   void update(T entity) throws DatabaseException;
   void delete(long id) throws DatabaseException;

}
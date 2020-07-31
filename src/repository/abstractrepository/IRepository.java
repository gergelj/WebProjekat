/***********************************************************************
 * Module:  IRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IRepository
 ***********************************************************************/

package repository.abstractrepository;

import java.util.*;

import exceptions.EntityNotFoundException;

public interface IRepository <T> {
   T getById(long id) throws EntityNotFoundException;
   List<T> getAll();
   T create(T entity);
   void update(T entity) throws EntityNotFoundException;
   void delete(long id) throws EntityNotFoundException;

}
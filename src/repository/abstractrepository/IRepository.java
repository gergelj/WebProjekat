/***********************************************************************
 * Module:  IRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IRepository
 ***********************************************************************/

package repository.abstractrepository;

import java.util.*;

import exceptions.EntityNotFoundException;
import exceptions.NotUniqueException;

public interface IRepository <T> {
   T getById(long id) throws EntityNotFoundException;
   List<T> getAll();
   T create(T entity) throws NotUniqueException;
   void update(T entity) throws EntityNotFoundException;
   void delete(long id) throws EntityNotFoundException;

}
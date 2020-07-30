/***********************************************************************
 * Module:  IRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IRepository
 ***********************************************************************/

package repository.abstract;

import java.util.*;

public interface IRepository <T,ID> {
   /** @param id */
   T getById(ID id);
   List<T> getAll();
   /** @param entity */
   T create(T entity);
   /** @param entity */
   void update(T entity);
   /** @param entity */
   void delete(T entity);

}
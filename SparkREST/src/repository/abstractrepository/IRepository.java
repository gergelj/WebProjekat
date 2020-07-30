/***********************************************************************
 * Module:  IRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IRepository
 ***********************************************************************/

package repository.abstractrepository;

import java.util.*;

public interface IRepository <T,ID> {
   T getById(ID id);
   List<T> getAll();
   T create(T entity);
   void update(T entity);
   void delete(T entity);

}
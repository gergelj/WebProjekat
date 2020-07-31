/***********************************************************************
 * Module:  IEagerCsvRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IEagerCsvRepository
 ***********************************************************************/

package repository.csv;

import java.util.*;

import exceptions.EntityNotFoundException;

public interface IEagerCsvRepository <T> {
   T getEager(long id) throws EntityNotFoundException;
   List<T> getAllEager() throws EntityNotFoundException;

}
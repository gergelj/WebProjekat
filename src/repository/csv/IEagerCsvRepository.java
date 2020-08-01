/***********************************************************************
 * Module:  IEagerCsvRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IEagerCsvRepository
 ***********************************************************************/

package repository.csv;

import java.util.*;

import exceptions.DatabaseException;

public interface IEagerCsvRepository <T> {
   T getEager(long id) throws DatabaseException;
   List<T> getAllEager() throws DatabaseException;

}
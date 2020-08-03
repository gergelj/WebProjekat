/***********************************************************************
 * Module:  ICsvStream.java
 * Author:  Geri
 * Purpose: Defines the Interface ICsvStream
 ***********************************************************************/

package repository.csv.stream;

import java.util.*;

import exceptions.DatabaseException;

public interface ICsvStream <T> {
   void saveAll(List<T> entities) throws DatabaseException;
   List<T> readAll() throws DatabaseException;
   void appendToFile(T entity) throws DatabaseException;

}
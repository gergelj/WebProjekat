/***********************************************************************
 * Module:  ICsvStream.java
 * Author:  Geri
 * Purpose: Defines the Interface ICsvStream
 ***********************************************************************/

package repository.csv.stream;

import java.util.*;

public interface ICsvStream <T> {
   void saveAll(List<T> entities);
   List<T> readAll();
   void appendToFile(T entity);

}
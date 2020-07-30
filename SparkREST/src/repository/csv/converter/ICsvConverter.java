/***********************************************************************
 * Module:  ICsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Interface ICsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.util.*;

public interface ICsvConverter <T> {
   /** @param entity */
   String toCsv(T entity);
   /** @param entityCsv */
   T fromCsv(String entityCsv);

}
/***********************************************************************
 * Module:  ICsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Interface ICsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.util.*;

public interface ICsvConverter <T> {
   String toCsv(T entity);
   T fromCsv(String entityCsv);

}
/***********************************************************************
 * Module:  IEagerCsvRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IEagerCsvRepository
 ***********************************************************************/

package repository.csv;

import java.util.*;

public interface IEagerCsvRepository <T> {
   T getEager(long id);
   List<T> getAllEager();

}
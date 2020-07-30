/***********************************************************************
 * Module:  IEagerCsvRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IEagerCsvRepository
 ***********************************************************************/

package repository.csv;

import java.util.*;

public interface IEagerCsvRepository <T,ID> {
   T getEager(ID id);
   List<T> getAllEager();

}
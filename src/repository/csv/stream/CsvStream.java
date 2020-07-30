/***********************************************************************
 * Module:  CsvStream.java
 * Author:  Geri
 * Purpose: Defines the Class CsvStream
 ***********************************************************************/

package repository.csv.stream;

import repository.csv.converter.ICsvConverter;
import java.util.*;

public class CsvStream <T> implements ICsvStream<T> {
   private String path;
   
   private ICsvConverter<T> converter;
   
   
   
   public CsvStream(String path, ICsvConverter<T> converter) {
	   super();
	   this.path = path;
	   this.converter = converter;
   }

public void writeAllLinesToFile(String[] content) {
      // TODO: implement
   }
   
   public void saveAll(List<T> entities) {
      // TODO: implement
   }
   
   public List<T> readAll() {
      // TODO: implement
      return null;
   }
   
   public void appendToFile(T entity) {
      // TODO: implement
   }

}
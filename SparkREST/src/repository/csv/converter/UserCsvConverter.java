/***********************************************************************
 * Module:  UserCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class UserCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import beans.User;

public class UserCsvConverter implements ICsvConverter<User> {
   private String delimiter;
   
   public String toCsv(User entity) {
      // TODO: implement
      return null;
   }
   
   public User fromCsv(String entityCsv) {
      // TODO: implement
      return null;
   }

}
/***********************************************************************
 * Module:  UserCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class UserCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.util.*;

import beans.User;

public class UserCsvConverter implements ICsvConverter<User> {
   private String delimiter;
   
   /** @param entity */
   public String toCsv(User entity) {
      // TODO: implement
      return null;
   }
   
   /** @param entityCsv */
   public User fromCsv(String entityCsv) {
      // TODO: implement
      return null;
   }

}
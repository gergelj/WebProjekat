/***********************************************************************
 * Module:  UserCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class UserCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.util.StringJoiner;

import beans.Gender;
import beans.User;
import beans.UserType;

public class UserCsvConverter implements ICsvConverter<User> {
   private String delimiter = "~";
   
   public String toCsv(User entity) {
	   StringJoiner joiner = new StringJoiner(delimiter);
	   
	   joiner.add(String.valueOf(entity.getId()));
	   joiner.add(entity.getUsername());
	   joiner.add(entity.getPassword());
	   joiner.add(entity.getName());
	   joiner.add(entity.getSurname());
	   joiner.add(String.valueOf(entity.isDeleted()));
	   joiner.add(String.valueOf(entity.isBlocked()));
	   joiner.add(String.valueOf(entity.getGender()));
	   joiner.add(String.valueOf(entity.getUserType()));
	   
	   return joiner.toString();
   }
   
   public User fromCsv(String entityCsv) {
      String[] tokens = entityCsv.split(delimiter);
      
      long id = Long.valueOf(tokens[0]);
      String username = tokens[1];
      String password = tokens[2];
      String name = tokens[3];
      String surname = tokens[4];
      boolean deleted = Boolean.valueOf(tokens[5]);
      boolean blocked = Boolean.valueOf(tokens[6]);
      Gender gender = Gender.valueOf(tokens[7]);
      UserType userType = UserType.valueOf(tokens[8]);
      
      return new User(id, username, password, name, surname, deleted, blocked, gender, userType);
   }

}
/***********************************************************************
 * Module:  UserCsvConverter.java
 * Author:  Geri
 * Purpose: Defines the Class UserCsvConverter
 ***********************************************************************/

package repository.csv.converter;

import java.util.StringJoiner;

import beans.Account;
import beans.User;
import beans.enums.Gender;
import beans.enums.UserType;

public class UserCsvConverter implements ICsvConverter<User> {
   private String delimiter = "~";
   
   public String toCsv(User entity) {
	   StringJoiner joiner = new StringJoiner(delimiter);
	   
	   joiner.add(String.valueOf(entity.getId()));
	   joiner.add(String.valueOf(entity.getAccount().getId()));
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
      Account account = new Account(Long.valueOf(tokens[1]));
      String name = tokens[2];
      String surname = tokens[3];
      boolean deleted = Boolean.valueOf(tokens[4]);
      boolean blocked = Boolean.valueOf(tokens[5]);
      Gender gender = Gender.valueOf(tokens[6]);
      UserType userType = UserType.valueOf(tokens[7]);
      
      return new User(id, account, name, surname, deleted, blocked, gender, userType);
   }

}
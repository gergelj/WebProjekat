/***********************************************************************
 * Module:  UserRepository.java
 * Author:  Geri
 * Purpose: Defines the Class UserRepository
 ***********************************************************************/

package repository;

import java.util.*;

public class UserRepository extends repository.csv.CSVRepository<User,Long> implements repository.abstract.IUserRepository {
   private String entityName;
   private String notUniqueError;
   
   /** @param username */
   private boolean isUsernameUnique(String username) {
      // TODO: implement
      return false;
   }
   
   /** @param specification */
   public List<User> find(ISpecification<User> specification) {
      // TODO: implement
      return null;
   }

}
/***********************************************************************
 * Module:  UserService.java
 * Author:  Geri
 * Purpose: Defines the Class UserService
 ***********************************************************************/

package service;

import repository.UserRepository;
import dto.UserDTO;
import beans.User;
import dto.UserFilterDTO;
import java.util.*;

public class UserService {
   private UserRepository userRepository;
   
   /** @param user */
   public void register(UserDTO user) {
      // TODO: implement
   }
   
   /** @param user */
   public void login(UserDTO user) {
      // TODO: implement
   }
   
   /** @param id */
   public User getById(long id) {
      // TODO: implement
      return null;
   }
   
   public User update() {
      // TODO: implement
      return null;
   }
   
   public void validate() {
      // TODO: implement
   }
   
   public List<User> getAll() {
      // TODO: implement
      return null;
   }
   
   /** @param host */
   public List<User> getGuestsByHost(User host) {
      // TODO: implement
      return null;
   }
   
   /** @param filter */
   public List<User> find(UserFilterDTO filter) {
      // TODO: implement
      return null;
   }

}
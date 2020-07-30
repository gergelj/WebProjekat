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
   
   
//Constructors
   public UserService(UserRepository userRepository) {
	super();
	this.userRepository = userRepository;
   }

//Methods
   public void register(UserDTO user) {
      // TODO: implement
   }
   
   public void login(UserDTO user) {
      // TODO: implement
   }
   
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
   
   public List<User> getGuestsByHost(User host) {
      // TODO: implement
      return null;
   }
   
   public List<User> find(UserFilterDTO filter) {
      // TODO: implement
      return null;
   }

}
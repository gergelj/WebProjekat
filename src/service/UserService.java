/***********************************************************************
 * Module:  UserService.java
 * Author:  Geri
 * Purpose: Defines the Class UserService
 ***********************************************************************/

package service;

import repository.UserRepository;
import dto.UserDTO;
import beans.Account;
import beans.Gender;
import beans.User;
import beans.UserType;
import dto.UserFilterDTO;
import exceptions.BadRequestException;
import exceptions.DatabaseException;
import exceptions.NotUniqueException;

import java.util.*;

public class UserService {
   private UserRepository userRepository;
   
   
//Constructors
   public UserService(UserRepository userRepository) {
	super();
	this.userRepository = userRepository;
   }

//Methods
   public void register(UserDTO user) throws BadRequestException, DatabaseException {
	   validateRegistration(user);
      
      User createdUser = new User(new Account(user.getUsername(), user.getPassword(), false), user.getName(), user.getSurname(), false, false, user.getGender(), UserType.guest);
      userRepository.create(createdUser);
   }
   
   public void login(UserDTO user) {
	   
   }
   
   public User getById(long id) {
      // TODO: implement
      return null;
   }
   
   public User update() {
      // TODO: implement
      return null;
   }
   
   private void validateRegistration(UserDTO user) throws BadRequestException, DatabaseException {
	   if(user.getName().isEmpty() || user.getSurname().isEmpty() || user.getUsername().isEmpty() || user.getGender() == Gender.undefined || user.getPassword().isEmpty() || user.getControlPassword().isEmpty()) {
		   throw new BadRequestException();
	   }
	   
	   if(!isUsernameUnique(user.getUsername()))
		   throw new NotUniqueException(String.format("Username '%s' is not unique!", user.getUsername()));
	   
	   if(!user.getPassword().equals(user.getControlPassword())) {
		   throw new BadRequestException();
	   }
   }
   
   private boolean isUsernameUnique(String username) {
	   return userRepository.isUsernameUnique(username);
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
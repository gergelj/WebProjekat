/***********************************************************************
 * Module:  UserRepository.java
 * Author:  Geri
 * Purpose: Defines the Class UserRepository
 ***********************************************************************/

package repository;

import java.util.*;
import java.util.stream.Collectors;

import beans.User;
import exceptions.NotUniqueException;
import repository.abstractrepository.IUserRepository;
import repository.csv.CSVRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;
import specification.ISpecification;

public class UserRepository extends CSVRepository<User> implements IUserRepository {
   private String notUniqueError = "Username '%s' is not unique!";
   
   public UserRepository(ICsvStream<User> stream, LongSequencer sequencer) {
		super("User", stream, sequencer);
	}
   
   @Override
   public User create(User entity) throws NotUniqueException {
	   if(isUsernameUnique(entity.getUsername()))
		   return super.create(entity);
	   else
		   throw new NotUniqueException(String.format(notUniqueError, entity.getUsername()));
   }
   
   private boolean isUsernameUnique(String username) {
	   User user = getByUsername(username);
	   return user == null ? true : false;
   }
   
   public List<User> find(ISpecification<User> specification) {
      return getAll().stream().filter(user -> specification.isSatisfiedBy(user)).collect(Collectors.toList());
   }

	@Override
	public User getByUsername(String username) {
		try {
			return getAll().stream().filter(user -> user.getUsername().equals(username)).findFirst().get();			
		}catch(NoSuchElementException e) {
			return null;
		}
	}

}
/***********************************************************************
 * Module:  UserRepository.java
 * Author:  Geri
 * Purpose: Defines the Class UserRepository
 ***********************************************************************/

package repository;

import java.util.*;

import beans.User;
import repository.abstractrepository.IUserRepository;
import repository.csv.CSVRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.ISequencer;
import specification.ISpecification;

public class UserRepository extends CSVRepository<User,Long> implements IUserRepository {
   private String notUniqueError;
   
   public UserRepository(ICsvStream<User> stream, ISequencer<Long> sequencer) {
		super("User", stream, sequencer);
	}
   
   private boolean isUsernameUnique(String username) {
      // TODO: implement
      return false;
   }
   
   public List<User> find(ISpecification<User> specification) {
      // TODO: implement
      return null;
   }

}
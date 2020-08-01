/***********************************************************************
 * Module:  UserRepository.java
 * Author:  Geri
 * Purpose: Defines the Class UserRepository
 ***********************************************************************/

package repository;

import java.util.*;
import java.util.stream.Collectors;

import beans.Account;
import beans.User;
import exceptions.DatabaseException;
import exceptions.EntityNotFoundException;
import exceptions.NotUniqueException;
import repository.abstractrepository.IAccountRepository;
import repository.abstractrepository.IUserRepository;
import repository.csv.CSVRepository;
import repository.csv.IEagerCsvRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;
import specification.ISpecification;

public class UserRepository extends CSVRepository<User> implements IUserRepository, IEagerCsvRepository<User> {
   private String notUniqueError = "Username '%s' is not unique!";
   
   private IAccountRepository accountRepository;
     
   
   public UserRepository(ICsvStream<User> stream, LongSequencer sequencer, IAccountRepository accountRepository) {
		super("User", stream, sequencer);
		this.accountRepository = accountRepository;
	}
   
   
   private boolean isUsernameUnique(String username) {
		try {
			getByUsername(username);
			return false;
		} catch (DatabaseException e) {
			return true;
		}
   }
   
   public List<User> find(ISpecification<User> specification) {
      return getAll().stream().filter(user -> specification.isSatisfiedBy(user)).collect(Collectors.toList());
   }

	@Override
	public User getByUsername(String username) throws DatabaseException {
		try {
			return getAll().stream().filter(user -> user.getAccount().getUsername().equals(username)).findFirst().get();			
		}catch(NoSuchElementException e) {
			throw new EntityNotFoundException(String.format(this.notFoundError, this.entityName, "username", username));
		}
	}


	private void bind(List<User> users) throws DatabaseException
	{
		for(User user: users)
		{
			user.setAccount(getAccountById(user.getAccount()));
		}
	}
	
	@Override
	public User getEager(long id) throws DatabaseException {
		User user = getById(id);
		
		user.setAccount(getAccountById(user.getAccount()));
	
		return user;
	}

	private Account getAccountById(Account account) throws DatabaseException
	{
		return account == null? null : accountRepository.getById(account.getId());
	}
	

	@Override
	public List<User> getAllEager() throws DatabaseException {
		List<User> users = getAll();
		
		bind(users);
		
		return users;
	}

}
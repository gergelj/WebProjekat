/***********************************************************************
 * Module:  IUserRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IUserRepository
 ***********************************************************************/

package repository.abstractrepository;

import java.util.*;

import beans.User;
import exceptions.DatabaseException;
import specification.ISpecification;

public interface IUserRepository extends IRepository<User> {

   List<User> find(ISpecification<User> specification);
   
   User getByUsername(String username) throws DatabaseException;

}
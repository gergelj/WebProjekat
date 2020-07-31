/***********************************************************************
 * Module:  IUserRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IUserRepository
 ***********************************************************************/

package repository.abstractrepository;

import java.util.*;

import beans.User;
import specification.ISpecification;

public interface IUserRepository extends IRepository<User> {
   /** @param specification */
   List<User> find(ISpecification<User> specification);

}
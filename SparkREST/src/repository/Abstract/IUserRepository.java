/***********************************************************************
 * Module:  IUserRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IUserRepository
 ***********************************************************************/

package repository.abstract;

import java.util.*;

public interface IUserRepository extends IRepository<User,Long> {
   /** @param specification */
   List<User> find(ISpecification<User> specification);

}
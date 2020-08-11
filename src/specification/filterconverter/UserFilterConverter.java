/***********************************************************************
 * Module:  UserFilterConverter.java
 * Author:  Geri
 * Purpose: Defines the Class UserFilterConverter
 ***********************************************************************/

package specification.filterconverter;

import dto.UserFilterDTO;
import specification.ISpecification;
import specification.specificationimpl.BooleanSpecification;
import specification.specificationimpl.UserGenderSpecification;
import specification.specificationimpl.UserUserTypeSpecification;
import specification.specificationimpl.UserUsernameSpecification;
import beans.User;
import beans.enums.Gender;
import beans.enums.UserType;

public class UserFilterConverter {

   public static ISpecification<User> getSpecification(UserFilterDTO filter) {
      ISpecification<User> specification = new BooleanSpecification<User>(true);
      
      if(!filter.getUsername().isEmpty())
    	  specification = specification.and(new UserUsernameSpecification(filter.getUsername()));
      
      if(filter.getUserType() != UserType.undefined)
    	  specification = specification.and(new UserUserTypeSpecification(filter.getUserType()));
      
      if(filter.getGender() != Gender.undefined)
    	  specification = specification.and(new UserGenderSpecification(filter.getGender()));
      
      return specification;
   }

}
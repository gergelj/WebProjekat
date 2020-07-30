/***********************************************************************
 * Module:  UserUserTypeSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class UserUserTypeSpecification
 ***********************************************************************/

package specification.specificationimpl;

import beans.User;
import beans.UserType;
import specification.AbstractSpecification;

public class UserUserTypeSpecification extends AbstractSpecification<User> {

	private UserType userType;
	
	public UserUserTypeSpecification(UserType userType) {
		super();
		this.userType = userType;
	}

	@Override
	public boolean isSatisfiedBy(User candidate) {
		// TODO Auto-generated method stub
		return false;
	}
}
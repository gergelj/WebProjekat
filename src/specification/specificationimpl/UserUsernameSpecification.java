/***********************************************************************
 * Module:  UserUsernameSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class UserUsernameSpecification
 ***********************************************************************/

package specification.specificationimpl;

import beans.User;
import specification.AbstractSpecification;

public class UserUsernameSpecification extends AbstractSpecification<User> {

	private String username;
	
	public UserUsernameSpecification(String username) {
		super();
		this.username = username;
	}

	@Override
	public boolean isSatisfiedBy(User candidate) {
		// TODO Auto-generated method stub
		return false;
	}
}
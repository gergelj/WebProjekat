/***********************************************************************
 * Module:  UserGenderSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class UserGenderSpecification
 ***********************************************************************/

package specification.specificationimpl;

import beans.Gender;
import beans.User;
import specification.AbstractSpecification;

public class UserGenderSpecification extends AbstractSpecification<User> {

	private Gender gender;
	
	public UserGenderSpecification(Gender gender) {
		super();
		this.gender = gender;
	}

	@Override
	public boolean isSatisfiedBy(User candidate) {
		// TODO Auto-generated method stub
		return false;
	}
}
/***********************************************************************
 * Module:  UserGenderSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class UserGenderSpecification
 ***********************************************************************/

package specification.specificationimpl;

import beans.User;
import beans.enums.Gender;
import specification.AbstractSpecification;

public class UserGenderSpecification extends AbstractSpecification<User> {

	private Gender gender;
	
	public UserGenderSpecification(Gender gender) {
		super();
		this.gender = gender;
	}

	@Override
	public boolean isSatisfiedBy(User candidate) {
		return gender == candidate.getGender();
	}
}
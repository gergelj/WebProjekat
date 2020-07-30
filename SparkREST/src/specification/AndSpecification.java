/***********************************************************************
 * Module:  AndSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class AndSpecification
 ***********************************************************************/

package specification;

import java.util.*;

public class AndSpecification <T> extends AbstractSpecification<T> {
   private ISpecification<T> leftSpecification;
   private ISpecification<T> rightSpecification;
   
	@Override
	public boolean isSatisfiedBy(T candidate) {
		// TODO Auto-generated method stub
		return false;
	}

}
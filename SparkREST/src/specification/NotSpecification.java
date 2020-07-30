/***********************************************************************
 * Module:  NotSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class NotSpecification
 ***********************************************************************/

package specification;

import java.util.*;

public class NotSpecification <T> extends AbstractSpecification<T> {
   private ISpecification<T> leftSpecification;

	@Override
	public boolean isSatisfiedBy(T candidate) {
		// TODO Auto-generated method stub
		return false;
	}
   

}
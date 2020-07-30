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
   
//Constructors  
	public AndSpecification(ISpecification<T> leftSpecification, ISpecification<T> rightSpecification) {
		super();
		this.leftSpecification = leftSpecification;
		this.rightSpecification = rightSpecification;
	}

	@Override
	public boolean isSatisfiedBy(T candidate) {
		// TODO Auto-generated method stub
		return false;
	}

}
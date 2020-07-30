/***********************************************************************
 * Module:  BooleanSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class BooleanSpecification
 ***********************************************************************/

package specification.specificationimpl;


public class BooleanSpecification <T> extends specification.AbstractSpecification<T> {
   private boolean value;
   
	public BooleanSpecification(boolean value) {
		super();
		this.value = value;
	}

	@Override
	public boolean isSatisfiedBy(T candidate) {
		return this.value;
	}

}
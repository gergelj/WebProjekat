/***********************************************************************
 * Module:  OrSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class OrSpecification
 ***********************************************************************/

package specification;

public class OrSpecification <T> extends AbstractSpecification<T> {
   
	public OrSpecification(ISpecification<T> leftSpecification, ISpecification<T> rightSpecification) {
		super();
		this.leftSpecification = leftSpecification;
		this.rightSpecification = rightSpecification;
	}

	private ISpecification<T> leftSpecification;
	private ISpecification<T> rightSpecification;
   
	@Override
	public boolean isSatisfiedBy(T candidate) {
		// TODO Auto-generated method stub
		return false;
	}

}
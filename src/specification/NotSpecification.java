/***********************************************************************
 * Module:  NotSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class NotSpecification
 ***********************************************************************/

package specification;


public class NotSpecification <T> extends AbstractSpecification<T> {
   private ISpecification<T> leftSpecification;

	public NotSpecification(ISpecification<T> leftSpecification) {
		super();
		this.leftSpecification = leftSpecification;
	}

	@Override
	public boolean isSatisfiedBy(T candidate) {
		return !leftSpecification.isSatisfiedBy(candidate);
	}
   

}
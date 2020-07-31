/***********************************************************************
 * Module:  AndSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class AndSpecification
 ***********************************************************************/

package specification;


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
		return leftSpecification.isSatisfiedBy(candidate) && rightSpecification.isSatisfiedBy(candidate);
	}

}
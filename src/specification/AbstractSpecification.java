/***********************************************************************
 * Module:  AbstractSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class AbstractSpecification
 ***********************************************************************/

package specification;


public abstract class AbstractSpecification <T> implements ISpecification<T> {
	
   public ISpecification<T> and(ISpecification<T> other) {
      return new AndSpecification<T>(this, other);
   }
   
   public ISpecification<T> or(ISpecification<T> other) {
      return new OrSpecification<T>(this, other);
   }
   
   public ISpecification<T> not(ISpecification<T> other) {
      return new NotSpecification<T>(other);
   }
   
   public abstract boolean isSatisfiedBy(T candidate);

}
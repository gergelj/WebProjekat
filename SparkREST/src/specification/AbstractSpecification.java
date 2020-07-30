/***********************************************************************
 * Module:  AbstractSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class AbstractSpecification
 ***********************************************************************/

package specification;

import java.util.*;

public abstract class AbstractSpecification <T> implements ISpecification<T> {
   public ISpecification<T> and(ISpecification<T> other) {
      // TODO: implement
      return null;
   }
   
   public ISpecification<T> or(ISpecification<T> other) {
      // TODO: implement
      return null;
   }
   
   public ISpecification<T> not(ISpecification<T> other) {
      // TODO: implement
      return null;
   }
   
   public abstract boolean isSatisfiedBy(T candidate);

}
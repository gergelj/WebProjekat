/***********************************************************************
 * Module:  ISpecification.java
 * Author:  Geri
 * Purpose: Defines the Interface ISpecification
 ***********************************************************************/

package specification;

import java.util.*;

public interface ISpecification <T> {
   boolean isSatisfiedBy(T candidate);
   ISpecification<T> and(ISpecification<T> other);
   ISpecification<T> or(ISpecification<T> other);
   ISpecification<T> not(ISpecification<T> other);

}
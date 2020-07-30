/***********************************************************************
 * Module:  ISpecification.java
 * Author:  Geri
 * Purpose: Defines the Interface ISpecification
 ***********************************************************************/

package specification;

import java.util.*;

public interface ISpecification <T> {
   /** @param candidate */
   bool isSatisfiedBy(T candidate);
   /** @param other */
   ISpecification<T> and(ISpecification<T> other);
   /** @param other */
   ISpecification<T> or(ISpecification<T> other);
   /** @param other */
   ISpecification<T> not(ISpecification<T> other);

}
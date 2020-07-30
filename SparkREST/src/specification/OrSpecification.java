/***********************************************************************
 * Module:  OrSpecification.java
 * Author:  Geri
 * Purpose: Defines the Class OrSpecification
 ***********************************************************************/

package specification;

import java.util.*;

public class OrSpecification <T> extends AbstractSpecification<T> {
   private ISpecification<T> leftSpecification;
   private ISpecification<T> rightSpecification;
   
   /** @param candidate */
   public bool isSatisfiedBy(T candidate) {
      // TODO: implement
      return null;
   }

}
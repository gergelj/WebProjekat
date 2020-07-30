/***********************************************************************
 * Module:  PriceRange.java
 * Author:  Geri
 * Purpose: Defines the Class PriceRange
 ***********************************************************************/

package beans;

import java.util.*;

public class PriceRange {
   private double startPrice;
   private double endPrice;
   
   public boolean isIn(double price) {
      return price >= startPrice && price <= endPrice;
   }

}
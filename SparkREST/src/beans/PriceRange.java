/***********************************************************************
 * Module:  PriceRange.java
 * Author:  Geri
 * Purpose: Defines the Class PriceRange
 ***********************************************************************/

package beans;

public class PriceRange {
   private double startPrice;
   private double endPrice;
   
//Constructors
	public PriceRange(double startPrice, double endPrice) {
		super();
		this.startPrice = startPrice;
		this.endPrice = endPrice;
	}
	
	public PriceRange() {
		super();
	}
	
//Getters and Setters
	public double getStartPrice() {
		return startPrice;
	}
	
	public void setStartPrice(double startPrice) {
		this.startPrice = startPrice;
	}
	
	public double getEndPrice() {
		return endPrice;
	}
	
	public void setEndPrice(double endPrice) {
		this.endPrice = endPrice;
	}
	
//Methods
	public boolean isIn(double price) {
	   return price >= startPrice && price <= endPrice;
	}
   
}
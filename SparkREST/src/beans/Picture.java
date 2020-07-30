/***********************************************************************
 * Module:  Picture.java
 * Author:  Geri
 * Purpose: Defines the Class Picture
 ***********************************************************************/

package beans;

public class Picture {
   private String name;

//Constructors
	public Picture(String name) {
		super();
		this.name = name;
	}
	
	public Picture() {
		super();
	}
	
//Getters and Setters
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
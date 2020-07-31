/***********************************************************************
 * Module:  Amenity.java
 * Author:  Geri
 * Purpose: Defines the Class Amenity
 ***********************************************************************/

package beans;

public class Amenity implements IIdentifiable, IDeletable {
	private long id;
	private String name;
	private boolean deleted;
	
	public Amenity() {
		
	}
   
	public Amenity(long id, String name, boolean deleted) {
		this.id = id;
		this.name = name;
		this.deleted = deleted;
	}

	public Amenity(String name, boolean deleted) {
		this.name = name;
		this.deleted = deleted;
	}
	
	public Amenity(long id)
	{
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public long getId() {
		return id;
	}
   
	public void setId(long id) {
		this.id = id;
	}
   
	public void delete() {
		this.setDeleted(true);
	}

}
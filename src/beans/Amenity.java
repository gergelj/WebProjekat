/***********************************************************************
 * Module:  Amenity.java
 * Author:  Geri
 * Purpose: Defines the Class Amenity
 ***********************************************************************/

package beans;

import beans.interfaces.IDeletable;
import beans.interfaces.IIdentifiable;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Amenity other = (Amenity) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
/***********************************************************************
 * Module:  User.java
 * Author:  Geri
 * Purpose: Defines the Class User
 ***********************************************************************/

package beans;

public class User implements IDeletable, IIdentifiable {
   private Account account;
   private String name;
   private String surname;
   private long id;
   private boolean deleted;
   private boolean blocked;
   
   private Gender gender;
   private UserType userType;
   
   public long getId() {
	      return this.id;
   }
	   
   public void setId(long id) {
	  this.id = id;
   }
   
   public void delete() {
      this.setDeleted(true);
   }

	public User(long id, Account account, String name, String surname, boolean deleted, boolean blocked,
			Gender gender, UserType userType) {
		super();
		this.account = account;
		this.name = name;
		this.surname = surname;
		this.id = id;
		this.deleted = deleted;
		this.blocked = blocked;
		this.gender = gender;
		this.userType = userType;
	}

	public User() {
		super();
	}
	
	public User(Account account, String name, String surname, boolean deleted, boolean blocked,
			Gender gender, UserType userType) {
		super();
		this.account = account;
		this.name = name;
		this.surname = surname;
		this.deleted = deleted;
		this.blocked = blocked;
		this.gender = gender;
		this.userType = userType;
	}
	
	public User(long id) {
		this.id = id;
	}
	
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public UserType getUserType() {
		return userType;
	}
	
	public void setUserType(UserType userType) {
		this.userType = userType;
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
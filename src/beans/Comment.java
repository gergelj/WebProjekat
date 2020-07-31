/***********************************************************************
 * Module:  Comment.java
 * Author:  Geri
 * Purpose: Defines the Class Comment
 ***********************************************************************/

package beans;

public class Comment implements IDeletable, IIdentifiable {
   private long id;
   private String text;
   private int rating;
   private boolean deleted;
   private boolean approved;
   
   private User user;
   
//Constructors
	public Comment(long id, String text, int rating, boolean deleted, boolean approved, User user) {
		super();
		this.id = id;
		this.text = text;
		this.rating = rating;
		this.deleted = deleted;
		this.approved = approved;
		this.user = user;
	}
	
	public Comment() {
		super();
	}
	
	public Comment(String text, int rating, boolean deleted, boolean approved, User user) {
		super();
		this.text = text;
		this.rating = rating;
		this.deleted = deleted;
		this.approved = approved;
		this.user = user;
	}
	
	public Comment(long id)
	{
		this.id = id;
	}
	
//Getters and Setters
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public boolean isApproved() {
		return approved;
	}
	
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public long getId() {
	    return this.id;
	 }
	 
	 public void setId(long id) {
	    this.id = id;
	 }
  
//Methods
	 public void delete() {
	      // TODO: implement
	  }
}
package beans;

public class Account implements IIdentifiable, IDeletable{
	private long id;
	private String username;
	private String password;
	private boolean deleted;
	
//Constructors
	public Account(long id, String username, String password, boolean deleted) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.deleted = deleted;
	}
	
	public Account(String username, String password, boolean deleted) {
		super();
		this.username = username;
		this.password = password;
		this.deleted = deleted;
	}

	public Account(long id)
	{
		super();
		this.id = id;
	}
	
	public Account() {
		super();
	}

//Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;		
	}

	@Override
	public boolean isDeleted() {
		return this.deleted;
	}

//Methods
	@Override
	public void delete() {
		setDeleted(true);
	}


	
	
}

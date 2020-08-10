package dto;

import beans.UserType;

public class TokenDTO {
	
	private String token;
	private String username;
	private UserType userType;

	public TokenDTO() {
		
	}

	public TokenDTO(String token, String username, UserType userType) {
		super();
		this.token = token;
		this.username = username;
		this.userType = userType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
}

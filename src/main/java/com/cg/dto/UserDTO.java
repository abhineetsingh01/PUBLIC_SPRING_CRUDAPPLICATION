package com.cg.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
	
	private Integer id;
	
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@Email(message = "Email id not in required formats")
	private String emailId;
	
	public void setId(Integer id) {
		this.id=id;
	}
	
	public Integer getId() {
		return id;
	}

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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}

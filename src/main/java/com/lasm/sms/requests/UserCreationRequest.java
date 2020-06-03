package com.lasm.sms.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserCreationRequest {

	@NotBlank(message = "this field must not be NULL or EMPTY")
	@Size(min = 4, message = "this field must have at least 4 characters")
	private String firstName;
	
	@NotBlank(message = "this field must not be NULL or EMPTY")
	@Size(min = 4, message = "this field must have at least 4 characters")
	private String lastName;
	
	@NotBlank(message = "this field must not be NULL or EMPTY")
	@Size(min = 4, message = "this field must have at least 4 characters")
	private String role;
	
	@NotBlank(message = "this field must not be EMPTY or NULL")
	@Email
	private String email;
	
	@NotNull
	@Size(min = 8, message = "this field must have at least 8 characters")
	@Size(max = 12, message = "this field should not exceed 12 characters")
	private String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

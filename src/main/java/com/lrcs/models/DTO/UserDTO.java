package com.lrcs.models.DTO;

import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import com.lrcs.models.User;

public class UserDTO {
	
	private Long id;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String roles;
	
	public UserDTO(User user) {
		id = user.getId();
		email = user.getEmail();
		roles = user.getRoles().stream().map(x -> x.getAuthority()).collect(Collectors.joining(","));
	}
	
	public UserDTO(Long id, @NotBlank String email, @NotBlank String roles) {
		this.id = id;
		this.email = email;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
	
}

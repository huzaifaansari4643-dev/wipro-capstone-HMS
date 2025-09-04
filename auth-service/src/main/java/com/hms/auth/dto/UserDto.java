package com.hms.auth.dto;



import com.hms.auth.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {

	private long id;
	private String name;
	private String email;
	private String password;
	private UserRole userRole;


}
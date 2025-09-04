package com.hms.auth.services.auth;

import com.hms.auth.dto.SignupRequest;
import com.hms.auth.dto.UserDto;

public interface AuthService 
{

	UserDto signupUser(SignupRequest signupRequest);
	boolean hasUserWithEmail(String email);
}
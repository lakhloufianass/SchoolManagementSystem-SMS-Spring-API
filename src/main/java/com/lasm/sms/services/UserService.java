package com.lasm.sms.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lasm.sms.shared.dto.UserDto;



public interface UserService extends UserDetailsService{
	
	public UserDto createUser(UserDto userDto);
}

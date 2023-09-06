package com.springboot.rls.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.springboot.rls.model.User;
import com.springboot.rls.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDTO);
}

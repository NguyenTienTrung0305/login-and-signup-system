package com.springboot.rls.service;

import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtils {
	public static String getPrincipal() {
		org.springframework.security.core.Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		return username;
	}
}

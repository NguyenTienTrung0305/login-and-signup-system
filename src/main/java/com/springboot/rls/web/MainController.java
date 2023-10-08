package com.springboot.rls.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.rls.reponsitory.UserRepository;
import com.springboot.rls.service.SecurityUtils;

@Controller
public class MainController {
	
	private UserRepository userRepository;
	
	

	public MainController(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String home() {
		return "homepage";
	}
	
	@GetMapping("/showinfo")
	public String InfomationUser(Model model) {
		String email = SecurityUtils.getPrincipal();
		model.addAttribute("user" , userRepository.findByEmail(email));
		return "info";
	}
}

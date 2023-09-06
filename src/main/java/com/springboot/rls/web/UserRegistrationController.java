package com.springboot.rls.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.rls.service.UserService;
import com.springboot.rls.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
	
	private UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	// khi người dùng nhập data , data sẽ được lưu trữ gián tiếp trong user dto
	// gets a user object from here 
	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDTO() {
		return new UserRegistrationDto();
	}
	
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userRegistrationDTO) {
		userService.save(userRegistrationDTO);
		return "redirect:/registration?success";
	}
	
	
}

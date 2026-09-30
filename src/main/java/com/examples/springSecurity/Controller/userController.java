package com.examples.springSecurity.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examples.springSecurity.Service.AuthService;
import com.examples.springSecurity.dto.UserRegisterRequestDto;
import com.examples.springSecurity.dto.UserRegisterResponseDto;

@RestController
@RequestMapping("/api/user")
public class userController {
	
	private AuthService authService;
	
	public userController(AuthService authService)
	{
		this.authService=authService;
	}
	
	@GetMapping("/hello")
	public String sayhello()
	{
		return "hello";
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserRegisterResponseDto> register(
			@RequestBody UserRegisterRequestDto registerRequestDto)
	{
		UserRegisterResponseDto userRegisterResponsedto=authService.register(registerRequestDto);
		return ResponseEntity.ok(userRegisterResponsedto);
	}
}

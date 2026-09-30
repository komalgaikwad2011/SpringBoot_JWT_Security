package com.examples.springSecurity.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examples.springSecurity.Service.jwtService;
import com.examples.springSecurity.dto.LoginRequestDto;
import com.examples.springSecurity.dto.LoginResponseDto;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private jwtService JwtService;
	
	@PostMapping("/login")
	public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto)
	{
		Authentication authenticationRequest=
				UsernamePasswordAuthenticationToken.unauthenticated(
						loginRequestDto.getUsername(),
						loginRequestDto.getPassword()
						);
		
		Authentication authentication=
				authenticationManager.authenticate(authenticationRequest);
		
		
		String token=JwtService.generatedToken(authentication);
		return new LoginResponseDto(token);
	}
	

}

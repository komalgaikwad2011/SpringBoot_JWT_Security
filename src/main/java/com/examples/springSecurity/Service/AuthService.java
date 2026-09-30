package com.examples.springSecurity.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examples.springSecurity.Entity.Roles;
import com.examples.springSecurity.Entity.User;
import com.examples.springSecurity.Repository.roleRepo;
import com.examples.springSecurity.Repository.userRepo;
import com.examples.springSecurity.dto.UserRegisterRequestDto;
import com.examples.springSecurity.dto.UserRegisterResponseDto;

@Service
public class AuthService {
	
	private userRepo userrepo;
	private roleRepo relorepo;
	private PasswordEncoder passwordEncoder;

	public AuthService(userRepo userrepo, roleRepo relorepo, PasswordEncoder passwordEncoder) 
	{

		this.userrepo = userrepo;
		this.relorepo = relorepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserRegisterResponseDto register(UserRegisterRequestDto userRegisterRequestdto)
	{
		User user=new User();
		user.setUsername(userRegisterRequestdto.getUsername());
		
		String encodedPassword=passwordEncoder.encode(userRegisterRequestdto.getPassword());
		
		user.setPassword(encodedPassword);
		user.setEnabled(true);
		
		Roles role=relorepo.findByName("role_user").get();
		user.getRoles().add(role);
		
		userrepo.save(user);
		
		UserRegisterResponseDto responseDto=new UserRegisterResponseDto();
		
		responseDto.setUsername(user.getUsername());
		responseDto.setMessage("User saved Successfully");
		
		return responseDto;
		
	}
	
	
	

}

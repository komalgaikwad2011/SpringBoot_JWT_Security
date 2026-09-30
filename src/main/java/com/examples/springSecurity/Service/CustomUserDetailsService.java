package com.examples.springSecurity.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.examples.springSecurity.Entity.CustomeUserDetails;
import com.examples.springSecurity.Entity.User;
import com.examples.springSecurity.Repository.userRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private userRepo userrepo;
	
	public CustomUserDetailsService(userRepo userrepo)
	{
		this.userrepo=userrepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user=userrepo.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("User not found"));
		return new CustomeUserDetails(user);
	}
	
	

}

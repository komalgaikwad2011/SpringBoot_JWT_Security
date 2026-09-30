package com.examples.springSecurity.Entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.micrometer.common.lang.Nullable;

public class CustomeUserDetails implements UserDetails {
	
	private User user;
	
	public CustomeUserDetails(User user)
	{
		this.user=user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		return user.getRoles()
				.stream()
				.map(role->
						new SimpleGrantedAuthority(
								role.getName()
								)
						)
				.toList();
	}

	@Override
	public @Nullable String getPassword() 
	{
		return user.getPassword();
	}

	@Override
	public @Nullable String getUsername() 
	{
		return user.getUsername();
	}
	@Override
	public  boolean isEnabled() 
	{
		return user.isEnabled();
	}
	
	

}

package com.examples.springSecurity.Service;

import org.springframework.stereotype.Service;

import com.examples.springSecurity.Entity.Roles;
import com.examples.springSecurity.Repository.roleRepo;
@Service
public class roleService {

	
	private roleRepo rolerepo;
	
	public roleService(roleRepo rolerepo)
	{
		this.rolerepo=rolerepo;
	}
	
	
	public void addRole(Roles role)
	{
		rolerepo.save(role);
	}
}

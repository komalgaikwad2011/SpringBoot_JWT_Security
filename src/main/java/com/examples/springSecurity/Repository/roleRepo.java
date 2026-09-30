package com.examples.springSecurity.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examples.springSecurity.Entity.Roles;

public interface roleRepo extends JpaRepository<Roles,Long>{
	
	Optional<Roles> findByName(String name);

}

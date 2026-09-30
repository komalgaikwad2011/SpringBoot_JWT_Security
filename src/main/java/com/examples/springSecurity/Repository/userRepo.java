package com.examples.springSecurity.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examples.springSecurity.Entity.User;

public interface userRepo extends JpaRepository<User,Long>{
	
	Optional<User> findByUsername(String name);

}

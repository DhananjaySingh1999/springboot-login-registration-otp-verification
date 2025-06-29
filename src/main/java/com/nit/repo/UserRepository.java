package com.nit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nit.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{


	
	Optional<User> findByUemail(String username);

}

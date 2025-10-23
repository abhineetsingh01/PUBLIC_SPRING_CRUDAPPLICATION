package com.cg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmailId(String emailId);

}

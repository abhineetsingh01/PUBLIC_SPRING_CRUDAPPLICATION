package com.cg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
	Optional<Wallet> findByUserEmailId(String emailId);
	
	Optional<Wallet> findByUserId(Integer id);
	
}

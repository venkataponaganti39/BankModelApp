package com.minna.bma.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minna.bma.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	List<Transaction> findByBankAccountId(Long bankAccountId);

	List<Transaction> findByIdentifier(String identifier);

	Optional<Transaction> findById(Long id);

}

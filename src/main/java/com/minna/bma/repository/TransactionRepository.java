package com.minna.bma.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minna.bma.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByBankAccountUUID(Long bankAccountId);

	Optional<Transaction> findByUUID(Long UUID);

}

package com.minna.bma.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minna.bma.entity.CreditcardTransaction;

public interface CreditcardTransactionRepository extends JpaRepository<CreditcardTransaction, Long> {

	List<CreditcardTransaction> findByCreditcardUUID(Long creditcardId);

	Optional<CreditcardTransaction> findByUUID(Long UUID);

}

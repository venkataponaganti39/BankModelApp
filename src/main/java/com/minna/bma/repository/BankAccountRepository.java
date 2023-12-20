package com.minna.bma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minna.bma.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}

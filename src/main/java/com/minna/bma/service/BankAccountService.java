package com.minna.bma.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minna.bma.entity.BankAccount;
import com.minna.bma.entity.Transaction;
import com.minna.bma.repository.BankAccountRepository;
import com.minna.bma.repository.TransactionRepository;

@Service
public class BankAccountService {

	private final BankAccountRepository bankAccountRepository;
	private final TransactionRepository transactionRepository;

	@Autowired
	public BankAccountService(BankAccountRepository bankAccountRepository,
			TransactionRepository transactionRepository) {
		this.bankAccountRepository = bankAccountRepository;
		this.transactionRepository = transactionRepository;
	}

	public Transaction createTransaction(Long UUID, Transaction transaction) {
		transaction.setBankAccount(new BankAccount(UUID));
		return transactionRepository.save(transaction);
	}

	public BankAccount createBankAccount() {
		BankAccount bankAccount = new BankAccount(UUID.randomUUID(), UUID.randomUUID());
		return bankAccountRepository.save(bankAccount);
	}

	public BankAccount retrieveBankAccountById(Long UUID) {
		BankAccount bankAccount = bankAccountRepository.findById(UUID).orElse(null);
		return bankAccount;
	}

	public List<Transaction> retrieveAllTransactionsByBankId(Long UUID) {
		List<Transaction> transactionList = transactionRepository.findByBankAccountUUID(UUID);
		return transactionList;
	}

	public Transaction retrieveTransactionById(Long UUID) {
		Transaction transaction = transactionRepository.findById(UUID).orElse(null);
		return transaction;
	}

	public List<BankAccount> retrieveAllBankAccounts() {
		return bankAccountRepository.findAll();
	}

	public BankAccount updateBankAccount(Long UUID, BankAccount updatedBankAccount) throws Exception {
		BankAccount bankAccount = bankAccountRepository.findById(UUID)
				.orElseThrow(() -> new Exception("Bank account not found"));
		bankAccount.setIdentifier(updatedBankAccount.getIdentifier());
		bankAccount.setUserIdentifier(updatedBankAccount.getUserIdentifier());
		return bankAccountRepository.save(bankAccount);
	}
}

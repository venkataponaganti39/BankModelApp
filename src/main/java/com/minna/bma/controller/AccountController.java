package com.minna.bma.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minna.bma.entity.BankAccount;
import com.minna.bma.entity.DurationVO;
import com.minna.bma.entity.Transaction;
import com.minna.bma.service.BankAccountService;

@RestController
@RequestMapping("/api")
public class AccountController {
	private final BankAccountService bankAccountService;

	@Autowired
	public AccountController(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}
	
	@PostMapping("/bank-accounts")
	public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount) {
		return new ResponseEntity<>(bankAccountService.createBankAccount(bankAccount), HttpStatus.CREATED);
	}
	
	@GetMapping("/bank-accounts")
	public ResponseEntity<List<BankAccount>> retrieveAllBankAccounts() {
		List<BankAccount> bankAccounts = bankAccountService.retrieveAllBankAccounts();
		return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
	}

	@GetMapping("/bank-accounts/{id}")
	public ResponseEntity<BankAccount> retrieveBankAccountById(@PathVariable Long id) {
		BankAccount bankAccount = bankAccountService.retrieveBankAccountById(id);
		return new ResponseEntity<>(bankAccount, HttpStatus.OK);
	}
	
	@PutMapping("/bank-accounts/{id}")
	public ResponseEntity<BankAccount> updateBankAccount(@PathVariable Long id, @RequestBody BankAccount updteBankAccount) throws Exception {
		return new ResponseEntity<>(bankAccountService.updateBankAccount(id, updteBankAccount), HttpStatus.OK);
	}
	
	@GetMapping("/bank-accounts/{id}/transactions")
	public ResponseEntity<List<Transaction>> retrieveAllTransactionsByBankId(@PathVariable Long id) {
		List<Transaction> transactionList = bankAccountService.retrieveAllTransactionsByBankId(id);
		return new ResponseEntity<>(transactionList, HttpStatus.OK);
	}
	
	@PostMapping("/bank-accounts/{id}/transactions")
	public ResponseEntity<Transaction> createTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
		transaction.setDate(LocalDateTime.now());
		return new ResponseEntity<>(bankAccountService.createTransaction(id, transaction), HttpStatus.CREATED);
	}
	
	@GetMapping("/transactions/{id}")
	public ResponseEntity<Transaction> retrieveTransactoinById(@PathVariable Long id) {
		Transaction transaction = bankAccountService.retrieveTransactoinById(id);
		return new ResponseEntity<>(transaction, HttpStatus.OK);
	}

	@GetMapping
	public List<BankAccount> getBankAccountsByTransactionAmount() {
		List<BankAccount> bankAccountList = bankAccountService.retrieveAllBankAccounts();
		List<BankAccount> accountList = new ArrayList<>();
		Boolean accFlag;
		for (BankAccount account : bankAccountList) {
			accFlag = false;
			for (Transaction transaction : account.getTransactions()) {
				if (transaction.getAmount() < 0) {
					accFlag = true;
				}
			}
			if (accFlag)
				accountList.add(account);
		}
		return accountList;
	}

	@GetMapping("/balance/{id}")
	public Double calculateBalance(@PathVariable Long id) {
		Double balance = 0.0;
		List<Transaction> transactionList = bankAccountService.retrieveAllTransactionsByBankId(id);
		for (Transaction transaction : transactionList) {
			balance += transaction.getAmount();
		}
		return balance;
	}


	@GetMapping("/interval/{id}/{text}")
	public List<DurationVO> getTimeIntervalBetweenTransactions(@PathVariable Long id,
			@PathVariable String text) {
		List<Transaction> transactions = bankAccountService.retrieveAllTransactionsByBankId(id);
		List<Transaction> transactionList = new ArrayList<>();
		LocalDateTime firstTransactionTime = null;
		LocalDateTime secondTransactionTime = null;
		List<DurationVO> durationList = new ArrayList<>();
		DurationVO durationVO;
		for (Transaction transaction : transactions) {
			if (transaction.getText().trim().equalsIgnoreCase(text.trim())) {
				transactionList.add(transaction);
			}
		}
		for (int i = 0; i < (transactionList.size()) - 1; i++) {
			durationVO = new DurationVO();
			firstTransactionTime = transactions.get(i).getDate();
			secondTransactionTime = transactions.get(i + 1).getDate();
			durationVO.setDuration(Duration.between(firstTransactionTime, secondTransactionTime));
			durationVO.setBankAccId(id);
			durationVO.setText(text);
			durationVO.setDate(secondTransactionTime);
			durationList.add(durationVO);
		}
		return durationList;
	}
}

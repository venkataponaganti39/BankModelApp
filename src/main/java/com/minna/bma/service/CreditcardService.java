package com.minna.bma.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minna.bma.entity.Creditcard;
import com.minna.bma.entity.CreditcardTransaction;
import com.minna.bma.repository.CreditcardRepository;
import com.minna.bma.repository.CreditcardTransactionRepository;
import com.minna.bma.util.AccountUtil;

@Service
public class CreditcardService {

	private final CreditcardRepository creditcardRepository;
	private final CreditcardTransactionRepository creditcardTransactionRepository;

	@Autowired
	public CreditcardService(CreditcardRepository creditcardRepository,
			CreditcardTransactionRepository creditcardTransactionRepository) {
		this.creditcardRepository = creditcardRepository;
		this.creditcardTransactionRepository = creditcardTransactionRepository;
	}

	public CreditcardTransaction createTransaction(Long UUID, CreditcardTransaction transaction) {
		transaction.setCreditCard(new Creditcard(UUID));
		return creditcardTransactionRepository.save(transaction);
	}

	public Creditcard createCreditcard() {
		Creditcard creditcard = new Creditcard(AccountUtil.generateCreditCardNumber(), UUID.randomUUID());
		return creditcardRepository.save(creditcard);
	}

	public Creditcard retrieveCreditcardById(Long UUID) {
		Creditcard creditcard = creditcardRepository.findById(UUID).orElse(null);
		return creditcard;
	}

	public List<CreditcardTransaction> retrieveAllTransactionsByCreditcardId(Long UUID) {
		List<CreditcardTransaction> transactionList = creditcardTransactionRepository.findByCreditcardUUID(UUID);
		return transactionList;
	}

	public CreditcardTransaction retrieveTransactionById(Long UUID) {
		CreditcardTransaction transaction = creditcardTransactionRepository.findById(UUID).orElse(null);
		return transaction;
	}

	public List<Creditcard> retrieveAllCreditcards() {
		return creditcardRepository.findAll();
	}

}

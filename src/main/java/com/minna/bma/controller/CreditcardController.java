package com.minna.bma.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minna.bma.entity.Creditcard;
import com.minna.bma.entity.CreditcardTransaction;
import com.minna.bma.service.CreditcardService;

@RestController
@RequestMapping("/api")
public class CreditcardController {
	private final CreditcardService creditcardService;

	@Autowired
	public CreditcardController(CreditcardService creditcardService) {
		this.creditcardService = creditcardService;
	}

	@PostMapping("/creditcards")
	public ResponseEntity<Creditcard> createCreditcard() {
		return new ResponseEntity<>(creditcardService.createCreditcard(), HttpStatus.CREATED);
	}

	@GetMapping("/creditcards")
	public ResponseEntity<List<Creditcard>> retrieveCreditcards() {
		List<Creditcard> creditcards = creditcardService.retrieveAllCreditcards();
		return new ResponseEntity<>(creditcards, HttpStatus.OK);
	}

	@GetMapping("/creditcards/{UUID}")
	public ResponseEntity<Creditcard> retrieveCreditcardById(@PathVariable Long UUID) {
		Creditcard creditcard = creditcardService.retrieveCreditcardById(UUID);
		return new ResponseEntity<>(creditcard, HttpStatus.OK);
	}

	@PostMapping("/creditcards/{UUID}/transactions")
	public ResponseEntity<CreditcardTransaction> createTransaction(@PathVariable Long UUID,
			@RequestBody CreditcardTransaction transaction) {
		transaction.setDate(LocalDateTime.now());
		return new ResponseEntity<>(creditcardService.createTransaction(UUID, transaction), HttpStatus.CREATED);
	}


}
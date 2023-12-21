package com.minna.bma.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "creditcard")
public class Creditcard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long UUID;
	private String creditCardNumber;
	private UUID userIdentifier;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "creditcard", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CreditcardTransaction> transactions;

	public Creditcard() {
	}
	
	

	/**
	 * @param uUID
	 */
	public Creditcard(Long uUID) {
		super();
		UUID = uUID;
	}



	/**
	 * @param creditCardNumber
	 * @param userIdentifier
	 */
	public Creditcard(String creditCardNumber, java.util.UUID userIdentifier) {
		super();
		this.creditCardNumber = creditCardNumber;
		this.userIdentifier = userIdentifier;
	}



	/**
	 * @return the uUID
	 */
	public Long getUUID() {
		return UUID;
	}

	/**
	 * @param uUID the uUID to set
	 */
	public void setUUID(Long uUID) {
		UUID = uUID;
	}

	/**
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * @param creditCardNumber the creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	

	/**
	 * @return the userIdentifier
	 */
	public UUID getUserIdentifier() {
		return userIdentifier;
	}

	/**
	 * @param userIdentifier the userIdentifier to set
	 */
	public void setUserIdentifier(UUID userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	/**
	 * @return the transactions
	 */
	public List<CreditcardTransaction> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<CreditcardTransaction> transactions) {
		this.transactions = transactions;
	}

	

}

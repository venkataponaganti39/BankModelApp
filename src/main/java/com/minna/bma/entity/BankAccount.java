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
@Table(name = "bank_account")
public class BankAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long UUID;
	private UUID identifier;
	private UUID userIdentifier;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankAccount", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Transaction> transactions;

	public BankAccount() {
	}

	public BankAccount(Long uUID) {
		super();
		UUID = uUID;
	}
	
	/**
	 * @param identifier
	 * @param userIdentifier
	 */
	public BankAccount(java.util.UUID identifier, java.util.UUID userIdentifier) {
		super();
		this.identifier = identifier;
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
	 * @return the identifier
	 */
	public UUID getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(UUID identifier) {
		this.identifier = identifier;
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
	public List<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}

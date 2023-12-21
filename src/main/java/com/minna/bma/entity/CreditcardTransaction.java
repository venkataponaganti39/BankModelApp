package com.minna.bma.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "creditcard_transaction")
public class CreditcardTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long UUID;
	private LocalDateTime date;
	private String text;
	private String type;
	private double amount;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creditcard_id", referencedColumnName = "UUID")
	@JsonIgnore
	private Creditcard creditcard;

	public CreditcardTransaction() {
	}
	
	

	/**
	 * @param uUID
	 */
	public CreditcardTransaction(Long uUID) {
		super();
		UUID = uUID;
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
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the creditCard
	 */
	public Creditcard getCreditCard() {
		return creditcard;
	}

	/**
	 * @param creditcard the creditCard to set
	 */
	public void setCreditCard(Creditcard creditcard) {
		this.creditcard = creditcard;
	}

}

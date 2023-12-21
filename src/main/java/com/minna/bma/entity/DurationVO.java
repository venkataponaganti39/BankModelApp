package com.minna.bma.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class DurationVO {
	private LocalDateTime date;
	private String text;
	private Long bankAccId;
	private Duration duration;

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
	 * @return the bankAccId
	 */
	public Long getBankAccId() {
		return bankAccId;
	}

	/**
	 * @param bankAccId the bankAccId to set
	 */
	public void setBankAccId(Long bankAccId) {
		this.bankAccId = bankAccId;
	}

	/**
	 * @return the duration
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

}

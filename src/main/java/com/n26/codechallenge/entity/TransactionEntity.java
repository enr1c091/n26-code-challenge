package com.n26.codechallenge.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.n26.codechallenge.utils.Constants;

@Entity
@Table(name = "transaction")
public class TransactionEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@NotNull
	private Long timestamp;

	@NotNull
	private Double amount;

	public TransactionEntity(Long timestamp, Double amount) {
		this.timestamp = timestamp;
		this.amount = amount;
	}
	
	public TransactionEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public boolean isWithinTimeLimit() {
		return Instant.now().toEpochMilli() - this.timestamp <= Constants.TIMELIMIT_IN_EPOCH_MILLIS;
	}

}

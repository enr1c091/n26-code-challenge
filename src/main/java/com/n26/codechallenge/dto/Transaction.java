package com.n26.codechallenge.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents a Transaction within the System specifications")
@JsonInclude(Include.NON_NULL)
public class Transaction implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@NotNull
	@JsonProperty(value = "timestamp")
	@ApiModelProperty(notes = "Transaction time in epoch in millis in UTC time zone", required = true, allowEmptyValue = false, example = "1478192204000")
	private Long timestamp;
	
	@NotNull
	@JsonProperty(value = "amount")
	@ApiModelProperty(notes = "The desired amount of the transaction", required = true, allowEmptyValue = false, example = "12.3")
	private Double amount;
	
	@JsonCreator
	public Transaction(@JsonProperty("timestamp") Long timestamp, @JsonProperty("amount") Double amount) {
		this.timestamp = timestamp;
		this.amount = amount;
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
	
}

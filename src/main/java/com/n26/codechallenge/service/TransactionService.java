package com.n26.codechallenge.service;

import java.time.Instant;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.n26.codechallenge.dto.Transaction;
import com.n26.codechallenge.exception.BadRequestException;
import com.n26.codechallenge.exception.InternalServerException;
import com.n26.codechallenge.utils.Constants;

@Service
public class TransactionService {

	private static Logger logger = LoggerFactory.getLogger(TransactionService.class);
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private StatisticsService statisticsService;

	/*
	 * Receives a transaction object and persists it to a data collection in case its not older than 60 seconds. 
	 * 
	 * @param transaction	Transaction object containing its amount and timestamp.
	 * 
	 * @return				<code>409</code> if transaction data violates any validation constraints;
	 * 						<code>500</code> In case of Runtime Exceptions;
	 * 						<code>201</code> if transaction is successfully created;
	 * 						<code>204</code> Otherwise - if transaction is older than 60 seconds.
	 * */
	public ResponseEntity<HttpStatus> addTransaction(Transaction transaction) {
		Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
		if (!violations.isEmpty()) {
			throw new BadRequestException();
		}
		try {
			if (Instant.now().toEpochMilli() - transaction.getTimestamp() <= Constants.TIMELIMIT_IN_EPOCH_MILLIS) {
				statisticsService.addTransaction(transaction);
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
		} catch (Exception ex) {
			logger.error("Unable to add new transaction", ex);
			throw new InternalServerException();
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}


}
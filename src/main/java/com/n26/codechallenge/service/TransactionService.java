package com.n26.codechallenge.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.n26.codechallenge.dto.Transaction;
import com.n26.codechallenge.entity.TransactionEntity;
import com.n26.codechallenge.exception.BadRequestException;
import com.n26.codechallenge.exception.InternalServerException;
import com.n26.codechallenge.repository.TransactionRepository;

@Service
public class TransactionService {

	private static Logger logger = LoggerFactory.getLogger(TransactionService.class); 
	
	@Autowired
	private TransactionRepository repository;
	
	@Autowired
	private Validator validator;
	
	@Transactional(readOnly = false)
	public Transaction save(Transaction dto) {
		Set<ConstraintViolation<Transaction>> violations = validator.validate(dto);
		if (!violations.isEmpty()) {
			throw new BadRequestException();
		}
		
		try {
			TransactionEntity transaction = repository.save(new TransactionEntity(dto.getTimestamp(), dto.getAmount()));
			return toDto(transaction);
		} catch (Exception ex) {
			logger.error("Unable to add new transaction", ex);
			throw new InternalServerException();			
		}
	}
	

	private Transaction toDto(TransactionEntity entity) {
		return new Transaction(entity.getTimestamp(), entity.getAmount());
	}
	
}

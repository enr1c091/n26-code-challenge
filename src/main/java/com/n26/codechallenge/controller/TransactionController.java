package com.n26.codechallenge.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.codechallenge.dto.Transaction;
import com.n26.codechallenge.service.TransactionService;
import com.n26.codechallenge.utils.Constants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService service;
	
	
	
	
	@PostMapping
	@ApiOperation(value = "Create a new transaction in the database")
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Created transaction is younger or equals than 60 seconds"),
	        @ApiResponse(code = 204, message = "Created transaction is older than 60 seconds"),
	        @ApiResponse(code = 400, message = "The request has invalid parameters"),
	        @ApiResponse(code = 500, message = "An internal server error has been detected")
	})
	public ResponseEntity<HttpStatus> saveTransaction(@RequestBody(required = true) Transaction t) {
		return Instant.now().toEpochMilli() - service.save(t).getTimestamp() <= Constants.TIMELIMIT_IN_EPOCH_MILLIS
			? ResponseEntity.status(HttpStatus.CREATED).build()
			: ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}

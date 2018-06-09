package com.n26.codechallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.codechallenge.dto.Statistics;
import com.n26.codechallenge.service.StatisticsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "Resources for Transaction statistics")
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
	
	@Autowired
	private StatisticsService service;
	
	@GetMapping
	@ApiOperation(value = "Retrieve the statistics for transactions made within the last 60 seconds", response = Statistics.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Created transaction is younger or equals than 60 seconds"),
	        @ApiResponse(code = 500, message = "An internal server error has been detected")
	})
	public Statistics getTransactionStatistics() {
		return service.getStatistics();
	}

}

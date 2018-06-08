package com.n26.codechallenge.service;

import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.n26.codechallenge.dto.Statistics;
import com.n26.codechallenge.entity.TransactionEntity;
import com.n26.codechallenge.exception.InternalServerException;
import com.n26.codechallenge.repository.TransactionRepository;

@Service
public class StatisticsService {

	private static Logger logger = LoggerFactory.getLogger(StatisticsService.class); 
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Transactional(readOnly = true)
	public Statistics getStatistics() {
		Statistics statistics = new Statistics();
		try {
			DoubleSummaryStatistics dstats = transactionRepository.streamAll()
					.filter(TransactionEntity::isWithinTimeLimit) //Requires best way to handle overdue transactions. Avoid filtering all stream
					.collect(Collectors.summarizingDouble(TransactionEntity::getAmount)); 
	        if (dstats.getCount() > 0) {
	        	statistics.updateStatistics(dstats.getSum(), dstats.getAverage(), dstats.getMax(), dstats.getMin(), dstats.getCount());
	        }
		} catch (Exception ex) {
			logger.error("Unable to get transactions statistics", ex);
			throw new InternalServerException();			
		}
		return statistics;
	}
	
}

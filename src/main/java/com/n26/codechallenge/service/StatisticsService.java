package com.n26.codechallenge.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.n26.codechallenge.dto.Statistics;
import com.n26.codechallenge.dto.Transaction;
import com.n26.codechallenge.utils.Constants;

@Service
public class StatisticsService {

	private BlockingQueue<Transaction> validTransactions = new DelayQueue<Transaction>();
	private Statistics statistics = new Statistics();
	
	/*
	 * Returns the statistics of transactions made in the last 60 seconds.
	 * 
	 * @return		Object containing statistics (min, max, avg, sum, count) of transactions made in the last 60 seconds.
	 * */
	public Statistics getStatistics() {
		return this.statistics;
	}

	/*
	 * Checks the Transaction Collection every millisecond and removes any . 
	 * Updates statistics with remainder Transactions so no further processing is required upon requests.
	 * */
	@Scheduled(fixedRate = Constants.POOLING_INTERVAL)
	private void removeExpiredTransactions() {
		while (!validTransactions.isEmpty() && !validTransactions.peek().isWithinTimeLimit()) {
			validTransactions.poll();
		}
		setStatistics();
	}
	
	/*
	 * Adds a new transaction to the Collection
	 * 
	 * @param t		Valid Transaction within the last 60 seconds.
	 * */
	protected void addTransaction(Transaction t) throws InterruptedException {
		validTransactions.put(t);
	}

	/*
	 * Updates the current statistics in-memory
	 * */
	private void setStatistics() {
		this.statistics = new Statistics(validTransactions);
	}
}

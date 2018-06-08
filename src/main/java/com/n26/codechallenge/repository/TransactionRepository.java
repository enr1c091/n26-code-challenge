package com.n26.codechallenge.repository;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.n26.codechallenge.entity.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

	@Transactional(readOnly = true)
	@Query("select t from TransactionEntity t")
	Stream<TransactionEntity> streamAll();

}

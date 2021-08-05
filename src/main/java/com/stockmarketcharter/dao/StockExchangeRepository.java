package com.stockmarketcharter.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stockmarketcharter.model.CompanyEntity;
import com.stockmarketcharter.model.StockExchangeEntity;

public interface StockExchangeRepository extends JpaRepository<StockExchangeEntity, Integer> {

	@Query(value="SELECT * FROM StockExchange",nativeQuery = true)
	public Page<StockExchangeEntity> findallStockExchange(Pageable pePageable);
	
	public StockExchangeEntity findBystockExchange(String stockExchangename);
}

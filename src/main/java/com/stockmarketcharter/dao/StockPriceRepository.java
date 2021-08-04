package com.stockmarketcharter.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockmarketcharter.model.StockPriceEntity;

public interface StockPriceRepository extends JpaRepository<StockPriceEntity, Integer> {

}

package com.stockmarketcharter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockmarketcharter.model.StockPriceEntity;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPriceEntity, Integer> {

}

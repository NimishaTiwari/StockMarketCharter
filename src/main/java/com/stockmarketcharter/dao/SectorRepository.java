package com.stockmarketcharter.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockmarketcharter.model.SectorEntity;


public interface SectorRepository extends JpaRepository<SectorEntity, Integer>{
	
	public SectorEntity findBysectorName(String sectorname);
}

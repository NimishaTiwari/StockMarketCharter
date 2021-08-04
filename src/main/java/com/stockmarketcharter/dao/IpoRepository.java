package com.stockmarketcharter.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.stockmarketcharter.model.IPODetailEntity;
import com.stockmarketcharter.model.SectorEntity;

public interface IpoRepository extends JpaRepository<IPODetailEntity, Integer>{
	
	@Query(value="SELECT * FROM IPO",nativeQuery = true)
	public Page<IPODetailEntity> findallIpos(Pageable pePageable);
	
	
}

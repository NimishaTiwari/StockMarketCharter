package com.stockmarketcharter.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stockmarketcharter.model.CompanyEntity;
import com.stockmarketcharter.model.IPODetailEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer>{

	@Query(value="SELECT * FROM Company",nativeQuery = true)
	public Page<CompanyEntity> findallCompany(Pageable pePageable);
	
	public CompanyEntity findBycompanyName(String companyname);
}

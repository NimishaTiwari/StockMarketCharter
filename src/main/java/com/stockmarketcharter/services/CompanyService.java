package com.stockmarketcharter.services;

import com.stockmarketcharter.model.CompanyEntity;

public interface CompanyService {
	public CompanyEntity createCompany(CompanyEntity company,String sectorname);
	public CompanyEntity updatecompany(CompanyEntity companyEntity, CompanyEntity company, String sectorname);
}

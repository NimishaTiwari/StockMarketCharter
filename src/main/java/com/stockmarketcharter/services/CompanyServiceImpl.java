package com.stockmarketcharter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockmarketcharter.dao.CompanyRepository;
import com.stockmarketcharter.dao.SectorRepository;
import com.stockmarketcharter.model.CompanyEntity;
import com.stockmarketcharter.model.SectorEntity;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private SectorRepository sectorRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public CompanyEntity createCompany(CompanyEntity company, String sectorname) {
		SectorEntity sector = this.sectorRepository.findBysectorName(sectorname);
		company.setSector(sector);
		CompanyEntity companyEntity = this.companyRepository.save(company);
		return companyEntity;
	}

	public CompanyEntity updatecompany(CompanyEntity companyEntity, CompanyEntity company, String sectorname) {
		 companyEntity.setCompanyName(company.getCompanyName());
		 companyEntity.setTurnover(company.getTurnover());
		 companyEntity.setCEO(company.getCEO());
		 companyEntity.setBOD(company.getBOD());
		 companyEntity.setWriteUp(company.getWriteUp());
		 
		SectorEntity sector = this.sectorRepository.findBysectorName(sectorname);
		companyEntity.setSector(sector);
		
		CompanyEntity companyEntity2 = this.companyRepository.save(companyEntity);
		return companyEntity2;
	}

}

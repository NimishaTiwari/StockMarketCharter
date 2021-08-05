package com.stockmarketcharter.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="StockExchange")
public class StockExchangeEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int stockExchangeId;
	
	@Column
	private String stockExchange;
	
	@Column(length = 5000)
	private String SEBrief;
	
	@Column
	private String contactAddress;
	
	@Column(length = 2000)
	private String remarks;
	
	@OneToMany(mappedBy="StockExchange", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
    private List<IPODetailEntity> ipos;
	
	@OneToMany(mappedBy="stockexchange", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
    private List<StockPriceEntity> StockPrices;
	
	@ManyToMany(mappedBy="stockExchange")
	List<CompanyEntity> CompanyList;

	public StockExchangeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StockExchangeEntity(int stockExchangeId, String stockExchange, String sEBrief, String contactAddress,
			String remarks, List<IPODetailEntity> ipos, List<StockPriceEntity> stockPrices,
			List<CompanyEntity> companyList) {
		super();
		this.stockExchangeId = stockExchangeId;
		this.stockExchange = stockExchange;
		SEBrief = sEBrief;
		this.contactAddress = contactAddress;
		this.remarks = remarks;
		this.ipos = ipos;
		StockPrices = stockPrices;
		CompanyList = companyList;
	}

	public int getStockExchangeId() {
		return stockExchangeId;
	}

	public void setStockExchangeId(int stockExchangeId) {
		this.stockExchangeId = stockExchangeId;
	}

	public String getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}

	public String getSEBrief() {
		return SEBrief;
	}

	public void setSEBrief(String sEBrief) {
		SEBrief = sEBrief;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<IPODetailEntity> getIpos() {
		return ipos;
	}

	public void setIpos(List<IPODetailEntity> ipos) {
		this.ipos = ipos;
	}

	public List<StockPriceEntity> getStockPrices() {
		return StockPrices;
	}

	public void setStockPrices(List<StockPriceEntity> stockPrices) {
		StockPrices = stockPrices;
	}

	public List<CompanyEntity> getCompanyList() {
		return CompanyList;
	}

	public void setCompanyList(List<CompanyEntity> companyList) {
		CompanyList = companyList;
	}

	@Override
	public String toString() {
		return "StockExchangeEntity [stockExchangeId=" + stockExchangeId + ", stockExchange=" + stockExchange
				+ ", SEBrief=" + SEBrief + ", contactAddress=" + contactAddress + ", remarks=" + remarks + ", ipos="
				+ ipos + ", StockPrices=" + StockPrices + ", CompanyList=" + CompanyList + "]";
	}
	
	
}

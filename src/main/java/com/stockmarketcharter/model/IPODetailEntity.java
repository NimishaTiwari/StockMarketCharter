package com.stockmarketcharter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="IPO")
public class IPODetailEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ipoId;
	
	@Column
	private int pricePerShare;
	
	@Column
	private int totalNoOfShares;
	
	@Column(nullable = false)
	private Date openDateTime;
	
	@Column(length = 5000)
	private String remarks;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "companyId")
	@JsonBackReference
	private CompanyEntity company;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "stockExchangeEntityId")
	@JsonBackReference
	private StockExchangeEntity StockExchange;

	public IPODetailEntity(int ipoId, int pricePerShare, int totalNoOfShares, Date openDateTime, String remarks,
			CompanyEntity company, StockExchangeEntity stockExchange) {
		super();
		this.ipoId = ipoId;
		this.pricePerShare = pricePerShare;
		this.totalNoOfShares = totalNoOfShares;
		this.openDateTime = openDateTime;
		this.remarks = remarks;
		this.company = company;
		StockExchange = stockExchange;
	}

	public IPODetailEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIpoId() {
		return ipoId;
	}

	public void setIpoId(int ipoId) {
		this.ipoId = ipoId;
	}

	public int getPricePerShare() {
		return pricePerShare;
	}

	public void setPricePerShare(int pricePerShare) {
		this.pricePerShare = pricePerShare;
	}

	public int getTotalNoOfShares() {
		return totalNoOfShares;
	}

	public void setTotalNoOfShares(int totalNoOfShares) {
		this.totalNoOfShares = totalNoOfShares;
	}

	public Date getOpenDateTime() {
		return openDateTime;
	}

	public void setOpenDateTime(Date openDateTime) {
		this.openDateTime = openDateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public CompanyEntity getCompany() {
		return company;
	}

	public void setCompany(CompanyEntity company) {
		this.company = company;
	}

	public StockExchangeEntity getStockExchange() {
		return StockExchange;
	}

	public void setStockExchange(StockExchangeEntity stockExchange) {
		StockExchange = stockExchange;
	}

	@Override
	public String toString() {
		return "IPODetailEntity [ipoId=" + ipoId + ", pricePerShare=" + pricePerShare + ", totalNoOfShares="
				+ totalNoOfShares + ", openDateTime=" + openDateTime + ", remarks=" + remarks + ", company=" + company
				+ ", StockExchange=" + StockExchange + "]";
	}
	
	
}

package com.stockmarketcharter.model;

import java.time.LocalDate;
import java.time.LocalTime;

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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="StockPrice")
public class StockPriceEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int SPId;
	
	@Column
	private String companyCode;
	
	@Column
	private String currentPrice;
	
	@Column
	private LocalDate Date;
	
	@Column
	private LocalTime Time;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "stockExchangeId")
	@JsonBackReference
	private StockExchangeEntity stockexchange;

	public StockPriceEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StockPriceEntity(int sPId, String companyCode, String currentPrice, LocalDate date, LocalTime time,
			StockExchangeEntity stockexchange) {
		super();
		SPId = sPId;
		this.companyCode = companyCode;
		this.currentPrice = currentPrice;
		Date = date;
		Time = time;
		this.stockexchange = stockexchange;
	}

	public int getSPId() {
		return SPId;
	}

	public void setSPId(int sPId) {
		SPId = sPId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	public LocalDate getDate() {
		return Date;
	}

	public void setDate(LocalDate date) {
		Date = date;
	}

	public LocalTime getTime() {
		return Time;
	}

	public void setTime(LocalTime time) {
		Time = time;
	}

	public StockExchangeEntity getStockexchange() {
		return stockexchange;
	}

	public void setStockexchange(StockExchangeEntity stockexchange) {
		this.stockexchange = stockexchange;
	}

	@Override
	public String toString() {
		return "StockPriceEntity [SPId=" + SPId + ", companyCode=" + companyCode + ", currentPrice=" + currentPrice
				+ ", Date=" + Date + ", Time=" + Time + ", stockexchange=" + stockexchange + "]";
	}
	
	
}

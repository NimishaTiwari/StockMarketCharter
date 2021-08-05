package com.stockmarketcharter.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Company")
public class CompanyEntity {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int companyId;
	
	@Column
	private String companyName;
	
	@Column
	private String turnover;

	@Column
	private String CEO;
	
	@Column(name="BoardOfDirectors")
	private String BOD;
	
	@Column(length = 5000)
	private String writeUp;
	
	@OneToMany(mappedBy="company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
    private List<IPODetailEntity> ipos_listed;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SectorID")
	@JsonBackReference
	private SectorEntity sector;
	
	 @ManyToMany
	 @JoinTable(
				  name = "company_stockexchange", 
				  joinColumns = @JoinColumn(name = "companyId"), 
				  inverseJoinColumns = @JoinColumn(name = "stockExchangeId"))
	   
	private List<StockExchangeEntity> stockExchange;

	public CompanyEntity(int companyId, String companyName, String turnover, String cEO, String bOD, String writeUp,
			List<IPODetailEntity> ipos_listed, SectorEntity sector, List<StockExchangeEntity> stockExchange) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
		this.turnover = turnover;
		CEO = cEO;
		BOD = bOD;
		this.writeUp = writeUp;
		this.ipos_listed = ipos_listed;
		this.sector = sector;
		this.stockExchange = stockExchange;
	}

	public CompanyEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTurnover() {
		return turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	public String getCEO() {
		return CEO;
	}

	public void setCEO(String cEO) {
		CEO = cEO;
	}

	public String getBOD() {
		return BOD;
	}

	public void setBOD(String bOD) {
		BOD = bOD;
	}

	public String getWriteUp() {
		return writeUp;
	}

	public void setWriteUp(String writeUp) {
		this.writeUp = writeUp;
	}

	public List<IPODetailEntity> getIpos_listed() {
		return ipos_listed;
	}

	public void setIpos_listed(List<IPODetailEntity> ipos_listed) {
		this.ipos_listed = ipos_listed;
	}

	public SectorEntity getSector() {
		return sector;
	}

	public void setSector(SectorEntity sector) {
		this.sector = sector;
	}

	public List<StockExchangeEntity> getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(List<StockExchangeEntity> stockExchange) {
		this.stockExchange = stockExchange;
	}

	@Override
	public String toString() {
		return "CompanyEntity [companyId=" + companyId + ", companyName=" + companyName + ", turnover=" + turnover
				+ ", CEO=" + CEO + ", BOD=" + BOD + ", writeUp=" + writeUp + ", ipos_listed=" + ipos_listed
				+ ", sector=" + sector + ", stockExchange=" + stockExchange + "]";
	}

	 
	
	
	
}

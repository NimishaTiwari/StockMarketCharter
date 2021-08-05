package com.stockmarketcharter.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Sector")
public class SectorEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int sectorId;
	
	@Column
	private String sectorName;
	
	@Column(length = 5000)
	private String sectorBrief;
	
	@OneToMany(mappedBy="sector", cascade = CascadeType.ALL)
	@JsonManagedReference 
	private List<CompanyEntity> Company;

	public SectorEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SectorEntity(int sectorId, String sectorName, String sectorBrief, List<CompanyEntity> company) {
		super();
		this.sectorId = sectorId;
		this.sectorName = sectorName;
		this.sectorBrief = sectorBrief;
		Company = company;
	}

	public int getSectorId() {
		return sectorId;
	}

	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getSectorBrief() {
		return sectorBrief;
	}

	public void setSectorBrief(String sectorBrief) {
		this.sectorBrief = sectorBrief;
	}

	public List<CompanyEntity> getCompany() {
		return Company;
	}

	public void setCompany(List<CompanyEntity> company) {
		Company = company;
	}

	@Override
	public String toString() {
		return "SectorEntity [sectorId=" + sectorId + ", sectorName=" + sectorName + ", sectorBrief=" + sectorBrief
				+ ", Company=" + Company + "]";
	}
	
	
}

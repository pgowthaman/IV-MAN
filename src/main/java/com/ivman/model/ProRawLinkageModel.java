package com.ivman.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity	
@Table(name = "pro_raw_linkage")
@Component
public class ProRawLinkageModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "pro_raw_linkage_id",strategy = GenerationType.AUTO)
	@Column(name = "pro_raw_linkage_id")
	private Integer proRawLinkageId;
	
	@Column(name = "raw_materaial_qty")
	private Double rawMaterailQty;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyModel companyModel;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "raw_material_id")
	private RawMaterialsModel rawMaterailsModel;

	public Integer getProRawLinkageId() {
		return proRawLinkageId;
	}

	public void setProRawLinkageId(Integer proRawLinkageId) {
		this.proRawLinkageId = proRawLinkageId;
	}

	public Double getRawMaterailQty() {
		return rawMaterailQty;
	}

	public void setRawMaterailQty(Double rawMaterailQty) {
		this.rawMaterailQty = rawMaterailQty;
	}

	public CompanyModel getCompanyModel() {
		return companyModel;
	}

	public void setCompanyModel(CompanyModel companyModel) {
		this.companyModel = companyModel;
	}

	public RawMaterialsModel getRawMaterailsModel() {
		return rawMaterailsModel;
	}

	public void setRawMaterailsModel(RawMaterialsModel rawMaterailsModel) {
		this.rawMaterailsModel = rawMaterailsModel;
	}

	@Override
	public String toString() {
		return "ProRawLinkageModel [proRawLinkageId=" + proRawLinkageId + ", rawMaterailQty=" + rawMaterailQty
				+ ", companyModel=" + companyModel + ", rawMaterailsModel=" + rawMaterailsModel + "]";
	}
	
}

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
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity	
@Table(name = "raw_materials")
@Component
public class RawMaterialsModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "raw_material_id",strategy = GenerationType.AUTO)
	@Column(name = "raw_material_id")
	private Integer rawMaterialId;
	
	@Column(name = "raw_material_code")
	private String rawMaterialCode;
	
	@Column(name = "raw_material_name")
	private String rawMaterialName;
	
	@Column(name = "raw_material_qty_type")
	private String rawMaterialQtyType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyModel companyModel;

	public Integer getRawMaterialId() {
		return rawMaterialId;
	}

	public void setRawMaterialId(Integer rawMaterialId) {
		this.rawMaterialId = rawMaterialId;
	}

	public String getRawMaterialName() {
		return rawMaterialName;
	}

	public void setRawMaterialName(String rawMaterialName) {
		this.rawMaterialName = rawMaterialName;
	}
	
	public String getRawMaterialQtyType() {
		return rawMaterialQtyType;
	}

	public void setRawMaterialQtyType(String rawMaterialQtyType) {
		this.rawMaterialQtyType = rawMaterialQtyType;
	}

	public CompanyModel getCompanyModel() {
		return companyModel;
	}

	public void setCompanyModel(CompanyModel companyModel) {
		this.companyModel = companyModel;
	}

	public String getRawMaterialCode() {
		return rawMaterialCode;
	}

	public void setRawMaterialCode(String rawMaterialCode) {
		this.rawMaterialCode = rawMaterialCode;
	}

	@Override
	public String toString() {
		return "RawMaterialsModel [rawMaterialId=" + rawMaterialId + ", rawMaterialCode=" + rawMaterialCode
				+ ", rawMaterialName=" + rawMaterialName + ", rawMaterialQtyType=" + rawMaterialQtyType
				+ ", companyModel=" + companyModel + "]";
	}
	
}

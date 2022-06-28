package com.ivman.model;

import java.io.Serializable;
import java.sql.Clob;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity	
@Table(name = "products")
@Component
public class ProductModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "product_id",strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "product_code")
	private String productCode;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_img")
	private Clob productImg;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyModel companyModel;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_raw_linkage_id")
	private List<ProRawLinkageModel> proRawLinkageList;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Clob getProductImg() {
		return productImg;
	}

	public void setProductImg(Clob productImg) {
		this.productImg = productImg;
	}

	public CompanyModel getCompanyModel() {
		return companyModel;
	}

	public void setCompanyModel(CompanyModel companyModel) {
		this.companyModel = companyModel;
	}

	public List<ProRawLinkageModel> getProRawLinkageList() {
		return proRawLinkageList;
	}

	public void setProRawLinkageList(List<ProRawLinkageModel> proRawLinkageList) {
		this.proRawLinkageList = proRawLinkageList;
	}

	@Override
	public String toString() {
		return "ProductModel [productId=" + productId + ", productCode=" + productCode + ", productName=" + productName
				+ ", productImg=" + productImg + ", companyModel=" + companyModel + ", proRawLinkageList="
				+ proRawLinkageList + "]";
	}

}

package com.ivman.model;

import java.io.Serializable;
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
@Table(name = "job_pro_linkage")
@Component
public class JobProLinkageModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "job_pro_linkage_id",strategy = GenerationType.AUTO)
	@Column(name = "job_pro_linkage_id")
	private Integer jobProLinkageId;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	private List<ProductModel> productModelList;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyModel companyModel;

	public Integer getJobProLinkageId() {
		return jobProLinkageId;
	}

	public void setJobProLinkageId(Integer jobProLinkageId) {
		this.jobProLinkageId = jobProLinkageId;
	}

	public List<ProductModel> getProductModelList() {
		return productModelList;
	}

	public void setProductModelList(List<ProductModel> productModelList) {
		this.productModelList = productModelList;
	}
	
	public CompanyModel getCompanyModel() {
		return companyModel;
	}

	public void setCompanyModel(CompanyModel companyModel) {
		this.companyModel = companyModel;
	}

	@Override
	public String toString() {
		return "JobProLinkageModel [jobProLinkageId=" + jobProLinkageId + ", productModelList=" + productModelList
				+ ", companyModel=" + companyModel + "]";
	}
	
}

package com.ivman.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ivman.model.CompanyModel;
import com.ivman.model.JobProLinkageModel;
import com.ivman.model.ProductModel;
import com.ivman.utils.StringUtils;

@Component
@JsonInclude(Include.NON_NULL)
public class JobProLinkageTO implements Serializable{
	
	private String jobProLinkageId;
	
	private List<ProductTO> productTOList;
	
	private CompanyTO companyTO;

	public String getJobProLinkageId() {
		return jobProLinkageId;
	}

	public void setJobProLinkageId(String jobProLinkageId) {
		this.jobProLinkageId = jobProLinkageId;
	}

	public List<ProductTO> getProductTOList() {
		return productTOList;
	}

	public void setProductTOList(List<ProductTO> productTOList) {
		this.productTOList = productTOList;
	}

	public CompanyTO getCompanyTO() {
		return companyTO;
	}

	public void setCompanyTO(CompanyTO companyTO) {
		this.companyTO = companyTO;
	}

	@Override
	public String toString() {
		return "JobProLinkageTO [jobProLinkageId=" + jobProLinkageId + ", productTOList=" + productTOList
				+ ", companyTO=" + companyTO + "]";
	}
	
	public void convertModelToTO(JobProLinkageModel model) {
		if(Objects.nonNull(model.getJobProLinkageId())) {
			this.jobProLinkageId = model.getJobProLinkageId().toString();
		}

		if(Objects.nonNull(model.getCompanyModel())) {
			CompanyTO companyTO = new CompanyTO();
			companyTO.convertModelToTO(model.getCompanyModel());
			this.setCompanyTO(companyTO);
		}
		
		List<ProductTO> productTOs = new ArrayList<ProductTO>();
		if(Objects.nonNull(model.getProductModelList()) && !model.getProductModelList().isEmpty()) {
			for (ProductModel productModel : model.getProductModelList()) {
				ProductTO productTO = new ProductTO();
				productTO.convertModelToTO(productModel);
				productTOs.add(productTO);
			}
			this.setProductTOList(productTOs);
		}
	}

	public void convertTOToModel(JobProLinkageModel model) {
		if(StringUtils.isNotEmpty(this.getJobProLinkageId())) {
			model.setJobProLinkageId(Integer.valueOf(this.getJobProLinkageId()));
		}
		
		if(Objects.nonNull(this.getCompanyTO())) {
			CompanyModel companyModel = new CompanyModel();
			CompanyTO companyTO = this.getCompanyTO();
			companyTO.convertTOToModel(companyModel);
			model.setCompanyModel(companyModel);
		}
		
		List<ProductModel> productModels = new ArrayList<ProductModel>();
		if(Objects.nonNull(this.getProductTOList()) && !this.getProductTOList().isEmpty()) {
			for (ProductTO productTO : this.getProductTOList()) {
				ProductModel productModel = new ProductModel();
				productTO.convertModelToTO(productModel);
				productModels.add(productModel);
			}
			model.setProductModelList(productModels);
		}
	}
	

}

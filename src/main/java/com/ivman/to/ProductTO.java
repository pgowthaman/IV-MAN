package com.ivman.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.engine.jdbc.ClobProxy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ivman.model.CompanyModel;
import com.ivman.model.ProRawLinkageModel;
import com.ivman.model.ProductModel;
import com.ivman.model.RawMaterialsModel;
import com.ivman.utils.StringUtils;

@Component
@JsonInclude(Include.NON_NULL)
public class ProductTO implements Serializable{
	
	private String productId;
	
	private String productCode;
	
	private String productName;
	
	private String productImg;
	
	private CompanyTO companyTO;
	
	private List<ProRawLinkageTO> proRawLinkageTOList;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
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

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public CompanyTO getCompanyTO() {
		return companyTO;
	}

	public void setCompanyTO(CompanyTO companyTO) {
		this.companyTO = companyTO;
	}

	public List<ProRawLinkageTO> getProRawLinkageTOList() {
		return proRawLinkageTOList;
	}

	public void setProRawLinkageTOList(List<ProRawLinkageTO> proRawLinkageTOList) {
		this.proRawLinkageTOList = proRawLinkageTOList;
	}

	@Override
	public String toString() {
		return "ProductTO [productId=" + productId + ", productCode=" + productCode + ", productName=" + productName
				+ ", productImg=" + productImg + ", companyTO=" + companyTO + ", proRawLinkageTOList="
				+ proRawLinkageTOList + "]";
	}
	
	public void convertModelToTO(ProductModel model) {
		if(Objects.nonNull(model.getProductId())) {
			this.productId = model.getProductId().toString();
		}

		this.productCode = model.getProductCode();
		this.productName = model.getProductName();
		if(Objects.nonNull(model.getProductImg())) {
			this.productImg = model.getProductImg().toString();
		}

		if(Objects.nonNull(model.getCompanyModel())) {
			CompanyTO companyTO = new CompanyTO();
			companyTO.convertModelToTO(model.getCompanyModel());
			this.setCompanyTO(companyTO);
		}
		List<ProRawLinkageTO> proRawLinkageTOs = new ArrayList<ProRawLinkageTO>();
		if(Objects.nonNull(model.getProRawLinkageList()) && !model.getProRawLinkageList().isEmpty()) {
			for (ProRawLinkageModel proRawLinkageModel : model.getProRawLinkageList()) {
				ProRawLinkageTO proRawLinkageTO = new ProRawLinkageTO();
				proRawLinkageTO.convertModelToTO(proRawLinkageModel);
				proRawLinkageTOs.add(proRawLinkageTO);
			}
			this.setProRawLinkageTOList(proRawLinkageTOs);
		}
	}

	public void convertTOToModel(ProductModel model) {
		if(StringUtils.isNotEmpty(this.getProductId())) {
			model.setProductId(Integer.valueOf(this.getProductId()));
		}
		model.setProductCode(this.productCode);
		model.setProductName(this.productName);
		if(StringUtils.isNotEmpty(this.productImg)) {
			model.setProductImg(ClobProxy.generateProxy(this.productImg));
		}
		
		if(Objects.nonNull(this.getCompanyTO())) {
			CompanyModel companyModel = new CompanyModel();
			CompanyTO companyTO = this.getCompanyTO();
			companyTO.convertTOToModel(companyModel);
			model.setCompanyModel(companyModel);
		}
		List<ProRawLinkageModel> proRawLinkageModels = new ArrayList<ProRawLinkageModel>();
		if(Objects.nonNull(this.getProRawLinkageTOList()) && !this.getProRawLinkageTOList().isEmpty()) {
			for (ProRawLinkageTO proRawLinkageTO : this.getProRawLinkageTOList()) {
				ProRawLinkageModel proRawLinkageModel = new ProRawLinkageModel();
				proRawLinkageTO.convertModelToTO(proRawLinkageModel);
				proRawLinkageModels.add(proRawLinkageModel);
			}
			model.setProRawLinkageList(proRawLinkageModels);
		}
		
	}
	
}

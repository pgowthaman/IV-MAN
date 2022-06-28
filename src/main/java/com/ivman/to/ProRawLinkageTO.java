package com.ivman.to;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ivman.model.CompanyModel;
import com.ivman.model.ProRawLinkageModel;
import com.ivman.model.RawMaterialsModel;
import com.ivman.utils.StringUtils;

@Component
@JsonInclude(Include.NON_NULL)
public class ProRawLinkageTO implements Serializable{

	private String proRawLinkageId;
	
	private String rawMaterailQty;
	
	private CompanyTO companyTO;
	
	private RawMaterialsTO rawMaterailsTO;

	
	public String getProRawLinkageId() {
		return proRawLinkageId;
	}

	public void setProRawLinkageId(String proRawLinkageId) {
		this.proRawLinkageId = proRawLinkageId;
	}

	public String getRawMaterailQty() {
		return rawMaterailQty;
	}

	public void setRawMaterailQty(String rawMaterailQty) {
		this.rawMaterailQty = rawMaterailQty;
	}

	public CompanyTO getCompanyTO() {
		return companyTO;
	}

	public void setCompanyTO(CompanyTO companyTO) {
		this.companyTO = companyTO;
	}

	public RawMaterialsTO getRawMaterailsTO() {
		return rawMaterailsTO;
	}

	public void setRawMaterailsTO(RawMaterialsTO rawMaterailsTO) {
		this.rawMaterailsTO = rawMaterailsTO;
	}

	@Override
	public String toString() {
		return "ProRawLinkageTO [proRawLinkageId=" + proRawLinkageId + ", rawMaterailQty=" + rawMaterailQty
				+ ", companyTO=" + companyTO + ", rawMaterailsTO=" + rawMaterailsTO + "]";
	}

	
	public void convertModelToTO(ProRawLinkageModel model) {
		if(Objects.nonNull(model.getProRawLinkageId())) {
			this.proRawLinkageId = model.getProRawLinkageId().toString();
		}
		
		if(Objects.nonNull(model.getRawMaterailQty())) {
			this.rawMaterailQty = model.getRawMaterailQty().toString();
		}

		if(Objects.nonNull(model.getCompanyModel())) {
			CompanyTO companyTO = new CompanyTO();
			companyTO.convertModelToTO(model.getCompanyModel());
			this.setCompanyTO(companyTO);
		}
		
		if(Objects.nonNull(model.getRawMaterailsModel())) {
			RawMaterialsTO rawMaterialsTO = new RawMaterialsTO();
			rawMaterialsTO.convertModelToTO(model.getRawMaterailsModel());
			this.setRawMaterailsTO(rawMaterialsTO);
		}
	}

	public void convertTOToModel(ProRawLinkageModel model) {
		if(StringUtils.isNotEmpty(this.getProRawLinkageId())) {
			model.setProRawLinkageId(Integer.valueOf(this.getProRawLinkageId()));
		}
		if(StringUtils.isNotEmpty(this.getRawMaterailQty())) {
			model.setRawMaterailQty(Double.valueOf(this.getRawMaterailQty()));
		}
		
		if(Objects.nonNull(this.getCompanyTO())) {
			CompanyModel companyModel = new CompanyModel();
			CompanyTO companyTO = this.getCompanyTO();
			companyTO.convertTOToModel(companyModel);
			model.setCompanyModel(companyModel);
		}
		
		if(Objects.nonNull(this.getRawMaterailsTO())) {
			RawMaterialsModel materialsModel = new RawMaterialsModel();
			RawMaterialsTO rawMaterialsTO = this.getRawMaterailsTO();
			rawMaterialsTO.convertTOToModel(materialsModel);
			model.setRawMaterailsModel(materialsModel);
		}
	}
}

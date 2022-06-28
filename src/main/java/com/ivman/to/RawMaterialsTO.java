package com.ivman.to;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ivman.model.CompanyModel;
import com.ivman.model.RawMaterialsModel;
import com.ivman.utils.StringUtils;

@Component
@JsonInclude(Include.NON_NULL)
public class RawMaterialsTO implements Serializable {

	private String rawMaterialId;

	private String rawMaterialCode;

	private String rawMaterialName;

	private String rawMaterialQtyType;

	private CompanyTO companyTO;

	public String getRawMaterialId() {
		return rawMaterialId;
	}

	public void setRawMaterialId(String rawMaterialId) {
		this.rawMaterialId = rawMaterialId;
	}

	public String getRawMaterialCode() {
		return rawMaterialCode;
	}

	public void setRawMaterialCode(String rawMaterialCode) {
		this.rawMaterialCode = rawMaterialCode;
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

	public CompanyTO getCompanyTO() {
		return companyTO;
	}

	public void setCompanyTO(CompanyTO companyTO) {
		this.companyTO = companyTO;
	}

	@Override
	public String toString() {
		return "RawMaterialsTO [rawMaterialId=" + rawMaterialId + ", rawMaterialCode=" + rawMaterialCode
				+ ", rawMaterialName=" + rawMaterialName + ", rawMaterialQtyType=" + rawMaterialQtyType + ", companyTO="
				+ companyTO + "]";
	}

	public void convertModelToTO(RawMaterialsModel model) {
		if(Objects.nonNull(model.getRawMaterialId())) {
			this.rawMaterialId = model.getRawMaterialId().toString();
		}

		this.rawMaterialCode = model.getRawMaterialCode();
		this.rawMaterialName = model.getRawMaterialName();
		this.rawMaterialQtyType = model.getRawMaterialQtyType();

		if(Objects.nonNull(model.getCompanyModel())) {
			CompanyTO companyTO = new CompanyTO();
			companyTO.convertModelToTO(model.getCompanyModel());
			this.setCompanyTO(companyTO);
		}
	}

	public void convertTOToModel(RawMaterialsModel model) {
		if(StringUtils.isNotEmpty(this.getRawMaterialId())) {
			model.setRawMaterialId(Integer.valueOf(this.getRawMaterialId()));
		}
		model.setRawMaterialCode(this.rawMaterialCode);
		model.setRawMaterialName(this.rawMaterialName);
		model.setRawMaterialQtyType(this.rawMaterialQtyType);
		
		if(Objects.nonNull(this.getCompanyTO())) {
			CompanyModel companyModel = new CompanyModel();
			CompanyTO companyTO = this.getCompanyTO();
			companyTO.convertTOToModel(companyModel);
			model.setCompanyModel(companyModel);
		}
	}

}

package com.ivman.to;

import java.util.Objects;

import com.ivman.model.CompanyModel;
import com.ivman.utils.StringUtils;

public class CompanyTO {

	private String companyId;
	private String companyName;
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Override
	public String toString() {
		return "CompanyTO [companyId=" + companyId + ", companyName=" + companyName + "]";
	}
	
	public void convertModelToTO(CompanyModel model) {
		if(Objects.nonNull(model.getCompanyId())) {
			this.companyId = model.getCompanyId().toString();
		}
		this.companyName = model.getCompanyName();
	}
	
	public void convertTOToModel(CompanyModel model) {
		if(StringUtils.isNotEmpty(this.getCompanyId())) {
			model.setCompanyId(Integer.valueOf(this.getCompanyId()));
		}
		model.setCompanyName(this.companyName);
	}
}

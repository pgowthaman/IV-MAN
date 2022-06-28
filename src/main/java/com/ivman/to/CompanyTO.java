package com.ivman.to;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ivman.model.CompanyModel;
import com.ivman.model.UserRoleModel;
import com.ivman.utils.StringUtils;

@Component
@JsonInclude(Include.NON_NULL)
public class CompanyTO implements Serializable {

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
		if (Objects.nonNull(model.getCompanyId())) {
			this.companyId = model.getCompanyId().toString();
		}
		this.companyName = model.getCompanyName();
	}

	public void convertTOToModel(CompanyModel model) {
		if (StringUtils.isNotEmpty(this.getCompanyId())) {
			model.setCompanyId(Integer.valueOf(this.getCompanyId()));
		}
		model.setCompanyName(this.companyName);
	}

}

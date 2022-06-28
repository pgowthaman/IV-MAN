package com.ivman.to;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ivman.model.UserModel;
import com.ivman.model.UserRoleModel;
import com.ivman.utils.StringUtils;

@Component
@JsonInclude(Include.NON_NULL)
public class UserRoleTO implements Serializable {

	private String userRoleId;

	private String userRoleDesc;

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserRoleDesc() {
		return userRoleDesc;
	}

	public void setUserRoleDesc(String userRoleDesc) {
		this.userRoleDesc = userRoleDesc;
	}

	@Override
	public String toString() {
		return "UserRoleTO [userRoleId=" + userRoleId + ", userRoleDesc=" + userRoleDesc + "]";
	}
	
	public void convertModelToTO(UserRoleModel model) {
		if(Objects.nonNull(model.getUserRoleId())) {
			this.userRoleId = model.getUserRoleId().toString();
		}
		this.userRoleDesc = model.getUserRoleDesc();
	}
	
	public void convertTOToModel(UserRoleModel model) {
		if(StringUtils.isNotEmpty(this.getUserRoleId())) {
			model.setUserRoleId(Integer.valueOf(this.getUserRoleId()));
		}
		
		model.setUserRoleDesc(this.userRoleDesc);
	}

}

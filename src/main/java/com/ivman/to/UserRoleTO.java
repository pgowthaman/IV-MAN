package com.ivman.to;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ivman.model.UserRoleModel;
import com.ivman.utils.StringUtils;

@Component
@JsonInclude(Include.NON_NULL)
public class UserRoleTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	public void convertModelToTO(UserRoleModel userRoleModel) {
		if(Objects.nonNull(userRoleModel.getUserRoleId())) {
			this.userRoleId = userRoleModel.getUserRoleId().toString();
		}
		this.userRoleDesc = userRoleModel.getUserRoleDesc();
	}

	public void convertTOToModel(UserRoleModel roleModel) {
		
		if(StringUtils.isNotEmpty(this.getUserRoleId())) {
			roleModel.setUserRoleId(Integer.valueOf(this.getUserRoleId()));
		}
		roleModel.setUserRoleDesc(this.userRoleDesc);
	}

}

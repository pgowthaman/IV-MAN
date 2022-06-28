package com.ivman.apivalidator;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ivman.dao.UserRoleDao;
import com.ivman.to.UserRoleTO;

@Service
public class ValidateUserRole {
	
	private UserRoleDao userRoleDao;
	
	public ValidateUserRole(UserRoleDao userRoleDao){
		this.userRoleDao = userRoleDao;
	}

	public void checkUserRole(@Valid UserRoleTO userRoleTO) throws IvManException {
		
		if(userRoleTO==null) {
			throw new IvManException("User Role Object cannot be empty");
		}else if(userRoleTO.getUserRoleId()==null) {
			throw new IvManException("User Role ID cannot be empty");
		}else {
			UserRoleTO userRoleTOnew = userRoleDao.getByUserRoleId(Integer.valueOf(userRoleTO.getUserRoleId()));
			if(userRoleTOnew==null) {
				throw new IvManException("Given User Role Is Not Valid");
			}
		}
	}
	
	

}

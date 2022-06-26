package com.ivman.apivalidator;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ivman.daointerface.UserInterfaceDao;
import com.ivman.model.UserModel;

@Service
public class ValidateUser {
	
	private UserInterfaceDao userInterfaceDao;
	
	public ValidateUser(UserInterfaceDao userInterfaceDao){
		this.userInterfaceDao = userInterfaceDao;
	}

	public void checkPhoneNumber(@Valid String phoneNumber) throws IvManException {
		Optional<UserModel> userModel = userInterfaceDao.findbyPhoneNumber(phoneNumber);
		if(userModel!=null) {
			throw new IvManException("Phone Number Already Registered");
		}
	}
	
	

}

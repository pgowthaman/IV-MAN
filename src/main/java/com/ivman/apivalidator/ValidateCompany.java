package com.ivman.apivalidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivman.dao.CompanyDao;
import com.ivman.to.CompanyTO;

@Service
public class ValidateCompany {
	
	@Autowired
	private CompanyDao companyDao;
	
	public void validateCompanyDetails(CompanyTO companyTO) throws IvManException {
		if(companyTO==null) {
			throw new IvManException("CompanyTO Object cannot be empty");
		}else if(companyTO.getCompanyId()==null) {
			throw new IvManException("Company ID cannot be empty");
		}else {
			CompanyTO companyTONew = companyDao.getByCompanyId(Integer.valueOf(companyTO.getCompanyId()));
			if(companyTONew==null) {
				throw new IvManException("Given Company Is Not Valid");
			}
		}
	}
}

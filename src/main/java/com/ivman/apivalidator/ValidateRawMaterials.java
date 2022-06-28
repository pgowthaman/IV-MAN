package com.ivman.apivalidator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivman.dao.RawMaterailsDao;
import com.ivman.to.RawMaterialsTO;
import com.ivman.utils.StringUtils;

@Service
public class ValidateRawMaterials {
	
	@Autowired
	private RawMaterailsDao rawMaterialsDao;
	
	@Autowired
	private ValidateCompany validateCompany;
	
	
	private void validateRawMaterialsDetails(RawMaterialsTO rawMaterialsTO) throws IvManException {
		validateCompany.validateCompanyDetails(rawMaterialsTO.getCompanyTO());
		if(StringUtils.isEmpty(rawMaterialsTO.getRawMaterialCode())) {
			throw new IvManException("Raw Material Code is mandatory");
		}
		if(StringUtils.isEmpty(rawMaterialsTO.getRawMaterialName())) {
			throw new IvManException("Raw Material Name is mandatory");
		}
		if(StringUtils.isEmpty(rawMaterialsTO.getRawMaterialQtyType())) {
			throw new IvManException("Raw Material Quantity Type is mandatory");
		}
	}
	
	public void validateRawMaterialsBeforeSave(RawMaterialsTO rawMaterialsTO) throws IvManException {
		validateRawMaterialsDetails(rawMaterialsTO);
		List<RawMaterialsTO> rawMaterialsTOCodes = rawMaterialsDao.getByRawMaterialCode(rawMaterialsTO.getRawMaterialCode(), rawMaterialsTO.getCompanyTO().getCompanyId());
		if(rawMaterialsTOCodes!=null && !rawMaterialsTOCodes.isEmpty()) {
			throw new IvManException("Raw Material Code already exists");
		}
		List<RawMaterialsTO> rawMaterialsTONames = rawMaterialsDao.getByRawMaterialName(rawMaterialsTO.getRawMaterialName(), rawMaterialsTO.getCompanyTO().getCompanyId());
		if(rawMaterialsTONames!=null && !rawMaterialsTONames.isEmpty()) {
			throw new IvManException("Raw Material Name already exists");
		}
	}
	
	public void validateRawMaterialsBeforeUpdate(RawMaterialsTO rawMaterialsTO) throws IvManException {
		if(StringUtils.isEmpty(rawMaterialsTO.getRawMaterialId())) {
			throw new IvManException("Raw Material Id is mandatory");
		}
		validateRawMaterialsDetails(rawMaterialsTO);
		List<RawMaterialsTO> rawMaterialsTOCodes = rawMaterialsDao.getByRawMaterialCode(rawMaterialsTO.getRawMaterialCode(), rawMaterialsTO.getCompanyTO().getCompanyId());
		if(rawMaterialsTOCodes!=null && !rawMaterialsTOCodes.isEmpty()) {
			for (RawMaterialsTO rawMaterialsTOCode : rawMaterialsTOCodes) {
				if(!rawMaterialsTO.getRawMaterialId().equals(rawMaterialsTOCode.getRawMaterialId())) {
					throw new IvManException("Raw Material Code already exists");
				}
			}
		}
		List<RawMaterialsTO> rawMaterialsTONames = rawMaterialsDao.getByRawMaterialName(rawMaterialsTO.getRawMaterialName(), rawMaterialsTO.getCompanyTO().getCompanyId());
		if(rawMaterialsTONames!=null && !rawMaterialsTONames.isEmpty()) {
			for (RawMaterialsTO rawMaterialsTOName : rawMaterialsTONames) {
				if(!rawMaterialsTO.getRawMaterialId().equals(rawMaterialsTOName.getRawMaterialId())) {
					throw new IvManException("Raw Material Name already exists");
				}
			}
		}
	}

	public void validateRawMaterailsBeforeFetch(RawMaterialsTO searchRawMaterialsTO) throws IvManException {
		validateCompany.validateCompanyDetails(searchRawMaterialsTO.getCompanyTO());
		
	}

}

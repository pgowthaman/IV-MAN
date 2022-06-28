package com.ivman.apicontroller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivman.apivalidator.IvManException;
import com.ivman.apivalidator.ValidateCompany;
import com.ivman.apivalidator.ValidateRawMaterials;
import com.ivman.dao.RawMaterailsDao;
import com.ivman.to.MainModel;
import com.ivman.to.RawMaterialsTO;
import com.ivman.utils.AppConstants;
import com.ivman.utils.StringUtils;

@RestController
@RequestMapping(value="Api")
@RolesAllowed(value = AppConstants.SUPER_ADMIN)
public class APIRawMaterialsController {
	
	@Autowired
	private RawMaterailsDao rawMaterialsDao;
	
	@Autowired
	private ValidateRawMaterials validateRawMaterials;
	
	@Autowired
	private ValidateCompany validateCompany;

	@PostMapping(value="/rawMaterials")
	public ResponseEntity<MainModel> rawMaterials(@RequestBody MainModel mainModel) {
		RawMaterialsTO searchRawMaterialsTO = mainModel.getSearchRawMaterialsTO();
		try {
			validateCompany.validateCompanyDetails(mainModel.getCompanyTO());
		} catch (IvManException e) {
			mainModel.setMessage(e.getMessage());
			mainModel.setResponse(false);
			return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		List<RawMaterialsTO> rawMaterialsTOs = showTablePagination(mainModel, searchRawMaterialsTO);
		mainModel.setRawMaterialsTOList(rawMaterialsTOs); 
		System.out.println(rawMaterialsTOs);
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	private List<RawMaterialsTO> showTablePagination(MainModel mainModel, RawMaterialsTO searchRawMaterialsTO) {
		String currentStrPage = mainModel.getPageNumber();
		Integer currentPage =null; 
		Integer previousPage = null;
		Integer nextPage = null;
		if(currentStrPage==null ) {
			currentPage=1;
		}else {
			currentPage = Integer.valueOf(currentStrPage);
		}
		String pageStrSize = mainModel.getPageSize();
		Integer pageSize = null;
		if(pageStrSize==null ) {
			pageSize=10;
			mainModel.setPageSize("10");
		}else {
			pageSize = Integer.valueOf(pageStrSize);
		}
		Integer totalRecords = rawMaterialsDao.rawMaterialsTotalRecordCount(mainModel.getCompanyTO());
		Integer totalNumberOfpages = totalRecords/pageSize;
		if(totalRecords%pageSize!=0) {
			totalNumberOfpages++;
		}
		if(currentPage>1) {
			previousPage = currentPage-1;
			mainModel.setPreviousPage(previousPage.toString());
		}
		mainModel.setCurrentPage(currentPage.toString());
		if(currentPage<totalNumberOfpages) {
			nextPage = currentPage+1;
			mainModel.setNextPage(nextPage.toString());
		}
		
		mainModel.setTotalRecords(totalRecords.toString());
		mainModel.setTotalNumberOfpages(totalNumberOfpages.toString());
		return rawMaterialsDao.findPaginated(currentPage,pageSize,searchRawMaterialsTO);
	}
	
	@PostMapping(value="/rawMaterialsSave")
	public ResponseEntity<MainModel> rawMaterialsSave(@RequestBody RawMaterialsTO rawMaterialsTO) {
		System.out.println("Inside RawMaterials Save"+rawMaterialsTO);
		MainModel mainModel =  new MainModel();
		try {
			validateRawMaterials.validateRawMaterialsBeforeSave(rawMaterialsTO);
		} catch (IvManException e) {
			mainModel.setMessage(e.getMessage());
			mainModel.setResponse(false);
			return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		
		rawMaterialsDao.save(rawMaterialsTO);
		List<RawMaterialsTO> rawMaterialsTOs = showTablePagination(mainModel, null);
		mainModel.setRawMaterialsTOList(rawMaterialsTOs); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/rawMaterialsUpdate")
	public ResponseEntity<MainModel> rawMaterialsUpdate(@RequestBody RawMaterialsTO rawMaterialsTO) {
		System.out.println("Inside RawMaterials Save"+rawMaterialsTO);
		MainModel mainModel =  new MainModel();
		try {
			validateRawMaterials.validateRawMaterialsBeforeUpdate(rawMaterialsTO);
		} catch (IvManException e) {
			mainModel.setMessage(e.getMessage());
			mainModel.setResponse(false);
			return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		
		rawMaterialsDao.save(rawMaterialsTO);
		List<RawMaterialsTO> rawMaterialsTOs = showTablePagination(mainModel, null);
		mainModel.setRawMaterialsTOList(rawMaterialsTOs); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/rawMaterialsDelete")
	public ResponseEntity<MainModel> rawMaterialsDelete(@RequestBody RawMaterialsTO rawMaterialsTO) {
		System.out.println("Inside RawMaterials Save"+rawMaterialsTO);
		MainModel mainModel =  new MainModel();
		if(StringUtils.isNotEmpty(rawMaterialsTO.getRawMaterialId())) {
			rawMaterialsDao.delete(Integer.valueOf(rawMaterialsTO.getRawMaterialId()));
		}else {
			mainModel.setMessage("Raw Materials Id is Mandatory");
			mainModel.setResponse(false);
			return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		List<RawMaterialsTO> rawMaterialsTOs = showTablePagination(mainModel, null);
		mainModel.setRawMaterialsTOList(rawMaterialsTOs); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/rawMaterialsDeleteAll")
	public ResponseEntity<MainModel> rawMaterialsDeleteAll() {
		rawMaterialsDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<RawMaterialsTO> rawMaterialsTOs = showTablePagination(mainModel, null);
		mainModel.setRawMaterialsTOList(rawMaterialsTOs); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	@PostMapping(value="/rawMaterialsCount")
	public ResponseEntity<MainModel> rawMaterialsDeleteAll(@RequestBody MainModel mainModel) {
		try {
			validateCompany.validateCompanyDetails(mainModel.getCompanyTO());
		} catch (IvManException e) {
			mainModel.setMessage(e.getMessage());
			mainModel.setResponse(false);
			return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		Integer totalRecords = rawMaterialsDao.rawMaterialsTotalRecordCount(mainModel.getCompanyTO());
		mainModel.setTotalRecords(totalRecords.toString());
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
}

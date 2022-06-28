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

import com.ivman.dao.JobDao;
import com.ivman.dao.JobProLinkageDao;
import com.ivman.to.JobTO;
import com.ivman.to.MainModel;
import com.ivman.utils.AppConstants;
import com.ivman.utils.StringUtils;

@RestController
@RequestMapping(value="Api")
@RolesAllowed(value = AppConstants.SUPER_ADMIN)
public class APIJobController {
	
	@Autowired
	private JobDao jobDao;
	@Autowired
	private JobProLinkageDao jobProLinkageDao;

	@PostMapping(value="/job")
	public ResponseEntity<List<MainModel>> job(@RequestBody MainModel mainModel) {
		JobTO searchJobTO = mainModel.getSearchJobTO();
		System.out.println(searchJobTO);
		List<JobTO> jobTOs = showTablePagination(mainModel, searchJobTO);
		mainModel.setJobTOList(jobTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
	private List<JobTO> showTablePagination(MainModel mainModel, JobTO searchJobTO) {
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
		}else {
			pageSize = Integer.valueOf(pageStrSize);
		}
		Integer totalRecords = jobDao.jobTotalRecordCount();
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
		return jobDao.findPaginated(currentPage,pageSize,searchJobTO);
	}
	
	@PostMapping(value="/jobSave")
	public ResponseEntity<List<MainModel>> jobSave(@RequestBody JobTO jobTO) {
		System.out.println("Inside Job Save"+jobTO);
//		if(jobTO.getJobProLinkageModel()!=null) {
//			jobProLinkageDao.save(jobTO.getJobProLinkageModel());
//		}
		jobDao.save(jobTO);
		
		MainModel mainModel =  new MainModel();
		List<JobTO> jobTOs = showTablePagination(mainModel, null);
		mainModel.setJobTOList(jobTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/jobDelete")
	public ResponseEntity<List<MainModel>> jobDelete(@RequestBody JobTO jobTO) {
		System.out.println("Inside Job Save"+jobTO);
		if(StringUtils.isNotEmpty(jobTO.getJobId())) {
			jobDao.delete(Integer.valueOf(jobTO.getJobId()));
		}
		MainModel mainModel =  new MainModel();
		List<JobTO> jobTOs = showTablePagination(mainModel, null);
		mainModel.setJobTOList(jobTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/jobDeleteAll")
	public ResponseEntity<List<MainModel>> jobDeleteAll() {
		jobDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<JobTO> jobTOs = showTablePagination(mainModel, null);
		mainModel.setJobTOList(jobTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}

}

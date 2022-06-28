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

import com.ivman.dao.UserRoleDao;
import com.ivman.to.MainModel;
import com.ivman.to.UserRoleTO;
import com.ivman.utils.AppConstants;
import com.ivman.utils.StringUtils;

@RestController
@RequestMapping(value="Api")
@RolesAllowed(value = AppConstants.SUPER_ADMIN)
public class APIUserRolesController {
	
	@Autowired
	private UserRoleDao userRoleDao;

	@PostMapping(value="/userRole")
	public ResponseEntity<List<MainModel>> userRole(@RequestBody MainModel mainModel) {
		System.out.println("Main Model : "+ mainModel);
		UserRoleTO searchUserRoleTO = mainModel.getSearchUserRoleTO();
		List<UserRoleTO> userRoleTOs = showTablePagination(mainModel, searchUserRoleTO);
		mainModel.setUserRoleTOList(userRoleTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
	private List<UserRoleTO> showTablePagination(MainModel mainModel, UserRoleTO searchUserRoleTO) {
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
		Integer totalRecords = userRoleDao.userRoleTotalRecordCount();
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
		return userRoleDao.findPaginated(currentPage,pageSize,searchUserRoleTO);
	}
	
	@PostMapping(value="/userRoleSave")
	public ResponseEntity<List<MainModel>> userRoleSave(@RequestBody UserRoleTO userRoleTO) {
		System.out.println("Inside UserRole Save"+userRoleTO);
		userRoleDao.save(userRoleTO);
		MainModel mainModel =  new MainModel();
		List<UserRoleTO> userRoleTOs = showTablePagination(mainModel, null);
		mainModel.setUserRoleTOList(userRoleTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/userRoleDelete")
	public ResponseEntity<List<MainModel>> userRoleDelete(@RequestBody UserRoleTO userRoleTO) {
		System.out.println("Inside UserRole Save"+userRoleTO);
		if(StringUtils.isNotEmpty(userRoleTO.getUserRoleId())) {
			userRoleDao.delete(Integer.valueOf(userRoleTO.getUserRoleId()));
		}
		MainModel mainModel =  new MainModel();
		List<UserRoleTO> userRoleTOs = showTablePagination(mainModel, null);
		mainModel.setUserRoleTOList(userRoleTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/userRoleDeleteAll")
	public ResponseEntity<List<MainModel>> userRoleDeleteAll() {
		userRoleDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<UserRoleTO> userRoleTOs = showTablePagination(mainModel, null);
		mainModel.setUserRoleTOList(userRoleTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
//	@PostMapping(value="/userRoleDownloadCSVFile")
//	public ResponseEntity<byte[]> userRoleDownloadCSVFile() throws IOException {
//		System.out.println("Inside Download");
//		List<UserRoleTO> userRoleTOList =  userRoleDao.getAll();
//		String userRoleJsonData = JsonConvertorUtility.convertUserRoleTOToJSON(userRoleTOList);
//		File userRoleCSVFile = JsonConvertorUtility.convertJsontoCSVFile("userRoleData.csv", userRoleJsonData);
//	    HttpHeaders header = new HttpHeaders();
//	    header.setContentType(MediaType.valueOf("text/csv"));
//	    header.setContentLength((int)userRoleCSVFile.length());
//	    header.set("Content-Disposition", "attachment; filename=" + userRoleCSVFile.getName());
//	    FileInputStream fileInputStream = null;
//	    byte[] filedate = new byte[(int) userRoleCSVFile.length()];
//	    try {
//			 fileInputStream = new FileInputStream(userRoleCSVFile);
//			 fileInputStream.read(filedate);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			fileInputStream.close();
//		}
//	    return new ResponseEntity<>(filedate, header, HttpStatus.OK);
//	}
//	
//	@PostMapping(value="/userRoleSaveCSVFile" )
//	public ResponseEntity<List<MainModel>> uploadCSV(@RequestParam MultipartFile file) {
//		FileUploadModel fileUploadModel = new FileUploadModel();
//		fileUploadModel.setFileData(file);
//		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
//			List<UserRoleTO> userRoleTOList = JsonConvertorUtility.convertCSVFileToJsonUserRole(fileUploadModel);
//			userRoleDao.saveAll(userRoleTOList);
//		}
//		MainModel mainModel =  new MainModel();
//		List<UserRoleTO> userRoleTOs = showTablePagination(mainModel, null);
//		mainModel.setUserRoleTOList(userRoleTOs); 
//		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
//	}
	
}

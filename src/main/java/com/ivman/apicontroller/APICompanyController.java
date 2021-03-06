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

import com.ivman.dao.CompanyDao;
import com.ivman.to.CompanyTO;
import com.ivman.to.MainModel;
import com.ivman.utils.AppConstants;
import com.ivman.utils.StringUtils;

@RestController
@RequestMapping(value="Api")
@RolesAllowed(value = AppConstants.SUPER_ADMIN)
public class APICompanyController {
	
	@Autowired
	private CompanyDao companyDao;

	@PostMapping(value="/company")
	public ResponseEntity<List<MainModel>> company(@RequestBody MainModel mainModel) {
		CompanyTO searchCompanyTO = mainModel.getSearchCompanyTO();
		System.out.println(searchCompanyTO);
		List<CompanyTO> companyTOs = showTablePagination(mainModel, searchCompanyTO);
		mainModel.setCompanyTOList(companyTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
	private List<CompanyTO> showTablePagination(MainModel mainModel, CompanyTO searchCompanyTO) {
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
		Integer totalRecords = companyDao.companyTotalRecordCount();
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
		return companyDao.findPaginated(currentPage,pageSize,searchCompanyTO);
	}
	
	@PostMapping(value="/companySave")
	public ResponseEntity<List<MainModel>> companySave(@RequestBody CompanyTO companyTO) {
		System.out.println("Inside Company Save"+companyTO);
		companyDao.save(companyTO);
		MainModel mainModel =  new MainModel();
		List<CompanyTO> companyTOs = showTablePagination(mainModel, null);
		mainModel.setCompanyTOList(companyTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/companyDelete")
	public ResponseEntity<List<MainModel>> companyDelete(@RequestBody CompanyTO companyTO) {
		System.out.println("Inside Company Save"+companyTO);
		if(StringUtils.isNotEmpty(companyTO.getCompanyId())) {
			companyDao.delete(Integer.valueOf(companyTO.getCompanyId()));
		}
		MainModel mainModel =  new MainModel();
		List<CompanyTO> companyTOs = showTablePagination(mainModel, null);
		mainModel.setCompanyTOList(companyTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/companyDeleteAll")
	public ResponseEntity<List<MainModel>> companyDeleteAll() {
		companyDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<CompanyTO> companyTOs = showTablePagination(mainModel, null);
		mainModel.setCompanyTOList(companyTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
//	@PostMapping(value="/companyDownloadCSVFile")
//	public ResponseEntity<byte[]> companyDownloadCSVFile() throws IOException {
//		System.out.println("Inside Download");
//		List<CompanyTO> companyTOList =  companyDao.getAll();
//		String companyJsonData = JsonConvertorUtility.convertCompanyTOToJSON(companyTOList);
//		File companyCSVFile = JsonConvertorUtility.convertJsontoCSVFile("companyData.csv", companyJsonData);
//	    HttpHeaders header = new HttpHeaders();
//	    header.setContentType(MediaType.valueOf("text/csv"));
//	    header.setContentLength((int)companyCSVFile.length());
//	    header.set("Content-Disposition", "attachment; filename=" + companyCSVFile.getName());
//	    FileInputStream fileInputStream = null;
//	    byte[] filedate = new byte[(int) companyCSVFile.length()];
//	    try {
//			 fileInputStream = new FileInputStream(companyCSVFile);
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
//	@PostMapping(value="/companySaveCSVFile" )
//	public ResponseEntity<List<MainModel>> uploadCSV(@RequestParam MultipartFile file) {
//		FileUploadModel fileUploadModel = new FileUploadModel();
//		fileUploadModel.setFileData(file);
//		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
//			List<CompanyTO> companyTOList = JsonConvertorUtility.convertCSVFileToJsonCompany(fileUploadModel);
//			companyDao.saveAll(companyTOList);
//		}
//		MainModel mainModel =  new MainModel();
//		List<CompanyTO> companyTOs = showTablePagination(mainModel, null);
//		mainModel.setCompanyTOList(companyTOs); 
//		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
//	}
	
}

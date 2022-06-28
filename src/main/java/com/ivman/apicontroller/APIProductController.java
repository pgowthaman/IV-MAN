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

import com.ivman.dao.ProRawLinkageDao;
import com.ivman.dao.ProductDao;
import com.ivman.to.MainModel;
import com.ivman.to.ProductTO;
import com.ivman.utils.AppConstants;
import com.ivman.utils.StringUtils;

@RestController
@RequestMapping(value="Api")
@RolesAllowed(value = AppConstants.SUPER_ADMIN)
public class APIProductController {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProRawLinkageDao proRawLinkageDao;

	@PostMapping(value="/product")
	public ResponseEntity<List<MainModel>> product(@RequestBody MainModel mainModel) {
		ProductTO searchProductTO = mainModel.getSearchProductTO();
		System.out.println(searchProductTO);
		List<ProductTO> productTOs = showTablePagination(mainModel, searchProductTO);
		mainModel.setProductTOList(productTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
	private List<ProductTO> showTablePagination(MainModel mainModel, ProductTO searchProductTO) {
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
		Integer totalRecords = productDao.productTotalRecordCount();
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
		return productDao.findPaginated(currentPage,pageSize,searchProductTO);
	}
	
	@PostMapping(value="/productSave")
	public ResponseEntity<List<MainModel>> productSave(@RequestBody ProductTO productTO) {
		System.out.println("Inside Product Save"+productTO);
		productDao.save(productTO);
//		if(productTO.getProRawLinkageList()!= null && !productTO.getProRawLinkageList().isEmpty()) {
//			for (ProRawLinkageModel proRawLinkageModel : productTO.getProRawLinkageList()) {
//				proRawLinkageDao.save(proRawLinkageModel);
//			}
//		}
		MainModel mainModel =  new MainModel();
		List<ProductTO> productTOs = showTablePagination(mainModel, null);
		mainModel.setProductTOList(productTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/productDelete")
	public ResponseEntity<List<MainModel>> productDelete(@RequestBody ProductTO productTO) {
		System.out.println("Inside Product delete"+productTO);
		
//		if(dbProductTO.getProRawLinkageList()!= null && !dbProductTO.getProRawLinkageList().isEmpty()) {
//			for (ProRawLinkageModel proRawLinkageModel : dbProductTO.getProRawLinkageList()) {
//				proRawLinkageDao.delete(proRawLinkageModel.getProRawLinkageId());
//			}
//		}
		if(StringUtils.isNotEmpty(productTO.getProductId())) {
			productDao.delete(Integer.valueOf(productTO.getProductId()));
		}
		MainModel mainModel =  new MainModel();
		List<ProductTO> productTOs = showTablePagination(mainModel, null);
		mainModel.setProductTOList(productTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/productDeleteAll")
	public ResponseEntity<List<MainModel>> productDeleteAll() {
		proRawLinkageDao.deleteAll();
		productDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<ProductTO> productTOs = showTablePagination(mainModel, null);
		mainModel.setProductTOList(productTOs); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}

}

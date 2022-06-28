package com.ivman.to;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class MainModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pageNumber;
	
	private String pageSize;
	
	private String totalRecords;
	
	private String totalNumberOfpages;
	
	private String previousPage;
	
	private String nextPage;

	private String currentPage;
	
	private String message ;
	
	private boolean response = true;
	
	private List<UserTO> userTOList;
	
	private UserTO searchUserTO;
	
	private List<UserRoleTO> userRoleTOList;
	
	private UserRoleTO searchUserRoleTO;
	
	private List<CompanyTO> companyTOList;
	
	private CompanyTO searchCompanyTO;
	
	private List<JobTO> jobTOList;
	
	private JobTO searchJobTO;
	
	private List<JobProLinkageTO> JobProLinkageTOList;
	
	private JobProLinkageTO searchJobProLinkageTO;
	
	private List<ProductTO> ProductTOList;
	
	private ProductTO searchProductTO;
	
	private List<ProRawLinkageTO> proRawLinkageTOList;
	
	private ProRawLinkageTO searchProRawLinkageTO;
	
	private List<RawMaterialsTO> rawMaterialsTOList;
	
	private RawMaterialsTO searchRawMaterialsTO;

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getTotalNumberOfpages() {
		return totalNumberOfpages;
	}

	public void setTotalNumberOfpages(String totalNumberOfpages) {
		this.totalNumberOfpages = totalNumberOfpages;
	}

	public String getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	public List<UserTO> getUserTOList() {
		return userTOList;
	}

	public void setUserTOList(List<UserTO> userTOList) {
		this.userTOList = userTOList;
	}

	public UserTO getSearchUserTO() {
		return searchUserTO;
	}

	public void setSearchUserTO(UserTO searchUserTO) {
		this.searchUserTO = searchUserTO;
	}

	public List<UserRoleTO> getUserRoleTOList() {
		return userRoleTOList;
	}

	public void setUserRoleTOList(List<UserRoleTO> userRoleTOList) {
		this.userRoleTOList = userRoleTOList;
	}

	public UserRoleTO getSearchUserRoleTO() {
		return searchUserRoleTO;
	}

	public void setSearchUserRoleTO(UserRoleTO searchUserRoleTO) {
		this.searchUserRoleTO = searchUserRoleTO;
	}

	public List<CompanyTO> getCompanyTOList() {
		return companyTOList;
	}

	public void setCompanyTOList(List<CompanyTO> companyTOList) {
		this.companyTOList = companyTOList;
	}

	public CompanyTO getSearchCompanyTO() {
		return searchCompanyTO;
	}

	public void setSearchCompanyTO(CompanyTO searchCompanyTO) {
		this.searchCompanyTO = searchCompanyTO;
	}

	public List<JobTO> getJobTOList() {
		return jobTOList;
	}

	public void setJobTOList(List<JobTO> jobTOList) {
		this.jobTOList = jobTOList;
	}

	public JobTO getSearchJobTO() {
		return searchJobTO;
	}

	public void setSearchJobTO(JobTO searchJobTO) {
		this.searchJobTO = searchJobTO;
	}

	public List<JobProLinkageTO> getJobProLinkageTOList() {
		return JobProLinkageTOList;
	}

	public void setJobProLinkageTOList(List<JobProLinkageTO> jobProLinkageTOList) {
		JobProLinkageTOList = jobProLinkageTOList;
	}

	public JobProLinkageTO getSearchJobProLinkageTO() {
		return searchJobProLinkageTO;
	}

	public void setSearchJobProLinkageTO(JobProLinkageTO searchJobProLinkageTO) {
		this.searchJobProLinkageTO = searchJobProLinkageTO;
	}

	public List<ProductTO> getProductTOList() {
		return ProductTOList;
	}

	public void setProductTOList(List<ProductTO> productTOList) {
		ProductTOList = productTOList;
	}

	public ProductTO getSearchProductTO() {
		return searchProductTO;
	}

	public void setSearchProductTO(ProductTO searchProductTO) {
		this.searchProductTO = searchProductTO;
	}

	public List<ProRawLinkageTO> getProRawLinkageTOList() {
		return proRawLinkageTOList;
	}

	public void setProRawLinkageTOList(List<ProRawLinkageTO> proRawLinkageTOList) {
		this.proRawLinkageTOList = proRawLinkageTOList;
	}

	public ProRawLinkageTO getSearchProRawLinkageTO() {
		return searchProRawLinkageTO;
	}

	public void setSearchProRawLinkageTO(ProRawLinkageTO searchProRawLinkageTO) {
		this.searchProRawLinkageTO = searchProRawLinkageTO;
	}

	public List<RawMaterialsTO> getRawMaterialsTOList() {
		return rawMaterialsTOList;
	}

	public void setRawMaterialsTOList(List<RawMaterialsTO> rawMaterialsTOList) {
		this.rawMaterialsTOList = rawMaterialsTOList;
	}

	public RawMaterialsTO getSearchRawMaterialsTO() {
		return searchRawMaterialsTO;
	}

	public void setSearchRawMaterialsTO(RawMaterialsTO searchRawMaterialsTO) {
		this.searchRawMaterialsTO = searchRawMaterialsTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "MainModel [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", totalRecords=" + totalRecords
				+ ", totalNumberOfpages=" + totalNumberOfpages + ", previousPage=" + previousPage + ", nextPage="
				+ nextPage + ", currentPage=" + currentPage + ", message=" + message + ", response=" + response
				+ ", userTOList=" + userTOList + ", searchUserTO=" + searchUserTO + ", userRoleTOList=" + userRoleTOList
				+ ", searchUserRoleTO=" + searchUserRoleTO + ", companyTOList=" + companyTOList + ", searchCompanyTO="
				+ searchCompanyTO + ", jobTOList=" + jobTOList + ", searchJobTO=" + searchJobTO
				+ ", JobProLinkageTOList=" + JobProLinkageTOList + ", searchJobProLinkageTO=" + searchJobProLinkageTO
				+ ", ProductTOList=" + ProductTOList + ", searchProductTO=" + searchProductTO + ", proRawLinkageTOList="
				+ proRawLinkageTOList + ", searchProRawLinkageTO=" + searchProRawLinkageTO + ", rawMaterialsTOList="
				+ rawMaterialsTOList + ", searchRawMaterialsTO=" + searchRawMaterialsTO + "]";
	}
	
	
	
}

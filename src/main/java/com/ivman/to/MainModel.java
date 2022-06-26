package com.ivman.to;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
	
	private boolean response;
	
	private List<UserTO> userModelList;
	
	private UserTO searchUserModel;
	
	private List<UserRoleTO> userRoleModelList;
	
	private UserRoleTO searchUserRoleModel;
	
	private List<CompanyTO> companyModelList;
	
	private CompanyTO searchCompanyModel;

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

	public List<UserTO> getUserModelList() {
		return userModelList;
	}

	public void setUserModelList(List<UserTO> userModelList) {
		this.userModelList = userModelList;
	}

	public UserTO getSearchUserModel() {
		return searchUserModel;
	}

	public void setSearchUserModel(UserTO searchUserModel) {
		this.searchUserModel = searchUserModel;
	}

	public List<UserRoleTO> getUserRoleModelList() {
		return userRoleModelList;
	}

	public void setUserRoleModelList(List<UserRoleTO> userRoleModelList) {
		this.userRoleModelList = userRoleModelList;
	}

	public UserRoleTO getSearchUserRoleModel() {
		return searchUserRoleModel;
	}

	public void setSearchUserRoleModel(UserRoleTO searchUserRoleModel) {
		this.searchUserRoleModel = searchUserRoleModel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	@Override
	public String toString() {
		return "MainModel [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", totalRecords=" + totalRecords
				+ ", totalNumberOfpages=" + totalNumberOfpages + ", previousPage=" + previousPage + ", nextPage="
				+ nextPage + ", currentPage=" + currentPage + ", message=" + message + ", response=" + response
				+ ", userModelList=" + userModelList + ", searchUserModel=" + searchUserModel + ", userRoleModelList="
				+ userRoleModelList + ", searchUserRoleModel=" + searchUserRoleModel + ", companyModelList="
				+ companyModelList + ", searchCompanyModel=" + searchCompanyModel + "]";
	}

	public List<CompanyTO> getCompanyModelList() {
		return companyModelList;
	}

	public void setCompanyModelList(List<CompanyTO> companyModelList) {
		this.companyModelList = companyModelList;
	}

	public CompanyTO getSearchCompanyModel() {
		return searchCompanyModel;
	}

	public void setSearchCompanyModel(CompanyTO searchCompanyModel) {
		this.searchCompanyModel = searchCompanyModel;
	}
	
	
}

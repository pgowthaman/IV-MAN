package com.ivman.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity	
@Table(name = "jobs")
@Component
public class JobModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "job_id",strategy = GenerationType.AUTO)
	@Column(name = "job_id")
	private Integer jobId;
	
	@Column(name = "job_worker_name")
	private String jobWorkerName;
	
	@Column(name = "client_name")
	private String clientName;
	
	@Column(name = "job_assigned_date")
	private Date jobAssignedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyModel companyModel;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_pro_linkage_id")
	private JobProLinkageModel jobProLinkageModel;

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getJobWorkerName() {
		return jobWorkerName;
	}

	public void setJobWorkerName(String jobWorkerName) {
		this.jobWorkerName = jobWorkerName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getJobAssignedDate() {
		return jobAssignedDate;
	}

	public void setJobAssignedDate(Date jobAssignedDate) {
		this.jobAssignedDate = jobAssignedDate;
	}

	public CompanyModel getCompanyModel() {
		return companyModel;
	}

	public void setCompanyModel(CompanyModel companyModel) {
		this.companyModel = companyModel;
	}

	public JobProLinkageModel getJobProLinkageModel() {
		return jobProLinkageModel;
	}

	public void setJobProLinkageModel(JobProLinkageModel jobProLinkageModel) {
		this.jobProLinkageModel = jobProLinkageModel;
	}

	@Override
	public String toString() {
		return "JobModel [jobId=" + jobId + ", jobWorkerName=" + jobWorkerName + ", clientName=" + clientName
				+ ", jobAssignedDate=" + jobAssignedDate + ", companyModel=" + companyModel + ", jobProLinkageModel="
				+ jobProLinkageModel + "]";
	}

}

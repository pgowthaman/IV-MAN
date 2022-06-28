package com.ivman.to;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ivman.model.CompanyModel;
import com.ivman.model.JobModel;
import com.ivman.model.JobProLinkageModel;
import com.ivman.model.ProRawLinkageModel;
import com.ivman.model.RawMaterialsModel;
import com.ivman.utils.StringUtils;

@Component
@JsonInclude(Include.NON_NULL)
public class JobTO  implements Serializable{

	private String jobId;

	private String jobWorkerName;

	private String clientName;

	private String jobAssignedDate;

	private CompanyTO companyTO;

	private JobProLinkageTO jobProLinkageTO;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
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

	public String getJobAssignedDate() {
		return jobAssignedDate;
	}

	public void setJobAssignedDate(String jobAssignedDate) {
		this.jobAssignedDate = jobAssignedDate;
	}

	public CompanyTO getCompanyTO() {
		return companyTO;
	}

	public void setCompanyTO(CompanyTO companyTO) {
		this.companyTO = companyTO;
	}

	public JobProLinkageTO getJobProLinkageTO() {
		return jobProLinkageTO;
	}

	public void setJobProLinkageTO(JobProLinkageTO jobProLinkageTO) {
		this.jobProLinkageTO = jobProLinkageTO;
	}

	@Override
	public String toString() {
		return "JobTO [jobId=" + jobId + ", jobWorkerName=" + jobWorkerName + ", clientName=" + clientName
				+ ", jobAssignedDate=" + jobAssignedDate + ", companyTO=" + companyTO + ", jobProLinkageTO="
				+ jobProLinkageTO + "]";
	}

	public void convertModelToTO(JobModel model) {
		if(Objects.nonNull(model.getJobId())) {
			this.jobId = model.getJobId().toString();
		}
		this.jobWorkerName = model.getJobWorkerName();
		this.clientName = model.getClientName();
		if(Objects.nonNull(model.getJobAssignedDate())) {
			this.jobAssignedDate = model.getJobAssignedDate().toString();
		}

		if(Objects.nonNull(model.getCompanyModel())) {
			CompanyTO companyTO = new CompanyTO();
			companyTO.convertModelToTO(model.getCompanyModel());
			this.setCompanyTO(companyTO);
		}

		if(Objects.nonNull(model.getJobProLinkageModel())) {
			JobProLinkageTO jobProLinkageTO = new JobProLinkageTO();
			jobProLinkageTO.convertModelToTO(model.getJobProLinkageModel());
			this.setJobProLinkageTO(jobProLinkageTO);
		}
	}

	public void convertTOToModel(JobModel model) {
		if(StringUtils.isNotEmpty(this.getJobId())) {
			model.setJobId(Integer.valueOf(this.getJobId()));
		}
		long millis=System.currentTimeMillis();  
		model.setJobAssignedDate(new Date(millis));
		model.setClientName(this.clientName);
		model.setJobWorkerName(this.jobWorkerName);
		if(Objects.nonNull(this.getCompanyTO())) {
			CompanyModel companyModel = new CompanyModel();
			CompanyTO companyTO = this.getCompanyTO();
			companyTO.convertTOToModel(companyModel);
			model.setCompanyModel(companyModel);
		}

		if(Objects.nonNull(this.getJobProLinkageTO())) {
			JobProLinkageModel jobProLinkageModel = new JobProLinkageModel();
			JobProLinkageTO jobProLinkageTO = this.getJobProLinkageTO();
			jobProLinkageTO.convertTOToModel(jobProLinkageModel);
			model.setJobProLinkageModel(jobProLinkageModel);
		}
	}
}

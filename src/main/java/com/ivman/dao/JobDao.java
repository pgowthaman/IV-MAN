package com.ivman.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivman.model.JobModel;
import com.ivman.to.JobTO;

@Repository
public class JobDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(JobTO jobTO){
		if(jobTO!=null) {
			JobModel jobModel = new JobModel();
			jobTO.convertTOToModel(jobModel);
			sessionFactory.getCurrentSession().saveOrUpdate(jobModel);
		}
	}

	@Transactional
	public void saveAll(List<JobTO> jobTOs){
		for (JobTO jobTO : jobTOs) {
			JobModel jobModel = new JobModel();
			jobTO.convertTOToModel(jobModel);
			sessionFactory.getCurrentSession().save(jobModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from JobModel").executeUpdate();
	}

	@Transactional
	public List<JobTO> getAll(){
		List<JobModel> jobModels =sessionFactory.getCurrentSession().createQuery("from JobModel").list();
		
		List<JobTO> jobTOs = new ArrayList<JobTO>();
		for (JobModel jobModel : jobModels) {
			JobTO jobTO = new JobTO();
			jobTO.convertModelToTO(jobModel);
			jobTOs.add(jobTO);
		}
		
		return jobTOs;
	}

	@Transactional
	public JobTO getByJobId(Integer id) {
		String hql = "from JobModel where jobId='" + id+"'";
		List<JobModel> jobModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (jobModelList != null && !jobModelList.isEmpty()) {
			JobTO jobTO = new JobTO();
			jobTO.convertModelToTO(jobModelList.get(0));
			return jobTO;
		}

		return null;
	}

	@Transactional
	public boolean delete(Integer id) {
		JobModel jobModel = new JobModel();
		jobModel.setJobId(id);
		try {
			sessionFactory.getCurrentSession().delete(jobModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<JobTO> findPaginated(int pageNo, int pageSize, JobTO searchJobTO) {
		List<JobModel> jobModels = null;
		String hqlQuery = "from JobModel as job";
		if(searchJobTO!=null) {
			boolean whereAdded = false;
			if(searchJobTO.getJobId()!=null) {
				hqlQuery = hqlQuery +" where jobId like '"+searchJobTO.getJobId()+"'";
				whereAdded = true;
			}
			if(searchJobTO.getJobWorkerName()!=null && !"".equals(searchJobTO.getJobWorkerName())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where jobWorkerName like '"+searchJobTO.getJobWorkerName()+"'";
				}else {
					hqlQuery = hqlQuery +" and jobWorkerName like '"+searchJobTO.getJobWorkerName()+"'";
				}
				whereAdded = true;
			}
			if(searchJobTO.getClientName()!=null && !"".equals(searchJobTO.getClientName())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where clientName like '"+searchJobTO.getClientName()+"'";
				}else {
					hqlQuery = hqlQuery +" and clientName like '"+searchJobTO.getClientName()+"'";
				}
				whereAdded = true;
			}
			if(searchJobTO.getCompanyTO()!=null) {
				if(searchJobTO.getCompanyTO().getCompanyId()!=null) {
					if(!whereAdded) {
						hqlQuery = hqlQuery +" where job.companyModel.companyId = '"+searchJobTO.getCompanyTO().getCompanyId()+"'";
					}else {
						hqlQuery = hqlQuery +" and job.companyModel.companyId = '"+searchJobTO.getCompanyTO().getCompanyId()+"'";
					}
					whereAdded = true;
				}
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery);

		if(pageNo==1) {
			query.setFirstResult(pageNo-1);
		}else {
			query.setFirstResult((pageNo-1)*pageSize);
		}
		query.setMaxResults(pageSize);
		jobModels =  query.list();
		List<JobTO> jobTOs = new ArrayList<JobTO>();
		for (JobModel jobModel : jobModels) {
			JobTO jobTO = new JobTO();
			jobTO.convertModelToTO(jobModel);
			jobTOs.add(jobTO);
		}
		
		return jobTOs;
	}

	@Transactional
	public Integer jobTotalRecordCount() {   
		String hqlString = "select count(*) from JobModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  


}

package com.ivman.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivman.model.JobProLinkageModel;
import com.ivman.to.JobProLinkageTO;

@Repository
public class JobProLinkageDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(JobProLinkageTO jobProLinkageTO){
		if(jobProLinkageTO!=null) {
			JobProLinkageModel jobProLinkageModel = new JobProLinkageModel();
			jobProLinkageTO.convertTOToModel(jobProLinkageModel);
			sessionFactory.getCurrentSession().saveOrUpdate(jobProLinkageModel);
		}
	}

	@Transactional
	public void saveAll(List<JobProLinkageTO> jobProLinkageTOs){
		for (JobProLinkageTO jobProLinkageTO : jobProLinkageTOs) {
			JobProLinkageModel jobProLinkageModel = new JobProLinkageModel();
			jobProLinkageTO.convertTOToModel(jobProLinkageModel);
			sessionFactory.getCurrentSession().save(jobProLinkageModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from JobProLinkageModel").executeUpdate();
	}

	@Transactional
	public List<JobProLinkageTO> getAll(){
		List<JobProLinkageModel> jobProLinkageModels =sessionFactory.getCurrentSession().createQuery("from JobProLinkageModel").list();
		
		List<JobProLinkageTO> jobProLinkageTOs = new ArrayList<JobProLinkageTO>();
		for (JobProLinkageModel jobProLinkageModel : jobProLinkageModels) {
			JobProLinkageTO jobProLinkageTO = new JobProLinkageTO();
			jobProLinkageTO.convertModelToTO(jobProLinkageModel);
			jobProLinkageTOs.add(jobProLinkageTO);
		}
		
		return jobProLinkageTOs;
	}

	@Transactional
	public JobProLinkageModel getByJobProLinkageId(Integer id) {
		String hql = "from JobProLinkageModel where jobProLinkageId='" + id+"'";
		List<JobProLinkageModel> jobProLinkageModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (jobProLinkageModelList != null && !jobProLinkageModelList.isEmpty()) {
			return jobProLinkageModelList.get(0);
		}

		return null;
	}

	@Transactional
	public boolean delete(Integer id) {
		JobProLinkageModel jobProLinkageModel = new JobProLinkageModel();
		jobProLinkageModel.setJobProLinkageId(id);
		try {
			sessionFactory.getCurrentSession().delete(jobProLinkageModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

//	@Transactional
//	public List<JobProLinkageModel> findPaginated(int pageNo, int pageSize, JobProLinkageModel searchJobProLinkageModel) {
//		List<JobProLinkageModel> jobProLinkageModels = null;
//		String hqlQuery = "from JobProLinkageModel as jobProLinkage";
//		if(searchJobProLinkageModel!=null) {
//			boolean whereAdded = false;
//			if(searchJobProLinkageModel.getJobProLinkageId()!=null) {
//				hqlQuery = hqlQuery +" where jobProLinkageId like '"+searchJobProLinkageModel.getJobProLinkageId()+"'";
//				whereAdded = true;
//			}
//			if(searchJobProLinkageModel.getJobProLinkageCode()!=null && !"".equals(searchJobProLinkageModel.getJobProLinkageCode())) {
//				if(!whereAdded) {
//					hqlQuery = hqlQuery +" where jobProLinkageCode like '"+searchJobProLinkageModel.getJobProLinkageCode()+"'";
//				}else {
//					hqlQuery = hqlQuery +" and jobProLinkageCode like '"+searchJobProLinkageModel.getJobProLinkageCode()+"'";
//				}
//				whereAdded = true;
//			}
//			if(searchJobProLinkageModel.getJobProLinkageName()!=null && !"".equals(searchJobProLinkageModel.getJobProLinkageName())) {
//				if(!whereAdded) {
//					hqlQuery = hqlQuery +" where jobProLinkageName like '"+searchJobProLinkageModel.getJobProLinkageName()+"'";
//				}else {
//					hqlQuery = hqlQuery +" and jobProLinkageName like '"+searchJobProLinkageModel.getJobProLinkageName()+"'";
//				}
//				whereAdded = true;
//			}
//			
//			if(searchJobProLinkageModel.getCompanyModel()!=null) {
//				if(searchJobProLinkageModel.getCompanyModel().getCompanyId()!=null) {
//					if(!whereAdded) {
//						hqlQuery = hqlQuery +" where jobProLinkage.companyModel.companyId = '"+searchJobProLinkageModel.getCompanyModel().getCompanyId()+"'";
//					}else {
//						hqlQuery = hqlQuery +" and jobProLinkage.companyModel.companyId = '"+searchJobProLinkageModel.getCompanyModel().getCompanyId()+"'";
//					}
//					whereAdded = true;
//				}
//			}
//		}
//		Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery);
//
//		if(pageNo==1) {
//			query.setFirstResult(pageNo-1);
//		}else {
//			query.setFirstResult((pageNo-1)*pageSize);
//		}
//		query.setMaxResults(pageSize);
//		jobProLinkageModels =  query.list();
//
//		return jobProLinkageModels;
//	}

	@Transactional
	public Integer jobProLinkageTotalRecordCount() {   
		String hqlString = "select count(*) from JobProLinkageModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  


}

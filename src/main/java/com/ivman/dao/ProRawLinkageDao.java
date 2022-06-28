package com.ivman.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivman.model.ProRawLinkageModel;
import com.ivman.to.ProRawLinkageTO;

@Repository
public class ProRawLinkageDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(ProRawLinkageTO proRawLinkageTO){
		if(proRawLinkageTO!=null) {
			ProRawLinkageModel proRawLinkageModel = new ProRawLinkageModel();
			proRawLinkageTO.convertTOToModel(proRawLinkageModel);
			sessionFactory.getCurrentSession().saveOrUpdate(proRawLinkageModel);
		}
	}

	@Transactional
	public void saveAll(List<ProRawLinkageTO> proRawLinkageTOs){
		for (ProRawLinkageTO proRawLinkageTO : proRawLinkageTOs) {
			ProRawLinkageModel proRawLinkageModel = new ProRawLinkageModel();
			proRawLinkageTO.convertTOToModel(proRawLinkageModel);
			sessionFactory.getCurrentSession().save(proRawLinkageModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from ProRawLinkageModel").executeUpdate();
	}

	@Transactional
	public List<ProRawLinkageTO> getAll(){
		List<ProRawLinkageModel> proRawLinkageModels =sessionFactory.getCurrentSession().createQuery("from ProRawLinkageModel").list();
		
		List<ProRawLinkageTO> proRawLinkageTOs = new ArrayList<ProRawLinkageTO>();
		for (ProRawLinkageModel proRawLinkageModel : proRawLinkageModels) {
			ProRawLinkageTO proRawLinkageTO =  new ProRawLinkageTO();
			proRawLinkageTO.convertModelToTO(proRawLinkageModel);
			proRawLinkageTOs.add(proRawLinkageTO);
		}
		
		return proRawLinkageTOs;
	}

	@Transactional
	public ProRawLinkageTO getByProRawLinkageId(Integer id) {
		String hql = "from ProRawLinkageModel where proRawLinkageId='" + id+"'";
		List<ProRawLinkageModel> proRawLinkageModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (proRawLinkageModelList != null && !proRawLinkageModelList.isEmpty()) {
			ProRawLinkageTO proRawLinkageTO =  new ProRawLinkageTO();
			proRawLinkageTO.convertModelToTO(proRawLinkageModelList.get(0));
			return proRawLinkageTO;
		}

		return null;
	}

	@Transactional
	public boolean delete(Integer id) {
		ProRawLinkageModel proRawLinkageModel = new ProRawLinkageModel();
		proRawLinkageModel.setProRawLinkageId(id);
		try {
			sessionFactory.getCurrentSession().delete(proRawLinkageModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

//	@Transactional
//	public List<ProRawLinkageModel> findPaginated(int pageNo, int pageSize, ProRawLinkageModel searchProRawLinkageModel) {
//		List<ProRawLinkageModel> proRawLinkageModels = null;
//		String hqlQuery = "from ProRawLinkageModel as proRawLinkage";
//		if(searchProRawLinkageModel!=null) {
//			boolean whereAdded = false;
//			if(searchProRawLinkageModel.getProRawLinkageId()!=null) {
//				hqlQuery = hqlQuery +" where proRawLinkageId like '"+searchProRawLinkageModel.getProRawLinkageId()+"'";
//				whereAdded = true;
//			}
//			if(searchProRawLinkageModel.getProRawLinkageCode()!=null && !"".equals(searchProRawLinkageModel.getProRawLinkageCode())) {
//				if(!whereAdded) {
//					hqlQuery = hqlQuery +" where proRawLinkageCode like '"+searchProRawLinkageModel.getProRawLinkageCode()+"'";
//				}else {
//					hqlQuery = hqlQuery +" and proRawLinkageCode like '"+searchProRawLinkageModel.getProRawLinkageCode()+"'";
//				}
//				whereAdded = true;
//			}
//			if(searchProRawLinkageModel.getProRawLinkageName()!=null && !"".equals(searchProRawLinkageModel.getProRawLinkageName())) {
//				if(!whereAdded) {
//					hqlQuery = hqlQuery +" where proRawLinkageName like '"+searchProRawLinkageModel.getProRawLinkageName()+"'";
//				}else {
//					hqlQuery = hqlQuery +" and proRawLinkageName like '"+searchProRawLinkageModel.getProRawLinkageName()+"'";
//				}
//				whereAdded = true;
//			}
//			
//			if(searchProRawLinkageModel.getCompanyModel()!=null) {
//				if(searchProRawLinkageModel.getCompanyModel().getCompanyId()!=null) {
//					if(!whereAdded) {
//						hqlQuery = hqlQuery +" where proRawLinkage.companyModel.companyId = '"+searchProRawLinkageModel.getCompanyModel().getCompanyId()+"'";
//					}else {
//						hqlQuery = hqlQuery +" and proRawLinkage.companyModel.companyId = '"+searchProRawLinkageModel.getCompanyModel().getCompanyId()+"'";
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
//		proRawLinkageModels =  query.list();
//
//		return proRawLinkageModels;
//	}

	@Transactional
	public Integer proRawLinkageTotalRecordCount() {   
		String hqlString = "select count(*) from ProRawLinkageModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  


}

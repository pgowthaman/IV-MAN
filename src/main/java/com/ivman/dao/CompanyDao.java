package com.ivman.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivman.model.CompanyModel;
import com.ivman.to.CompanyTO;

@Repository
public class CompanyDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(CompanyTO companyTO){
		if(companyTO!=null) {
			CompanyModel companyModel = new CompanyModel();
			companyTO.convertTOToModel(companyModel);
			sessionFactory.getCurrentSession().saveOrUpdate(companyModel);
		}
	}

	@Transactional
	public void saveAll(List<CompanyTO> companyTOs){
		for (CompanyTO companyTO : companyTOs) {
			CompanyModel companyModel = new CompanyModel();
			companyTO.convertTOToModel(companyModel);
			sessionFactory.getCurrentSession().save(companyModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from CompanyModel").executeUpdate();
	}

	@Transactional
	public List<CompanyTO> getAll(){
		List<CompanyModel> companyModels =sessionFactory.getCurrentSession().createQuery("from CompanyModel").list();
		
		List<CompanyTO> roleTOs =  new ArrayList<CompanyTO>();
		for (CompanyModel companyModel : companyModels) {
			CompanyTO  companyTO = new CompanyTO();
			companyTO.convertModelToTO(companyModel);
			roleTOs.add(companyTO);
		}
		return roleTOs;
	}

	@Transactional
	public CompanyTO getByCompanyId(String id) {
		String hql = "from CompanyModel where companyId='" + id+"'";
		List<CompanyModel> companyModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (companyModelList != null && !companyModelList.isEmpty()) {
			CompanyTO  companyTO = new CompanyTO();
			companyTO.convertModelToTO(companyModelList.get(0));
			return companyTO;
		}

		return null;
	}

	@Transactional
	public boolean delete(String id) {
		CompanyModel companyModel = new CompanyModel();
		companyModel.setCompanyId(Integer.valueOf(id));
		try {
			sessionFactory.getCurrentSession().delete(companyModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<CompanyTO> findPaginated(int pageNo, int pageSize, CompanyTO searchCompanyTO) {
		List<CompanyModel> companyModels = null;
		String hqlQuery = "from CompanyModel";
		if(searchCompanyTO!=null) {
			boolean whereAdded = false;
			if(searchCompanyTO.getCompanyId()!=null) {
				hqlQuery = hqlQuery +" where companyId like '"+searchCompanyTO.getCompanyId()+"'";
				whereAdded = true;
			}
			if(searchCompanyTO.getCompanyName()!=null && !"".equals(searchCompanyTO.getCompanyName())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where companyName like '"+searchCompanyTO.getCompanyName()+"'";
				}else {
					hqlQuery = hqlQuery +" and companyName like '"+searchCompanyTO.getCompanyName()+"'";
				}
				whereAdded = true;
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hqlQuery);

		if(pageNo==1) {
			query.setFirstResult(pageNo-1);
		}else {
			query.setFirstResult((pageNo-1)*pageSize);
		}
		query.setMaxResults(pageSize);
		companyModels =  query.list();

		List<CompanyTO> roleTOs =  new ArrayList<CompanyTO>();
		for (CompanyModel companyModel : companyModels) {
			CompanyTO  companyTO = new CompanyTO();
			companyTO.convertModelToTO(companyModel);
			roleTOs.add(companyTO);
		}
		return roleTOs;
	}

	@Transactional
	public Integer companyTotalRecordCount() {   
		String hqlString = "select count(*) from CompanyModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  


}

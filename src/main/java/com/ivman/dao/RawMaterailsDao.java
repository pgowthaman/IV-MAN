package com.ivman.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivman.model.RawMaterialsModel;
import com.ivman.to.CompanyTO;
import com.ivman.to.RawMaterialsTO;

@Repository
public class RawMaterailsDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(RawMaterialsTO rawMaterialsTO){
		if(rawMaterialsTO!=null) {
			RawMaterialsModel rawMaterialsModel =  new RawMaterialsModel();
			rawMaterialsTO.convertTOToModel(rawMaterialsModel);
			sessionFactory.getCurrentSession().saveOrUpdate(rawMaterialsModel);
		}
	}

	@Transactional
	public void saveAll(List<RawMaterialsTO> rawMaterialsTOs){
		for (RawMaterialsTO rawMaterialsTO : rawMaterialsTOs) {
			RawMaterialsModel rawMaterialsModel =  new RawMaterialsModel();
			rawMaterialsTO.convertTOToModel(rawMaterialsModel);
			sessionFactory.getCurrentSession().save(rawMaterialsModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from RawMaterialsModel").executeUpdate();
	}

	@Transactional
	public List<RawMaterialsTO> getAll(){
		List<RawMaterialsModel> rawMaterialsModels =sessionFactory.getCurrentSession().createQuery("from RawMaterialsModel").list();
		
		List<RawMaterialsTO> rawMaterialsTOs = new ArrayList<RawMaterialsTO>();
		for (RawMaterialsModel rawMaterialsModel : rawMaterialsModels) {
			RawMaterialsTO rawMaterialsTO = new RawMaterialsTO();
			rawMaterialsTO.convertModelToTO(rawMaterialsModel);
			rawMaterialsTOs.add(rawMaterialsTO);
		}
		
		return rawMaterialsTOs;
	}

	@Transactional
	public RawMaterialsModel getByRawMaterailsId(Integer id) {
		String hql = "from RawMaterialsModel where companyId='" + id+"'";
		List<RawMaterialsModel> rawMaterialsModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (rawMaterialsModelList != null && !rawMaterialsModelList.isEmpty()) {
			return rawMaterialsModelList.get(0);
		}

		return null;
	}

	@Transactional
	public boolean delete(Integer id) {
		RawMaterialsModel rawMaterialsModel = new RawMaterialsModel();
		rawMaterialsModel.setRawMaterialId(id);
		try {
			sessionFactory.getCurrentSession().delete(rawMaterialsModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<RawMaterialsTO> findPaginated(int pageNo, int pageSize, RawMaterialsTO searchRawMaterialTO) {
		List<RawMaterialsModel> rawMaterialsModels = null;
		String hqlQuery = "from RawMaterialsModel as rawMaterials";
		if(searchRawMaterialTO!=null) {
			boolean whereAdded = false;
			if(searchRawMaterialTO.getRawMaterialId()!=null) {
				hqlQuery = hqlQuery +" where rawMaterialId like '"+searchRawMaterialTO.getRawMaterialId()+"'";
				whereAdded = true;
			}
			if(searchRawMaterialTO.getRawMaterialCode()!=null && !"".equals(searchRawMaterialTO.getRawMaterialCode())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where rawMaterialCode like '"+searchRawMaterialTO.getRawMaterialCode()+"'";
				}else {
					hqlQuery = hqlQuery +" and rawMaterialCode like '"+searchRawMaterialTO.getRawMaterialCode()+"'";
				}
				whereAdded = true;
			}
			if(searchRawMaterialTO.getRawMaterialName()!=null && !"".equals(searchRawMaterialTO.getRawMaterialName())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where rawMaterialName like '"+searchRawMaterialTO.getRawMaterialName()+"'";
				}else {
					hqlQuery = hqlQuery +" and rawMaterialName like '"+searchRawMaterialTO.getRawMaterialName()+"'";
				}
				whereAdded = true;
			}
			if(searchRawMaterialTO.getRawMaterialQtyType()!=null && !"".equals(searchRawMaterialTO.getRawMaterialQtyType())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where rawMaterialQtyType like '"+searchRawMaterialTO.getRawMaterialQtyType()+"'";
				}else {
					hqlQuery = hqlQuery +" and rawMaterialQtyType like '"+searchRawMaterialTO.getRawMaterialQtyType()+"'";
				}
				whereAdded = true;
			}
			
			if(searchRawMaterialTO.getCompanyTO()!=null) {
				if(searchRawMaterialTO.getCompanyTO().getCompanyId()!=null) {
					if(!whereAdded) {
						hqlQuery = hqlQuery +" where rawMaterials.companyModel.companyId = '"+searchRawMaterialTO.getCompanyTO().getCompanyId()+"'";
					}else {
						hqlQuery = hqlQuery +" and rawMaterials.companyModel.companyId = '"+searchRawMaterialTO.getCompanyTO().getCompanyId()+"'";
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
		rawMaterialsModels =  query.list();
		
		List<RawMaterialsTO> rawMaterialsTOs = new ArrayList<RawMaterialsTO>();
		for (RawMaterialsModel rawMaterialsModel : rawMaterialsModels) {
			RawMaterialsTO rawMaterialsTO = new RawMaterialsTO();
			rawMaterialsTO.convertModelToTO(rawMaterialsModel);
			rawMaterialsTOs.add(rawMaterialsTO);
		}
		
		return rawMaterialsTOs;

	}

	@Transactional
	public Integer rawMaterialsTotalRecordCount(CompanyTO companyTO) {   
		String hqlString = "select count(*) from RawMaterialsModel as rm rm.companyModel.companyId = '"+companyTO.getCompanyId()+"'";  
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  
	
	@Transactional
	public List<RawMaterialsTO> getByRawMaterialName(String rawMaterialName,String companyId) {
		String hql = "from RawMaterialsModel as rm where rawMaterialName='" + rawMaterialName+"'"+"and rm.companyModel.companyId = '"+companyId+"'";
		
		List<RawMaterialsModel> rawMaterialsModels = sessionFactory.getCurrentSession().createQuery(hql).list();
		List<RawMaterialsTO> rawMaterialsTOs = new ArrayList<RawMaterialsTO>();
		if (rawMaterialsModels != null && !rawMaterialsModels.isEmpty()) {
			for (RawMaterialsModel rawMaterialsModel : rawMaterialsModels) {
				RawMaterialsTO rawMaterialsTO = new RawMaterialsTO();
				rawMaterialsTO.convertModelToTO(rawMaterialsModel);
				rawMaterialsTOs.add(rawMaterialsTO);
			}
			return rawMaterialsTOs;
		}

		return null;
	}
	
	@Transactional
	public List<RawMaterialsTO> getByRawMaterialCode(String rawMaterialCode,String companyId) {
		String hql = "from RawMaterialsModel as rm where rawMaterialCode='" + rawMaterialCode+"'"+"and rm.companyModel.companyId = '"+companyId+"'";
		
		List<RawMaterialsModel> rawMaterialsModels = sessionFactory.getCurrentSession().createQuery(hql).list();
		List<RawMaterialsTO> rawMaterialsTOs = new ArrayList<RawMaterialsTO>();
		if (rawMaterialsModels != null && !rawMaterialsModels.isEmpty()) {
			for (RawMaterialsModel rawMaterialsModel : rawMaterialsModels) {
				RawMaterialsTO rawMaterialsTO = new RawMaterialsTO();
				rawMaterialsTO.convertModelToTO(rawMaterialsModel);
				rawMaterialsTOs.add(rawMaterialsTO);
			}
			return rawMaterialsTOs;
		}

		return null;
	}


}

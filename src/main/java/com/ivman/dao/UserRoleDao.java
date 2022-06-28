package com.ivman.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivman.model.UserRoleModel;
import com.ivman.to.UserRoleTO;

@Repository
public class UserRoleDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(UserRoleTO userRoleTO){
		if(userRoleTO!=null) {
			UserRoleModel userRoleModel = new UserRoleModel();
			userRoleTO.convertTOToModel(userRoleModel);
			sessionFactory.getCurrentSession().saveOrUpdate(userRoleModel);
		}
	}

	@Transactional
	public void saveAll(List<UserRoleTO> userRoleTOs){
		for (UserRoleTO userRoleTO : userRoleTOs) {
			UserRoleModel userRoleModel = new UserRoleModel();
			userRoleTO.convertTOToModel(userRoleModel);
			sessionFactory.getCurrentSession().saveOrUpdate(userRoleModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from UserRoleModel").executeUpdate();
	}

	@Transactional
	public List<UserRoleTO> getAll(){
		List<UserRoleModel> userRoleModels =sessionFactory.getCurrentSession().createQuery("from UserRoleModel").list();
		
		List<UserRoleTO> userRoleTOs = new ArrayList<UserRoleTO>();
		for (UserRoleModel roleModel : userRoleModels) {
			UserRoleTO userRoleTO = new UserRoleTO();
			userRoleTO.convertModelToTO(roleModel);
			userRoleTOs.add(userRoleTO);
		}
		return userRoleTOs;
	}

	@Transactional
	public UserRoleTO getByUserRoleId(Integer id) {
		String hql = "from UserRoleModel where userRoleId='" + id+"'";
		List<UserRoleModel> userRoleModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (userRoleModelList != null && !userRoleModelList.isEmpty()) {
			UserRoleTO userRoleTO = new UserRoleTO();
			userRoleTO.convertModelToTO(userRoleModelList.get(0));
			return userRoleTO;
		}

		return null;
	}

	@Transactional
	public boolean delete(Integer id) {
		UserRoleModel userRoleModel = new UserRoleModel();
		userRoleModel.setUserRoleId(id);
		try {
			sessionFactory.getCurrentSession().delete(userRoleModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<UserRoleTO> findPaginated(int pageNo, int pageSize, UserRoleTO searchUserRoleTO) {
		List<UserRoleModel> userRoleModels = null;
		String hqlQuery = "from UserRoleModel";
		if(searchUserRoleTO!=null) {
			boolean whereAdded = false;
			if(searchUserRoleTO.getUserRoleId()!=null) {
				hqlQuery = hqlQuery +" where userRoleId like '"+searchUserRoleTO.getUserRoleId()+"'";
				whereAdded = true;
			}
			if(searchUserRoleTO.getUserRoleDesc()!=null && !"".equals(searchUserRoleTO.getUserRoleDesc())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where userRoleDesc like '"+searchUserRoleTO.getUserRoleDesc()+"'";
				}else {
					hqlQuery = hqlQuery +" and userRoleDesc like '"+searchUserRoleTO.getUserRoleDesc()+"'";
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
		userRoleModels =  query.list();

		List<UserRoleTO> userRoleTOs = new ArrayList<UserRoleTO>();
		for (UserRoleModel roleModel : userRoleModels) {
			UserRoleTO userRoleTO = new UserRoleTO();
			userRoleTO.convertModelToTO(roleModel);
			userRoleTOs.add(userRoleTO);
		}
		return userRoleTOs;
	}

	@Transactional
	public Integer userRoleTotalRecordCount() {   
		String hqlString = "select count(*) from UserRoleModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  


}

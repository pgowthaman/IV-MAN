package com.ivman.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivman.model.ProductModel;
import com.ivman.to.ProductTO;

@Repository
public class ProductDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void save(ProductTO productTO){
		if(productTO!=null) {
			ProductModel productModel = new ProductModel();
			productTO.convertTOToModel(productModel);
			sessionFactory.getCurrentSession().saveOrUpdate(productModel);
		}
	}

	@Transactional
	public void saveAll(List<ProductTO> productTOs){
		for (ProductTO productTO : productTOs) {
			ProductModel productModel = new ProductModel();
			productTO.convertTOToModel(productModel);
			sessionFactory.getCurrentSession().save(productModel);
		}
	}

	@Transactional
	public void deleteAll(){
		sessionFactory.getCurrentSession().createQuery("delete from ProductModel").executeUpdate();
	}

	@Transactional
	public List<ProductTO> getAll(){
		List<ProductModel> productModels =sessionFactory.getCurrentSession().createQuery("from ProductModel").list();
		
		List<ProductTO> productTOs = new ArrayList<ProductTO>();
		for (ProductModel productModel : productModels) {
			ProductTO productTO = new ProductTO();
			productTO.convertModelToTO(productModel);
			productTOs.add(productTO);
		}
		
		return productTOs;
	}

	@Transactional
	public ProductTO getByProductId(Integer id) {
		String hql = "from ProductModel where productId='" + id+"'";
		List<ProductModel> productModelList = sessionFactory.getCurrentSession().createQuery(hql).list();

		if (productModelList != null && !productModelList.isEmpty()) {
			ProductTO productTO = new ProductTO();
			productTO.convertModelToTO(productModelList.get(0));
			return productTO;
		}

		return null;
	}

	@Transactional
	public boolean delete(Integer id) {
		ProductModel productModel = new ProductModel();
		productModel.setProductId(id);
		try {
			sessionFactory.getCurrentSession().delete(productModel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Transactional
	public List<ProductTO> findPaginated(int pageNo, int pageSize, ProductTO searchProductTO) {
		List<ProductModel> productModels = null;
		String hqlQuery = "from ProductModel as product";
		if(searchProductTO!=null) {
			boolean whereAdded = false;
			if(searchProductTO.getProductId()!=null) {
				hqlQuery = hqlQuery +" where productId like '"+searchProductTO.getProductId()+"'";
				whereAdded = true;
			}
			if(searchProductTO.getProductCode()!=null && !"".equals(searchProductTO.getProductCode())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where productCode like '"+searchProductTO.getProductCode()+"'";
				}else {
					hqlQuery = hqlQuery +" and productCode like '"+searchProductTO.getProductCode()+"'";
				}
				whereAdded = true;
			}
			if(searchProductTO.getProductName()!=null && !"".equals(searchProductTO.getProductName())) {
				if(!whereAdded) {
					hqlQuery = hqlQuery +" where productName like '"+searchProductTO.getProductName()+"'";
				}else {
					hqlQuery = hqlQuery +" and productName like '"+searchProductTO.getProductName()+"'";
				}
				whereAdded = true;
			}
			
			if(searchProductTO.getCompanyTO()!=null) {
				if(searchProductTO.getCompanyTO().getCompanyId()!=null) {
					if(!whereAdded) {
						hqlQuery = hqlQuery +" where product.companyModel.companyId = '"+searchProductTO.getCompanyTO().getCompanyId()+"'";
					}else {
						hqlQuery = hqlQuery +" and product.companyModel.companyId = '"+searchProductTO.getCompanyTO().getCompanyId()+"'";
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
		productModels =  query.list();

		List<ProductTO> productTOs = new ArrayList<ProductTO>();
		for (ProductModel productModel : productModels) {
			ProductTO productTO = new ProductTO();
			productTO.convertModelToTO(productModel);
			productTOs.add(productTO);
		}
		
		return productTOs;
		
	}

	@Transactional
	public Integer productTotalRecordCount() {   
		String hqlString = "select count(*) from ProductModel";   
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString);   
		Integer count = ((Number)query.uniqueResult()).intValue();    

		return count;   
	}  


}

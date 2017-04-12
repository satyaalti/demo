package com.ccp.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ccp.dao.PoolRqstDAO;
import com.ccp.model.PoolRqst;

@Repository
public class PoolRqstDAOImpl implements PoolRqstDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    private static final Logger logger = Logger.getLogger(PoolRqstDAOImpl.class);

	@Override
	public PoolRqst save(PoolRqst poolRqst) {
		Session session = this.sessionFactory.openSession();
       	try {
       		session.save(poolRqst);
       		return poolRqst;
       	}
   		catch(HibernateException hbe) {
   			logger.error("Error in DB - PoolRqstDAOImpl::save");
   			throw new ExceptionInInitializerError(hbe);
   		} 
   		finally {
   			if(session.isOpen()){
   				session.flush();
   				session.close();
   			}
   		}
	}
	
	@Override
	public Long checkRequestExists(String googleid, int tripid) {
		Session session = this.sessionFactory.openSession();
       	try {
       		Criteria criteria = session.createCriteria(PoolRqst.class);
       		criteria.add(Restrictions.eq("tripid", tripid));
       		criteria.add(Restrictions.eq("googleid", googleid));
			criteria.setProjection(Projections.rowCount());
			return (Long)criteria.uniqueResult();
       	}
   		catch(HibernateException hbe) {
   			logger.error("Error in DB - PoolRqstDAOImpl::checkRequestExists");
   			throw new ExceptionInInitializerError(hbe);
   		} 
   		finally {
   			if(session.isOpen()){
   				session.flush();
   				session.close();
   			}
   		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PoolRqst> getRequestedPoolList(String googleid) {
		Session session = this.sessionFactory.openSession();
       	try {
       		Criteria criteria = session.createCriteria(PoolRqst.class);
       		criteria.add(Restrictions.eq("googleid", googleid));
			return criteria.list();
       	}
   		catch(HibernateException hbe) {
   			logger.error("Error in DB - PoolRqstDAOImpl::checkRequestExists");
   			throw new ExceptionInInitializerError(hbe);
   		} 
   		finally {
   			if(session.isOpen()){
   				session.flush();
   				session.close();
   			}
   		}
	}
}

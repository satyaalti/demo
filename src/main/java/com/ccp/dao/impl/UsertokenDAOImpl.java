package com.ccp.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ccp.dao.UsertokenDAO;
import com.ccp.model.Usertoken;

@Repository
public class UsertokenDAOImpl implements UsertokenDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    private static final Logger logger = Logger.getLogger(UsertokenDAOImpl.class);
    
    public Usertoken isValidToken(int userid, String token)
    {
    	Session session = this.sessionFactory.openSession();
       	try {
       		Criteria criteria = session.createCriteria(Usertoken.class);
       		criteria.add(Restrictions.eq("userid", userid));
       		criteria.add(Restrictions.eq("token", token));
       		
			@SuppressWarnings("unchecked")
			List<Usertoken> usertokens = criteria.list();
			if(usertokens.size() > 0) {
				return usertokens.get(0);
			}
			return null;
       	}
   		catch(HibernateException hbe) {
   			logger.error("Error in DB - UsertokenDAOImpl::checkAccountExists");
   			throw new ExceptionInInitializerError(hbe);
   		} 
   		finally {
   			if(session.isOpen()){
   				session.flush();
   				session.close();
   			}
   		}
    }
    
    public void deleteToken(int userid, String token) 
	{
		Session session = this.sessionFactory.openSession();
		try {
			SQLQuery query = session.createSQLQuery("delete from  usertoken where userid = :userid and token = :token");
			query.addEntity(Usertoken.class);
			query.setParameter("userid", userid);
			query.setParameter("token", token);
			query.executeUpdate();
		}
		catch(HibernateException hbe) {
			logger.error("Something went wrong in UsertokenDAOImpl::deleteToken");
			throw new ExceptionInInitializerError(hbe);
		} 
		finally {
			if(session.isOpen()){
				session.flush();
				session.close();
			}
		}
	}
    
    public Usertoken save(Usertoken usertoken) 
    {
    	Session session = this.sessionFactory.openSession();
    	try {
	   		session.save(usertoken);
	   		return usertoken;
    	}
   		catch(HibernateException hbe) {
   			logger.error("Error in DB - UsertokenDAOImpl::save");
   			throw new ExceptionInInitializerError(hbe);
   		} 
   		finally {
   			if(session.isOpen()){
   				session.flush();
   				session.close();
   			}
   		}
    }
    
    public void deleteTokens(int userid) 
	{
		Session session = this.sessionFactory.openSession();
		try {
			SQLQuery query = session.createSQLQuery("delete from  usertoken where userid = :userid");
			query.addEntity(Usertoken.class);
			query.setParameter("userid", userid);
			query.executeUpdate();
		}
		catch(HibernateException hbe) {
			logger.error("Something went wrong in UsertokenDAOImpl::deleteTokens");
			throw new ExceptionInInitializerError(hbe);
		} 
		finally {
			if(session.isOpen()){
				session.flush();
				session.close();
			}
		}
	}
    
    public Usertoken getUsertoken(int userid) {
    	Session session = this.sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(Usertoken.class);
       		criteria.add(Restrictions.eq("userid", userid));
       		
       		@SuppressWarnings("unchecked")
			List<Usertoken> usertokens = criteria.list();
       		return usertokens.get(0);
		}
		catch(HibernateException hbe) {
			logger.error("Something went wrong in UsertokenDAOImpl::getUsertoken");
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

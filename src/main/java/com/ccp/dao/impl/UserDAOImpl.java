package com.ccp.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ccp.dao.UserDAO;
import com.ccp.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
    
    public User checkAccountExists(String googleid)
    {
    	Session session = this.sessionFactory.openSession();
       	try {
       		Criteria criteria = session.createCriteria(User.class)
       							.add(Restrictions.eq("googleid", googleid));
			@SuppressWarnings("unchecked")
			List<User> users = criteria.list();
			if(users.size() > 0) {
				return users.get(0);
			}
			return null;
       	}
   		catch(HibernateException hbe) {
   			logger.error("Error in DB - User::checkAccountExists");
   			throw new ExceptionInInitializerError(hbe);
   		} 
   		finally {
   			if(session.isOpen()){
   				session.flush();
   				session.close();
   			}
   		}
    }
    
    public User save(User user) 
    {
    	Session session = this.sessionFactory.openSession();
       	try {
       		session.save(user);
       		return user;
       	}
   		catch(HibernateException hbe) {
   			logger.error("Error in DB - User::save");
   			throw new ExceptionInInitializerError(hbe);
   		} 
   		finally {
   			if(session.isOpen()){
   				session.flush();
   				session.close();
   			}
   		}
    }

	public User update(User user) 
	{
		Session session = this.sessionFactory.openSession();
       	try {
       		session.update(user);
			return user;
       	}
   		catch(HibernateException hbe) {
   			logger.error("Error in DB - User::updateProfile");
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

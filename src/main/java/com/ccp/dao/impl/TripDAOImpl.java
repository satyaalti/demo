package com.ccp.dao.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ccp.controller.ConstantParams;
import com.ccp.dao.TripDAO;
import com.ccp.model.Trip;

@Repository
public class TripDAOImpl implements TripDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    private static final Logger logger = Logger.getLogger(TripDAOImpl.class);

	@Override
	public Trip save(Trip trip) {
		Session session = this.sessionFactory.openSession();
       	try {
       		session.save(trip);
       		return trip;
       	}
   		catch(HibernateException hbe) {
   			logger.error("Error in DB - TripDAOImpl::save");
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
	public List<Trip> tripList(String googleid) {
		Session session = this.sessionFactory.openSession();
       	try {
       		Criteria criteria = session.createCriteria(Trip.class);
       		criteria.add(Restrictions.eq("googleid", googleid));
       		criteria.add(Restrictions.sqlRestriction("{alias}.datetime >= NOW()"));
       		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
       		return criteria.list();
       	}
   		catch(HibernateException hbe) {
   			logger.error("Error in DB - TripDAOImpl::tripList");
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
	public Trip checkTripExists(int tripid) 
	{
		Session session = this.sessionFactory.openSession();
       	try {
       		return (Trip) session.get(Trip.class, tripid);
       	}
   		catch(HibernateException hbe) {
   			logger.error("Error in DB - TripDAOImpl::tripList");
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
	@SuppressWarnings("unchecked")
	public List<Trip> filterTrips(String googleid, Date datetime) throws ParseException {
		
		Session session = this.sessionFactory.openSession();
		
		String dateStr = ConstantParams.getDateAsString(datetime, ConstantParams.dateTimeInputFormat, ConstantParams.dbDateTimeFormat);
		
       	try {
       		String sqlWhere = " ({alias}.datetime between "
							+ " DATE_SUB('"+dateStr+"', INTERVAL 3 HOUR) "
							+ " AND DATE_ADD('"+dateStr+"', INTERVAL 3 HOUR)) ";
			
       		Criteria criteria = session.createCriteria(Trip.class);
			criteria.add(Restrictions.eq("status", 0));
			criteria.add(Restrictions.ne("googleid", googleid));
			criteria.add(Restrictions.sqlRestriction(sqlWhere));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return criteria.list();
       	}
   		catch(HibernateException hbe) {
   			logger.error("Error in DB - TripDAOImpl::tripList");
   			throw new ExceptionInInitializerError(hbe);
   		} 
   		finally {
   			if(session.isOpen()){
   				session.flush();
   				session.close();
   			}
   		}
	}
	
	//example join query with respective table restrictions
		
   	/*	Criteria criteria = session.createCriteria(Trip.class);
		criteria.add(Restrictions.not(Restrictions.eq("googleid", googleid)));
		criteria.add(Restrictions.sqlRestriction("{alias}.status = 0"));
		
		Criteria userCriteria = criteria.createCriteria("user", JoinType.LEFT_OUTER_JOIN);
		userCriteria.add(Restrictions.eq("username", "%satya%"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();*/
    
}

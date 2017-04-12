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

import com.ccp.dao.VehicleDAO;
import com.ccp.model.Vehicle;

@Repository
public class VehicleDAOImpl implements VehicleDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    private static final Logger logger = Logger.getLogger(VehicleDAOImpl.class);
    
    public Vehicle save(Vehicle vehicle) 
    {
    	Session session = this.sessionFactory.openSession();
       	try {
       		Criteria criteria = session.createCriteria(Vehicle.class);
       		criteria.add(Restrictions.eq("googleid", vehicle.getGoogleid()));
       		@SuppressWarnings("unchecked")
			List<Vehicle> vehicles = criteria.list();
       		if(vehicles.size() > 0) {
       			Vehicle veNew = vehicles.get(0);
       			veNew.setCompany(vehicle.getCompany());
       			veNew.setMfgYear(vehicle.getMfgYear());
       			veNew.setModel(vehicle.getModel());
       			veNew.setRegNo(vehicle.getRegNo());
       			veNew.setSeats(vehicle.getSeats());
       			session.update(veNew);
       			vehicle.setId(veNew.getId());
       		}
       		else {
       			session.save(vehicle);
       		}
       		return vehicle;
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
}

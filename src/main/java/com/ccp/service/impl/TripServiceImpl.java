package com.ccp.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ccp.dao.TripDAO;
import com.ccp.model.Trip;
import com.ccp.service.TripService;

public class TripServiceImpl implements TripService {
	
	private TripDAO tripDAO;
	
	public void setTripDAO(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }
	
	@Override
	@Transactional
	public Trip save(Trip trip) {
		return this.tripDAO.save(trip);
	}
	
	@Override
	@Transactional
	public List<Trip> tripList(String googleid) {
		return this.tripDAO.tripList(googleid);
	}
	

	@Override
	@Transactional
	public Trip checkTripExists(int tripid) {
		return this.tripDAO.checkTripExists(tripid);
	}
	
	@Override
	@Transactional
	public List<Trip> filterTrips(String googleid, Date datetime)  throws ParseException  {
		return this.tripDAO.filterTrips(googleid, datetime);
	}
}

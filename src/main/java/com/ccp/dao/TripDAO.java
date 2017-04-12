package com.ccp.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.ccp.model.Trip;

public interface TripDAO {
	
	public Trip save(Trip trip);
	public List<Trip> tripList(String googleid);
	public Trip checkTripExists(int tripid);
	public List<Trip> filterTrips(String googleid, Date datetime) throws ParseException ;
	
}

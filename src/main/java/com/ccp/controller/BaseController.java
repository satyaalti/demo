package com.ccp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.ServletContextAware;

import com.ccp.model.PoolRqst;
import com.ccp.model.Trip;
import com.ccp.model.User;
import com.ccp.model.Usertoken;
import com.ccp.model.Vehicle;
import com.ccp.service.PoolRqstService;
import com.ccp.service.TripService;
import com.ccp.service.UserService;
import com.ccp.service.UsertokenService;
import com.ccp.service.VehicleService;

public class BaseController implements ServletContextAware {
	
	private static final Logger logger = Logger.getLogger(BaseController.class);
	
	protected ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}
	
	protected UserService userService;
	protected UsertokenService usertokenService;
	protected VehicleService vehicleService;
	protected TripService tripService;
	protected PoolRqstService poolRqstService;
	
	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService a) {
		this.userService = a;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "usertokenService")
	public void setUsertokenService(UsertokenService a) {
		this.usertokenService = a;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "vehicleService")
	public void setVehicleService(VehicleService a) {
		this.vehicleService = a;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "tripService")
	public void setTripService(TripService a) {
		this.tripService = a;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "poolRqstService")
	public void setPoolRqstService(PoolRqstService a) {
		this.poolRqstService = a;
	}
	
	public User validateToken(HttpServletRequest request) throws ParseException {
		
		String token = request.getHeader("token");
 		String decToken = CipherUtils.decrypt(token);
 		
 		if(decToken == null) {
 			logger.error("BaseController::validateToken: Unable to decrypt the token");
 			return null;
 		}
 		
 		String[] tokenArr = decToken.split("\\"+ConstantParams.tokensep);
 		
 		try {
	 		String googleid = tokenArr[0];
	 		String clienttype = tokenArr[1];
	 		String timeStamp = tokenArr[2];
	 		
	 		if(googleid == null || clienttype == null || timeStamp == null) {
	 			logger.error("BaseController::validateToken: Required params are empty");
	 			return null;
	 		}
	 		
	 		User user = this.userService.checkAccountExists(googleid);
	 		Usertoken usertoken = this.usertokenService.isValidToken(user.getId(), token);
	 		if(usertoken == null) {
	 			logger.error("BaseController::validateToken: Token doesn't exist in DB.");
	 			return null;
	 		}
	 		Date lastaccesstime = usertoken.getLastaccesstime();
	 	    DateFormat df = new SimpleDateFormat(ConstantParams.dbDateTimeFormat); 
	 	    
	 	    try {
	 	        String currentdateStr = df.format(new Date());
	 	        Date currentdate = df.parse(currentdateStr);
	 	        Long timeDiff = ConstantParams.getTimeDiff(currentdate, lastaccesstime);
	 	        logger.error("currentdateStr:"+currentdateStr+"----startDate:"+timeStamp+"--------timediff:"+timeDiff+"-----maxexpireTime:"+ConstantParams.tokenExipireTimeInMin);
	 	        if(timeDiff > ConstantParams.tokenExipireTimeInMin) {
		 	        this.usertokenService.deleteToken(user.getId(), token);
	 	        	logger.error("BaseController::validateToken: Token Expired.");
	 	        	return null;
	 	        }
	 	    } catch (ParseException e) {
	 	        e.printStackTrace();
	 	    }
	 					
			return user;
 		}
 		catch(ArrayIndexOutOfBoundsException aiobe) {
 			logger.error("BaseController::validateToken: Token params Count doesn't match");
 			return null;
 		}
	}
	
	public String getDateAsString(String dDate, String fromFormat, String toFormat) throws ParseException
	{
		DateFormat toDateFormat = new SimpleDateFormat(toFormat);
		SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromFormat);
		try {
			Date parsedDate = fromDateFormat.parse(dDate);
			return toDateFormat.format(parsedDate);
		} 
		catch(ParseException e) {
			return dDate;
		}
	}
	
	public Date getDateAsObject(Date dDate, String fromFormat, String toFormat) throws ParseException
	{
		DateFormat toDateFormat = new SimpleDateFormat(toFormat);
		SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromFormat);
		
		String dobstr = toDateFormat.format(dDate);
		
		try {
			Date parsedDate = fromDateFormat.parse(dobstr);
			String toFormatString = toDateFormat.format(parsedDate);
			return toDateFormat.parse(toFormatString);
			
		} 
		catch(ParseException e) {
			return dDate;
		}
	}
	
	public boolean requiredParamsForLogin(User user) {
			
		try {
			if(!user.getGoogleid().isEmpty() && !user.getUserclienttype().isEmpty()) {
				 return true;
			}
		}
		catch(NullPointerException n) {
			return false;
		}
		
		return false;
	}
	
	public boolean requiredParamsForUpdateProfile(User user) {
		
		try {
			if(!user.getEmpid().isEmpty() && user.getGender() != 0 && !user.getMobileNumber().isEmpty()
			   && !user.getUseremail().isEmpty() && !user.getUsername().isEmpty() 
			   && user.getDob() != null && !user.getUserimage().isEmpty()) {
			   return true;
			}
		}
		catch(NullPointerException n) {
			return false;
		}
		
		return false;
	}
	
	public boolean requiredParamsForUpdatevehicle(Vehicle vehicle) {
		
		try {
			if(!vehicle.getCompany().isEmpty() && !vehicle.getModel().isEmpty() && 
				vehicle.getSeats() != 0 && !vehicle.getMfgYear().isEmpty() &&
				!vehicle.getRegNo().isEmpty()) {
				 return true;
			}
		}
		catch(NullPointerException n) {
			return false;
		}
		
		return false;
	}
	
	public boolean requiredParamsForTrip(Trip trip) {
		
		try {
			if(trip.getDatetime() != null && !trip.getSource().isEmpty() && !trip.getDestination().isEmpty() &&
				 trip.getPolyline() != null && trip.getSourcelat() != 0 && trip.getSourcelng() != 0 
				 && trip.getDestinationlat() != 0 && trip.getDestinationlng() != 0) {
				 return true;
			}
		}
		catch(NullPointerException n) {
			return false;
		}
		
		return false;
	}
	
	public boolean requiredParamsForPoolRequest(PoolRqst poolRqst) {
		try {
			if(!poolRqst.getDestinationaddress().isEmpty() && poolRqst.getDestinationlat() != 0 && 
					poolRqst.getDestinationlng() != 0 && poolRqst.getSourcelat() != 0 && 
					poolRqst.getSourcelng() != 0 && !poolRqst.getSourceaddress().isEmpty() && 
					poolRqst.getTripid() != 0) {
				 return true;
			}
		}
		catch(NullPointerException n) {
			return false;
		}
		
		return false;
	}
	
	public boolean requiredParamsForFilterTrips(Trip trip) {
		try {
			if(trip.getDestinationlat() != 0 && trip.getDestinationlng() != 0 && trip.getSourcelat() != 0 && 
					trip.getSourcelng() != 0 && trip.getDatetime() != null) {
				 return true;
			}
		}
		catch(NullPointerException n) {
			return false;
		}
		
		return false;
	}
	
	public boolean requiredParamsForGetDirections(Trip trip) {
		try {
			if(!trip.getSource().isEmpty() && !trip.getDestination().isEmpty()) {
				 return true;
			}
		}
		catch(NullPointerException n) {
			return false;
		}
		
		return false;
	}
}

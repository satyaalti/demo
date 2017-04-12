package com.ccp.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;

import com.ccp.common.DistanceCalculator;
import com.ccp.json.JsonResponse;
import com.ccp.json.PoolRqstExclusionStrategy;
import com.ccp.json.TripExclusionStrategy;
import com.ccp.model.PoolRqst;
import com.ccp.model.Trip;
import com.ccp.model.User;
import com.ccp.model.Usertoken;
import com.ccp.pushnotification.FCMServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class ManageCarPoolController extends BaseController {
	
	@RequestMapping(value="/create/trip", method= {RequestMethod.POST}, consumes = { "application/json;charset=utf-8"},  produces = { "application/json;harset=utf-8" } )
	public String saveTrip(@RequestBody Trip tripreq, BindingResult result, SessionStatus status,
			HttpServletRequest request) throws ParseException, JsonProcessingException {
		
		//common code which needs to be included in each request to validate the token
		User user = this.validateToken(request);
		if(user == null) {
			return JsonResponse.getInstance().getAuthErrorMessage();
		}
		
		if(!this.requiredParamsForTrip(tripreq)) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
		
		if(user.getVehicle() == null) {
			return JsonResponse.getInstance().getNeedVehicleInfoMessage();
		}
		tripreq.setGoogleid(user.getGoogleid());
		this.tripService.save(tripreq);
		
		return JsonResponse.getInstance().getTripSaveMessage();
	}
	
	@RequestMapping(value="/pool/request", method= {RequestMethod.POST}, consumes = { "application/json;charset=utf-8"},  produces = { "application/json;harset=utf-8" } )
	public String TripRequest(@RequestBody PoolRqst poolrqstreq, BindingResult result, SessionStatus status,
			HttpServletRequest request) throws Exception {
		
		//common code which needs to be included in each request to validate the token
		User user = this.validateToken(request);
		if(user == null) {
			return JsonResponse.getInstance().getAuthErrorMessage();
		}
		
		if(!this.requiredParamsForPoolRequest(poolrqstreq)) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
		
		Trip selectedTrip = this.tripService.checkTripExists(poolrqstreq.getTripid());
		if(selectedTrip == null) {
			return JsonResponse.getInstance().getTripNotExistsMessage();
		}
		
		if(selectedTrip.equals(user.getGoogleid())) {
			return JsonResponse.getInstance().getTripOwnerCannotRequestMessage();
		}
		
		if(this.poolRqstService.checkRequestExists(user.getGoogleid(), poolrqstreq.getTripid()) > 0) {
			return JsonResponse.getInstance().getRequestExistsMessage();
		}
		
		poolrqstreq.setGoogleid(user.getGoogleid());
		this.poolRqstService.save(poolrqstreq);
		
		Usertoken usertoken = this.usertokenService.getUsertoken(user.getId());
		FCMServer.getInstance().buildMessage(selectedTrip.getUser().getUsername(), selectedTrip.getDatetime(), selectedTrip.getSource());
		FCMServer.getInstance().pushNotification(usertoken.getDeviceId());
		
		return JsonResponse.getInstance().getPoolRequestsentMessage();
	}
	
	@RequestMapping(value="/trip/list", method= {RequestMethod.GET},  produces = { "application/json;harset=utf-8" } )
	public String UserTrips(HttpServletRequest request) throws ParseException, JsonProcessingException {
		
		//common code which needs to be included in each request to validate the token
		User user = this.validateToken(request);
		if(user == null) {
			return JsonResponse.getInstance().getAuthErrorMessage();
		}
		
		List<Trip> triplist = this.tripService.tripList(user.getGoogleid());
		
		Gson gson = new GsonBuilder()
		.setDateFormat(ConstantParams.dateTimeInputFormat)
	    .setExclusionStrategies(new PoolRqstExclusionStrategy())
	    .create();
		
		return JsonResponse.getInstance().getSuccessMessage(gson.toJson(triplist));
	}
	
	@RequestMapping(value="/filter/trips", method= {RequestMethod.POST}, consumes = { "application/json;charset=utf-8"},  produces = { "application/json;harset=utf-8" } )
	public String filtertrips(@RequestBody Trip tripreq, BindingResult result, SessionStatus status,
			HttpServletRequest request) throws ParseException, JsonProcessingException {
		
		//common code which needs to be included in each request to validate the token
		User user = this.validateToken(request);
		if(user == null) {
			return JsonResponse.getInstance().getAuthErrorMessage();
		}
		
		if(!this.requiredParamsForFilterTrips(tripreq)) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
		
		List<Trip> triplist = this.tripService.filterTrips(user.getGoogleid(), tripreq.getDatetime());
		List<Trip> triplistNew = new ArrayList<Trip>();
		for(Trip trip : triplist) {
			
			double distance = DistanceCalculator.distance(trip.getSourcelat(), trip.getSourcelng(), tripreq.getSourcelat(), tripreq.getSourcelng(), "K");
			if(distance <= 50) {
				triplistNew.add(trip);
			}
			System.out.println("distance:"+ distance);
		}

		Gson gson = new GsonBuilder()
		.setDateFormat(ConstantParams.dateTimeInputFormat)
	    .setExclusionStrategies(new PoolRqstExclusionStrategy())
	    .create();
		
		return JsonResponse.getInstance().getSuccessMessage(gson.toJson(triplistNew));
	}
	
	@RequestMapping(value="/getrequested/poollist", method= {RequestMethod.GET}, produces = { "application/json;harset=utf-8" } )
	public String getRequestedPoolList(HttpServletRequest request) throws ParseException, JsonProcessingException {
		
		//common code which needs to be included in each request to validate the token
		User user = this.validateToken(request);
		if(user == null) {
			return JsonResponse.getInstance().getAuthErrorMessage();
		}
		
		List<PoolRqst> poolRqst = this.poolRqstService.getRequestedPoolList(user.getGoogleid());
		
		Gson gson = new GsonBuilder()
		.setDateFormat(ConstantParams.dateTimeInputFormat)
	    .setExclusionStrategies(new TripExclusionStrategy())
	    .create();
		
		return JsonResponse.getInstance().getSuccessMessage(gson.toJson(poolRqst));
	}
	
	@RequestMapping(value="/get/directions", method= {RequestMethod.POST}, consumes = { "application/json;charset=utf-8"},  produces = { "application/json;harset=utf-8" } )
	public String getDirections(@RequestBody Trip tripreq, BindingResult result, SessionStatus status,
			HttpServletRequest request) throws ParseException, JsonProcessingException {
		
		//common code which needs to be included in each request to validate the token
		User user = this.validateToken(request);
		if(user == null) {
			return JsonResponse.getInstance().getAuthErrorMessage();
		}
		
		if(!this.requiredParamsForGetDirections(tripreq)) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
		
		String googleMapApiUrl = ConstantParams.googleMapsApiUrl+"?origin="+tripreq.getSource()
								+"&destination="+tripreq.getDestination()
								+"&alternatives=true"
								+"&key="+ConstantParams.googleMapsApiKey
								;
		
	    RestTemplate restTemplate = new RestTemplate();
	     
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	     
	    ResponseEntity<String> dirresult = restTemplate.exchange(googleMapApiUrl, HttpMethod.GET, entity, String.class);
		
		return JsonResponse.getInstance().getSuccessMessage(dirresult.getBody());
	}
}
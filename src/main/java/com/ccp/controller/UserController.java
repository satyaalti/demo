package com.ccp.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import com.ccp.json.JsonResponse;
import com.ccp.model.User;
import com.ccp.model.Vehicle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping(value="/user")
public class UserController extends BaseController {
	
	@RequestMapping(value="/updatevehicle", method= {RequestMethod.POST}, consumes = { "application/json;charset=utf-8"},  produces = { "application/json;harset=utf-8" } )
	public String updateVehicle(@RequestBody Vehicle vehicle, BindingResult result, SessionStatus status,
			HttpServletRequest request) throws ParseException {
		
		User user = this.validateToken(request);
		if(user == null) {
			return JsonResponse.getInstance().getAuthErrorMessage();
		}
		
		if(!this.requiredParamsForUpdatevehicle(vehicle)) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
		
		vehicle.setGoogleid(user.getGoogleid());
		vehicle = this.vehicleService.save(vehicle);
		
		Gson gson = new GsonBuilder().create();
		return JsonResponse.getInstance().getSuccessMessage(gson.toJson(vehicle));
	}
	
	@RequestMapping(value="/updateprofile", method= {RequestMethod.POST}, consumes = { "application/json;charset=utf-8"},  produces = { "application/json;harset=utf-8" } )
	public String updateProfile(@RequestBody User userfromreq, BindingResult result, SessionStatus status,
			HttpServletRequest request) throws ParseException {
		
		User user = this.validateToken(request);
		if(user == null) {
			return JsonResponse.getInstance().getAuthErrorMessage();
		}
		
		if(!this.requiredParamsForUpdateProfile(userfromreq)) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
		
		user.setDob(userfromreq.getDob());
		user.setEmpid(userfromreq.getEmpid());
		user.setGender(userfromreq.getGender());
		user.setMobileNumber(userfromreq.getMobileNumber());
		user.setUseremail(userfromreq.getUseremail());
		user.setUserimage(userfromreq.getUserimage());
		user.setUsername(userfromreq.getUsername());
		
		user = this.userService.update(user);
		
		Gson gson = new GsonBuilder().setDateFormat(ConstantParams.dateInputFormat).create();
		return JsonResponse.getInstance().getSuccessMessage(gson.toJson(user));
	}
}

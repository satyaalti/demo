package com.ccp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import com.ccp.json.JsonResponse;
import com.ccp.model.User;
import com.ccp.model.Usertoken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class AuthenticationController extends BaseController {
	
	@RequestMapping(value="/loginerror", method= {RequestMethod.GET},  produces = { "application/json;harset=utf-8" } )
	public String loginError() {
		return JsonResponse.getInstance().getAuthErrorMessage();
	}
	
	@RequestMapping(value="/login", method= {RequestMethod.POST}, consumes = { "application/json;charset=utf-8"},  produces = { "application/json;harset=utf-8" } )
	public String login(@RequestBody User userfromreq, BindingResult result, SessionStatus status,
			HttpServletRequest request) throws ParseException {
		
		try {
			if(request.getHeader("deviceId").isEmpty()) {
				return JsonResponse.getInstance().getInsufficientMessage();
			}
		}
		catch(NullPointerException ne) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
			
		if(!this.requiredParamsForLogin(userfromreq)) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
		User user = this.userService.checkAccountExists(userfromreq.getGoogleid());
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(ConstantParams.dbDateTimeFormat);
		String formattedDate = sdf.format(date);
		String authtoken = userfromreq.getGoogleid() + ConstantParams.tokensep + userfromreq.getUserclienttype() + ConstantParams.tokensep + formattedDate;
		String encryptedauthtoken = CipherUtils.encrypt(authtoken);
		
		if(user == null) {
			user = this.userService.save(userfromreq);
		}
		this.usertokenService.deleteTokens(user.getId());
		
		Usertoken usertoken = new Usertoken();
		
		usertoken.setUserid(user.getId());
		usertoken.setToken(encryptedauthtoken);
		usertoken.setLastaccesstime(sdf.parse(formattedDate));
		usertoken.setDeviceId(request.getHeader("deviceId"));
		this.usertokenService.save(usertoken);
		
		Gson gson = new GsonBuilder().setDateFormat(ConstantParams.dateInputFormat).create();
		return JsonResponse.getInstance().getLoginSuccessMessage(gson.toJson(user), encryptedauthtoken);
	}
	
	@RequestMapping(value="/logout", method= {RequestMethod.POST}, consumes = { "application/json;charset=utf-8"},  produces = { "application/json;harset=utf-8" } )
	public String logout(@RequestBody User userfromreq, BindingResult result, SessionStatus status,
			HttpServletRequest request) {
		
		try {
			if(!userfromreq.getGoogleid().isEmpty()) {
				User user = this.userService.checkAccountExists(userfromreq.getGoogleid());
				if(user != null) {
					this.usertokenService.deleteTokens(user.getId());
				}
			}
			else {
				return JsonResponse.getInstance().getInsufficientMessage();
			}
		}
		catch(NullPointerException nl) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
		return JsonResponse.getInstance().getLogoutMessage();
	}
}

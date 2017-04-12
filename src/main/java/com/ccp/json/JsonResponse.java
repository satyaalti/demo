package com.ccp.json;

public class JsonResponse {
	
	private static JsonResponse jsonResponse = new JsonResponse( );
	
	private static final String INSUFFICIENT_PARAMETERS = "Insufficient parameters in the request.";
	private static final String LOGUT_MSG = "Logged out successfully";
	private static final String AUTH_ERR = "Invalid Credentials";
	private static final String TRIP_SAVED = "Trip saved successfully";
	private static final String TRIP_EXISTS = "You've already requested for Car-pool for this trip.";
	private static final String POOL_RQST_SENT = "Car-pool request sent successfully.";
	private static final String TRIP_NOT_EXISTS = "Trip not exists";
	private static final String TRIP_OWNER_CANNOT_REUQSET = "As you are the Owner of this trip, you can't request for car-pool for your own trip.";
	private static final String NEED_VEHICLE_INFO_MSG = "Vehicle information is required to creating a trip.";
	private static final String UPDATE_YOUR_PROFILE = "Before requesting for a pool please update your profile";
	
	private String type;
	
	private String message;
	
	private String token;
	
	private int statusCode;
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/* A private Constructor prevents any other 
	* class from instantiating.
	*/
	private JsonResponse(){ }

	/* Static 'instance' method */
	public static JsonResponse getInstance( ) {
	  return jsonResponse;
	}
	
	public String buildMessage() {
		
		String response = "{"
						 + "\"status\": {"
						 				+ "\"statusCode\": \""+this.statusCode+"\", "
						 				+  "\"statusMessage\": \""+this.type+"\" "
						 			 + "}, ";
		
		if(this.getToken() != null)
			response += " \"token\":\""+this.getToken()+"\", ";
		
		response +=	 "\"result\":"+this.message+" "
				+ "}";
		
		return response;
	}
	
	public String getInsufficientMessage() {
		this.type = "false";
		this.message = "{\"message\": \""+INSUFFICIENT_PARAMETERS+"\"}";
		this.token = null;
		this.statusCode = 101;
		return this.buildMessage();
	}
	
	public String getAuthErrorMessage() {
		this.type = "false";
		this.message = "{\"message\": \""+AUTH_ERR+"\"}";
		this.token = null;
		this.statusCode = 102;
		return this.buildMessage();
	}
	
	public String getTripNotExistsMessage() {
		this.type = "false";
		this.message = "{\"message\": \""+TRIP_NOT_EXISTS+"\"}";
		this.token = null;
		this.statusCode = 103;
		return this.buildMessage();
	}
	
	public String getTripOwnerCannotRequestMessage() {
		this.type = "false";
		this.message = "{\"message\": \""+TRIP_OWNER_CANNOT_REUQSET+"\"}";
		this.token = null;
		this.statusCode = 104;
		return this.buildMessage();
	}
	
	public String getNeedVehicleInfoMessage() {
		this.type = "false";
		this.message = "{\"message\": \""+NEED_VEHICLE_INFO_MSG+"\"}";
		this.token = null;
		this.statusCode = 105;
		return this.buildMessage();
	}
	
	public String getMsgUpdatingYourProfile() {
		this.type = "false";
		this.message = "{\"message\": \""+UPDATE_YOUR_PROFILE+"\"}";
		this.token = null;
		this.statusCode = 106;
		return this.buildMessage();
	}
	
	public String getSuccessMessage(String response) {
		this.type = "true";
		this.message = response;
		this.token = null;
		this.statusCode = 200;
		return this.buildMessage();
	}
	
	public String getLogoutMessage() {
		this.type = "true";
		this.message = "{\"message\": \""+LOGUT_MSG+"\"}";
		this.token = null;
		this.statusCode = 200;
		return this.buildMessage();
	}
	
	public String getLoginSuccessMessage(String response, String token) {
		this.type = "true";
		this.message = response;
		this.token = token;
		this.statusCode = 200;
		return this.buildMessage();
	}
	
	public String getTripSaveMessage() {
		this.type = "true";
		this.message = "{\"message\": \""+TRIP_SAVED+"\"}";;
		this.token = null;
		this.statusCode = 200;
		return this.buildMessage();
	}
	
	public String getRequestExistsMessage() {
		this.type = "true";
		this.message = "{\"message\": \""+TRIP_EXISTS+"\"}";;
		this.token = null;
		this.statusCode = 200;
		return this.buildMessage();
	}
	
	public String getPoolRequestsentMessage() {
		this.type = "true";
		this.message = "{\"message\": \""+POOL_RQST_SENT+"\"}";;
		this.token = null;
		this.statusCode = 200;
		return this.buildMessage();
	}
}

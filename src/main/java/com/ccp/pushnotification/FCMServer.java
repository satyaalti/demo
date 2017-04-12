package com.ccp.pushnotification;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;

import com.google.gson.JsonObject;

public class FCMServer
{
	private String url = "https://fcm.googleapis.com/fcm/send";    
	private String API_KEY = "AIzaSyByCTvA4oGLNUU-kwAa2pl-ijF7gQlyvHc";  
	
	private static FCMServer fcmServer = new FCMServer( );
	
	/* A private Constructor prevents any other 
	* class from instantiating.
	*/
	private FCMServer(){ }

	/* Static 'instance' method */
	public static FCMServer getInstance( ) {
	  return fcmServer;
	}
	
	private String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String message;
	
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void pushNotification(String token) {
		JsonObject json  = new JsonObject(); 
		JsonObject notification = new JsonObject();
		//JSONArray jsonArray = new JSONArray();    
		//jsonArray.put(token );   
		 
		// for mutliple tokens
		//json.put("registration_ids",jsonArray);   
		 
		// for single token
		json.addProperty("to", token);       
		 
		// populate message
		notification.addProperty("body", this.getMessage());
		notification.addProperty("title", this.getTitle());
		json.add("notification", notification);
		
		System.out.println(json.toString());
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try{
		 
			//Create POST request
			HttpPost request = new HttpPost(url);     
			request.setHeader("Content-type", MediaType.APPLICATION_JSON.toString()); 
			request.addHeader("Authorization", "Key="+API_KEY);  
			
			StringEntity params = new StringEntity(json.toString());        
			request.setEntity(params);      
			 
			// get response from server
			HttpResponse response = httpClient.execute(request);       
			String mresult = EntityUtils.toString(response.getEntity());        
			 
			System.out.println("result:" + mresult); 
		}
		catch (Exception ex) {     
			ex.printStackTrace();  
		} 
		finally
		{     
		   if(httpClient != null)     
		   {         
			   try {      
		          httpClient.close();    
		       } 
			   catch (IOException e) { 
		               e.printStackTrace();       
			   }       
		   }  
		}
	}
	
	public void buildMessage(String username, Date datetime, String source) {
		
		String body = username +" sent car-pool request for trip scheduled on " + 
					  datetime +" starting from "+ source; 
		this.setTitle("Car pool request sent by "+ username);
		this.setMessage(body);
	}
	
	public static void main(String[] args) {
		FCMServer.getInstance().buildMessage("Satya", new Date(), "Bangalore");
		FCMServer.getInstance().pushNotification("dAsd3ZV1rok:APA91bErkUuqklRPoCN0I8P5-JTca36Rw18sFfQQdd4Ywzdla6JATxaQXrY2ZCkJNVBwijf-Lj4nk1v1TddgVBvrJNE8Gikmq6MfK1TdefpcTk81GA7RECHHLYum5Fe21cjHrS4B72lC");
	}
}


 

package com.ccp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the usertoken database table.
 * 
 */
@Entity
@NamedQuery(name="Usertoken.findAll", query="SELECT u FROM Usertoken u")
public class Usertoken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastaccesstime;

	private String token;

	private int userid;
	
	private String deviceId;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Usertoken() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getLastaccesstime() {
		return this.lastaccesstime;
	}

	public void setLastaccesstime(Date lastaccesstime) {
		this.lastaccesstime = lastaccesstime;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}
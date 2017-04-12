package com.ccp.model;

import java.io.Serializable;

import javax.persistence.*;

import com.ccp.controller.ConstantParams;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Override
	public String toString() {
		return "User [id=" + id + ", dob=" + dob + ", empid=" + empid
				+ ", gender=" + gender + ", googleid=" + googleid
				+ ", mobileNumber=" + mobileNumber 
				+ ", useremail=" + useremail + ", userimage="
				+ userimage + ", username=" + username + "]";
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=ConstantParams.dateInputFormat)
	private Date dob;

	private String empid;

	private byte gender;

	private String googleid;

	private String mobileNumber;

	private String useremail;

	private String userimage;

	private String username;
	
	@Transient
	private String userclienttype;

	public String getUserclienttype() {
		return userclienttype;
	}

	public void setUserclienttype(String userclienttype) {
		this.userclienttype = userclienttype;
	}

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmpid() {
		return this.empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public byte getGender() {
		return this.gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public String getGoogleid() {
		return this.googleid;
	}

	public void setGoogleid(String googleid) {
		this.googleid = googleid;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUseremail() {
		return this.useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getUserimage() {
		return this.userimage;
	}

	public void setUserimage(String userimage) {
		this.userimage = userimage;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="googleid", referencedColumnName="googleid", insertable=false, updatable=false)
	private Vehicle vehicle;

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

}
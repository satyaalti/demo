package com.ccp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vehicles database table.
 * 
 */
@Entity
@Table(name="vehicles")
@NamedQuery(name="Vehicle.findAll", query="SELECT v FROM Vehicle v")
public class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private String company;

	private String googleid;

	private String mfgYear;

	private String model;

	private String regNo;

	private int seats;

	public Vehicle() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getGoogleid() {
		return this.googleid;
	}

	public void setGoogleid(String googleid) {
		this.googleid = googleid;
	}

	public String getMfgYear() {
		return this.mfgYear;
	}

	public void setMfgYear(String mfgYear) {
		this.mfgYear = mfgYear;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRegNo() {
		return this.regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public int getSeats() {
		return this.seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

}
package com.ccp.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the pool_rqst database table.
 * 
 */
@Entity
@Table(name="pool_rqst")
@NamedQuery(name="PoolRqst.findAll", query="SELECT p FROM PoolRqst p")
public class PoolRqst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private String destinationaddress;

	private double destinationlat;

	private double destinationlng;
	
	private String googleid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date laststatusupdatedatetime;

	private Timestamp rqstdatetime;

	private String sourceaddress;

	private double sourcelat;

	private double sourcelng;

	private byte status;

	private int tripid;

	public PoolRqst() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDestinationaddress() {
		return this.destinationaddress;
	}

	public void setDestinationaddress(String destinationaddress) {
		this.destinationaddress = destinationaddress;
	}

	public double getDestinationlat() {
		return this.destinationlat;
	}

	public void setDestinationlat(double destinationlat) {
		this.destinationlat = destinationlat;
	}

	public double getDestinationlng() {
		return this.destinationlng;
	}

	public void setDestinationlng(double destinationlng) {
		this.destinationlng = destinationlng;
	}

	public String getGoogleid() {
		return this.googleid;
	}

	public void setGoogleid(String googleid) {
		this.googleid = googleid;
	}

	public Date getLaststatusupdatedatetime() {
		return this.laststatusupdatedatetime;
	}

	public void setLaststatusupdatedatetime(Date laststatusupdatedatetime) {
		this.laststatusupdatedatetime = laststatusupdatedatetime;
	}

	public Timestamp getRqstdatetime() {
		return this.rqstdatetime;
	}

	public void setRqstdatetime(Timestamp rqstdatetime) {
		this.rqstdatetime = rqstdatetime;
	}

	public String getSourceaddress() {
		return this.sourceaddress;
	}

	public void setSourceaddress(String sourceaddress) {
		this.sourceaddress = sourceaddress;
	}

	public double getSourcelat() {
		return this.sourcelat;
	}

	public void setSourcelat(double sourcelat) {
		this.sourcelat = sourcelat;
	}

	public double getSourcelng() {
		return this.sourcelng;
	}

	public void setSourcelng(double sourcelng) {
		this.sourcelng = sourcelng;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int getTripid() {
		return this.tripid;
	}

	public void setTripid(int tripid) {
		this.tripid = tripid;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="googleid", referencedColumnName="googleid", insertable=false, updatable=false)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="tripid", insertable=false, updatable=false)
	private Trip trip;

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}


}
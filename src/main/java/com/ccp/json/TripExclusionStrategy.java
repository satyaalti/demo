package com.ccp.json;

import com.ccp.model.Trip;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class TripExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		// TODO Auto-generated method stub
		return (f.getDeclaringClass() == Trip.class && f.getName().equals("poolrqsts"));
	}

}

package com.ccp.json;

import com.ccp.model.PoolRqst;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class PoolRqstExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return (f.getDeclaringClass() == PoolRqst.class && f.getName().equals("trip"));
	}

	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

}

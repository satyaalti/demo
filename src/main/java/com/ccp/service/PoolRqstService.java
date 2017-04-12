package com.ccp.service;

import java.util.List;

import com.ccp.model.PoolRqst;

public interface PoolRqstService {
	
	public PoolRqst save(PoolRqst poolRqst);
	public Long checkRequestExists(String googleid, int tripid);
	public List<PoolRqst> getRequestedPoolList(String googleid);
	
}

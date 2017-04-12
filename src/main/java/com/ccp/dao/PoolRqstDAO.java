package com.ccp.dao;

import java.util.List;

import com.ccp.model.PoolRqst;

public interface PoolRqstDAO {
	
	public PoolRqst save(PoolRqst poolRqs);
	public Long checkRequestExists(String googleid, int tripid);
	public List<PoolRqst> getRequestedPoolList(String googleid);
}

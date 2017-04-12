package com.ccp.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ccp.dao.PoolRqstDAO;
import com.ccp.model.PoolRqst;
import com.ccp.service.PoolRqstService;

public class PoolRqstServiceImpl implements PoolRqstService {
	
	private PoolRqstDAO poolRqstDAO;
	
	public void setPoolRqstDAO(PoolRqstDAO poolRqstDAO) {
        this.poolRqstDAO = poolRqstDAO;
    }
	
	@Override
	@Transactional
	public PoolRqst save(PoolRqst poolRqst) {
		return this.poolRqstDAO.save(poolRqst);
	}
	
	@Override
	@Transactional
	public Long checkRequestExists(String googleid, int tripid) {
		return this.poolRqstDAO.checkRequestExists(googleid, tripid);
	}
	
	@Override
	@Transactional
	public List<PoolRqst> getRequestedPoolList(String googleid) {
		return this.poolRqstDAO.getRequestedPoolList(googleid);
	}
}

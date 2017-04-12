package com.ccp.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.ccp.dao.UsertokenDAO;
import com.ccp.model.Usertoken;
import com.ccp.service.UsertokenService;

public class UsertokenServiceImpl implements UsertokenService {
	
	private UsertokenDAO usertokenDAO;
	
	public void setUsertokenDAO(UsertokenDAO usertokenDAO) {
        this.usertokenDAO = usertokenDAO;
    }
	
	@Override
	@Transactional
	public Usertoken isValidToken(int userid, String token) {
		return this.usertokenDAO.isValidToken(userid, token);
	}
	
	@Override
	@Transactional
	public void deleteToken(int userid, String token) {
		this.usertokenDAO.deleteToken(userid, token);
	}
	
	@Override
	@Transactional
	public Usertoken save(Usertoken usertoken) {
		return this.usertokenDAO.save(usertoken);
	}
	
	@Override
	@Transactional
	public void deleteTokens(int userid) {
		this.usertokenDAO.deleteTokens(userid);
	}
	
	@Override
	@Transactional
	public Usertoken getUsertoken(int userid) {
		return this.usertokenDAO.getUsertoken(userid);
	}
}

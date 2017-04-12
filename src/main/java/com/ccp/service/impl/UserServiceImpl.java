package com.ccp.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.ccp.dao.UserDAO;
import com.ccp.model.User;
import com.ccp.service.UserService;

public class UserServiceImpl implements UserService {
	
	private UserDAO userDAO;
	
	public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
	
	@Override
	@Transactional
	public User checkAccountExists(String googleid) {
		return this.userDAO.checkAccountExists(googleid);
	}
	
	@Override
	@Transactional
	public User save(User user) {
		return this.userDAO.save(user);
	}
	
	@Override
	@Transactional
	public User update(User user) {
		return this.userDAO.update(user);
	}
	
}

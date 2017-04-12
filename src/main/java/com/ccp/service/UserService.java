package com.ccp.service;

import com.ccp.model.User;

public interface UserService {
	public User checkAccountExists(String googleid);
	public User save(User user);
	public User update(User user);
}

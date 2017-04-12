package com.ccp.dao;

import com.ccp.model.User;

public interface UserDAO {
	public User checkAccountExists(String googleid);
	public User save(User user);
	public User update(User user);
}

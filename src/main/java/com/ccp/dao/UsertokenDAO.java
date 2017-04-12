package com.ccp.dao;

import com.ccp.model.Usertoken;

public interface UsertokenDAO {
	
	public Usertoken isValidToken(int userid, String token);
	public void deleteToken(int userid, String token);
	public Usertoken save(Usertoken usertoken);
	public void deleteTokens(int userid);
	public Usertoken getUsertoken(int userid);
	
}

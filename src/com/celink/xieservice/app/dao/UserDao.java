package com.celink.xieservice.app.dao;

import java.util.Map;

import com.celink.xieservice.app.pojo.User;

public interface UserDao {

	public User findUserByAccount(String account);

	public int saveUser(Map<String, Object> map);
	
	public int updateUserInfo(Map<String, Object> map);

	public User checkLogin(Map<String, Object> map);

	public User findUserById(int id);

	

}

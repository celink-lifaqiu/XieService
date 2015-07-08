package com.celink.xieservice.app.service;

public interface UserService {

	public String regist(String account, String password);

	public String login(String account, String password);

	public String updateUser(String jsonData, byte[] icon);

}

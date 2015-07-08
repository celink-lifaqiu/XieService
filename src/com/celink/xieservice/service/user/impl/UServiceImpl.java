package com.celink.xieservice.service.user.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


import com.celink.xieservice.app.service.UserService;
import com.celink.xieservice.service.user.UService;
@Component
public class UServiceImpl implements UService {
private final static Logger logger = Logger.getLogger(UServiceImpl.class);
	
	private UserService userService;
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String regist(String account, String password) {
		
		return this.userService.regist(account, password);
	}

	@Override
	public String login(String account, String password) {
		return this.userService.login(account, password);
	}

	@Override
	public String updateUser(String jsonData, byte[] icon) {
		return this.userService.updateUser(jsonData, icon);
	}

	@Override
	public String updatePwd(String jsonData) {
		return this.userService.updatePwd(jsonData);
	}

	
}

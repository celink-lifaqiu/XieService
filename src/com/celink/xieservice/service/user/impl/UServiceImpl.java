package com.celink.xieservice.service.user.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


import com.celink.xieservice.app.common.Result;
import com.celink.xieservice.app.constants.Constants;
import com.celink.xieservice.app.service.UserService;
import com.celink.xieservice.service.user.UService;
import com.celink.xieservice.utils.JsonUtils;
import com.celink.xieservice.utils.des.DES;
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
	public String regist1(String account, String password) {
		
		return this.userService.regist(account, password);
	}

	@Override
	public String login1(String account, String password) {
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

	@Override
	public String getUserOrderListByState(int userId, int state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrderDescByOrderId(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrder(String jsonData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrderComments(int userId, int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserServiceAddress(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addUserServiceAddress(String jsonData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserCouponByIsUsed(int userId, int isUsed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServicePackageByType(int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkVersion(String platform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String regist(String ciphertext) {
		Result result = new Result();
		
		try {
			String jsonData = DES.decryptDES(ciphertext, DES.KEY);
			Map<String, Object> params = JsonUtils.parseJSON2Map1(jsonData);
			return this.userService.regist(params.get("account").toString(), params.get("password").toString());
		} catch (Exception e) {
			result.setCode(111);
			result.setErr(Constants.SERVER_DECRYPTION_ERR);
		}
		
		return result.toJson();
	}

	@Override
	public String login(String ciphertext) {
		Result result = new Result();
		
		try {
			String jsonData = DES.decryptDES(ciphertext, DES.KEY);
			Map<String, Object> params = JsonUtils.parseJSON2Map1(jsonData);
			return this.userService.login(params.get("account").toString(), params.get("password").toString());
		} catch (Exception e) {
			result.setCode(111);
			result.setErr(Constants.SERVER_DECRYPTION_ERR);
		}
		
		return result.toJson();
	}

	
}

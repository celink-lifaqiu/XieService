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
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
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
		return this.userService.getUserOrderListByState(userId, state);
	}

	@Override
	public String getOrderDescByOrderId(int orderId) {
		return this.userService.getOrderDescByOrderId(orderId);
	}

	@Override
	public String addOrder(String jsonData) {
		return this.userService.addOrder(jsonData);
	}

	@Override
	public String getCommentByType(int type) {
		return this.userService.getCommentByType(type);
	}

	@Override
	public String getUserServiceAddress(int userId) {
		return this.userService.getUserServiceAddress(userId);
	}

	@Override
	public String addUserServiceAddress(String jsonData) {
		return this.userService.addUserServiceAddress(jsonData);
	}

	@Override
	public String getUserCouponByIsUsed(int userId, int isUsed) {
		return this.userService.getUserCouponByIsUsed(userId, isUsed);
	}

	@Override
	public String getServicePackageByType(int type) {
		return this.userService.getServicePackageByType(type);
	}

	@Override
	public String checkVersion(String platform) {
		return this.userService.checkVersion(platform);
	}

	@Override
	public String regist(String ciphertext) {
		Result result = new Result();
		
		try {
			String jsonData = DES.decryptDES(ciphertext, DES.KEY);
			Map<String, Object> params = JsonUtils.parseJSON2Map1(jsonData);
			return this.userService.regist(params.get("account").toString(), params.get("password").toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
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

	@Override
	public String getAllCommentsByType(int type) {
		return this.userService.getAllCommentsByType(type);
	}

	@Override
	public String updateUserServiceAddress(String jsonData) {
		return this.userService.updateUserServiceAddress(jsonData);
	}

	@Override
	public String getServicePackageDescById(int id) {
		return this.userService.getServicePackageDescById(id);
	}

	@Override
	public String submitComment(String jsonData) {
		return this.userService.submitComment(jsonData);
	}

	@Override
	public String updateOrder(int userId, int orderId) {
		return this.userService.updateOrder(userId, orderId);
	}

	
}

package com.celink.xieservice.app.dao;

import java.util.List;
import java.util.Map;

import com.celink.xieservice.app.pojo.User;

public interface UserDao {

	public User findUserByAccount(String account);

	public int saveUser(Map<String, Object> map);
	
	public int updateUserInfo(Map<String, Object> map);

	public User checkLogin(Map<String, Object> map);

	public User findUserById(int id);

	public List<Map<String, Object>> getUserOrderListByState(
			Map<String, Object> params);

	public Map<String, Object> getOrderDescByOrderId(int orderId);

	public int addOrder(Map<String, Object> params);

	public Map<String, Object> getCommentByType(Map<String, Object> params);

	public List<Map<String, Object>> getAllCommentsByType(int type);

	public List<Map<String, Object>> getUserServiceAddress(int userId);

	public int addUserServiceAddress(Map<String, Object> params);

	public int updateUserServiceAddress(Map<String, Object> params);

	public int updateAddress(int string);

	public List<Map<String, Object>> getUserCouponByIsUsed(
			Map<String, Object> params);

	public List<Map<String, Object>> getServicePackageByType(int type);

	public Map<String, Object> getServicePackageDescById(int id);

	public Map<String, Object> checkVersion(String platform);

	public List<Map<String, Object>> getVersionChange(Map<String, Object> params);

	public int submitComment(Map<String, Object> params);

	

}

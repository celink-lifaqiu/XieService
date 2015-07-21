package com.celink.xieservice.app.service;


public interface UserService {

	public String regist(String account, String password);

	public String login(String account, String password);

	public String updateUser(String jsonData, byte[] icon);

	public String updatePwd(String jsonData);

	public String getUserOrderListByState(int userId, int state);

	public String getOrderDescByOrderId(int orderId);

	public String addOrder(String jsonData);

	public String getCommentByType(int type);

	public String getUserServiceAddress(int userId);

	public String addUserServiceAddress(String jsonData);

	public String getUserCouponByIsUsed(int userId, int isUsed);

	public String getServicePackageByType(int type);

	public String checkVersion(String platform);

	public String getAllCommentsByType(int type);

	public String updateUserServiceAddress(String jsonData);

	public String getServicePackageDescById(int id);

	public String submitComment(String jsonData);

	public String updateOrder(int userId, int orderId);

}

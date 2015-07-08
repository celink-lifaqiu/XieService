package com.celink.xieservice.service.user;

public interface UService {

	/**
	 * 注册接口
	 * @param account 用户账号（手机号码）
	 * @param password 用户密码
	 * @return
	 */
	public String regist(String account, String password);

	/**
	 * 登录接口
	 * @param account 用户账号（手机号码）
	 * @param password 用户密码
	 * @return
	 */
	public String login(String account, String password);
	
	/**
	 * 修改用户资料接口
	 * @param jsonData {id:int(必带), email:String.....可选}
	 * @param icon 用户头像，传byte[] 字节数组
	 * @return
	 */
	public String updateUser(String jsonData, byte[] icon);
}

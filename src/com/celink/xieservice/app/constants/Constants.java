package com.celink.xieservice.app.constants;

import com.celink.xieservice.utils.listener.plugin.PropertiesLoader;



public class Constants {
	static {
		
		 //项目url
		ICON_URL = "http://"+PropertiesLoader.getInstance().getStringProperty("com.celink.xieservice.icon.url", "")+":8080/XieService/icons/";

	}
	
	// 用户头像url
	public static String ICON_URL;
	
	// 错误信息
	
	// 500
	public static String SYS_ERR = "服务器出错";
	// 100
	public static String PARAMS_ERR = "参数不能为空";
	// 101
	public static String ACCOUNT_ERR = "账号必须是手机号码";
	// 102
	public static String PASSWORD_ERR = "密码长度必须在6到16位之间";
	// 103
	public static String ACCOUNT_EXIST_ERR = "账号已存在";
	// 104
	public static String ACCOUNT_OR_PASSWORD_ERR = "错误的账号或者密码";
	// 105
	public static String ID_ERR = "必须传用户id";
	// 106
	public static String ID_NOT_EXIST_ERR = "用户id不存在";
	// 107
	public static String ICONFILE_ERR = "头像传输有误";
	// 108
	public static String OPERATION_ERR = "操作失败";
	// 109
	public static String ACCOUNT_NOT_EXIST_ERR = "账号不存在";
	// 110
	public static String ANSWER_ERR = "密保答案不正确";
	// 111
	public static String SERVER_DECRYPTION_ERR = "服务器解密出错";
	
	
	
	
}

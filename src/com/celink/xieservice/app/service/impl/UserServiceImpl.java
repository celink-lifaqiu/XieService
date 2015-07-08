package com.celink.xieservice.app.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;

import com.celink.xieservice.app.common.Result;
import com.celink.xieservice.app.dao.UserDao;
import com.celink.xieservice.app.pojo.User;
import com.celink.xieservice.app.service.UserService;
import com.celink.xieservice.utils.DateUtils;
import com.celink.xieservice.utils.ValidationUtils;

@Component
public class UserServiceImpl implements UserService {
	@Resource
	UserDao userDao;

	@Override
	public String regist(String account, String password) {
		Result result = new Result();
		if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
			result.setCode(100);
			result.setErr("参数不能为空");
			return result.toJson();
		}	
    	account = account.trim();
    	password = password.trim();
    	
    	if(!ValidationUtils.validateMoblie(account)){
    		result.setCode(101);
			result.setErr("账号必须是手机号码");
			return result.toJson();
    	}
    	
    	if(password.trim().length()<6 || password.trim().length()>16){
    		result.setCode(102);
			result.setErr("密码长度必须在6到16位之间");
			return result.toJson();
    	}
    	Map<String, Object> map = new HashMap<String, Object>();
    	// 根据用户名查找用户，如果存在，看其密码是否为null，如果是则表示用户从划一划游戏那里来的
    	User user = userDao.findUserByAccount(account);
    	int i = 0;
    	if(user!=null){
    		result.setCode(103);
			result.setErr("账号已存在");
			return result.toJson();
    	}else{
    		map.put("account", account);
    		map.put("password", getMD5Password(password, account));
    		map.put("registDate", DateUtils.getDateTimestamp());
    		i = userDao.saveUser(map);
    	}
    	map.clear();
    	if(i > 0){
    		map.put("state", true);
    	}else{
    		map.put("state", false);
    	}
		result.setResult(map); //将返回的对象塞到result里面，它会自动被转换成JSON送给客户端
		return result.toJson();
	}

	@Override
	public String login(String account, String password) {
		Result result = new Result();
		if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
			result.setCode(100);
			result.setErr("参数不能为空");
			return result.toJson();
		}
    	
    	account = account.trim();
    	password = password.trim();
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("account", account);
		map.put("password", getMD5Password(password, account));
    	// 检查用户名和密码是否正确
    	User user = userDao.checkLogin(map);
    	if(user == null){
    		result.setCode(104);
			result.setErr("错误的账号或者密码");
			return result.toJson();
    	}
		result.setResult(user); //将返回的对象塞到result里面，它会自动被转换成JSON送给客户端
		return result.toJson();
	}

	@Override
	public String updateUser(String jsonData, byte[] icon) {
		
		
		
		
		
		
		
		
		
		
		
		return null;
	}
	
	

	
	private String getMD5Password(String password, String account){
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
    	return encoder.encodePassword(password, account);
	}
}

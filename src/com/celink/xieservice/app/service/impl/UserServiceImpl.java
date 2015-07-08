package com.celink.xieservice.app.service.impl;

import java.io.File;
import java.io.IOException;
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
import com.celink.xieservice.app.constants.Constants;
import com.celink.xieservice.app.dao.UserDao;
import com.celink.xieservice.app.pojo.User;
import com.celink.xieservice.app.service.UserService;
import com.celink.xieservice.utils.DateUtils;
import com.celink.xieservice.utils.JsonUtils;
import com.celink.xieservice.utils.ValidationUtils;
import com.celink.xieservice.utils.application.ApplicationContextUtil;
import com.celink.xieservice.utils.file.FileUtil;
import com.celink.xieservice.utils.image.ImageUtil;

@Component
public class UserServiceImpl implements UserService {
	@Resource
	UserDao userDao;

	@Override
	public String regist(String account, String password) {
		Result result = new Result();
		if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
			result.setCode(100);
			result.setErr(Constants.PARAMS_ERR);
			return result.toJson();
		}	
    	account = account.trim();
    	password = password.trim();
    	
    	if(!ValidationUtils.validateMoblie(account)){
    		result.setCode(101);
			result.setErr(Constants.ACCOUNT_ERR);
			return result.toJson();
    	}
    	
    	if(password.trim().length()<6 || password.trim().length()>16){
    		result.setCode(102);
			result.setErr(Constants.PASSWORD_ERR);
			return result.toJson();
    	}
    	Map<String, Object> map = new HashMap<String, Object>();
    	// 根据用户名查找用户，如果存在，看其密码是否为null，如果是则表示用户从划一划游戏那里来的
    	User user = userDao.findUserByAccount(account);
    	int i = 0;
    	if(user!=null){
    		result.setCode(103);
			result.setErr(Constants.ACCOUNT_EXIST_ERR);
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
			result.setErr(Constants.PARAMS_ERR);
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
			result.setErr(Constants.ACCOUNT_OR_PASSWORD_ERR);
			return result.toJson();
    	}
		result.setResult(user); //将返回的对象塞到result里面，它会自动被转换成JSON送给客户端
		return result.toJson();
	}

	@Override
	public String updateUser(String jsonData, byte[] icon) {
		Result result = new Result();
		Map<String, Object> params = JsonUtils.parseJSON2Map1(jsonData);
		
		if(params.get("id")==null){
			result.setCode(105);
			result.setErr(Constants.ID_ERR);
			return result.toJson();
		}
		
		int id = Integer.parseInt(params.get("id").toString());
		
		User user = userDao.findUserById(id);
		if(user==null){
			result.setCode(106);
			result.setErr(Constants.ID_NOT_EXIST_ERR);
			return result.toJson();
		}
		try {
		if(null != icon) {
			String root = ApplicationContextUtil.getContext().getResource("").getFile().getAbsolutePath();
			String fileOld = root + File.separator + "icons" + File.separator + user.getIcon();
			//删除旧文件
			FileUtil.deleteFile(fileOld);
			String iconStr = ImageUtil.saveImageBigAndlittle(root+"/icons",".png", icon);
			params.put("icon", iconStr);
		}
		
		} catch (IOException e) {
			result.setCode(107);
			result.setErr(Constants.ICONFILE_ERR);
			return result.toJson();
		} catch (Exception e) {
			result.setCode(500);
			result.setErr(Constants.SYS_ERR);
			return result.toJson();
		}
		if(userDao.updateUserInfo(params) > 0){
			user = userDao.findUserById(id);
		}else{
			result.setCode(108);
			result.setErr(Constants.OPERATION_ERR);
			return result.toJson();
		}
		result.setResult(user);
		return result.toJson();
	}
	
	

	
	private String getMD5Password(String password, String account){
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
    	return encoder.encodePassword(password, account);
	}

	@Override
	public String updatePwd(String jsonData) {
		Result result = new Result();
		Map<String, Object> params = JsonUtils.parseJSON2Map1(jsonData);
		
		if(params.get("account")==null){
			result.setCode(100);
			result.setErr(Constants.PARAMS_ERR);
			return result.toJson();
		}
		
		User user = userDao.findUserByAccount(params.get("account").toString());
		if(user==null){
			result.setCode(109);
			result.setErr(Constants.ACCOUNT_NOT_EXIST_ERR);
			return result.toJson();
		}
		if(!params.get("pwdAnswer").toString().equals(user.getPwdAnswer())){
			result.setCode(110);
			result.setErr(Constants.ANSWER_ERR);
			return result.toJson();
		}
		
		String newPassword = params.get("newPassword").toString().trim();
		
		if(newPassword.trim().length()<6 || newPassword.trim().length()>16){
    		result.setCode(102);
			result.setErr(Constants.PASSWORD_ERR);
			return result.toJson();
    	}
		
		params.clear();
		params.put("id", user.getId());
		params.put("pwd", getMD5Password(newPassword, user.getAccount()));
		
		if(userDao.updateUserInfo(params) > 0){
			params.clear();
			params.put("state", true);
			result.setResult(params);
		}else{
			result.setCode(108);
			result.setErr(Constants.OPERATION_ERR);
			return result.toJson();
		}
		
		return result.toJson();
	}
}

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
    		map.put("nickName", "");
    		map.put("icon", "");
    		map.put("email", "");
    		map.put("address", "");
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

	@Override
	public String getUserOrderListByState(int userId, int state) {
		Result result = new Result();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("state", state);
		List<Map<String, Object>> list = this.userDao.getUserOrderListByState(params);
		if(list != null && list.size() > 0){
			for(Map<String, Object> map: list){
				map.put("updateTime", DateUtils.dateToInputStrAppendTime(DateUtils.inputStrAppendTimeToDate(map.get("updateTime").toString())));
				map.put("createDate", DateUtils.dateToInputStrAppendTime(DateUtils.inputStrAppendTimeToDate(map.get("createDate").toString())));
			}
		}
		result.setResult(list);
		return result.toJson();
	}

	@Override
	public String getOrderDescByOrderId(int orderId) {
		Result result = new Result();
		Map<String, Object> map = this.userDao.getOrderDescByOrderId(orderId);
		map.put("updateTime", DateUtils.dateToInputStrAppendTime(DateUtils.inputStrAppendTimeToDate(map.get("updateTime").toString())));
		map.put("createDate", DateUtils.dateToInputStrAppendTime(DateUtils.inputStrAppendTimeToDate(map.get("createDate").toString())));
		result.setResult(map);
		return result.toJson();
	}

	@Override
	public String addOrder(String jsonData) {
		Result result = new Result();
		Map<String, Object> params = JsonUtils.parseJSON2Map1(jsonData);
		params.put("additionalRequirements", params.get("additionalRequirements")==null?"":params.get("additionalRequirements").toString());
		params.put("serviceTime", params.get("serviceTime")==null?"":params.get("serviceTime").toString());
		params.put("orderName", params.get("orderName")==null?"":params.get("orderName").toString());
		params.put("updateTime", DateUtils.getDate());
		boolean state = false;
		if(this.userDao.addOrder(params) > 0){
			state = true;
		}
		params.clear();
		params.put("state", state);
		result.setResult(params);
		return result.toJson();
	}

	@Override
	public String getCommentByType(int type) {
		Result result = new Result();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isComment", 1);
		params.put("typeId", type);
		params = this.userDao.getCommentByType(params);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if(params != null){
			int sumStar = Integer.parseInt(params.get("sumStar").toString());
			int orderCnt = Integer.parseInt(params.get("orderCnt").toString());
			returnMap.put("averageStar", sumStar/orderCnt);
			returnMap.put("orderCnt", Integer.parseInt(params.get("orderCnt").toString()));
		}else{
			returnMap.put("averageStar", 0);
			returnMap.put("orderCnt", 0);
		}
		result.setResult(returnMap);
		return result.toJson();
	}

	@Override
	public String getUserServiceAddress(int userId) {
		Result result = new Result();
		List<Map<String, Object>> list = this.userDao.getUserServiceAddress(userId);
		result.setResult(list);
		return result.toJson();
	}

	@Override
	public String addUserServiceAddress(String jsonData) {
		Result result = new Result();
		Map<String, Object> params = JsonUtils.parseJSON2Map1(jsonData);
		
		params.put("reservation", params.get("reservation")==null?"":params.get("reservation").toString());
		params.put("phone", params.get("phone")==null?"":params.get("phone").toString());
		params.put("districtInformation", params.get("districtInformation")==null?"":params.get("districtInformation").toString());
		params.put("addresss", params.get("addresss")==null?"":params.get("addresss").toString());
		
		int idDefaultServiceAddress = Integer.parseInt(params.get("idDefaultServiceAddress").toString());
		if(idDefaultServiceAddress==1){
			this.userDao.updateAddress(Integer.parseInt(params.get("userId").toString()));
		}
		boolean state = false;
		if(this.userDao.addUserServiceAddress(params) > 0){
			state = true;
		}
		params.clear();
		params.put("state", state);
		result.setResult(params);
		return result.toJson();
	}

	@Override
	public String getUserCouponByIsUsed(int userId, int isUsed) {
		Result result = new Result();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("isUsed", isUsed);
		List<Map<String, Object>> list = this.userDao.getUserCouponByIsUsed(params);
		if(list != null && list.size() > 0){
			for(Map<String, Object> map: list){
				map.put("updateTime", DateUtils.dateToInputStrAppendTime(DateUtils.inputStrAppendTimeToDate(map.get("updateTime").toString())));
				map.put("createDate", DateUtils.dateToInputStrAppendTime(DateUtils.inputStrAppendTimeToDate(map.get("createDate").toString())));
			}
		}
		result.setResult(list);
		return result.toJson();
	}

	@Override
	public String getServicePackageByType(int type) {
		Result result = new Result();
		List<Map<String, Object>> list = this.userDao.getServicePackageByType(type);
		result.setResult(list);
		return result.toJson();
	}

	@Override
	public String checkVersion(String platform) {
		Result result = new Result();
		Map<String, Object> version = this.userDao.checkVersion(platform);
		if(version != null){
			List<Map<String, Object>> list = this.userDao.getVersionChange(version);
			version.put("versionChange", list);
		}
		result.setResult(version);
		return result.toJson();
	}

	@Override
	public String getAllCommentsByType(int type) {
		Result result = new Result();
		List<Map<String, Object>> list = this.userDao.getAllCommentsByType(type);
		if(list != null && list.size() > 0){
			for(Map<String, Object> map: list){
				map.put("commentTime", DateUtils.dateToInputStrAppendTime(DateUtils.inputStrAppendTimeToDate(map.get("commentTime").toString())));
			}
		}
		result.setResult(list);
		return result.toJson();
	}

	@Override
	public String updateUserServiceAddress(String jsonData) {
		Result result = new Result();
		Map<String, Object> params = JsonUtils.parseJSON2Map1(jsonData);
		if(params.get("idDefaultServiceAddress") != null){
			int idDefaultServiceAddress = Integer.parseInt(params.get("idDefaultServiceAddress").toString());
			if(idDefaultServiceAddress==1){
				this.userDao.updateAddress(Integer.parseInt(params.get("userId").toString()));
			}
		}
		boolean state = false;
		if(this.userDao.updateUserServiceAddress(params) > 0){
			state = true;
		}
		params.clear();
		params.put("state", state);
		result.setResult(params);
		return result.toJson();
	}

	@Override
	public String getServicePackageDescById(int id) {
		Result result = new Result();
		Map<String, Object> params = this.userDao.getServicePackageDescById(id);
		result.setResult(params);
		return result.toJson();
	}

	@Override
	public String submitComment(String jsonData) {
		Result result = new Result();
		Map<String, Object> params = JsonUtils.parseJSON2Map1(jsonData);
		
		params.put("content", params.get("content")==null?"":params.get("content").toString());
		params.put("userId", params.get("userId")==null?0:Integer.parseInt(params.get("userId").toString()));
		params.put("orderId", params.get("orderId")==null?0:Integer.parseInt(params.get("orderId").toString()));
		params.put("star", params.get("star")==null?0:Integer.parseInt(params.get("star").toString()));
		
		
		boolean state = false;
		if(this.userDao.submitComment(params) > 0){
			state = true;
		}
		params.clear();
		params.put("state", state);
		result.setResult(params);
		return result.toJson();
	}
}

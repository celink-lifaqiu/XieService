package com.celink.xieservice.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.celink.xieservice.app.dao.UserDao;
import com.celink.xieservice.app.pojo.User;
import com.celink.xieservice.app.service.UserService;
@Component
public class UserServiceImpl implements UserService {
	@Resource
	UserDao userDao;
	
	
	@Override
	public String findAllUser() {
		List<User> list = this.userDao.findAllUser();
		return list==null?"0":list.get(0).toString();
	}

}

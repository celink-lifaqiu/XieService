package com.celink.xieservice.test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;

import com.celink.xieservice.service.user.UService;


public class Tester {
	private  UService uservice ;
	@Before
	public void before() throws MalformedURLException{
		String u = "http://192.168.4.130:8080/XieService/hessianservice/uservice";
		HessianProxyFactory factory = new HessianProxyFactory();
		uservice =  (UService) factory.create(UService.class,u);
	}
	

	/**
	 * 测试注册接口
	 * 
	 * @throws Exception
	 */
	@Test
	public void testString() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", "celinkli@126.com");
		params.put("password", "123456");
		params.put("nickName", "lifaqiu");
		System.out.println(uservice.findAllUser()+"");
	}
	
	
	public static void main(String[] args) {
		try {
			Tester t = new Tester();
		    t.before();
		    t.testString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}

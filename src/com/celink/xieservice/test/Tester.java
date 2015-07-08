package com.celink.xieservice.test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

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
		System.out.println(uservice.login("13580130321", "qiusss"));
	}
	@Test
	public void updateUserInfo() throws Exception {
		byte[] url1 = loadImages("http://192.168.4.130:8080/GoogFitService/foodicons/1.png");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", 3);
		params.put("email", "13580130321@163.com");
		params.put("nickName", "qiu");
		params.put("address", "深圳宝安");
		params.put("birthday", "1989-08-15");
		System.out.println(uservice.updateUser(JSONObject.fromObject(params).toString(), url1));
	}
	@Test
	public void updatePwd() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", "13580130321");
		params.put("pwdAnswer", "1970年01月01日");
		params.put("newPassword", "qiusss");
		System.out.println(uservice.updatePwd(JSONObject.fromObject(params).toString()));
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
	
	
	
	public byte[] loadImages(String imageUrl) throws Exception {  
        //new一个URL对象  
        URL url = new URL(imageUrl);  
        //打开链接  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        //设置请求方式为"GET"  
        conn.setRequestMethod("GET");  
        //超时响应时间为5秒  
        conn.setConnectTimeout(5 * 1000);  
        //通过输入流获取图片数据  
        InputStream inStream = conn.getInputStream();  
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
        byte[] data = readInputStream(inStream);  
        return data;
    }  
    public byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
}

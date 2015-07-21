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
import com.celink.xieservice.utils.des.DES;


public class Tester {
	private  UService uservice ;
	@Before
	public void before() throws MalformedURLException{
		//		String u = "http://115.28.17.190:8080/XieService/hessianservice/uservice";
		String u = "http://115.28.17.190:8080/XieService/hessianservice/uservice";
		HessianProxyFactory factory = new HessianProxyFactory();
		uservice =  (UService) factory.create(UService.class,u);
	}
	

	@Test
	public void testRegister() throws Exception {
		String plaintext = "{\"account\":\"13580130322\", \"password\":\"qiusss\"}";
		String ciphertext = DES.encryptDES(plaintext, DES.KEY);
		System.out.println(uservice.regist(ciphertext));
	}
	@Test
	public void testLogin() throws Exception {
		String plaintext = "{\"account\":\"13580130321\", \"password\":\"qiussss\"}";
		String ciphertext = DES.encryptDES(plaintext, DES.KEY);
		System.out.println(uservice.login(ciphertext));
	}
	@Test
	public void updateUserInfo() throws Exception {
		byte[] url1 = loadImages("http://192.168.4.130:8080/GoogFitService/foodicons/1.png");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", 4);
		params.put("email", "13580130321@163.com");
		params.put("nickName", "qiuaaaaa");
		params.put("address", "深圳宝安");
		params.put("birthday", "1989-08-15");
		System.out.println(uservice.updateUser(JSONObject.fromObject(params).toString(), url1));
	}
	@Test
	public void updatePwd() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", "3");
		params.put("pwdAnswer", "1970年01月01日");
		params.put("newPassword", "qiussss");
		System.out.println(uservice.updatePwd(JSONObject.fromObject(params).toString()));
	}
	
	/**
	 * 增加订单
	 * @param jsonData
	 * {
	 * userId：int 用户编号
	 * orderName:String 服务名称(比如，如果是猫的订单，那这个服务名称就是“猫的服务订单”)
	 * packageServiceId：int 选择的套餐编号
	 * price：float 小计费用
	 * serviceAddressId：int 服务地址编号
	 * serviceTime:String 服务时间
	 * additionalRequirements:String  附加要求
	 * isUseVoucher：int 是否使用抵用卷（0、否，1、是）
	 * sumPrice：float 总费用
	 * }
	 * @return
	 */
	@Test
	public void addOrder() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", 3);
		params.put("orderName", "日常保洁服务");
		params.put("packageServiceId", 2);
		params.put("price", 50.0);
		params.put("serviceAddressId", 1);
		params.put("serviceTime", "2015-07-22 10点");
		params.put("additionalRequirements", "无");
		params.put("isUseVoucher", 0);
		params.put("sumPrice", 50.0);
		System.out.println(uservice.addOrder(JSONObject.fromObject(params).toString()));
	}
	
	
	/**
	 * 增加用户服务地址接口
	 * @param jsonData
	 * {
	 *  userId：int 用户Id 必传
	 *	reservation:String 预约人
	 *	phone:String 电话
	 *	districtInformation:String 小区信息
	 *	address:String 详细地址
	 *	idDefaultServiceAddress:int  是否默认为服务地址（0、否，1、是）注意，所有地址中只能有一个是默认地址，如果设置了这个为默认，则其他已经设置的会自动改为0
	 * }
	 * @return
	 */
	@Test
	public void addUserServiceAddress() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", 3);
		params.put("reservation", "黎法秋");
		params.put("phone", "13580130321");
		params.put("districtInformation", "广州花都");
		params.put("address", "花都街109号");
		params.put("idDefaultServiceAddress", 1);
		System.out.println(uservice.addUserServiceAddress(JSONObject.fromObject(params).toString()));
	}
	
	@Test
	public void updateUserServiceAddress() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", 1);
		params.put("userId", 3);
		params.put("reservation", "黎法秋");
		params.put("phone", "13580130321");
		params.put("districtInformation", "广州花都区");
		params.put("address", "花都街109号");
		params.put("idDefaultServiceAddress", 1);
		System.out.println(uservice.updateUserServiceAddress(JSONObject.fromObject(params).toString()));
	}
	
	/**
	 * 提交评论
	 * @param jsonData
	 * {
	 * userId int 用户编号
	 * orderId int 订单编号
	 * star int 星级
	 * content String 评论内容
	 * }
	 * @return
	 */
	@Test
	public void submitComment() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", 3);
		params.put("orderId", 2);
		params.put("star", 5);
		params.put("content", "师傅态度很好，给个赞");
		System.out.println(uservice.submitComment(JSONObject.fromObject(params).toString()));
	}
	@Test
	public void getUserOrderListByState() throws Exception {
		System.out.println(uservice.getUserOrderListByState(3,1));
	}
	@Test
	public void getOrderDescByOrderId() throws Exception {
		System.out.println(uservice.getOrderDescByOrderId(2));
	}
	@Test
	public void getCommentByType() throws Exception {
		System.out.println(uservice.getCommentByType(1));
	}
	@Test
	public void getAllCommentsByType() throws Exception {
		System.out.println(uservice.getAllCommentsByType(1));
	}
	@Test
	public void getUserServiceAddress() throws Exception {
		System.out.println(uservice.getUserServiceAddress(3));
	}
	@Test
	public void getUserCouponByIsUsed() throws Exception {
		System.out.println(uservice.getUserCouponByIsUsed(3,0));
	}
	@Test
	public void getServicePackageByType() throws Exception {
		System.out.println(uservice.getServicePackageByType(1));
	}
	@Test
	public void getServicePackageDescById() throws Exception {
		System.out.println(uservice.getServicePackageDescById(1));
	}
	@Test
	public void checkVersion() throws Exception {
		System.out.println(uservice.checkVersion("ios"));
	}
	@Test
	public void updateOrder() throws Exception {
		System.out.println(uservice.updateOrder(3,2));
	}

	public static void main(String[] args) {
		try {
			Tester t = new Tester();
		    t.before();
		    t.testLogin();
			
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

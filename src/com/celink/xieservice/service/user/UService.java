package com.celink.xieservice.service.user;

import java.util.Date;

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
	 * @param jsonData 
	 * {
	 * id:int, (必带)
	 * email:String,(可选)
	 * nickName:String,(可选)
	 * address:String,(可选)
	 * birthday:String,(可选 "1970年01月01日")
	 * pwdAnswer:String(可选 "1970年01月01日")
	 * }
	 * @param icon 用户头像，传byte[] 字节数组
	 * @return
	 */
	public String updateUser(String jsonData, byte[] icon);
	
	/**
	 * 修改密码接口
	 * @param jsonData
	 * { 
	 * account：String
	 * pwdAnswer：String  "1970年01月01日"
	 * newPassword：String 
	 * }
	 * @return
	 */
	public String updatePwd(String jsonData);
	
	/**
	 * 获取用户订单接口
	 * ps：
	 * 传state=3查找完成的订单，传state=0查找未完成的订单
	 * @param userId 用户id
	 * @param state 订单状态
	 * @return
	 */
	public String getUserOrderListByState(int userId, int state);
	
	/**
	 * 获取订单详情接口
	 * @param orderId 订单id
	 * @return
	 */
	public String getOrderDescByOrderId(int orderId);
	
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
	public String addOrder(String jsonData);
	
	/**
	 * 获取评论列表接口
	 * @param userId 用户Id
	 * @param orderId 订单id
	 * @return
	 */
	public String getOrderComments(int userId, int orderId);
	
	/**
	 * 获取用户的服务地址接口
	 * @param userId 用户Id
	 * @return
	 */
	public String getUserServiceAddress(int userId);
	
	/**
	 * 增加用户服务地址接口
	 * @param jsonData
	 * {
	 *  userId：int 用户Id
	 *	reservation:String 预约人
	 *	phone:String 电话
	 *	districtInformation:String 小区信息
	 *	addresss:String 详细地址
	 *	idDefaultServiceAddress:int  是否默认为服务地址（0、否，1、是）
	 * }
	 * @return
	 */
	public String addUserServiceAddress(String jsonData);
	
	/**
	 * 获取用户的优惠券接口
	 * @param userId 用户Id
	 * @param isUsed 是否已使用（0、否，1、是）
	 * @return
	 */
	public String getUserCouponByIsUsed(int userId, int isUsed);
	
	/**
	 * 获取服务套餐列表接口
	 * ps:
	 * type=1-->日常保洁
	 * type=2-->采耳
	 * type=3-->狗
	 * type=4-->猫
	 * @param type 套餐类型
	 * @return
	 */
	public String getServicePackageByType(int type);
	
	/**
	 * 检查APP是否有更新
	 * @param platform ios/android
	 * @return
	 */
	public String checkVersion(String platform);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

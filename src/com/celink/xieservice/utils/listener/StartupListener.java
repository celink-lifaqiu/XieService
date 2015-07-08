package com.celink.xieservice.utils.listener;

import org.springframework.context.ApplicationContext;

/**
* @ClassName: StartupListener 
* @Description: TODO(实现此类可监听服务器启动，以预加载必须的配置) 
* @author Famor  
* @date 2014-1-22 上午10:10:13 
 */
public interface StartupListener {
	/**
	 * 加载
	 * @param context
	 */
	public void load(ApplicationContext context);
	
	/**
	 * 初始化插件
	 */
	public void initializePlugin();
}

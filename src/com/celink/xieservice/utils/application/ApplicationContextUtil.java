package com.celink.xieservice.utils.application;

import java.io.File;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.celink.xieservice.utils.listener.Dispathcer;
import com.celink.xieservice.utils.listener.plugin.PropertiesLoader;



public class ApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		ApplicationContextUtil.context = context;
		
		//实例化插件
		PropertiesLoader.getInstance().initializePlugin();
		
		//派发服务器启动事件
		Dispathcer.init(context);

	}

	public static ApplicationContext getContext() {
		return context;
	}
	
	private static String rootPath = null;	
	public static String getApplicationRootPath()
	{
		try
		{
			if(rootPath == null)
			{
				rootPath = getContext().getResource("").getFile().getAbsolutePath();
			}
			
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return rootPath;
	}
	
	private static String iconsRoot = null;
	public static String getIconsRootPath()
	{
		if(iconsRoot == null)
		{
			iconsRoot = getApplicationRootPath()+File.separator+"icons";
		}
		
		return iconsRoot;
	}
}

package com.celink.xieservice.utils.listener.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.celink.xieservice.utils.listener.Dispathcer;
import com.celink.xieservice.utils.listener.StartupListener;


/**
* @ClassName: PropertiesLoader 
* @Description: TODO(配置加载工具) 
* @author Famor  
* @date 2014-1-22 上午11:16:10 
 */
public class PropertiesLoader implements StartupListener {
	private final static Logger logger = Logger.getLogger(PropertiesLoader.class);
	
	private static Map<String, Object> properties = new HashMap<String, Object>(); 
	
	private static PropertiesLoader pl = new PropertiesLoader();
	
	public static PropertiesLoader getInstance() {
		return pl;
	}
	
	@Override
	public void load(ApplicationContext context) {
		logger.info("开始加载配置...#########################");
		//加载配置
		InputStream in = null;
		try {
			
			Properties props = new Properties();  
			in = this.getClass().getResourceAsStream("/config.properties");
			props.load(in);
			//输出属性文件中的信息   
	        Set<Object> set = props.keySet();  
	        Iterator<Object> it = set.iterator();  
	        while (it.hasNext()) {  
	            String key = (String) it.next();  
	            properties.put(key, props.getProperty(key));
	            logger.info(key + "=" + props.getProperty(key));  
	        }  
	        logger.info("配置加载完成...#########################"); 
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally {  
            if (in != null) {  
            	try {
					in.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
            }
		}
		
		
	}
	
	@Override
	public void initializePlugin() {
		Dispathcer.addListener(this);
	}
	
	public String getStringProperty(String key, String defaultValue) {
		String val = null;
		
		if(null == properties.get(key)) {
			InputStream in = null;
			Properties props = new Properties();  
			in = this.getClass().getResourceAsStream("/config.properties");
			try {
				props.load(in);
				val = props.getProperty(key);
				if(null != val) {
					properties.put(key, props.getProperty(key));
				}else {
					val = defaultValue;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			val = (String) properties.get(key);
		}
		return val;
	}
	
}

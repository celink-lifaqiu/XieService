package com.celink.xieservice.utils;

import java.util.UUID;

/**
 * Created by lifaqiu on 14-4-30.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static String deNull(Object obj){
        if (obj == null || !(obj instanceof String)) {
            return "";
        }
        return String.valueOf(obj);
    }
    
    public static String getFirstImage(String images) {
		if(images != null){
			String[] str = images.split("\"");
			return str[3];
		}
		return null;
	}
    public static String[] getImages(String images) {
		if(images != null){
			String[] str = images.split("\"");
			return str;
		}
		return null;
	}
    

	/**
	 * 获取uuid
	 * 
	 * @return
	 */
	public static String getuuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
    
}

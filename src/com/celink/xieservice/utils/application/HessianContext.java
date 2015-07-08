package com.celink.xieservice.utils.application;

import javax.servlet.ServletRequest;

/**
* @ClassName: HessianContext 
* @Description: TODO(hessian上下文工具) 
* @author Famor  
* @date 2014-3-17 上午11:39:32 
 */
public class HessianContext {
	private ServletRequest _request;  
    private static final ThreadLocal<HessianContext> _localContext   
                                  = new ThreadLocal<HessianContext>() {  
        @Override  
        public HessianContext initialValue() {  
            return new HessianContext();  
        }  
    };  
  
    private HessianContext() {  
    }  
  
    public static void setRequest(ServletRequest request) {  
        _localContext.get()._request = request;  
    }  
  
    public static ServletRequest getRequest() {  
        return _localContext.get()._request;  
    }  
  
    public static void clear() {  
        _localContext.get()._request = null;  
    }  
}

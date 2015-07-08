package com.celink.xieservice.utils.aop;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class HessianAop   {
	
	private final static Logger logger = Logger.getLogger(HessianAop.class);

	@Around("execution (* com.celink.xieservice.*.service.impl.*.*(..))") 
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		
		StopWatch clock = new StopWatch();
		clock.start(); // 计时开始
		Object retVal = pjp.proceed();
		clock.stop(); // 计时结束
		if(clock.getTime() > 500){
			StringBuilder sb = new StringBuilder();
			sb.append(pjp.getTarget().getClass().getName());
			// 获取将要执行的方法名称
			String methodName = pjp.getSignature().getName();
			sb.append(".");
			sb.append(methodName);
		/*	sb.append("(");
			// 获取执行方法的参数
			Object[] args = pjp.getArgs();
			// 从参数列表中获取参数对象
			if(args != null && args.length > 0){
				for (Object obj : args) {// 查看参数值
						sb.append(obj.getClass().getName() + ",");
				}
			}
			sb.append("): ");
			*/
			//logger.info("#"+clock.getTime() + "#" + sb.toString());
			
		}
		return retVal;
	}
}

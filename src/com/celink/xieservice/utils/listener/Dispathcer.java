package com.celink.xieservice.utils.listener;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.context.ApplicationContext;

/**
* @ClassName: Dispathcer 
* @Description: TODO(事件派发器) 
* @author Famor  
* @date 2014-1-22 上午10:06:32 
 */
public class Dispathcer {
	private static Set<StartupListener> listeners = new CopyOnWriteArraySet<StartupListener>();
	
	public static void addListener(StartupListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.add(listener);
	}

	public static void removeListener(StartupListener listener) {
		listeners.remove(listener);
	}
	
	public static void init(ApplicationContext context) {
		for(StartupListener listener : listeners) {
			listener.load(context);
		}
	}
}

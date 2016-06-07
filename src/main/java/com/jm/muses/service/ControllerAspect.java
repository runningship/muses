package com.jm.muses.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {

	public static final ThreadLocal<String> controllerPrefix =  new ThreadLocal<>();
	
	@Pointcut("execution(* com.jm.muses.controller..*.*(..))")
	public void controllerAction() {}

	@Before("controllerAction()")
	public void getControllerName(JoinPoint jp) {
		String className = jp.getTarget().getClass().getSimpleName();
		
		String mapperPrefix = null;
		
		int index = className.indexOf("Controller");
		if (index != -1) {
			mapperPrefix = className.substring(0, index)+"Mapper";
		}
		
		if (mapperPrefix != null) {
			controllerPrefix.set(mapperPrefix);
		}
	}
	
	@Pointcut("execution(* com.jm.muses.service.BaseService.*(..))")
	public void serviceAction() {}

	@Before("serviceAction()")
	public void setControllerName(JoinPoint jp) {
		BaseService currentService = (BaseService) jp.getTarget();
		
		currentService.setMapperPrefix(controllerPrefix.get());
	}
}

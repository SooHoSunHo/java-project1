package com.birdhill.common.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggerAspect {
	protected Logger logger = LoggerFactory.getLogger(LoggerAspect.class);
	static String name = "";
	static String type = "";
	
	@Around("execution(* com.birdhill..controller.*Controller.*(..)) or execution(* com.birdhill..service.*Impl.*(..)) or execution(* com.birdhill..dao.*DAO.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		type = joinPoint.getSignature().getDeclaringTypeName();
		
		if(type.indexOf("Controller") > -1) {
			name = "Controller \t: ";
		}
		else if(type.indexOf("Service") > -1) {
			name="ServiceImpl \t: ";
		}
		else if(type.indexOf("DAO") > -1) {
			name = "DAO \t\t: ";
		}
		logger.debug(name+type+"."+joinPoint.getSignature().getName()+"()");
		return joinPoint.proceed();
		
	}
	
}

package com.birdhill.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggerInterceptor extends HandlerInterceptorAdapter {
	protected Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (logger.isDebugEnabled()) {
        	logger.debug("======================================          START         ======================================");
        	logger.debug(" Request URI \t:  " + request.getRequestURI());
        }
        return super.preHandle(request, response, handler);
    }
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (logger.isDebugEnabled()) {
        	logger.debug("======================================           END          ======================================\n");
        }
    }
}

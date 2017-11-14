package com.lunchtech.vlbs.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CORSAuthInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String host = request.getHeader("referer");
		if (host != null) {
			response.addHeader("Access-Control-Allow-Origin", host);
		}
		response.addHeader("Access-Control-Allow-Credentials", "true");
		return true;
	}
}

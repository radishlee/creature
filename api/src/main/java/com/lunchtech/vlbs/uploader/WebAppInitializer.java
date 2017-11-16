package com.lunchtech.vlbs.uploader;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.lunchtech.vlbs.service.ServiceAppConfig;

public class WebAppInitializer implements WebApplicationInitializer {
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(WebAppConfig.class, ServiceAppConfig.class);
		ctx.setServletContext(servletContext);
		//增加对application/x-www-form-urlencoded的支持
		//http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html
		//搜索关键字“application/x-www-form-urlencoded”
		servletContext.addFilter("httpPutFormFilter", new HttpPutFormContentFilter()).
			addMappingForServletNames(null, false, "dispatcher");
		Dynamic dynamic = servletContext.addServlet("dispatcher",
				new DispatcherServlet(ctx));
		dynamic.addMapping("/");
		dynamic.setLoadOnStartup(1);
	}
}
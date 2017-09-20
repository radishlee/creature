package com.lesports.gene.vct.admin;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.lesports.gene.vct.admin.advice.JsonViewSupportFactoryBean;
import com.lesports.gene.vct.admin.advice.OutputWrapper;
import com.lesports.gene.vct.admin.interceptor.CORSAuthInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("com.lesports.gene")
public class WebAppConfig extends WebMvcConfigurationSupport {
	protected final Log logger = LogFactory.getLog(getClass());
	
//	/**
//	 * 统一输出json格式
//	 * @return
//	 */
//	@Bean
//	public JsonViewSupportFactoryBean outputWrapper() {
//		return new JsonViewSupportFactoryBean();
//	}
//	/**
//	 * CORS验证
//	 * @return
//	 */
//	@Bean
//	public CORSAuthInterceptor cORSAuthInterceptor() {
//		return new CORSAuthInterceptor();
//	}


//	protected void addReturnValueHandlers(
//			List<HandlerMethodReturnValueHandler> returnValueHandlers) {
//		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//		messageConverters.add(new MappingJackson2HttpMessageConverter());
//		returnValueHandlers.add(new OutputWrapper(messageConverters));
//	}
//	/**
//	 * json解析器
//	 */
//	@Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//		converter.setFeatures(SerializerFeature.WriteMapNullValue);
//		converter.setFeatures(SerializerFeature.WriteNullStringAsEmpty);
//		converter.setFeatures(SerializerFeature.WriteNullListAsEmpty);
//		converters.add(converter);
//    }


	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		logger.info("addResourceHandlers");
		registry.addResourceHandler("/images/**").addResourceLocations("/images/");
		registry.addResourceHandler("/plug/**").addResourceLocations("/plug/").resourceChain(true).addResolver(
				new VersionResourceResolver().addFixedVersionStrategy("1.10", "/**/*.js").addContentVersionStrategy("/**"));
		registry.addResourceHandler("/plug/**").addResourceLocations("/plug/").resourceChain(true).addResolver(
				new VersionResourceResolver().addFixedVersionStrategy("1.10", "/**/*.css").addContentVersionStrategy("/**"));
		registry.addResourceHandler("/common/**").addResourceLocations("/common/");
	}

	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/pages/");
		return resolver;
	}
}
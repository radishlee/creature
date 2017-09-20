package com.lesports.gene.vct.api;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.lesports.gene.vct.api.advice.JsonViewSupportFactoryBean;
import com.lesports.gene.vct.api.advice.OutputWrapper;
import com.lesports.gene.vct.api.interceptor.CORSAuthInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("com.lesports.gene.vct.api")
public class WebAppConfig extends WebMvcConfigurationSupport {
	protected final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 统一输出json格式
	 * @return
	 */
	@Bean
	public JsonViewSupportFactoryBean outputWrapper() {
		return new JsonViewSupportFactoryBean();
	}
	/**
	 * CORS验证
	 * @return
	 */
	@Bean
	public CORSAuthInterceptor cORSAuthInterceptor() {
		return new CORSAuthInterceptor();
	}


	protected void addReturnValueHandlers(
			List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		returnValueHandlers.add(new OutputWrapper(messageConverters));
	}
	/**
	 * json解析器
	 */
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		converter.setFeatures(SerializerFeature.WriteMapNullValue);
		converter.setFeatures(SerializerFeature.WriteNullStringAsEmpty);
		converter.setFeatures(SerializerFeature.WriteNullListAsEmpty);
		converters.add(converter);
    }

}
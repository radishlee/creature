package com.lunchtech.vlbs.api.advice;

import java.io.IOException;
import java.util.List;

import com.lunchtech.vlbs.api.model.ResponseDataModel;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

public class OutputWrapper extends RequestResponseBodyMethodProcessor {

	public OutputWrapper(List<HttpMessageConverter<?>> messageConverters) {
		super(messageConverters);
	}

	public OutputWrapper(List<HttpMessageConverter<?>> messageConverters,
			ContentNegotiationManager contentNegotiationManager) {

		super(messageConverters, contentNegotiationManager);
	}

	public OutputWrapper(List<HttpMessageConverter<?>> messageConverters,
			ContentNegotiationManager contentNegotiationManager, List<Object> responseBodyAdvice) {

		super(messageConverters, contentNegotiationManager, responseBodyAdvice);
	}
	/**
	 * 统一返回正确时的数据模型
	 */
	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
			throws IOException, HttpMediaTypeNotAcceptableException {

		mavContainer.setRequestHandled(true);
		ResponseDataModel rs = ResponseDataModel.SUCCESS();
		rs.setData(returnValue);
		writeWithMessageConverters(rs, returnType, webRequest);
	}

}

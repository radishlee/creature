package com.lunchtech.vlbs.admin.advice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

public class JsonViewSupportFactoryBean implements InitializingBean {
	protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private RequestMappingHandlerAdapter adapter;
    @Override
    public void afterPropertiesSet() throws Exception {
    	List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
    	List<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>(returnValueHandlers);
        decorateHandlers(handlers);
        adapter.setReturnValueHandlers(handlers);
        returnValueHandlers = null;
    }
    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        for (HandlerMethodReturnValueHandler handler : handlers) {
            if (handler instanceof RequestResponseBodyMethodProcessor)
            {
            	OutputWrapper decorator = new OutputWrapper(this.adapter.getMessageConverters());
                int index = handlers.indexOf(handler);
                handlers.set(index, decorator);
                logger.info("JsonView decorator support wired up");
                break;
            }
        }        
    }
}
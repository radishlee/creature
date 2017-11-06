package com.lunchtech.vlbs.api.advice;

import javax.servlet.http.HttpServletRequest;

import com.lunchtech.vlbs.api.model.ResponseDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lunchtech.vlbs.common.exception.BaseException;
import com.lunchtech.vlbs.common.exception.InternalException;
import com.lunchtech.vlbs.common.exception.RetryException;
import com.lunchtech.vlbs.common.exception.UrlAuthException;

/**
 * Performs the same exception handling as {@link ExceptionHandlingController}
 * but offers them globally. The exceptions below could be raised by any
 * controller and they would be handled here, if not handled in the controller
 * already.
 * 
 * @author zhoujie04
 */
@ControllerAdvice
public class ExceptionAdvice {

	protected Logger logger;

	public ExceptionAdvice() {
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Demonstrates how to take total control - setup a model, add useful
	 * information and return the "support" view name. This method explicitly
	 * creates and returns
	 * 
	 * @param req
	 *            Current HTTP request.
	 * @param exception
	 *            The exception thrown - always {@link UrlAuthException}.
	 * @return Json Object
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseDataModel handleError(HttpServletRequest req, Exception exception)
			throws Exception {
		
		// Rethrow annotated exceptions or they will be processed here instead.
		if (AnnotationUtils.findAnnotation(exception.getClass(),
				ResponseStatus.class) != null)
			throw exception;
		logger.error("Request: " + req.getRequestURI() + " raised " + exception);
		ResponseDataModel rs;
		if (exception instanceof RetryException) {
			//提示客户端重试该接口
			rs = ResponseDataModel.RETRY();
		} else {
			rs = ResponseDataModel.ERROR();
		}
		ResponseDataModel.Meta meta = rs.getMeta();
		
		if (exception instanceof BaseException) {
			meta.setError_type(((BaseException)exception).getType());
			meta.setError_message(exception.getMessage());
		} else {
			//不要对外暴露系统内部的异常，把异常记录到日志中
			meta.setError_type(InternalException.class.getSimpleName());
			meta.setError_message("系统内部错误");
			logger.error(exception.getMessage());
			exception.printStackTrace();
		}
		
		
		return rs;
	}
}
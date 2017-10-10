/******************************************************************************
 * CREATETIME : 2016年6月19日 下午3:57:01
 ******************************************************************************/
package com.sinosoft.common.web.utils.support;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSONObject;

/**
 * 可以处理json异常的异常处理适配
 * @author ★LiuPing
 * @CreateTime 2016年6月19日
 */
public class JsonMappingExceptionResolver extends SimpleMappingExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(JsonMappingExceptionResolver.class);
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex) {
		String viewName = determineViewName(ex,request);
		if(viewName!=null){// JSP格式返回
			String accept = request.getHeader("accept");
			String requested = request.getHeader("X-Requested-With");
			if( !( ( accept!=null&&accept.indexOf("application/json")> -1 )||( requested!=null&&requested.indexOf("XMLHttpRequest")> -1 ) )){
				logger.error(request.getServletPath(),ex);

				// 如果不是异步请求
				// Apply HTTP status code for error views, if specified.
				// Only apply it if we're processing a top-level request.
				Integer statusCode = determineStatusCode(request,viewName);
				if(statusCode!=null){
					applyStatusCodeIfPossible(request,response,statusCode);
				}
				return getModelAndView(viewName,ex,request);
			}else{// JSON格式返回
				try{
					response.setStatus(500);
					JSONObject jsonObject = new JSONObject();
					Throwable rootCas = ex;

					if(rootCas.getCause()!=null){// 得到最顶层错误
						rootCas = rootCas.getCause();
					}

					String msg = getCausedbyMsg(rootCas);

					jsonObject.put("msg",msg);
					if(ex instanceof RuntimeException){
						jsonObject.put("title","系统提示");
						jsonObject.put("type","info");
						logger.warn("{}.warn:{}",request.getServletPath(),msg);
					}else{
						jsonObject.put("title","系统异常");
						jsonObject.put("type","error");
						logger.error(request.getServletPath(),ex);
					}
					response.setCharacterEncoding(DEFAULT_CHARSET.name());
					String jsonMsg = jsonObject.toJSONString();
					PrintWriter writer = response.getWriter();

					writer.write(jsonMsg);
					writer.flush();
					writer.close();
				}catch(IOException e){
					e.printStackTrace();
				}
				return null;
			}
		}else{
			ex.printStackTrace();
			return null;
		}
	}

	private String getCausedbyMsg(Throwable rootCas) {
		String msg = rootCas.getMessage();
		if(msg==null){
			msg = rootCas.getClass().getName();
			StackTraceElement[] stackeTrace = rootCas.getStackTrace();
			msg += "\n\t"+( stackeTrace[0].toString() );
			return msg;
		}
		int idx = msg.indexOf("Caused");
		int idxAt = 0;
		String causedbyMsg = null;

		if(idx<0){
			causedbyMsg = msg;
		}else{
			causedbyMsg = "";
		}

		// 截取 Caused 到at的串，这些为关键信息
		while(idx> -1){
			msg = msg.substring(idx);
			idxAt = msg.indexOf("at ");
			if(idxAt> -1){
				causedbyMsg += msg.substring(0,idxAt);
				msg = msg.substring(idxAt);
			}else{
				causedbyMsg += msg;
			}
			idx = msg.indexOf("Caused");
		}

		if(causedbyMsg.length()>300) causedbyMsg = causedbyMsg.substring(0,300);

		return causedbyMsg;
	}
}

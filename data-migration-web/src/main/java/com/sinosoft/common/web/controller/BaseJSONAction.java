/******************************************************************************
 * CREATETIME : 2016年6月7日 上午10:43:27
 ******************************************************************************/
package com.sinosoft.common.web.controller;

import ins.framework.web.util.ResponseUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSONObject;

/**
 * <pre></pre>
 * @author ★LiuPing
 * @CreateTime 2016年6月7日
 */

public class BaseJSONAction {

	protected static Logger logger = LoggerFactory.getLogger(BaseJSONAction.class);
	// 常用格式定义

	private final static ThreadLocal<JSONObject> jsonLocal = new ThreadLocal<JSONObject>();

	/**
	 * 在 response 里面放一个数据
	 * @param key
	 * @param value
	 * @modified: ☆LiuPing(2016年6月7日 ): <br>
	 */
	public void putData(String key,Object value) {
		// CodeTransSerializer.getInstance().trans(value);// 翻译处理
		JSONObject responseJsonObj = jsonLocal.get();
		if(responseJsonObj==null){
			responseJsonObj = new JSONObject();
			jsonLocal.set(responseJsonObj);
		}
		responseJsonObj.put(key,value);
	}

	/**
	 * 将map数据转换为json数据，
	 * @return
	 * @modified: ☆LiuPing(2016年6月7日 ): <br>
	 */
	public String success() {
		JSONObject responseJsonObj = jsonLocal.get();
		if(responseJsonObj==null){
			responseJsonObj = new JSONObject();
		}
		responseJsonObj.put("status",HttpStatus.OK);
		String keyStr = "";
		for(String key:responseJsonObj.keySet()){
			keyStr += key+",";
		}
		logger.debug("\n====responseJsonObj.MapKey=="+keyStr);
		String respJson = ResponseUtils.jsonToString(responseJsonObj);
		jsonLocal.remove();
		return respJson;
	}

	public String error(String errorMsg) {
		jsonLocal.remove();
		throw new IllegalArgumentException(errorMsg);
		// responseJsonObj.put("status",HttpStatus.INTERNAL_SERVER_ERROR);
		// responseJsonObj.put("errorMsg",errorMsg);
		// return ResponseUtils.jsonToString(responseJsonObj);
	}
}

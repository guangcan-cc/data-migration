package com.sinosoft.common.web.utils.support;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Spring MVC Json 转换适配器 <br>
 * 修改了readInternal方法 新增了getContentTypeCharset的私有方法
 * @author ★LiuPing
 * @CreateTime 2016年05月09日
 */
public class FastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private SerializerFeature[] serializerFeature;

	public SerializerFeature[] getSerializerFeature() {
		return this.serializerFeature;
	}

	public void setSerializerFeature(SerializerFeature[] serializerFeature) {
		this.serializerFeature = (SerializerFeature[])serializerFeature.clone();
	}

	public FastJsonHttpMessageConverter(){
		super(new MediaType("application","json",DEFAULT_CHARSET));
	}

	public boolean canRead(Class<?> clazz,MediaType mediaType) {
		return true;
	}

	public boolean canWrite(Class<?> clazz,MediaType mediaType) {
		return true;
	}

	protected boolean supports(Class<?> clazz) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param clazz
	 * @param inputMessage
	 * @return
	 * @throws IOException
	 * @throws HttpMessageNotReadableException
	 */
	protected Object readInternal(Class<?> clazz,HttpInputMessage inputMessage) throws IOException,HttpMessageNotReadableException {
		Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
		String message = StreamUtils.copyToString(inputMessage.getBody(),charset);
		if(message.trim().startsWith("{")){
			return JSON.parseObject(message,clazz);
		}else{
			return JSON.parseArray(message,clazz);
		}
	}

	/**
	 * 默认字体不是utf-8
	 * @param contentType
	 * @return
	 */
	private Charset getContentTypeCharset(MediaType contentType) {
		if(contentType!=null&&contentType.getCharSet()!=null){
			return contentType.getCharSet();
		}else{
			return DEFAULT_CHARSET;
		}
	}

	protected void writeInternal(Object o,HttpOutputMessage outputMessage) throws IOException,HttpMessageNotWritableException {
		// CodeTransSerializer.getInstance().trans(o);// 翻译处理
		String jsonString = JSON.toJSONString(o,this.serializerFeature);
		if(jsonString.charAt(0)==34){
			jsonString = jsonString.substring(0,jsonString.length()-1).replaceFirst("\"","").replaceAll("\\\\","");
		}

		OutputStream out = outputMessage.getBody();
		out.write(jsonString.getBytes(DEFAULT_CHARSET.name()));
		out.flush();
	}
}

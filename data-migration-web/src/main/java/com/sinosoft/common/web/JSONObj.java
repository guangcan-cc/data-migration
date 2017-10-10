/******************************************************************************
 * CREATETIME : 2016年6月11日 下午9:18:17
 ******************************************************************************/
package com.sinosoft.common.web;

import static com.alibaba.fastjson.util.TypeUtils.castToBigDecimal;
import static com.alibaba.fastjson.util.TypeUtils.castToBigInteger;
import static com.alibaba.fastjson.util.TypeUtils.castToBoolean;
import static com.alibaba.fastjson.util.TypeUtils.castToByte;
import static com.alibaba.fastjson.util.TypeUtils.castToBytes;
import static com.alibaba.fastjson.util.TypeUtils.castToDate;
import static com.alibaba.fastjson.util.TypeUtils.castToDouble;
import static com.alibaba.fastjson.util.TypeUtils.castToFloat;
import static com.alibaba.fastjson.util.TypeUtils.castToInt;
import static com.alibaba.fastjson.util.TypeUtils.castToLong;
import static com.alibaba.fastjson.util.TypeUtils.castToShort;
import static com.alibaba.fastjson.util.TypeUtils.castToSqlDate;
import static com.alibaba.fastjson.util.TypeUtils.castToTimestamp;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;

/**
 * <pre></pre>
 * @author ★LiuPing
 * @CreateTime 2016年6月11日
 */
public class JSONObj extends JSON implements Map<String,Object>,Cloneable,Serializable,InvocationHandler {

	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	private final Map<String,Object> map;

	public JSONObj(){
		this(DEFAULT_INITIAL_CAPACITY,false);
	}

	public JSONObj(Map<String,Object> map){
		this.map = map;
	}

	public JSONObj(boolean ordered){
		this(DEFAULT_INITIAL_CAPACITY,ordered);
	}

	public JSONObj(int initialCapacity){
		this(initialCapacity,false);
	}

	public JSONObj(int initialCapacity,boolean ordered){
		if(ordered){
			map = new LinkedHashMap<String,Object>(initialCapacity);
		}else{
			map = new HashMap<String,Object>(initialCapacity);
		}
	}

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Object get(Object key) {
		return map.get(key);
	}

	public <T> List<T> getList(String key,Class<T> clazz) {
		Object value = map.get(key);
		return JSON.parseArray(value.toString(),clazz);
		/*
		if(value instanceof JSONArray){
			JSONArray array = (JSONArray)value;
			List<T> list = new ArrayList<T>();
			for(int i = 0; i<array.size(); i++ ){
				list.add(array.getObject(i,clazz));
			}
			return list;
		}
		return (List<T>)toJSON(value);
		*/
	}

	public <T> T getObject(String key,Class<T> clazz) {
		Object obj = map.get(key);
		return JSON.parseObject(obj.toString(),clazz);
		// return TypeUtils.castToJavaBean(obj,clazz);//这个方法日期格式错误
	}

	public Boolean getBoolean(String key) {
		Object value = get(key);

		if(value==null){
			return null;
		}

		return castToBoolean(value);
	}

	public byte[] getBytes(String key) {
		Object value = get(key);

		if(value==null){
			return null;
		}

		return castToBytes(value);
	}

	public boolean getBooleanValue(String key) {
		Object value = get(key);

		if(value==null){
			return false;
		}

		return castToBoolean(value).booleanValue();
	}

	public Byte getByte(String key) {
		Object value = get(key);

		return castToByte(value);
	}

	public byte getByteValue(String key) {
		Object value = get(key);

		if(value==null){
			return 0;
		}

		return castToByte(value).byteValue();
	}

	public Short getShort(String key) {
		Object value = get(key);

		return castToShort(value);
	}

	public short getShortValue(String key) {
		Object value = get(key);

		if(value==null){
			return 0;
		}

		return castToShort(value).shortValue();
	}

	public Integer getInteger(String key) {
		Object value = get(key);

		return castToInt(value);
	}

	public int getIntValue(String key) {
		Object value = get(key);

		if(value==null){
			return 0;
		}

		return castToInt(value).intValue();
	}

	public Long getLong(String key) {
		Object value = get(key);

		return castToLong(value);
	}

	public long getLongValue(String key) {
		Object value = get(key);

		if(value==null){
			return 0L;
		}

		return castToLong(value).longValue();
	}

	public Float getFloat(String key) {
		Object value = get(key);

		return castToFloat(value);
	}

	public float getFloatValue(String key) {
		Object value = get(key);

		if(value==null){
			return 0F;
		}

		return castToFloat(value).floatValue();
	}

	public Double getDouble(String key) {
		Object value = get(key);

		return castToDouble(value);
	}

	public double getDoubleValue(String key) {
		Object value = get(key);

		if(value==null){
			return 0D;
		}

		return castToDouble(value);
	}

	public BigDecimal getBigDecimal(String key) {
		Object value = get(key);

		return castToBigDecimal(value);
	}

	public BigInteger getBigInteger(String key) {
		Object value = get(key);

		return castToBigInteger(value);
	}

	public String getString(String key) {
		Object value = get(key);

		if(value==null){
			return null;
		}

		return value.toString();
	}

	public Date getDate(String key) {
		Object value = get(key);

		return castToDate(value);
	}

	public java.sql.Date getSqlDate(String key) {
		Object value = get(key);

		return castToSqlDate(value);
	}

	public java.sql.Timestamp getTimestamp(String key) {
		Object value = get(key);

		return castToTimestamp(value);
	}

	public Object put(String key,Object value) {
		return map.put(key,value);
	}

	public void putAll(Map<? extends String,? extends Object> m) {
		map.putAll(m);
	}

	public void clear() {
		map.clear();
	}

	public Object remove(Object key) {
		return map.remove(key);
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public Collection<Object> values() {
		return map.values();
	}

	public Set<Map.Entry<String,Object>> entrySet() {
		return map.entrySet();
	}

	@Override
	public Object clone() {
		return new JSONObject(new HashMap<String,Object>(map));
	}

	public boolean equals(Object obj) {
		return this.map.equals(obj);
	}

	public int hashCode() {
		return this.map.hashCode();
	}

	@Override
	public Object invoke(Object proxy,Method method,Object[] args) throws Throwable {
		Class<?>[] parameterTypes = method.getParameterTypes();
		if(parameterTypes.length==1){
			if(method.getName().equals("equals")){
				return this.equals(args[0]);
			}

			Class<?> returnType = method.getReturnType();
			if(returnType!=void.class){
				throw new JSONException("illegal setter");
			}

			String name = null;
			JSONField annotation = method.getAnnotation(JSONField.class);
			if(annotation!=null){
				if(annotation.name().length()!=0){
					name = annotation.name();
				}
			}

			if(name==null){
				name = method.getName();

				if( !name.startsWith("set")){
					throw new JSONException("illegal setter");
				}

				name = name.substring(3);
				if(name.length()==0){
					throw new JSONException("illegal setter");
				}
				name = Character.toLowerCase(name.charAt(0))+name.substring(1);
			}

			map.put(name,args[0]);
			return null;
		}

		if(parameterTypes.length==0){
			Class<?> returnType = method.getReturnType();
			if(returnType==void.class){
				throw new JSONException("illegal getter");
			}

			String name = null;
			JSONField annotation = method.getAnnotation(JSONField.class);
			if(annotation!=null){
				if(annotation.name().length()!=0){
					name = annotation.name();
				}
			}

			if(name==null){
				name = method.getName();
				if(name.startsWith("get")){
					name = name.substring(3);
					if(name.length()==0){
						throw new JSONException("illegal getter");
					}
					name = Character.toLowerCase(name.charAt(0))+name.substring(1);
				}else if(name.startsWith("is")){
					name = name.substring(2);
					if(name.length()==0){
						throw new JSONException("illegal getter");
					}
					name = Character.toLowerCase(name.charAt(0))+name.substring(1);
				}else if(name.startsWith("hashCode")){
					return this.hashCode();
				}else if(name.startsWith("toString")){
					return this.toString();
				}else{
					throw new JSONException("illegal getter");
				}
			}

			Object value = map.get(name);
			return TypeUtils.cast(value,method.getGenericReturnType(),ParserConfig.getGlobalInstance());
		}

		throw new UnsupportedOperationException(method.toGenericString());
	}

}

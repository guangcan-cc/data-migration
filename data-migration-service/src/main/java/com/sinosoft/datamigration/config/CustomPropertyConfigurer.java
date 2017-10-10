/******************************************************************************
 * CREATETIME : 2016年2月23日 上午10:24:08
 ******************************************************************************/
package com.sinosoft.datamigration.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.SpringProperties;

/**
 * 系统配置文件初始化，可以使用本类的getProperty方法读取Spring的配置文件，也可以用SpringProperties.getProperty
 * @author ★LiuPing
 */
public class CustomPropertyConfigurer extends PropertyPlaceholderConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(CustomPropertyConfigurer.class);
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory,Properties props) throws BeansException {
		super.processProperties(beanFactory,props);
		// load properties to SpringProperties
		for(Object key:props.keySet()){
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			SpringProperties.setProperty(keyStr,value);
			logger.info("Load property {}={}",key,value);
		}
	}

}

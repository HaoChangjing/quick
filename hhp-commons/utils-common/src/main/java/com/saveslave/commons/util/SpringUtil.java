package com.saveslave.commons.util;

import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @ClassName: SpringUtil
 * @Description: spring获取bean工具类
 *
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext ;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}
		Object arguments = applicationContext.getBean("springApplicationArguments");
		if(arguments!=null  && arguments instanceof ApplicationArguments){
			String[] sourceArgs = ((ApplicationArguments) arguments).getSourceArgs();
			System.out.println("startup params is "+ Arrays.toString(sourceArgs));

		}

	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	//通过name获取 Bean.
	public static Object getBean(String name){
		return getApplicationContext().getBean(name);
	}

	//通过class获取Bean.
	public static <T> T getBean(Class<T> clazz){
		return getApplicationContext().getBean(clazz);
	}

	public static <T>  Collection<T>  getBeansOfType(Class<T> clazz){
       return  getApplicationContext().getBeansOfType(clazz).values();
    }
	//通过name,以及Clazz返回指定的Bean
	public static <T> T getBean(String name,Class<T> clazz){
		return getApplicationContext().getBean(name, clazz);
	}
	public static String getProperty(String key) {
		return applicationContext.getBean(Environment.class).getProperty(key);
	}
}

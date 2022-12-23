package br.com.lojavirtual;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ApplicationContextLoad implements ApplicationContextAware {

	
	@Autowired
	private static ApplicationContext applicationContext;
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	

}

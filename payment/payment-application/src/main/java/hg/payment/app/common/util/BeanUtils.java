package hg.payment.app.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils implements ApplicationContextAware{

	private static ApplicationContext appContext;
	
	
	
	public static <T> Map<String,T> getBean(Class<T> clazz){
		Map<String,T> map = new HashMap<String,T>();
		map = appContext.getBeansOfType(clazz);
		return map;
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appContext = applicationContext;
		
	}
	

}

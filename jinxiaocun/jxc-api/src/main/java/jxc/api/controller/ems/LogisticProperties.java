package jxc.api.controller.ems;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class LogisticProperties {
	
	private  Properties properties = null;
	private  final String PROP_PATH = "/logistic.properties";
	private  static LogisticProperties payProperties;
	private  LogisticProperties() {
		init();
	}
	private void init(){
		try {
			InputStream inputStream = getInputStream();
			properties = new Properties();
			properties.load(inputStream);
			inputStream.close();
			System.out.println("SysProperties.init()");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//LogMessage.logwrite(this.getClass(), e.getMessage());
		}
	}
   
	private InputStream getInputStream(){
	   return LogisticProperties.class.getResourceAsStream(PROP_PATH);
    }
	public static LogisticProperties getInstance() {
		if (payProperties == null) {
			payProperties = new LogisticProperties();
        }
		return payProperties;
	}
	
	public String get(String key){
		 return properties.getProperty(key);
	}

}

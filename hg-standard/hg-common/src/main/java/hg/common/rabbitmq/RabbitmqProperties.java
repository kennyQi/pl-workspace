package hg.common.rabbitmq;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RabbitmqProperties {

	private  Properties properties = null;
	private  final String PROP_PATH = "/rabbitmq.properties";
	private  static RabbitmqProperties rabbitmqProperties;
	private  RabbitmqProperties() {
		init();
	}
	private void init(){
		try {
			InputStream inputStream = getInputStream();
			properties = new Properties();
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
		}
	}
   
	private InputStream getInputStream(){
	   return RabbitmqProperties.class.getResourceAsStream(PROP_PATH);
    }
	public static RabbitmqProperties getInstance() {
		if (rabbitmqProperties == null) {
			rabbitmqProperties = new RabbitmqProperties();
        }
		return rabbitmqProperties;
	}
	
	public String get(String key){
		 return properties.getProperty(key);
	}
}

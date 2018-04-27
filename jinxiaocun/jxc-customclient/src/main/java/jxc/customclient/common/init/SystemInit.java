//package slfx.mp.tcclient.common.init;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Map;
//import java.util.Properties;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Component;
//
//import com.sflx.mp.tc.service.ConnectService;
//import com.sflx.mp.tc.util.ReadProperties;
//@Component
//public class SystemInit implements InitializingBean {
//	private static Logger logger =LoggerFactory.getLogger(SystemInit.class);
//	private Map<String, ConnectService> serviceMap;
//	public String getUp(){
//		String up="";
//		Properties prop = new Properties(); 
//    	InputStream in = Object.class.getResourceAsStream("/system.properties"); 
//    	try { 
//	        prop.load(in); 
//	        up=prop.getProperty("up").trim();
//	    } catch (IOException e) { 
//	    	logger.error(e.getMessage());
//	        e.printStackTrace(); 
//	    } 
//    	return up;
//	}
//		
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		String up=getUp();
//		String[] ups=up.split(",");
//		for(int i=0;i<ups.length;i++){
//			if(StringUtils.isNotEmpty(ups[i])){
//				String[] keyVal=ups[i].split(":");
//				if(keyVal.length==2){
//					if(StringUtils.isNotEmpty(keyVal[0])&&StringUtils.isNotEmpty(keyVal[1])){
//						try {
//							serviceMap.put(keyVal[0], (ConnectService) Class.forName(keyVal[1]).newInstance());
//						} catch (InstantiationException e) {
//							logger.error(e.getMessage());
//							e.printStackTrace();
//						} catch (IllegalAccessException e) {
//							logger.error(e.getMessage());
//							e.printStackTrace();
//						} catch (ClassNotFoundException e) {
//							logger.error(e.getMessage());
//							e.printStackTrace();
//						}
//					}
//				}
//				
//			}
//		}
//		
//	}
//
//	public Map<String, ConnectService> getServiceMap() {
//		return serviceMap;
//	}
//
//	public void setServiceMap(Map<String, ConnectService> serviceMap) {
//		this.serviceMap = serviceMap;
//	}
//	
//}

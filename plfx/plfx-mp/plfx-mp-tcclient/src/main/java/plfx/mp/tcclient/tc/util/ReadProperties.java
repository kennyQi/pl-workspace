package plfx.mp.tcclient.tc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadProperties {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ReadProperties.class);

//	 	private static String tc_url; 
//	    private static String tc_url_test; 
//	    private static String tc_is_test;
//	    private static String tc_verson;
//	    private static String tc_imageBaseUrl;
//	    private static String client_id;
//	    private static String account_id;
//	    private static String account_key;
//	    private static String tc_base_url;
//	    private static String tc_base_url_test;
//	    private static String tc_order_url;
//	    private static String tc_order_url_test;
	    

//		tc_url=http\://tcopenapi.17usoft.com/handlers/scenery/queryhandler.ashx
//			tc_url_test=http\://tcopenapitest.17usoft.com/handlers/scenery/queryhandler.ashx
//			tc_base_url=http\://tcopenapi.17usoft.com/Handlers/General/AdministrativeDivisionsHandler.ashx
//			tc_base_url_test=http\://tcopenapitest.17usoft.com/Handlers/General/AdministrativeDivisionsHandler.ashx
//			tc_order_url=http://tcopenapi.17usoft.com/handlers/scenery/orderhandler.ashx
//			tc_order_url_test=http://tcopenapitest.17usoft.com/handlers/scenery/orderhandler.ashx
//			tc_is_test=0
//			tc_verson=20111128102912
//			tc_imageBaseUrl=http\://upload.17u.com/uploadfile/
//			client_id=127.0.0.1
//			tc_account_id=bf298db9-5398-4964-a022-254ac8905252
//			tc_account_key=55eeb4b9004dd5aa
		
	 	private static String tc_url = "http://tcopenapi.17usoft.com/handlers/scenery/queryhandler.ashx"; 
	    private static String tc_url_test= "http://tcopenapitest.17usoft.com/handlers/scenery/queryhandler.ashx"; 
	    private static String tc_is_test= "0";
	    private static String tc_verson= "20111128102912";
	    private static String tc_imageBaseUrl= "http://upload.17u.com/uploadfile/";
	    private static String client_id= "127.0.0.1";
	    private static String account_id= "bf298db9-5398-4964-a022-254ac8905252";
	    private static String account_key= "55eeb4b9004dd5aa";
	    private static String tc_base_url= "http://tcopenapi.17usoft.com/Handlers/General/AdministrativeDivisionsHandler.ashx";
	    private static String tc_base_url_test= "http://tcopenapitest.17usoft.com/Handlers/General/AdministrativeDivisionsHandler.ashx";
	    private static String tc_order_url= "http://tcopenapi.17usoft.com/handlers/scenery/orderhandler.ashx";
	    private static String tc_order_url_test= "http://tcopenapitest.17usoft.com/handlers/scenery/orderhandler.ashx";


//	    static { 
//	        Properties prop = new Properties(); 
//	        InputStream in = Object.class.getResourceAsStream("/tc_param.properties"); 
//	        try { 
//	            prop.load(in); 
//	            tc_url = prop.getProperty("tc_url").trim(); 
//	            tc_url_test = prop.getProperty("tc_url_test").trim(); 
//	            tc_is_test = prop.getProperty("tc_is_test").trim(); 
//	            tc_verson = prop.getProperty("tc_verson").trim(); 
//	            tc_imageBaseUrl = prop.getProperty("tc_imageBaseUrl").trim(); 
//	            client_id = prop.getProperty("client_id").trim();
//	            account_id=prop.getProperty("tc_account_id").trim();
//	            account_key=prop.getProperty("tc_account_key").trim();
//	            tc_base_url=prop.getProperty("tc_base_url").trim();
//	            tc_base_url_test=prop.getProperty("tc_base_url_test").trim();
//	            tc_order_url=prop.getProperty("tc_order_url").trim();
//	            tc_order_url_test=prop.getProperty("tc_order_url_test").trim();
//	        } catch (IOException e) { 
//	        	logger.error(e.getMessage());
//	            e.printStackTrace(); 
//	        } 
//	    } 

//	    /** 
//	     * 私有构造方法，不需要创建对象 
//	     */ 
//	    private ReadProperties() { 
//	    	
//	    } 

	    public static String getTc_url() {
			return tc_url;
		}

		public static String getTc_url_test() {
			return tc_url_test;
		}

		public static String getTc_is_test() {
			return tc_is_test;
		}


		public static String getTc_verson() {
			return tc_verson;
		}

		public static String getTc_imageBaseUrl() {
			return tc_imageBaseUrl;
		}

		public static String getClient_id() {
			return client_id;
		}
		public static String getAccount_id() {
			return account_id;
		}

		public static String getAccount_key() {
			return account_key;
		}
		
		public static String getTc_base_url() {
			return tc_base_url;
		}

		public static String getTc_base_url_test() {
			return tc_base_url_test;
		}

		public static String getTc_order_url() {
			return tc_order_url;
		}

		public static String getTc_order_url_test() {
			return tc_order_url_test;
		}

		public static void main(String args[]){ 
	       // System.out.println(getTc_url()); 
	       // System.out.println(getTc_is_test()); 
	    }

		
}

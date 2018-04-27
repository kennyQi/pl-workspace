/**
 * @文件名称：JfProperty.java
 * @类路径：hgtech.jfaddmin
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月15日下午3:31:34
 */
package hgtech.jf;

import hgtech.jf.entity.dao.BaseEntityFileDao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @类功能说明：积分系统属性配置
 * @类修改者：
 * @修改日期：2014年10月15日下午3:31:34
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月15日下午3:31:34
 *
 */
public class JfProperty {
	private static JfProperty me=new JfProperty();
	private Properties properties;
	public static final String K_SYS_DOMAIN_NAME = "sysDomain.name";
	public static final String K_SYS_DOMAIN_CODE = "sysDomain.code";

	void doinit(){
		InputStream inStream=JfProperty.class.getResourceAsStream("/jf.properties");
		System.out.println("jf.properties 文件的位置为 "+JfProperty.class.getResource("/jf.properties"));
		properties = new Properties();
		try {
			if(inStream!=null)
				properties.load(new InputStreamReader(inStream, "utf-8"));
			System.out.println("the JfProperty: " +" \n"+properties);
		} catch (IOException e) {
		    	e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月26日下午2:17:36
	 * @修改内容：
	 * @参数：@return
	 * @return:int
	 * @throws
	 */
	public static int getJfValidYear() {
		//积分有效期（年）
		String property = getProperties().getProperty("jfValidYear", "-1");
		int validY = Integer.parseInt(property);
		return validY;
	}
	
	
	public static String getJfPath() {
		//积分有效期（年）
		String property = getProperties().getProperty("jfPath","jfPath");
		 return property;
	}
	/**
	 * 
	 * @return 商城结算周期,消费撤销等交易时候，在途积分生效天数
	 */
	public static int getMallSettlementCycle() {
		//结算周期
		String property = getProperties().getProperty("mallSettlementCycle", "0");
		int mallSettlementCycle = Integer.parseInt(property);
		return mallSettlementCycle;
	}

	/**
	 * 积分奖励控制
	 * @return
	 */
	public static String getJfRuleStatus() {
		String jfRuleStatus = getProperties().getProperty("jfRuleStatus", "1");
		return jfRuleStatus;
	}

	public synchronized static Properties getProperties(){
		if(me.properties==null || me.properties.size()==0)
			me.doinit();
		return me.properties;
	}
	
	public static void main(String[] args) {
		System.out.println(JfProperty.getProperties());
	}

	public static String getMallUrl(){
		return me.properties.getProperty("mallUrl","http://mall.51hjf.com");
	}
	public static int getExpireWithinDays(){
		return Integer.parseInt( me.properties.getProperty("expireDays","7"));
	}
	public static int getDelayCalTime(){
		return Integer.parseInt( me.properties.getProperty("delayCalTime","30000"));
	}
	public static String geturlAdmin(){
		return me.properties.getProperty("urlAdmin");
	}
//	public static String getIdCheckUrl(){
//		return me.properties.getProperty("idCheckUrl", "http://121.79.134.210:8923/auth/IdentifyInterfaceAction.identify.do");
//	}
	/*
	 * 是否检查实名认证。如果检查，将影响奖励积分的使用
	 */
	public static boolean checkReal(){
		return Boolean.parseBoolean(me.properties.getProperty("checkReal", "false"));
	}
	
	public static String getIdCheckSecretKey(){
		return  (me.properties.getProperty("idCheckSecretKey", "1e83fa97223c48d682cefc5ea78b0795"));
	}
	
	public static String getIdCheckUrl(){
		return  (me.properties.getProperty("idCheckUrl", "http://sfrztest.hg365.com/hg_auth/api"));
	}
	
	public static boolean isProductEnv(){
		return Boolean.parseBoolean(me.properties.getProperty("isProduct", "true"));
	}
	
	/**
	 * 
	 * @return 没有身份认证下，奖励积分使用的限额
	 */
	public static int unRealBonusAmount(){
		return Integer.parseInt(me.properties.getProperty("unRealBonusAmount","0"));
	}
}

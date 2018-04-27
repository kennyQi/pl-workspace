package plfx.mp.tcclient.tc.pojo;

import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

import plfx.mp.tcclient.tc.util.ReadProperties;
import plfx.mp.tcclient.tc.util.TcSign;
import plfx.mp.tcclient.tc.util.TcTimeUtils;

public class RequestHead {
	/**
	 * 接口协议版本号，详见接口协议文档
	 */
	private String version;
	/**
	 *  API帐户ID(小写)，待申请审批通过后发放
	 */
	private String accountID;
	
	
	/**
	 * 调用接口的方法名称
	 */
	private String serviceName;
	/**
	 * 验证签名sign
	 */
	private String digitalSign;
	
	/**
	 * 当前日期
	 */
	private String reqTime;


	private RequestHead(){
	}
	
	public static RequestHead getRequestHead(String serviceName) throws Exception{
		RequestHead head=new RequestHead();
		head.setReqTime(TcTimeUtils.getCurrentTime());
		head.setVersion(ReadProperties.getTc_verson());
		head.setAccountID(ReadProperties.getAccount_id());
		head.setServiceName(serviceName);
		Hashtable<String, String> ht = new Hashtable<String, String>(); 
		ht.put("version",head.getVersion()); // 接口协议版本号，详见接口协议文档
		ht.put("accountId", head.getAccountID()); // API帐户ID(小写)，待申请审批通过后发放
		ht.put("accountKey", ReadProperties.getAccount_key()); // API帐户密钥，待申请审批通过后发放
		ht.put("serviceName", serviceName); // 调用接口的方法名称
		ht.put("reqTime",head.getReqTime()); // 当前日期
		head.setDigitalSign(TcSign.createDigitalSign(ht));
		
		return head;
	}
	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAccountID() {
		return accountID;
	}
	//设置为小写
	public void setAccountID(String accountID) {
		if(!StringUtils.isEmpty(accountID)){
			accountID=accountID.toLowerCase();
		}
		this.accountID = accountID;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDigitalSign() {
		return digitalSign;
	}

	public void setDigitalSign(String digitalSign) {
		this.digitalSign = digitalSign;
	}
	
}

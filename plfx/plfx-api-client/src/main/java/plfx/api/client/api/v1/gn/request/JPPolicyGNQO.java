package plfx.api.client.api.v1.gn.request;

import plfx.api.client.common.BaseClientQO;
import plfx.api.client.common.api.PlfxApiAction;


/****
 * 
 * @类功能说明：政策查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月3日下午3:05:15
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@PlfxApiAction(PlfxApiAction.GN_QueryAirPolicy)
public class JPPolicyGNQO extends BaseClientQO {


	/** 
	 * 加密字符串 
	 * 来自于QueryFlight的结果  Y   (Y代表必填N代表可以不填)  
	 */
	private String encryptString;

	/** 
	 * 政策取向   
	 * 1：仅定向；2：定向优先      N
	 */
	private String airpGet;
	
	/**
	 * 政策来源   
	 * 供应商代码，用/分隔    N
	 */ 
	private String airpSource;
	
	/**
	 * 票号类型   
	 * 1--B2B，2—BSP，3—所有    Y
	 */
	private Integer tickType;
	
	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}

	public String getAirpGet() {
		return airpGet;
	}

	public void setAirpGet(String airpGet) {
		this.airpGet = airpGet;
	}

	public String getAirpSource() {
		return airpSource;
	}

	public void setAirpSource(String airpSource) {
		this.airpSource = airpSource;
	}

	public Integer getTickType() {
		return tickType;
	}

	public void setTickType(Integer tickType) {
		this.tickType = tickType;
	}

	
}

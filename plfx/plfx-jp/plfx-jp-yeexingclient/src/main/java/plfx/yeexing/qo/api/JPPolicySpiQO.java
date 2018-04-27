package plfx.yeexing.qo.api;

import hg.common.component.BaseQo;

/***
 * 
 * @类功能说明：政策查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月23日上午10:34:57
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPPolicySpiQO extends BaseQo{

	/** 
	 * 加密字符串 来自于QueryFlight的结果  Y   (Y代表必填N代表可以不填)  
	 */
	private String encryptString;

	/** 
	 * 政策取向   1：仅定向；2：定向优先      N
	 */
	private String airpGet;
	
	/**
	 * 政策来源   供应商代码，用/分隔    N
	 */ 
	private String airpSource;
	
	/**
	 * 票号类型   1--B2B，2—BSP，3—所有    Y
	 */
	private Integer tickType;
	
	/**
	 * 签名   所有参数经MD5加密算法后得出的结果     Y
	 */
	private String sign;

	/** 
	 * 合作伙伴用户名
	 */
	private String userName;
	
	/**
	 * 起飞日期 格式：2012-05-10
	 */
	private String startDate;
	/**
	 * 航班号 : CA8906
	 */
	private String flightNo;
	
	/***
	 * 密钥
	 */
	private String key;
	
	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}

package plfx.yeexing.qo.api;


import hg.common.component.BaseQo;

/****
 * 
 * @类功能说明：航班查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月15日上午9:21:24
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPFlightSpiQO extends BaseQo{

	/**
	 * 始发地  格式：上海
	 */
	private String orgCity;

	/**
	 * 目的地 格式：杭州
	 */
	private String dstCity;

	/**
	 * 起飞日期 格式：2012-05-10
	 */
	private String startDate;
	
	/**
	 * 起飞时间 格式：7:00
	 */
	private String startTime;
	
	/***
	 *航空公司             
	 *格式：上海航空公司
	 */
	private String airCompany;
	
	/***
	 *航空公司             
	 *格式：上航
	 */
	private String airCompanyShotName;

	/**
	 * 合作伙伴用户名  合作伙伴在易行天下的用户名
	 */
	private String userName;
	/***
	 * 密钥
	 */
	private String key;
	/**
	 * 签名   所有参数经MD5加密算法后得出的结果
	 */
	private String sign;
	
	/**
	 * 航班号 : CA8906
	 */
	private String flightNo;

	public String getOrgCity() {
		return orgCity;
	}

	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}

	public String getDstCity() {
		return dstCity;
	}

	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getAirCompany() {
		return airCompany;
	}

	public void setAirCompany(String airCompany) {
		this.airCompany = airCompany;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getAirCompanyShotName() {
		return airCompanyShotName;
	}

	public void setAirCompanyShotName(String airCompanyShotName) {
		this.airCompanyShotName = airCompanyShotName;
	}

}

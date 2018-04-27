package hsl.api.v1.request.qo.jp;

import hsl.api.base.ApiPayload;

/**
 * 政策查询
 * 
 * @author yuxx
 * 
 */
public class JPPolicyQO extends ApiPayload {

	/** 
	 * 航班号
	 */
	private String flightNo;

	/** 
	 * 舱位代码 
	 */
	private String classCode;
	
	/**
	 * 航班日期
	 * yyyyMMdd
	 */
	private String departDate;

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getDepartDate() {
		return departDate;
	}

	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}

}

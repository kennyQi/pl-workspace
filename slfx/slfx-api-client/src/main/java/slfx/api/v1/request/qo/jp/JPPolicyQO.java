package slfx.api.v1.request.qo.jp;

import slfx.api.base.ApiPayload;

/**
 * 
 * @类功能说明：政策查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月1日下午4:46:31
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
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
	
	/**
	 * 经销商代码
	 */
	private String dealerCode;

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

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

}

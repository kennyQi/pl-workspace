package slfx.api.v1.request.qo.jp;

import slfx.api.base.ApiPayload;

/**
 * 
 * @类功能说明：航班查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月1日下午4:45:52
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPFlightQO extends ApiPayload {

	/**
	 * 出发机场的三字代码(例如：PEK 北京)
	 */
	private String from;

	/**
	 * 目地机场三字代码
	 */
	private String arrive;

	/**
	 * 航班日期 (例如：2008-03-16)
	 */
	private String date;
	
	/**
	 * 航班 : CA8906
	 */
	private String flightNo;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

}

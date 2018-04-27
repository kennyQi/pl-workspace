package slfx.jp.qo.api;

import java.util.Date;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：航班经停信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:14:35
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class FlightStopQO extends BaseQo {
	
	/**
	 * 航班号(例如：ZH9677)
	 */
	private String flightNo;
	
	/**
	 * 航班日期 (例如：2012-10-29)
	 */
	private Date date;
	
	/**
	 * 返回数据输出格式： 
	 * [X]:输出XML格式(默认) 
	 * [J]:输出JSON格式
	 */
	private String dataFormat;

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
	
}

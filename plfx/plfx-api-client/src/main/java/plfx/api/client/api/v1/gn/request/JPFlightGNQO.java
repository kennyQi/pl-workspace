package plfx.api.client.api.v1.gn.request;

import java.util.Date;

import plfx.api.client.common.BaseClientQO;
import plfx.api.client.common.api.PlfxApiAction;

/****
 * 
 * @类功能说明：航班查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月3日下午3:04:58
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@PlfxApiAction(PlfxApiAction.GN_QueryFlightList)
public class JPFlightGNQO extends BaseClientQO {

	/**
	 * 始发地  
	 * 格式：上海	
	 */
	private String orgCity;

	/**
	 * 目的地 
	 * 格式：杭州
	 */
	private String dstCity;

	/**
	 * 起飞日期 格式：2012-05-10
	 */
//	private String startDate;
	private Date startDate;
	
	/**
	 * 起飞时间 
	 * 格式：7:00
	 */
//	private String startTime;
	private Date startTime;
	/***
	 *航空公司             
	 *格式：上海航空公司
	 */
	private String airCompany;
	
	/***
	 *航空公司     简称
	 *格式：上航
	 */
	private String airCompanyShotName;
	
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

//	public String getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(String startDate) {
//		this.startDate = startDate;
//	}
//
//	public String getStartTime() {
//		return startTime;
//	}
//
//	public void setStartTime(String startTime) {
//		this.startTime = startTime;
//	}

	
	public String getAirCompany() {
		return airCompany;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setAirCompany(String airCompany) {
		this.airCompany = airCompany;
	}

	public String getAirCompanyShotName() {
		return airCompanyShotName;
	}

	public void setAirCompanyShotName(String airCompanyShotName) {
		this.airCompanyShotName = airCompanyShotName;
	}

}

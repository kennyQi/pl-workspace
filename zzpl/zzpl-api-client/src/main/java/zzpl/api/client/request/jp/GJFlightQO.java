package zzpl.api.client.request.jp;

import java.util.Date;

import zzpl.api.client.base.ApiPayload;

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
public class GJFlightQO extends ApiPayload {

	/**
	 * 始发地 格式：SHA
	 */
	private String orgCity;

	/**
	 * 目的地 格式：PEK
	 */
	private String dstCity;

	/**
	 * 起飞日期(精确到日)
	 */
	private Date orgDate;

	/**
	 * 回程起飞日期(精确到日)
	 */
	private Date dstDate;

	/**
	 * 排序条件
	 * 1：起飞时间
	 * 2：价格
	 */
	private String orderBy;
	
	public final static Integer TAKEOFF_START_TIME = 1;
	
	public final static Integer BACK_START_TIME = -1;
	
	public final static Integer IBE_PRICE = 2;
	
	/**
	 *顺序
	 *1：asc
	 *2：desc
	 */
	private String orderType;
	
	public final static Integer ASC = 1;
	
	public final static Integer DESC = 2;

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

	public Date getOrgDate() {
		return orgDate;
	}

	public void setOrgDate(Date orgDate) {
		this.orgDate = orgDate;
	}

	public Date getDstDate() {
		return dstDate;
	}

	public void setDstDate(Date dstDate) {
		this.dstDate = dstDate;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}

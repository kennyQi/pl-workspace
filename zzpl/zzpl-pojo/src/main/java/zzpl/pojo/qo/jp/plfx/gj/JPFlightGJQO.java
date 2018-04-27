package zzpl.pojo.qo.jp.plfx.gj;

import hg.common.component.BaseQo;

import java.util.Date;

@SuppressWarnings("serial")
public class JPFlightGJQO extends BaseQo{
	/**
	 * 始发地城市三字码
	 */
	private String orgCity;

	/**
	 * 目的地城市三字码
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

}
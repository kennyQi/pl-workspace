package plfx.api.client.api.v1.gj.request;

import java.util.Date;

import plfx.api.client.common.BaseClientQO;
import plfx.api.client.common.api.PlfxApiAction;

/**
 * @类功能说明：国际航班查询
 * @类修改者：
 * @修改日期：2015-7-6下午4:00:29
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-6下午4:00:29
 */
@SuppressWarnings("serial")
@PlfxApiAction(PlfxApiAction.GJ_QueryFlight)
public class FlightGJQO extends BaseClientQO {

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

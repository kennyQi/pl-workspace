package hg.dzpw.dealer.client.api.v1.request;

import hg.dzpw.dealer.client.common.BaseClientPageQO;
import hg.dzpw.dealer.client.common.api.DealerApiAction;

import java.util.Date;

/**
 * @类功能说明：门票政策查询
 * @类修改者：
 * @修改日期：2014-11-21下午5:17:27
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-21下午5:17:27
 */
@SuppressWarnings("serial")
@DealerApiAction(DealerApiAction.QueryTicketPolicy)
public class TicketPolicyQO extends BaseClientPageQO {

	/**
	 * 门票政策ID
	 */
	private String[] ticketPolicyIds;

	/**
	 * 景区ID
	 */
	private String scenicSpotId;

	/**
	 * 门票政策类型 1独立单票 2联票
	 */
	private Integer type;

	/**
	 * 修改时间开始
	 */
	private Date modifyDateBegin;

	/**
	 * 修改时间截止
	 */
	private Date modifyDateEnd;

	/**
	 * 是否查询价格日历
	 */
	private Boolean calendarFetch = false;

	/**
	 * 是否查询门票政策下的单个景区门票政策
	 */
	private Boolean singleTicketPoliciesFetch = false;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String[] getTicketPolicyIds() {
		return ticketPolicyIds;
	}

	public void setTicketPolicyIds(String[] ticketPolicyIds) {
		this.ticketPolicyIds = ticketPolicyIds;
	}

	public Date getModifyDateBegin() {
		return modifyDateBegin;
	}

	public void setModifyDateBegin(Date modifyDateBegin) {
		this.modifyDateBegin = modifyDateBegin;
	}

	public Date getModifyDateEnd() {
		return modifyDateEnd;
	}

	public void setModifyDateEnd(Date modifyDateEnd) {
		this.modifyDateEnd = modifyDateEnd;
	}

	public Boolean getCalendarFetch() {
		return calendarFetch;
	}

	public void setCalendarFetch(Boolean calendarFetch) {
		this.calendarFetch = calendarFetch;
	}

	public Boolean getSingleTicketPoliciesFetch() {
		return singleTicketPoliciesFetch;
	}

	public void setSingleTicketPoliciesFetch(Boolean singleTicketPoliciesFetch) {
		this.singleTicketPoliciesFetch = singleTicketPoliciesFetch;
	}

}

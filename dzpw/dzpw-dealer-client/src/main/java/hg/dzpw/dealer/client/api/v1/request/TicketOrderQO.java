package hg.dzpw.dealer.client.api.v1.request;

import java.util.Date;

import hg.dzpw.dealer.client.common.BaseClientPageQO;
import hg.dzpw.dealer.client.common.api.DealerApiAction;

/**
 * @类功能说明：门票订单查询对象
 * @类修改者：
 * @修改日期：2014-11-21下午5:43:14
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-21下午5:43:14
 */
@SuppressWarnings("serial")
@DealerApiAction(DealerApiAction.QueryTicketOrder)
public class TicketOrderQO extends BaseClientPageQO {

	/**
	 * 订单id
	 */
	private String orderId;

	/**
	 * 下单时间开始
	 */
	private Date createDateBegin;

	/**
	 * 下单时间截止
	 */
	private Date createDateEnd;

	/**
	 * 支付状态(0,未支付；1,已支付；2,已出票；3，交易成功；4，交易关闭)
	 */
	private Integer status;

	/**
	 * 是否查询门票列表
	 */
	private Boolean groupTicketsFetch = false;
	/**
	 * 是否查询单票
	 */
	private Boolean singleTicketsFetch = false;
	/**
	 * 是否查询游客信息
	 */
	private Boolean touristFetch = false;

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public Boolean getGroupTicketsFetch() {
		return groupTicketsFetch;
	}

	public void setGroupTicketsFetch(Boolean groupTicketsFetch) {
		this.groupTicketsFetch = groupTicketsFetch;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Boolean getSingleTicketsFetch() {
		return singleTicketsFetch;
	}

	public void setSingleTicketsFetch(Boolean singleTicketsFetch) {
		this.singleTicketsFetch = singleTicketsFetch;
	}

	public Boolean getTouristFetch() {
		return touristFetch;
	}

	public void setTouristFetch(Boolean touristFetch) {
		this.touristFetch = touristFetch;
	}

}
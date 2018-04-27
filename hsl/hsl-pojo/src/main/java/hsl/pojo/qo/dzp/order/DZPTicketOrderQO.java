package hsl.pojo.qo.dzp.order;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;
import hsl.pojo.util.HSLConstants;

import java.util.Date;

/**
 * 电子票订单查询对象
 *
 * @author zhurz
 * @since 1.8
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "dzpTicketOrderDAO")
public class DZPTicketOrderQO extends BaseQo {

	/**
	 * 票务名称
	 */
	private String ticketPolicyName;

	/**
	 * 票务类型
	 *
	 * @see HSLConstants.DZPWTicketPolicyType
	 */
	private Integer ticketPolicyType;

	/**
	 * 包含景区
	 */
	private String scenicSpotId;

	/**
	 * 用户下单时间 - 开始
	 */
	@QOAttr(name = "orderDate", type = QOAttrType.GE)
	private Date orderDateBegin;
	/**
	 * 用户下单时间 - 截止
	 */
	@QOAttr(name = "orderDate", type = QOAttrType.LE)
	private Date orderDateEnd;
	/**
	 * 预定人
	 */
	@QOAttr(name = "baseInfo.linkMan", type = QOAttrType.LIKE_ANYWHERE)
	private String linkMan;

	/**
	 * 订单编号
	 */
	@QOAttr(name = "baseInfo.orderNo")
	private String orderNo;

	/**
	 * 订单状态
	 *
	 * @see HSLConstants.DZPTicketOrderStatus
	 */
	@QOAttr(name = "status.current")
	private Integer orderStatus;

	/**
	 * 是否获取订单下的门票（查看详情用）
	 */
	private boolean groupTicketsFetch;

	public String getTicketPolicyName() {
		return ticketPolicyName;
	}

	public void setTicketPolicyName(String ticketPolicyName) {
		this.ticketPolicyName = ticketPolicyName;
	}

	public Integer getTicketPolicyType() {
		return ticketPolicyType;
	}

	public void setTicketPolicyType(Integer ticketPolicyType) {
		this.ticketPolicyType = ticketPolicyType;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public Date getOrderDateBegin() {
		return orderDateBegin;
	}

	public void setOrderDateBegin(Date orderDateBegin) {
		this.orderDateBegin = orderDateBegin;
	}

	public Date getOrderDateEnd() {
		return orderDateEnd;
	}

	public void setOrderDateEnd(Date orderDateEnd) {
		this.orderDateEnd = orderDateEnd;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public boolean isGroupTicketsFetch() {
		return groupTicketsFetch;
	}

	public void setGroupTicketsFetch(boolean groupTicketsFetch) {
		this.groupTicketsFetch = groupTicketsFetch;
	}
}

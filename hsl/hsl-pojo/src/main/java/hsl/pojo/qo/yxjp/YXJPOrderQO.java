package hsl.pojo.qo.yxjp;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

import java.util.Date;

/**
 * 易行机票订单查询
 *
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "yxjpOrderDAO")
public class YXJPOrderQO extends BaseQo {

	/**
	 * 订单编号
	 */
	@QOAttr(name = "baseInfo.orderNo")
	private String orderNo;

	/**
	 * 分销订单号
	 */
	@QOAttr(name = "baseInfo.outOrderNo")
	private String outOrderNo;

	/**
	 * 来源标识：0 mobile , 1  pc
	 */
	@QOAttr(name = "baseInfo.fromType")
	private Integer fromType;

	/**
	 * 订单创建时间
	 */
	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.GE)
	private Date createDateBegin;
	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.LE)
	private Date createDateEnd;
	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.ORDER)
	private Integer createDateOrder;

	/**
	 * 订单的pnr
	 */
	@QOAttr(name = "baseInfo.pnr")
	private String pnr;

	/**
	 * 航班信息
	 */
	@QOAttr(name = "flight", type = QOAttrType.JOIN)
	private YXJPOrderFlightQO flightQo;

	/**
	 * 下单用户ID
	 */
	@QOAttr(name = "userInfo.userId")
	private String orderUserId;

	/**
	 * 下单用户帐号
	 */
	@QOAttr(name = "userInfo.loginName")
	private String orderUserLoginName;

	/**
	 * 下单用户手机号
	 */
	@QOAttr(name = "userInfo.mobile")
	private String orderUserMobile;

	/**
	 * 乘客信息
	 */
	private YXJPOrderPassengerQO passengerQO;

	/**
	 * 是否加载乘客
	 */
	private boolean fetchPassengers;

	/**
	 * 联系人姓名
	 */
	@QOAttr(name = "linkman.linkName", ifTrueUseLike = "linkNameLike")
	private String linkName;
	private boolean linkNameLike;

	/**
	 * 联系人手机
	 */
	@QOAttr(name = "linkman.linkMobile", ifTrueUseLike = "linkMobileLike")
	private String linkMobile;

	/**
	 * 联系人邮箱
	 */
	@QOAttr(name = "linkman.linkEmail", ifTrueUseLike = "linkEmailLike")
	private String linkEmail;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public Integer getFromType() {
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

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

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public YXJPOrderFlightQO getFlightQo() {
		return flightQo;
	}

	public void setFlightQo(YXJPOrderFlightQO flightQo) {
		this.flightQo = flightQo;
	}

	public String getOrderUserId() {
		return orderUserId;
	}

	public void setOrderUserId(String orderUserId) {
		this.orderUserId = orderUserId;
	}

	public String getOrderUserLoginName() {
		return orderUserLoginName;
	}

	public void setOrderUserLoginName(String orderUserLoginName) {
		this.orderUserLoginName = orderUserLoginName;
	}

	public String getOrderUserMobile() {
		return orderUserMobile;
	}

	public void setOrderUserMobile(String orderUserMobile) {
		this.orderUserMobile = orderUserMobile;
	}

	public YXJPOrderPassengerQO getPassengerQO() {
		return passengerQO;
	}

	public void setPassengerQO(YXJPOrderPassengerQO passengerQO) {
		this.passengerQO = passengerQO;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public boolean isLinkNameLike() {
		return linkNameLike;
	}

	public void setLinkNameLike(boolean linkNameLike) {
		this.linkNameLike = linkNameLike;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

	public boolean isFetchPassengers() {
		return fetchPassengers;
	}

	public void setFetchPassengers(boolean fetchPassengers) {
		this.fetchPassengers = fetchPassengers;
	}

	public Integer getCreateDateOrder() {
		return createDateOrder;
	}

	public void setCreateDateOrder(Integer createDateOrder) {
		this.createDateOrder = createDateOrder;
	}
}

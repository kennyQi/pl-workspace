package hsl.pojo.qo.jp;
import java.util.Date;

import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class FlightOrderQO extends BaseQo {
	/**
	 * 订单号查询
	 */
	private String orderNO;
	/**
	 * 用户ID
	 */
	private String userID;
	/**
	 * 成员ID
	 */
	private String memeberId;
	/**
	 * 航班起飞
	 */
	private Date startTime;
	/**
	 * 航班起飞日期
	 */
	private Date startDate;
	/**
	 * 航班结束时间
	 */
	private Date EndTime;
	/**
	 * 航班号查询
	 */
	private String flightNo;
	/**
	 * 用户证件号码
	 */
	private String idNo;
	/**
	 * 订单状态
	 */
	private Integer status;
	
	/**
	 * 支付状态
	 */
	private Integer payStatus;
	/**
	 *  订单创建开始时间
	 */
	private String beginDateTime;
	/**
	 *  订单创建结束时间
	 */
	private String endDateTime;
	private Integer[] sts;
	private Integer[] paySts;
	/**
	 * 支付交易号
	 */
	private String payTradeNo;
	
	private Boolean isFetchPassenger;
	/**
	 * 下单人登录名
	 */
	private String loginName;
	/**
	 * 登机人姓名
	 */
	private String actName;
	
	private String special;
	
	/**
	 *  订单种类
	 * 1：正常订单
	 * 2：取消订单
	 * 3：退款订单
	 * 4:记录订单
	 */
	private String orderType;
	
	
	/**
	 * 是否全部为member
	 */
	private boolean showMember=false;
	
	/**
	 * 卡券使用订单查询
	 */
	private boolean isCouponOrderQuery=false;
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public boolean getIsCouponOrderQuery() {
		return isCouponOrderQuery;
	}

	public void setIsCouponOrderQuery(boolean isCouponOrderQuery) {
		this.isCouponOrderQuery = isCouponOrderQuery;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}	
	
	public String getMemeberId() {
		return memeberId;
	}

	public void setMemeberId(String memeberId) {
		this.memeberId = memeberId;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return EndTime;
	}

	public void setEndTime(Date endTime) {
		EndTime = endTime;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public Integer[] getSts() {
		return sts;
	}

	public void setSts(Integer[] sts) {
		this.sts = sts;
	}

	public Integer[] getPaySts() {
		return paySts;
	}

	public void setPaySts(Integer[] paySts) {
		this.paySts = paySts;
	}

	public Boolean getIsFetchPassenger() {
		return isFetchPassenger;
	}

	public void setIsFetchPassenger(Boolean isFetchPassenger) {
		this.isFetchPassenger = isFetchPassenger;
	}

	public void setBeginDateTime(String beginDateTime) {
		this.beginDateTime = beginDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getBeginDateTime() {
		return beginDateTime;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public boolean isShowMember() {
		return showMember;
	}

	public void setShowMember(boolean showMember) {
		this.showMember = showMember;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date date) {
		this.startDate = date;
	}


	
}

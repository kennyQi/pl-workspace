package hsl.pojo.qo.jp;

import hg.common.component.BaseQo;

import java.util.Date;

@SuppressWarnings("serial")
public class HslJPOrderQO extends BaseQo {
	/**
	 * 商城订单号
	 */
	private String dealerOrderCode;
	
	/**
	 * 平台订单号
	 */
	private String orderCode;
	
	/** 
	 * 航班号 
	 */
	private String flightNo;
	
	/**
	 * 下单时间
	 */
	private Date createDate;
	
	/**
	 * 下单区间 开始时间
	 */
	private String beginDateTime;
	
	/**
	 * 下单区间 结束时间
	 */
	private String endDateTime;
	
	/**
	 * 订单状态
	 */
	private Integer status;
	
	/**
	 * 支付状态
	 */
	private Integer payStatus;
	
	/**
	 * 下单人姓名
	 */
//	private JPOrderUser orderUser;
	
	/**
	 * 下单人登录名
	 */
	private String loginName;

	/**
	 * 下单人手机号
	 */
	private String mobile;
	
	/**
	 * 下单人ID
	 */
	private String userId;
	
	/**
	 * 登机人姓名
	 */
	private String actName;
	
	/**
	 * PNR
	 */
	private String pnr;
	
	/**
	 * 订单状态集合
	 */
	private Integer[] sts;
	
	/**
	 * 订单支付状态集合
	 */
	private Integer[] paySts;
	
	/**
	 * 页码
	 */
	private Integer pageNo;
	
	/**
	 * 返回条数
	 */
	private Integer pageSize;
	
	/**
	 * 组织ID
	 */
	private String companyId;
	
	/**
	 * 组织名称
	 */
	private String companyName;
	
	/**
	 * 部门ID
	 */
	private String departmentId;
	
	/**
	 * 部门名称
	 */
	private String departmentName;
	
	/**
	 * 成员ID
	 */
	private String memeberId;
	
	/**
	 * 航班起飞时间(区间开始)
	 */
	private String flightStartTime;
	
	/**
	 * 航班起飞时间(区间截至)
	 */
	private String flightEndTime;

	/**
	 * 乘机人身份证号
	 */
	private String cardNo;
	/**
	 * 是否全部为member
	 */
	private boolean showMember=false;
	
	/**第三方支付单号 */
	private String payTradeNo;
	
	private String special;
	
	
	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public boolean isShowMember() {
		return showMember;
	}

	public void setShowMember(boolean showMember) {
		this.showMember = showMember;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBeginDateTime() {
		return beginDateTime;
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

	/*public JPOrderUser getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(JPOrderUser orderUser) {
		this.orderUser = orderUser;
	}*/
	

	public String getUserId() {
		return userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public Integer[] getSts() {
		return sts;
	}

	public void setSts(Integer[] sts) {
		this.sts = sts;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getMemeberId() {
		return memeberId;
	}

	public void setMemeberId(String memeberId) {
		this.memeberId = memeberId;
	}

	public String getFlightStartTime() {
		return flightStartTime;
	}

	public void setFlightStartTime(String flightStartTime) {
		this.flightStartTime = flightStartTime;
	}

	public String getFlightEndTime() {
		return flightEndTime;
	}

	public void setFlightEndTime(String flightEndTime) {
		this.flightEndTime = flightEndTime;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer[] getPaySts() {
		return paySts;
	}

	public void setPaySts(Integer[] paySts) {
		this.paySts = paySts;
	}
}

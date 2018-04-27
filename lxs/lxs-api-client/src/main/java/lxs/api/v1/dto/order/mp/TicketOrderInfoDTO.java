package lxs.api.v1.dto.order.mp;

import java.util.Date;
import java.util.List;

import lxs.api.v1.dto.mp.TouristDTO;

public class TicketOrderInfoDTO {

	/**
	 * 订单ID
	 */
	private String orderID;

	/**
	 * 订单编号
	 */
	private String orderNO;

	/**
	 * 景区ID
	 */
	private String scenicSpotID;

	/**
	 * 门票政策ID
	 */
	private String ticketPolicyId;

	/**
	 * 门票政策版本
	 */
	private Integer ticketPolicyVersion;

	/**
	 * 政策类型
	 */
	private Integer type;

	/**
	 * 景区名称
	 */
	private String name;

	/**
	 * 景区简称
	 */
	private String scenicSpotNameStr;

	/**
	 * 预定须知
	 */
	private String notice;

	/**
	 * 游玩日期（独立单票必传）
	 */
	private Date travelDate;

	/**
	 * 有效天数(独立单票可入园天数 or 联票自出票后的有效天数)
	 */
	private Integer validDays;

	/**
	 * 联票(与经销商)游玩价
	 */
	private Double playPrice;

	/**
	 * 联票(与经销商)游玩价总价
	 */
	private Double playPriceSUM;

	/**
	 * 预订人姓名
	 */
	private String bookMan;
	/**
	 * 预订人手机号码
	 */
	private String bookManMobile;

	/**
	 * 预定人支付宝账号 （用于退款）
	 */
	private String bookManAliPayAccount;

	/** 等待支付 */
	public final static Integer ORDER_STATUS_PAY_WAIT = 0;
	/** 支付成功 */
	public final static Integer ORDER_STATUS_PAY_SUCC = 1;
	/** 出票成功 */
	public final static Integer ORDER_STATUS_OUT_SUCC = 2;
	/** 交易成功 */
	public final static Integer ORDER_STATUS_DEAL_SUCC = 3;
	/** 交易关闭 */
	public final static Integer ORDER_STATUS_DEAL_CLOSE = 4;

	/**
	 * 当前状态
	 */
	private Integer currentValue;

	/** 等待支付 */
	public final static Integer WAIT_TO_PAY = 0;
	/** 本地支付成功 */
	public final static Integer PAYED = 1;
	/**
	 * 本地支付状态
	 */
	private Integer localPayStatus;

	/**
	 * 退票规则
	 */
	private Integer returnRule;

	/**
	 * 本地状态
	 */
	private Integer localStatus;

	/**
	 * 创建订单时间
	 */
	private Date creatDate;

	/**
	 * 出票时间 即 收到支付宝通知时间
	 */
	private Date printTicketDate;
	
	/**
	 * 游客列表
	 */
	private List<TouristDTO> touristDTOs;
	
	

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getScenicSpotID() {
		return scenicSpotID;
	}

	public void setScenicSpotID(String scenicSpotID) {
		this.scenicSpotID = scenicSpotID;
	}

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public Integer getTicketPolicyVersion() {
		return ticketPolicyVersion;
	}

	public void setTicketPolicyVersion(Integer ticketPolicyVersion) {
		this.ticketPolicyVersion = ticketPolicyVersion;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScenicSpotNameStr() {
		return scenicSpotNameStr;
	}

	public void setScenicSpotNameStr(String scenicSpotNameStr) {
		this.scenicSpotNameStr = scenicSpotNameStr;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}

	public Double getPlayPrice() {
		return playPrice;
	}

	public void setPlayPrice(Double playPrice) {
		this.playPrice = playPrice;
	}

	public Double getPlayPriceSUM() {
		return playPriceSUM;
	}

	public void setPlayPriceSUM(Double playPriceSUM) {
		this.playPriceSUM = playPriceSUM;
	}

	public String getBookMan() {
		return bookMan;
	}

	public void setBookMan(String bookMan) {
		this.bookMan = bookMan;
	}

	public String getBookManMobile() {
		return bookManMobile;
	}

	public void setBookManMobile(String bookManMobile) {
		this.bookManMobile = bookManMobile;
	}

	public String getBookManAliPayAccount() {
		return bookManAliPayAccount;
	}

	public void setBookManAliPayAccount(String bookManAliPayAccount) {
		this.bookManAliPayAccount = bookManAliPayAccount;
	}

	public Integer getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}

	public Integer getLocalPayStatus() {
		return localPayStatus;
	}

	public void setLocalPayStatus(Integer localPayStatus) {
		this.localPayStatus = localPayStatus;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public List<TouristDTO> getTouristDTOs() {
		return touristDTOs;
	}

	public void setTouristDTOs(List<TouristDTO> touristDTOs) {
		this.touristDTOs = touristDTOs;
	}

	public Integer getLocalStatus() {
		return localStatus;
	}

	public void setLocalStatus(Integer localStatus) {
		this.localStatus = localStatus;
	}

	public Integer getReturnRule() {
		return returnRule;
	}

	public void setReturnRule(Integer returnRule) {
		this.returnRule = returnRule;
	}

	public Date getPrintTicketDate() {
		return printTicketDate;
	}

	public void setPrintTicketDate(Date printTicketDate) {
		this.printTicketDate = printTicketDate;
	}

	
}

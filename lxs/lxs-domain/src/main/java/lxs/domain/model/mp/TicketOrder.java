package lxs.domain.model.mp;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lxs.domain.model.M;

/**
 * @类功能说明：门票订单
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午2:56:06
 * @版本：V1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_MP + "TICKET_ORDER")
public class TicketOrder extends BaseModel {

	/**
	 * 登录人ID
	 */
	@Column(name = "USER_ID", length = 512)
	private String userID;

	/**
	 * 订单编号
	 */
	@Column(name = "ORDER_NO", length = 512)
	private String orderNO;

	/**
	 * 景区ID
	 */
	@Column(name = "SCENIC_SPOT_ID", length = 512)
	private String scenicSpotID;
	/**
	 * 门票政策ID
	 */
	@Column(name = "TICKET_POLICY_ID", length = 512)
	private String ticketPolicyId;

	/**
	 * 门票政策版本
	 */
	@Column(name = "TICKET_POLICY_VERSION")
	private Integer ticketPolicyVersion;

	/**
	 * 政策类型
	 */
	@Column(name = "TYPE")
	private Integer type;

	/**
	 * 景区名称
	 */
	@Column(name = "NAME", length = 512)
	private String name;

	/**
	 * 景区简称
	 */
	@Column(name = "SCENIC_SPOT_NAME_STR", length = 512)
	private String scenicSpotNameStr;

	/**
	 * 预定须知
	 */
	@Column(name = "NOTICE", length = 512)
	private String notice;

	/**
	 * 游玩日期（独立单票必传）
	 */
	@Column(name = "TRAVEL_DATE", columnDefinition = M.DATE_COLUM)
	private Date travelDate;

	/**
	 * 有效天数(独立单票可入园天数 or 联票自出票后的有效天数)
	 */
	@Column(name = "VALID_DAYS")
	private Integer validDays;

	/**
	 * 联票(与经销商)游玩价
	 */
	@Column(name = "PLAY_PRICE")
	private Double playPrice;

	/**
	 * 购买数量
	 */
	@Column(name = "BUY_NUM")
	private Integer buyNum;

	/**
	 * 联票(与经销商)游玩价总价
	 */
	@Column(name = "PLAY_PRICE_SUM")
	private Double playPriceSUM;

	/**
	 * 预订人姓名
	 */
	@Column(name = "BOOK_MAN", length = 512)
	private String bookMan;
	/**
	 * 预订人手机号码
	 */
	@Column(name = "BOOK_MAN_MOBILE", length = 512)
	private String bookManMobile;

	/**
	 * 预定人支付宝账号 （用于退款）
	 */
	@Column(name = "BOOK_MAN_ALIPAY_ACCOUNT", length = 512)
	private String bookManAliPayAccount;

	
	/**
	 * 退票规则 1.不能退2.无条件退3.退票收取百分比手续费4.退票收取X元手续费
	 */
	@Column(name = "RETURN_RULE")
	private Integer returnRule;
	
	/** 等待支付 */
	public final static Integer ORDER_STATUS_PAY_WAIT = 0;
	/** 出票成功 */
	public final static Integer ORDER_STATUS_OUT_SUCC = 2;
	/** 出票失败 */
	public final static Integer ORDER_STATUS_OUT_FAIL = 3;
	/** 交易关闭 */
	public final static Integer ORDER_STATUS_DEAL_CLOSE = 4;
	/** 等待退票*/
	public final static Integer ORDER_STATUS_CANCEL_WAIT = 5;
	/** 退票成功*/
	public final static Integer ORDER_STATUS_CANCEL_SUCC = 6;
	/** 退票失败*/
	public final static Integer ORDER_STATUS_CANCEL_FAIL = 7;
	
	/**
	 * 当前状态
	 */
	@Column(name = "CURRENT_VALUE")
	private Integer currentValue;

	/** 等待支付 */
	public final static Integer WAIT_TO_PAY = 1;
	/** 本地支付成功 */
	public final static Integer PAYED = 2;
	
	/**
	 * 本地支付状态
	 */
	@Column(name = "LOCAL_PAY_STATUS")
	private Integer localPayStatus;

	/*
	 * 显示规则
	 * if(==010){
	 *    等待支付
	 * } else if(==220){
	 *    出票成功
	 * }else if(<400){
	 *    等待出票
	 * }else if(>400){
	 *    交易关闭
	 * }
	 * 按钮规则
	 * 运营端
	 * if(==320){
	 *    申请出票
	 * }else if(==723){
	 *    申请退票
	 * }
	 * 
	 * APP端
	 * if(<400){
	 *    显示‘取消’
	 * }
	 * if(==101){
	 *    显示‘支付’
	 * }
	 * 
	 */
	//新建一个订单
	//电子票务状态(currentValue) 等待支付(ORDER_STATUS_PAY_WAIT) 本地支付状态 等待支付(WAIT_TO_PAY)
	/**
	 * 等待支付 010 app留‘支付’‘取消订单’按钮
	 */
	
	//用户支付收到通知
	//电子票务状态(currentValue) 等待支付(ORDER_STATUS_PAY_WAIT) 本地支付状态 支付本地成功(PAYED)
	/**
	 * 等待出票 020 app‘取消订单’按钮
	 */
	
	//去电子票务支付成功
	//电子票务状态(currentValue) 支付成功(ORDER_STATUS_OUT_SUCC) 本地支付状态 支付本地成功(PAYED)
	/**
	 * 出票成功 220 app‘取消订单’按钮
	 */
	
	//去电子票务支付失败
	//电子票务状态(currentValue) 支付失败(ORDER_STATUS_OUT_FAIL) 本地支付状态 支付本地成功(PAYED)
	/**
	 * 等待出票 320 app‘取消订单’按钮 
	 * 						运营端留‘申请出票’按钮
	 */
	
	//电子票务通知关闭订单--已支付
	//电子票务状态(currentValue) 订单关闭(ORDER_STATUS_DEAL_CLOSE) 游客退款状态 等待退款(WAIT_TO_REFUND_SYS)
	/**
	 * 交易关闭 421
	 */
	
	//收到支付宝退款通知
	//电子票务状态(currentValue) 订单关闭(ORDER_STATUS_DEAL_CLOSE) 游客退款状态 退款成功(REFUNDED_SYS)
	/**
	 * 交易关闭 422
	 */
	
	//电子票务通知关闭订单--未支付 [游客退款状态 默认值为 ‘未发生退款’]
	//电子票务状态(currentValue) 订单关闭(ORDER_STATUS_DEAL_CLOSE)
	/**
	 * 交易关闭 410
	 */
	
	//用户点击退票--未支付
	//电子票务状态(currentValue) 订单关闭(ORDER_STATUS_DEAL_CLOSE)
	/**
	 * 交易关闭 410
	 */
	
	//用户点击退票--已支付--未出票 发起退款
	//电子票务状态(currentValue) 订单关闭(ORDER_STATUS_DEAL_CLOSE) 游客退款状态 等待退款(WAIT_TO_REFUND_USER)
	/**
	 * 交易关闭 423
	 */
	
	//用户点击退票--已支付--未出票 收到支付宝退款通知
	//电子票务状态(currentValue) 订单关闭(ORDER_STATUS_DEAL_CLOSE) 游客退款状态 退款成功(REFUNDED_USER)
	/**
	 * 交易关闭 424
	 */
	
	//用户点击退票--已支付--已出票 发起退票
	//电子票务状态(currentValue) 等待取消(ORDER_STATUS_CANCEL_WAIT) 游客退款状态 等待退款(WAIT_TO_REFUND_USER)
	/**
	 * 交易关闭 523
	 */
	
	//用户点击退票--已支付--已出票 退票成功
	//电子票务状态(currentValue) 取消成功(ORDER_STATUS_CANCEL_SUCC) 游客退款状态 等待退款(WAIT_TO_REFUND_USER)
	/**
	 * 交易关闭 623
	 */
	
	//用户点击退票--已支付--已出票 收到支付宝退款通知
	//电子票务状态(currentValue) 取消成功(ORDER_STATUS_CANCEL_SUCC) 游客退款状态 退款成功(REFUNDED_USER)
	/**
	 * 交易关闭 624
	 */
	
	//用户点击退票--已支付--已出票 退票失败
	//电子票务状态(currentValue) 取消失败(ORDER_STATUS_CANCEL_FAIL) 游客退款状态 等待退款(WAIT_TO_REFUND_USER)
	/**
	 * 交易关闭 723 运营端留‘申请退票’按钮
	 */
	
	/**
	 * 本地订单状态
	 */
	@Column(name = "LOCAL_STATUS")
	private Integer localStatus;
	
	/**
	 * 支付宝信息 退款信息
	 */
	/**
	 * 付款金额
	 */
	@Column(name = "PRICE")
	private Double price;

	/**
	 * 支付宝流水号
	 */
	@Column(name = "SERIAL_NUMBER", length = 512)
	private String serialNumber;

	/**
	 * 支付账号
	 */
	@Column(name = "PAYMENT_ACCOUNT", length = 512)
	private String paymentAccount;

	/**
	 * 请求类型
	 */
	@Column(name = "REQUEST_TYPE", length = 512)
	private String requestType;

	/**
	 * 创建订单时间
	 */
	@Column(name = "CREAT_DATE", columnDefinition = M.DATE_COLUM)
	private Date creatDate;
	
	/**
	 * 出票时间 即 收到支付宝通知时间
	 */
	@Column(name = "PRINT_TICKET_DATE", columnDefinition = M.DATE_COLUM)
	private Date printTicketDate;

	/**
	 * 电子票务下单成功后 订单快照
	 */
	@Column(name = "DZPW_ORDER_SNAPSHOT", columnDefinition = "LONGTEXT")
	private String DZPWOrderSnapShot;
	
	@Column(name = "SCENIC_SPOT_SNAPSHOT", columnDefinition = "LONGTEXT")
	private String ScenicSpotSnapShot;
	
	@Column(name = "TICKET_POLICY_SNAPSHOT", columnDefinition = "LONGTEXT")
	private String TicketPolicySnapShot;

	/**
	 * 
	 * @方法功能说明：支付成功
	 * @修改者名字：cangs
	 * @修改时间：2016年3月11日上午9:41:27
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void localstatus(int current,int localPayStatus,int touristStatus){
		int sum = current*100+localPayStatus*10+touristStatus*1;
		setLocalStatus(sum);
	}
	/**
	 * 
	 * @方法功能说明：支付成功
	 * @修改者名字：cangs
	 * @修改时间：2016年3月11日上午9:41:27
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void localPayed(){
		setLocalPayStatus(PAYED);
	}
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
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

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
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

	public String getDZPWOrderSnapShot() {
		return DZPWOrderSnapShot;
	}

	public void setDZPWOrderSnapShot(String dZPWOrderSnapShot) {
		DZPWOrderSnapShot = dZPWOrderSnapShot;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public String getScenicSpotID() {
		return scenicSpotID;
	}

	public void setScenicSpotID(String scenicSpotID) {
		this.scenicSpotID = scenicSpotID;
	}

	public Integer getLocalPayStatus() {
		return localPayStatus;
	}

	public void setLocalPayStatus(Integer localPayStatus) {
		this.localPayStatus = localPayStatus;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
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
	public String getScenicSpotSnapShot() {
		return ScenicSpotSnapShot;
	}
	public void setScenicSpotSnapShot(String scenicSpotSnapShot) {
		ScenicSpotSnapShot = scenicSpotSnapShot;
	}
	public String getTicketPolicySnapShot() {
		return TicketPolicySnapShot;
	}
	public void setTicketPolicySnapShot(String ticketPolicySnapShot) {
		TicketPolicySnapShot = ticketPolicySnapShot;
	}
	public Date getPrintTicketDate() {
		return printTicketDate;
	}
	public void setPrintTicketDate(Date printTicketDate) {
		this.printTicketDate = printTicketDate;
	}

	
	
}
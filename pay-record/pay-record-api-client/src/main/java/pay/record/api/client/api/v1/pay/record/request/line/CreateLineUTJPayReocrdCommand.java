package pay.record.api.client.api.v1.pay.record.request.line;

import pay.record.api.client.common.ApiRequestBody;
import pay.record.api.client.common.api.PayRecordApiAction;

/**
 * 
 * @类功能说明：用户->经销商线路支付记录COMMAND
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2016年1月12日下午4:09:23
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@PayRecordApiAction(PayRecordApiAction.PAY_RECORD_LINE_UTJ_CREATE)
public class CreateLineUTJPayReocrdCommand extends ApiRequestBody{
	
	/**
	 * 经销商订单创建时间
	 */
	private String dealerOrderCreateDate;
	
	/**
	 * 经销商订单号
	 */
	private String dealerOrderNo;
	
	/**
	 * 用户支付总价
	 */
	private Double userPayAmount;
	
	/**
	 * 用户支付的现金
	 */
	private Double userPayCash;
	
	/**
	 * 用户支付的优惠券金额
	 */
	private Double userPayCoupon;
	
	/**
	 * 用户支付的余额金额
	 */
	private Double userPayBalances;
	
	/**
	 * 客户付款账号
	 */
	private String userPayAccountNo;
	
	/**
	 * 客户业务流水号
	 */
	private String userBusinessSerialNumber;
	
	/**
	 * 线路id
	 */
	private String lineId;
	
	/**
	 * 线路名称
	 */
	private String lineName;
	
	/** 
	 * 线路类别
	 */
	private String type;
	
	/**
	 * 成人人数
	 */
	private Integer adultNo;
	
	/**
	 * 儿童人数
	 */
	private Integer childNo;
	
	/**
	 * 成人销售单价
	 */
	private Double adultUnitPrice;
	
	/**
	 * 儿童销售单价
	 */
	private Double childUnitPrice;
	
	/**
	 * 销售总价
	 */
	private Double salePrice;
	
	/**
	 * 销售定金
	 */
	private Double bargainMoney;
	
	/**
	 * 发团日期
	 */
	private Double travelDate;

	public String getDealerOrderCreateDate() {
		return dealerOrderCreateDate;
	}

	public void setDealerOrderCreateDate(String dealerOrderCreateDate) {
		this.dealerOrderCreateDate = dealerOrderCreateDate;
	}

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public Double getUserPayAmount() {
		return userPayAmount;
	}

	public void setUserPayAmount(Double userPayAmount) {
		this.userPayAmount = userPayAmount;
	}

	public Double getUserPayCash() {
		return userPayCash;
	}

	public void setUserPayCash(Double userPayCash) {
		this.userPayCash = userPayCash;
	}

	public Double getUserPayCoupon() {
		return userPayCoupon;
	}

	public void setUserPayCoupon(Double userPayCoupon) {
		this.userPayCoupon = userPayCoupon;
	}

	public Double getUserPayBalances() {
		return userPayBalances;
	}

	public void setUserPayBalances(Double userPayBalances) {
		this.userPayBalances = userPayBalances;
	}

	public String getUserPayAccountNo() {
		return userPayAccountNo;
	}

	public void setUserPayAccountNo(String userPayAccountNo) {
		this.userPayAccountNo = userPayAccountNo;
	}

	public String getUserBusinessSerialNumber() {
		return userBusinessSerialNumber;
	}

	public void setUserBusinessSerialNumber(String userBusinessSerialNumber) {
		this.userBusinessSerialNumber = userBusinessSerialNumber;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getAdultNo() {
		return adultNo;
	}

	public void setAdultNo(Integer adultNo) {
		this.adultNo = adultNo;
	}

	public Integer getChildNo() {
		return childNo;
	}

	public void setChildNo(Integer childNo) {
		this.childNo = childNo;
	}

	public Double getAdultUnitPrice() {
		return adultUnitPrice;
	}

	public void setAdultUnitPrice(Double adultUnitPrice) {
		this.adultUnitPrice = adultUnitPrice;
	}

	public Double getChildUnitPrice() {
		return childUnitPrice;
	}

	public void setChildUnitPrice(Double childUnitPrice) {
		this.childUnitPrice = childUnitPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getBargainMoney() {
		return bargainMoney;
	}

	public void setBargainMoney(Double bargainMoney) {
		this.bargainMoney = bargainMoney;
	}

	public Double getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Double travelDate) {
		this.travelDate = travelDate;
	}
}

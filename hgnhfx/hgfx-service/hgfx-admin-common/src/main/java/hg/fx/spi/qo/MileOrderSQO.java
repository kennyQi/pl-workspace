package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;
import hg.fx.enums.MileOrderOrderWayEnum;

/**
 * 订单查询SQO
 * @author zqq
 * @date 2016-6-1下午2:45:33
 * @since
 */
public class MileOrderSQO extends BaseSPIQO{

	/**  */
	private static final long serialVersionUID = 1L;
	/**
	 * -1--订单取消
	 */
	public static final int STATUS_CANCEL = -1;
	/**
	 *  0--已付款（冻结）
	 */
	public static final int STATUS_PAID = 0;
	
	/**
	 * 1--审核中,待审核
	 */
	public static final int STATUS_NO_CHECK = 1;

	/**
	 * 2--处理中(审核通过）
	 */
	public static final int STATUS_CHECK_PASS = 2;

	/**
	 * 3--已拒绝
	 *  审核不通过（拒绝）
	 */
	public static final int STATUS_CHECK_NO_PASS = 3;

	/**
	 *  提交南航
	 */
	public static final int STATUS_TO_CSAIR = 25;

	/**4--订单成功
	 *  南航成功
	 */
	public static final int STATUS_CSAIR_SUCCEED = 4;

	/** 5--订单失败
	 *  南航失败
	 */
	public static final int STATUS_CSAIR_ERROR = 5;

	/**
	 * 订单id
	 */
	private String Id;
	/**
	 * 下单商户Id
	 */
	private String distributorId;

	/**
	 * 订单号
	 */
	private String orderCode;

	/**
	 * 订单创建时间/导入时间
	 */
	private String strImportDate;
	
	/**
	 * 订单创建时间/导入时间
	 */
	private String endImportDate;
	/**
	 * 排序方式
	 */
	private MileOrderOrderWayEnum orderWay;

	/**
	 * 状态
	 * -1--订单取消
	 * 0--已付款（冻结）
	 * 1--审核中,待审核
	 * 2--处理中(审核通过）
	 * 3--已拒绝
	 * 4--订单成功 南航成功
	 * 5--订单失败 南航失败
	 * 25--提交南航
	 */
	private Integer status;

	/**购买商品	
	 */
	private String productId;
	 
	/**
	 * 渠道id
	 */
	private String channelId;
	
	/**
	 * 订单类型1异常，2正常
	 */
	private Integer orderType;
	
	/**
	 * 会员号
	 */
	private String  csairCard;
	
	/**
	 * 查询不是这个状态的订单
	 */
	private Integer nonStatus;
	/**
	 * 流水号
	 */
	private String flowNo;
	/**
	 * 是否直接关联加载
	 */
	private boolean isJoin;
	
	
	public boolean isJoin() {
		return isJoin;
	}

	public void setJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public MileOrderOrderWayEnum getOrderWay() {
		return orderWay;
	}

	public void setOrderWay(MileOrderOrderWayEnum orderWay) {
		this.orderWay = orderWay;
	}

	public Integer getNonStatus() {
		return nonStatus;
	}

	public void setNonStatus(Integer nonStatus) {
		this.nonStatus = nonStatus;
	}

	public String getCsairCard() {
		return csairCard;
	}

	public void setCsairCard(String csairCard) {
		this.csairCard = csairCard;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	
	public String getStrImportDate() {
		return strImportDate;
	}

	public void setStrImportDate(String strImportDate) {
		this.strImportDate = strImportDate;
	}

	public String getEndImportDate() {
		return endImportDate;
	}

	public void setEndImportDate(String endImportDate) {
		this.endImportDate = endImportDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}

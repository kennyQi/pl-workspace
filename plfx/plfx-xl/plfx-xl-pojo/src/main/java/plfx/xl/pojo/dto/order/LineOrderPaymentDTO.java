package plfx.xl.pojo.dto.order;

import java.util.Date;

import plfx.xl.pojo.dto.BaseXlDTO;

/**
 * 
 * @类功能说明：订单支付信息DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月23日下午4:39:38
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class LineOrderPaymentDTO extends BaseXlDTO {

	/**
	 * 线路订单
	 */
	private LineOrderDTO lineOrder;
	
	/**
	 * 游玩人
	 */
//	private LineOrderTravelerDTO lineOrderTraveler;
	
	/**
	 * 游玩人姓名列表，用"，"隔开
	 */
	private String lineOrderTravelerNames;
	
	/**
	 * 支付方式
	 * 1:支付宝
	 */
	private Integer paymentType;
	
	/**
	 * 支付账号
	 */
	private String paymentAccount;
	
	/**
	 * 付款姓名
	 */
	private String paymentName;
	
	/**
	 * 流水号
	 */
	private String serialNumber;
	
	/**
	 * 支付金额
	 */
	private Double paymentAmount;
	
	/**
	 * 创建日期
	 */
	private Date createDate;

	public LineOrderDTO getLineOrder() {
		return lineOrder;
	}

	public void setLineOrder(LineOrderDTO lineOrder) {
		this.lineOrder = lineOrder;
	}

//	public LineOrderTravelerDTO getLineOrderTraveler() {
//		return lineOrderTraveler;
//	}
//
//	public void setLineOrderTraveler(LineOrderTravelerDTO lineOrderTraveler) {
//		this.lineOrderTraveler = lineOrderTraveler;
//	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLineOrderTravelerNames() {
		return lineOrderTravelerNames;
	}

	public void setLineOrderTravelerNames(String lineOrderTravelerNames) {
		this.lineOrderTravelerNames = lineOrderTravelerNames;
	}

	
	
}

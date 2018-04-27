package plfx.xl.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import plfx.xl.domain.model.M;
import plfx.xl.pojo.command.pay.BatchPayLineOrderCommand;

/**
 * 
 * @类功能说明：线路订单支付信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月19日下午2:24:28
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "LINE_ORDER_PAYMENT")
public class LineOrderPayment extends BaseModel {

	/**
	 * 线路订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LINE_ORDER_ID")
	private LineOrder lineOrder;
	
	/**
	 * 游玩人
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LINE_ORDER_TRAVELER_ID")
	private LineOrderTraveler lineOrderTraveler;
	
	/**
	 * 支付方式
	 * 1:支付宝
	 */
	@Column(name = "PAYMENT_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer paymentType;
	
	/**
	 * 支付账号
	 */
	@Column(name = "PAYMENT_ACCOUNT", length = 64 )
	private String paymentAccount;
	
	/**
	 * 付款姓名
	 */
	@Column(name = "PAYMENT_NAME", length = 64 )
	private String paymentName;
	
	/**
	 * 流水号
	 */
	@Column(name = "SERIAL_NUMBER", length = 64 )
	private String serialNumber;
	
	/**
	 * 支付金额
	 */
	@Column(name = "PAYMENT_AMOUNT", columnDefinition = M.DOUBLE_COLUM)
	private Double paymentAmount;
	
	/**
	 * 创建日期
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 
	 * @方法功能说明：批量创建线路订单支付信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月26日下午5:25:26
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public List<LineOrderPayment> batchCreate(BatchPayLineOrderCommand command){
		
		List<LineOrderPayment> lineOrderPaymentList = new ArrayList<LineOrderPayment>();
		String lineOrderTravelers = command.getLineOrderTravelers();
		String[] travelerArr = lineOrderTravelers.split(",");
		for(String travelerID:travelerArr){
			LineOrderPayment lineOrderPayment = new LineOrderPayment();
			lineOrderPayment.setId(UUIDGenerator.getUUID());
			LineOrder lineOrder = new LineOrder();
			lineOrder.setId(command.getLineOrderID());
			lineOrderPayment.setLineOrder(lineOrder);
			LineOrderTraveler lineOrderTraveler = new LineOrderTraveler();
			lineOrderTraveler.setId(travelerID);
			lineOrderPayment.setLineOrderTraveler(lineOrderTraveler);
			lineOrderPayment.setPaymentAccount(command.getPaymentAccount());
			lineOrderPayment.setPaymentAmount(command.getPaymentAmount());
			lineOrderPayment.setPaymentName(command.getPaymentName());
			lineOrderPayment.setPaymentType(command.getPaymentType());
			lineOrderPayment.setSerialNumber(command.getSerialNumber());
			lineOrderPayment.setCreateDate(new Date());
			lineOrderPaymentList.add(lineOrderPayment);
		}
		
		return lineOrderPaymentList;
		
	}
	
	public LineOrder getLineOrder() {
		return lineOrder;
	}

	public void setLineOrder(LineOrder lineOrder) {
		this.lineOrder = lineOrder;
	}

	public LineOrderTraveler getLineOrderTraveler() {
		return lineOrderTraveler;
	}

	public void setLineOrderTraveler(LineOrderTraveler lineOrderTraveler) {
		this.lineOrderTraveler = lineOrderTraveler;
	}

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

}
package hsl.domain.model.user.account;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.account.AccountConsumeSnapshotCommand;
import hsl.pojo.command.account.RefundCommand;

import javax.persistence.*;
import java.util.Date;

/**
 * @类功能说明：账户消费快照
 * @类修改者：
 * @修改日期：2015年7月18日下午12:07:35
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年7月18日下午12:07:35
 */
@Entity
@Table(name=M.TABLE_PREFIX_HSL_ACCOUNT+"CONSUME_SN")
public class AccountConsumeSnapshot extends BaseModel{
	private static final long serialVersionUID = 1L;
	/**
	 * 订单类型机票(1)
	 */
	public final static Integer ORDERTYPE_JP=1;
	/**+++++++++++
	 * 订单类型门票(2)
	 */
	public final static Integer ORDERTYPE_MP=2;
	/**
	 * 订单类型线路(3)
	 */
	public final static Integer ORDERTYPE_XL=3;
	/**
	 * 订单类型酒店(4)
	 */
	public final static Integer ORDERTYPE_JD=4;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade = {CascadeType.DETACH})
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;
	/**
	 * 订单类型 
	 */
	@Column(name="ORDERTYPE",columnDefinition=M.TYPE_NUM_COLUM)
	private Integer orderType;
	/**
	 * 订单ID
	 */
	@Column(name="ORDERID", length = 64)
    private String orderId;
    /**
	 * 消费价格（如果该消费记录是退费状态，该字段显示的是退费金额）
	 */
	@Column(name="PAYPRICE",columnDefinition=M.DOUBLE_COLUM)
    private Double payPrice;
    /**
   	 * 详细
   	 */
	@Column(name="DETAIL",columnDefinition=M.TEXT_COLUM)
    private String detail;
	/**
	 * 记录状态 占用
	 */
	public final static Integer STATUS_ZY=1;
	/**
	 * 记录状态 使用
	 */
	public final static Integer STATUS_SY=2;
	/**
	 * 记录状态已退款
	 */
	public final static Integer STATUS_TK=3;
	/**
	 * 记录状态已作废
	 */
	public final static Integer STATUS_ZF=4;
	 /**
   	 * 状态
   	 */
	@Column(name="STATUS",columnDefinition=M.TYPE_NUM_COLUM)
    private Integer status;
	/**
	 *退款订单的实体ID
	 */
	@Column(name="REFUND_ORDER_ID")
	private String refundOrderId;

	@Column(name="REFUND_PRICE",columnDefinition=M.DOUBLE_COLUM)
	private Double refundPrice;
	  /**
     * 创建日期
     */
	@Column(name="CREATEDATE",columnDefinition=M.DATE_COLUM)
    private Date createDate;
	
	public void create(AccountConsumeSnapshotCommand command) {
		this.setId(UUIDGenerator.getUUID());
		this.setOrderId(command.getOrderId());
		this.setOrderType(command.getOrderType());
		this.setPayPrice(command.getPayPrice());
		this.setDetail(command.getDetail());
		this.setStatus(command.getStatus());
		this.setCreateDate(new Date());
	}
	public void refund(RefundCommand command){
		this.setId(UUIDGenerator.getUUID());
		this.setOrderId(command.getOrderID());
		this.setOrderType(ORDERTYPE_JP);
		this.setPayPrice(command.getPayPrice());
		this.setDetail("退费机票");
		this.setStatus(STATUS_TK);
		this.setRefundOrderId(command.getRefundOrderId());
		this.setRefundPrice(command.getRefundMoney());
		this.setCreateDate(new Date());
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public Double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
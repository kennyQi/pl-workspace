package hg.payment.domain.model.refund;

import hg.common.component.BaseModel;
import hg.payment.domain.Pay;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.pojo.command.admin.dto.ModifyRefundDTO;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
 *@类功能说明：订单退款记录
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月26日下午1:46:01
 *
 */
@Entity
@Table(name = Pay.TABLE_PREFIX + "REFUND")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Refund extends BaseModel{
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * 退款的订单
	 */
	@ManyToOne
	@JoinColumn(name="PAYORDER_ID")
	private PayOrder payOrder;
	
	/**
	 * 退款时间
	 */
	@Column(name="REFUN_DDATE")
	private Date refundDate;
	
	/**
	 * 退款金额
	 */
	@Column(name="AMOUNT")
	private Double amount;
	
	/**
	 * 退款理由
	 */
	@Column(name="REFUND_MESSAGE")
	private String refundMessage;
	
	/**
	 * 退款帐号
	 */
	@Column(name="REFUND_ACCOUNT")
	private String refundAccount;
	
	/**
	 * 退款备注
	 */
	@Column(name="REFUND_REMARK")
	private String refundRemark;
	
	/**
	 * 支付宝退款收取的服务费
	 */
	@Column(name="SERVICE_CHARGE")
	private Double serviceCharge;
	
	/**
	 * 退款状态
	 */
	private RefundProcessor refundProcessor;
	
	public void modify(ModifyRefundDTO modifyRefundDTO){
		if(StringUtils.isNotBlank(modifyRefundDTO.getRefundRemark())){
			setRefundRemark(modifyRefundDTO.getRefundRemark());
		}
		if(StringUtils.isNotBlank(modifyRefundDTO.getRefundAccount())){
			setRefundAccount(modifyRefundDTO.getRefundAccount());
		}
		if(modifyRefundDTO.getServiceCharge() != null){
			setServiceCharge(modifyRefundDTO.getServiceCharge());
		}
	}
	
	public PayOrder getPayOrder() {
		return payOrder;
	}

	public void setPayOrder(PayOrder payOrder) {
		this.payOrder = payOrder;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRefundMessage() {
		return refundMessage;
	}

	public void setRefundMessage(String refundMessage) {
		this.refundMessage = refundMessage;
	}

	public String getRefundAccount() {
		return refundAccount;
	}

	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}
	
	public String getRefundRemark() {
		return refundRemark;
	}

	public void setRefundRemark(String refundRemark) {
		this.refundRemark = refundRemark;
	}

	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public RefundProcessor getRefundProcessor() {
		return refundProcessor;
	}

	public void setRefundProcessor(RefundProcessor refundProcessor) {
		this.refundProcessor = refundProcessor;
	}
	
	
	
	
	
	
}

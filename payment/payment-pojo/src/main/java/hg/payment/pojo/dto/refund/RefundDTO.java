package hg.payment.pojo.dto.refund;

import java.util.Date;

import javax.persistence.Column;

import hg.payment.pojo.dto.BaseDTO;

/**
 * 
 * 
 *@类功能说明：退款异步通知商城响应内容
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月1日下午6:19:11
 *
 */
public class RefundDTO extends BaseDTO{
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * 退款金额
	 */
	private Double amount;

	/**
	 * 退款状态 1.退款成功  2.退款失败  
	 */
	private Integer refundStatus;
	
	/**
	 * 商城订单号
	 */
	private String tradeNo;
	
	/**
	 * 退款时间
	 */
	private Date refundDate;
	
	/**
	 * 退款错误信息
	 */
	private String refundRemark;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getRefundRemark() {
		return refundRemark;
	}

	public void setRefundRemark(String refundRemark) {
		this.refundRemark = refundRemark;
	}
	
	
	
	
}

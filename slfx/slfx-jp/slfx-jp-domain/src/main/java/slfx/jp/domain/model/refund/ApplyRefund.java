package slfx.jp.domain.model.refund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hg.common.component.BaseModel;
import slfx.jp.domain.model.J;
import slfx.jp.domain.model.order.JPOrder;

/**
 * 
 * @类功能说明：退废票记录 model
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:30:13
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX + "APPLY_REFUND")
public class ApplyRefund extends BaseModel{
	
   /** 票号 */
   @Column(name = "TICKE_NO", length = 64)
   private String TicketNo;
   
   /** 退废票原因 */
   @Column(name = "REASON", length = 1000)
   private String reason;
   
   /** 退废种类 */
   @Column(name = "REFUND_TYPE", length = 8)
   private String refundType;
   
   /** 退废票的通知地址 */
   @Column(name = "NOTICE_URL", length = 1000)
   private String noticeUrl;
   
   /** 接口错误代码 */
   @Column(name = "ERROR_CODE", length = 16)
   private String errorCode;
   
   /** 错误描述信息 */
   @Column(name = "ERROR_MSG", length = 1000)
   private String errorMsg;
   
   /** 退票订单号 */
   @Column(name = "SUPPLIER_REFUND_ORDERNO", length = 64)
   private String supplierRefundOrderNo;
   
   /** 平台退票单号 */
   @Column(name = "PLATFORM_REFUND_ORDERNO", length = 64)
   private String platformRefundOrderNo;
   
   /** 机票平台订单 */
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "JP_ORDER_ID")
   public JPOrder jpOrder;
   
   /** 退废票种类 */
   @Column(name = "REFUND_ACTION_TYPE", length =32)
   public String refundActionType;

	public String getTicketNo() {
		return TicketNo;
	}
	
	public void setTicketNo(String ticketNo) {
		TicketNo = ticketNo;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getRefundType() {
		return refundType;
	}
	
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	
	public String getNoticeUrl() {
		return noticeUrl;
	}
	
	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getSupplierRefundOrderNo() {
		return supplierRefundOrderNo;
	}
	
	public void setSupplierRefundOrderNo(String supplierRefundOrderNo) {
		this.supplierRefundOrderNo = supplierRefundOrderNo;
	}
	
	public String getPlatformRefundOrderNo() {
		return platformRefundOrderNo;
	}
	
	public void setPlatformRefundOrderNo(String platformRefundOrderNo) {
		this.platformRefundOrderNo = platformRefundOrderNo;
	}
	
	public JPOrder getJpOrder() {
		return jpOrder;
	}
	
	public void setJpOrder(JPOrder jpOrder) {
		this.jpOrder = jpOrder;
	}

	public String getRefundActionType() {
		return refundActionType;
	}

	public void setRefundActionType(String refundActionType) {
		this.refundActionType = refundActionType;
	}
	
	
}
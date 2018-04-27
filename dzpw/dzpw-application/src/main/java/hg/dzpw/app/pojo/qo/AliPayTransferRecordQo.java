package hg.dzpw.app.pojo.qo;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

@SuppressWarnings("serial")
@QOConfig(daoBeanId = "aliPayTransferRecordDao")
public class AliPayTransferRecordQo extends BaseQo {
	
	/**
	 * 转账类型
	 */
	@QOAttr(name = "type")
	private Integer type;
	
	/**
	 * 记录所属DZPW订单号
	 */
	@QOAttr(name = "ticketOrderId")
	private String ticketOrderId;
	
	/**
	 * 记录所属门票ID
	 * 注：只有类型为退款时改属性才有值
	 */
	@QOAttr(name = "groupTicketId")
	private String groupTicketId;

	/**
	 * 是否有返回结果
	 */
	@QOAttr(name = "hasResponse")
	private Boolean hasResponse;
	
	/**
	 * 支付宝流水号
	 */
	@QOAttr(name = "tradeNo")
	private String tradeNo;
	
	/**
	 * 转入账户
	 */
	@QOAttr(name = "transAccountIn")
	private String transAccountIn;
	
	/**
	 * 转出账户
	 */
	@QOAttr(name = "transAccountOut")
	private String transAccountOut;
	
	/**
	 * 退款批次号
	 */
	@QOAttr(name = "refundBatchNo")
	private String refundBatchNo;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTicketOrderId() {
		return ticketOrderId;
	}

	public void setTicketOrderId(String ticketOrderId) {
		this.ticketOrderId = ticketOrderId;
	}

	public String getGroupTicketId() {
		return groupTicketId;
	}

	public void setGroupTicketId(String groupTicketId) {
		this.groupTicketId = groupTicketId;
	}

	public Boolean getHasResponse() {
		return hasResponse;
	}

	public void setHasResponse(Boolean hasResponse) {
		this.hasResponse = hasResponse;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTransAccountIn() {
		return transAccountIn;
	}

	public void setTransAccountIn(String transAccountIn) {
		this.transAccountIn = transAccountIn;
	}

	public String getTransAccountOut() {
		return transAccountOut;
	}

	public void setTransAccountOut(String transAccountOut) {
		this.transAccountOut = transAccountOut;
	}

	public String getRefundBatchNo() {
		return refundBatchNo;
	}

	public void setRefundBatchNo(String refundBatchNo) {
		this.refundBatchNo = refundBatchNo;
	}
	
}

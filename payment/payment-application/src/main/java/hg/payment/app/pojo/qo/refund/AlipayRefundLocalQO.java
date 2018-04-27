package hg.payment.app.pojo.qo.refund;

public class AlipayRefundLocalQO extends RefundLocalQO{
 
	private static final long serialVersionUID = 1L;
	
	/**
	 * 支付宝退款批次号
	 */
	private String refundBatchNo;
	

	public String getRefundBatchNo() {
		return refundBatchNo;
	}

	public void setRefundBatchNo(String refundBatchNo) {
		this.refundBatchNo = refundBatchNo;
	}
	
	
	

}

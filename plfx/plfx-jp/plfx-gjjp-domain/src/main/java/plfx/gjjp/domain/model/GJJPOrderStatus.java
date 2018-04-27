package plfx.gjjp.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import plfx.gjjp.domain.common.GJJPConstants;
import plfx.jp.domain.model.J;

/**
 * @类功能说明：国际机票供应商订单状态
 * @类修改者：
 * @修改日期：2015-7-7下午4:40:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-7下午4:40:35
 */
@Embeddable
@SuppressWarnings("serial")
public class GJJPOrderStatus implements Serializable {

	/**
	 * 供应商订单状态
	 * 
	 * @see GJJPConstants#SUPPLIER_ORDER_STATUS_MAP
	 */
	@Column(name = "SUPPLIER_STATUS", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer supplierCurrentValue;

	/**
	 * 平台订单状态
	 * 
	 * @see GJJPConstants#ORDER_STATUS_MAP
	 */
	@Column(name = "STATUS", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer currentValue;
	
	// --------------------------------------------------------------

	/**
	 * 取消理由
	 */
	@Column(name = "CANCEL_REASON", length = 1024)
	private String cancelReason;
	
	/**
	 * 拒绝取消理由
	 */
	@Column(name = "REFUSE_CANCEL_REASON", length = 1024)
	private String refuseCancelReason;
	
	/**
	 * 拒绝出票理由
	 */
	@Column(name = "REFUSE_OUTTICKET_REASON", length = 1024)
	private String refuseOutTicketReason;

	public Integer getSupplierCurrentValue() {
		return supplierCurrentValue;
	}

	public void setSupplierCurrentValue(Integer supplierCurrentValue) {
		this.supplierCurrentValue = supplierCurrentValue;
	}

	public Integer getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getRefuseCancelReason() {
		return refuseCancelReason;
	}

	public void setRefuseCancelReason(String refuseCancelReason) {
		this.refuseCancelReason = refuseCancelReason;
	}

	public String getRefuseOutTicketReason() {
		return refuseOutTicketReason;
	}

	public void setRefuseOutTicketReason(String refuseOutTicketReason) {
		this.refuseOutTicketReason = refuseOutTicketReason;
	}
	
}

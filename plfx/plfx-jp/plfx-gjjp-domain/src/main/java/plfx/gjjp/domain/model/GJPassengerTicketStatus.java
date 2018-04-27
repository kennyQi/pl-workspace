package plfx.gjjp.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import plfx.gjjp.domain.common.GJJPConstants;
import plfx.jp.domain.model.J;

/**
 * @类功能说明：乘客机票状态
 * @类修改者：
 * @修改日期：2015-7-14上午11:00:04
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-14上午11:00:04
 */
@Embeddable
@SuppressWarnings("serial")
public class GJPassengerTicketStatus implements Serializable {

	/**
	 * 供应商机票状态
	 * 
	 * @see GJJPConstants#SUPPLIER_TICKET_STATUS_MAP
	 */
	@Column(name = "SUPPLIER_STATUS", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer supplierCurrentValue;

	/**
	 * 平台机票状态
	 * 
	 * @see GJJPConstants#TICKET_STATUS_MAP
	 */
	@Column(name = "STATUS", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer currentValue;

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

}

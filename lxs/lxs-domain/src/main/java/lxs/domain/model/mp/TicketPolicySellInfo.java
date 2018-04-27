package lxs.domain.model.mp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明:联票政策销售信息
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:05:04
 * @版本：V1.0
 */
@Embeddable
public class TicketPolicySellInfo {

	/**
	 * 是否过期自动退款
	 */
	@Column(name = "AUTO_MATIC_REFUND")
	private Boolean autoMaticRefund;

	public Boolean getAutoMaticRefund() {
		return autoMaticRefund;
	}

	public void setAutoMaticRefund(Boolean autoMaticRefund) {
		this.autoMaticRefund = autoMaticRefund;
	}

}
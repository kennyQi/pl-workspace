package plfx.jp.command.api.gj;

import hg.common.component.BaseCommand;

import java.util.Map;

/**
 * @类功能说明：国际机票订单供应商审核通过
 * @类修改者：
 * @修改日期：2015年7月19日下午5:33:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015年7月19日下午5:33:17
 */
@SuppressWarnings("serial")
public class GJJPOrderAuditSuccessCommand extends BaseCommand {

	/**
	 * 国际机票供应商政策
	 */
	private Map<Integer, GJJPSupplierPolicy> policyMap;

	/**
	 * 供应商订单支付总价
	 */
	private Double supplierOrderTotalPrice;

	/**
	 * 供应商订单状态
	 */
	private Integer supplierOrderStatus;

	public Map<Integer, GJJPSupplierPolicy> getPolicyMap() {
		return policyMap;
	}

	public void setPolicyMap(Map<Integer, GJJPSupplierPolicy> policyMap) {
		this.policyMap = policyMap;
	}

	public Double getSupplierOrderTotalPrice() {
		return supplierOrderTotalPrice;
	}

	public void setSupplierOrderTotalPrice(Double supplierOrderTotalPrice) {
		this.supplierOrderTotalPrice = supplierOrderTotalPrice;
	}

	public Integer getSupplierOrderStatus() {
		return supplierOrderStatus;
	}

	public void setSupplierOrderStatus(Integer supplierOrderStatus) {
		this.supplierOrderStatus = supplierOrderStatus;
	}

	/**
	 * @类功能说明：国际机票供应商政策
	 * @类修改者：
	 * @修改日期：2015年7月19日下午5:39:31
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zhurz
	 * @创建时间：2015年7月19日下午5:39:31
	 */
	public static class GJJPSupplierPolicy {

		/**
		 * 乘客类型
		 */
		private Integer psgType;

		/**
		 * 票面价
		 */
		private Double ticketPrice;

		/**
		 * 基础返点(<1)
		 */
		private Double basicDisc;

		/**
		 * 奖励返点(<1)
		 */
		private Double extraDisc;

		/**
		 * 税金
		 */
		private Double tax;

		/**
		 * 开票费
		 */
		private Double outTktMoney;

		/**
		 * 单张支付总价
		 */
		private Double ordDetPrice;

		public Integer getPsgType() {
			return psgType;
		}

		public void setPsgType(Integer psgType) {
			this.psgType = psgType;
		}

		public Double getTicketPrice() {
			return ticketPrice;
		}

		public void setTicketPrice(Double ticketPrice) {
			this.ticketPrice = ticketPrice;
		}

		public Double getBasicDisc() {
			return basicDisc;
		}

		public void setBasicDisc(Double basicDisc) {
			this.basicDisc = basicDisc;
		}

		public Double getExtraDisc() {
			return extraDisc;
		}

		public void setExtraDisc(Double extraDisc) {
			this.extraDisc = extraDisc;
		}

		public Double getTax() {
			return tax;
		}

		public void setTax(Double tax) {
			this.tax = tax;
		}

		public Double getOutTktMoney() {
			return outTktMoney;
		}

		public void setOutTktMoney(Double outTktMoney) {
			this.outTktMoney = outTktMoney;
		}

		public Double getOrdDetPrice() {
			return ordDetPrice;
		}

		public void setOrdDetPrice(Double ordDetPrice) {
			this.ordDetPrice = ordDetPrice;
		}

	}
}

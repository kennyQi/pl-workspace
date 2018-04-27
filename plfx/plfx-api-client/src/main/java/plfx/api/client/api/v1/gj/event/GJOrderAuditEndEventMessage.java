package plfx.api.client.api.v1.gj.event;

import java.util.Map;

import plfx.api.client.common.PlfxApiConstants.GJ;
import plfx.api.client.common.publish.PublishEventMessage;

/**
 * @类功能说明：国际机票订单审核完成事件消息
 * @类修改者：
 * @修改日期：2015-7-8下午3:30:09
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-8下午3:30:09
 */
public class GJOrderAuditEndEventMessage extends PublishEventMessage {

	/**
	 * 分销平台国际机票订单号
	 */
	private String platformOrderId;

	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

	/**
	 * 订单支付总价
	 */
	private Double ordAllPrice;

	/**
	 * 乘客类型与价格
	 * 
	 * <乘客类型, 机票价格详情>
	 * 
	 * @see GJ#PASSENGER_TYPE_MAP
	 */
	private Map<Integer, PriceDetail> priceDetailMap;

	/**
	 * 审核状态
	 * 
	 * 1-通过，2-退回
	 */
	private Integer auditStatus;

	/**
	 * 客服审核退回原因
	 */
	private String refuseMemo;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public Double getOrdAllPrice() {
		return ordAllPrice;
	}

	public void setOrdAllPrice(Double ordAllPrice) {
		this.ordAllPrice = ordAllPrice;
	}

	public Map<Integer, PriceDetail> getPriceDetailMap() {
		return priceDetailMap;
	}

	public void setPriceDetailMap(Map<Integer, PriceDetail> priceDetailMap) {
		this.priceDetailMap = priceDetailMap;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getRefuseMemo() {
		return refuseMemo;
	}

	public void setRefuseMemo(String refuseMemo) {
		this.refuseMemo = refuseMemo;
	}

	/**
	 * @类功能说明：机票价格详情
	 * @类修改者：
	 * @修改日期：2015-7-8下午3:41:15
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zhurz
	 * @创建时间：2015-7-8下午3:41:15
	 */
	public static class PriceDetail {
		/**
		 * 票面价
		 */
		private Double ticketPrice;

		/**
		 * 税金
		 */
		private Double tax;

		/**
		 * 开票费
		 */
		private Double outTktMoney;

		/**
		 * 政策价格
		 */
		private Double policyPrice;

		/**
		 * 单张支付总价=票面价+政策价格+开票费+税金
		 */
		private Double ordDetPrice;

		public Double getTicketPrice() {
			return ticketPrice;
		}

		public void setTicketPrice(Double ticketPrice) {
			this.ticketPrice = ticketPrice;
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

		public Double getPolicyPrice() {
			return policyPrice;
		}

		public void setPolicyPrice(Double policyPrice) {
			this.policyPrice = policyPrice;
		}

		public Double getOrdDetPrice() {
			return ordDetPrice;
		}

		public void setOrdDetPrice(Double ordDetPrice) {
			this.ordDetPrice = ordDetPrice;
		}

	}
}

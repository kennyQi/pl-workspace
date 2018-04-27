package hg.dzpw.dealer.client.dto.policy;

import hg.dzpw.dealer.client.common.EmbeddDTO;

import java.util.Date;

/**
 * @类功能说明:联票政策销售信息
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:05:04
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class TicketPolicySellInfoDTO extends EmbeddDTO {

	/** 退票规则：不能退 */
	public final static Integer RETURN_RULE_DISABLE = 1;
	/** 退票规则：无条件退 */
	public final static Integer RETURN_RULE_UNCONDITIONAL = 2;
	/** 退票规则：退票收取百分比手续费 */
	public final static Integer RETURN_RULE_COST_PERCENT = 3;
	/** 退票规则：退票收取X元手续费 */
	public final static Integer RETURN_RULE_COST_RMB_YUAN = 4;


	/**
	 * 剩余可售数量
	 * -1表示库存无限
	 */
	private Integer remainingQty = 0;
	
	/**
	 * 已售数量
	 */
	private Integer soldQty = 0;

	/**
	 * 退票规则 1.不能退2.无条件退3.退票收取百分比手续费4.退票收取X元手续费
	 */
	private Integer returnRule;
	
	/**
	 * 退票手续费
	 */
	private Double returnCost = 0d;
	
	/**
	 * 是否过期自动退款
	 */
	private Boolean autoMaticRefund;
	

	public Integer getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(Integer remainingQty) {
		this.remainingQty = remainingQty;
	}

	public Integer getReturnRule() {
		return returnRule;
	}

	public void setReturnRule(Integer returnRule) {
		this.returnRule = returnRule;
	}

	public Double getReturnCost() {
		return returnCost;
	}

	public void setReturnCost(Double returnCost) {
		this.returnCost = returnCost;
	}

	public Integer getSoldQty() {
		return soldQty;
	}

	public void setSoldQty(Integer soldQty) {
		this.soldQty = soldQty;
	}

	public Boolean getAutoMaticRefund() {
		return autoMaticRefund;
	}

	public void setAutoMaticRefund(Boolean autoMaticRefund) {
		this.autoMaticRefund = autoMaticRefund;
	}

}
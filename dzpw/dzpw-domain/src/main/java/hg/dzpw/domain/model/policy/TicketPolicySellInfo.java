package hg.dzpw.domain.model.policy;

import hg.dzpw.domain.model.M;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

/**
 * @类功能说明:联票政策销售信息
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:05:04
 * @版本：V1.0
 */
@Embeddable
public class TicketPolicySellInfo implements Serializable {
	private static final long serialVersionUID = 1L;

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
	@Column(name = "REMAINING_QTY", columnDefinition = M.NUM_COLUM)
	private Integer remainingQty = 0;
	
	/**
	 * 已售数量
	 */
	@Column(name = "SOLD_QTY", columnDefinition = M.NUM_COLUM)
	private Integer soldQty = 0;

	/**
	 * 退票规则 1.不能退2.无条件退3.退票收取百分比手续费4.退票收取X元手续费
	 */
	@Column(name = "RETURN_RULE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer returnRule;
	
	/**
	 * 是否过期自动退款
	 */
	@Type(type = "yes_no")
	@Column(name = "AUTOMATIC_REFUND")
	private Boolean autoMaticRefund;

	/**
	 * 退票手续费
	 */
	@Column(name = "RETURN_COST", columnDefinition = M.MONEY_COLUM)
	private Double returnCost = 0d;

	/**
	 * @方法功能说明：是否可以退票
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-3下午3:27:04
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean canReturn() {
		if (returnRule == null || returnRule == RETURN_RULE_DISABLE.intValue())
			return false;
		return true;
	}


	public Integer getRemainingQty() {
		if (remainingQty == null)
			remainingQty = 0;
		return remainingQty;
	}

	public void setRemainingQty(Integer remainingQty) {
		this.remainingQty = remainingQty;
	}

	public Integer getSoldQty() {
		if (soldQty == null)
			soldQty = 0;
		return soldQty;
	}

	public void setSoldQty(Integer soldQty) {
		this.soldQty = soldQty;
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

	public Boolean getAutoMaticRefund() {
		return autoMaticRefund;
	}

	public void setAutoMaticRefund(Boolean autoMaticRefund) {
		this.autoMaticRefund = autoMaticRefund;
	}

}
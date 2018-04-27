package hsl.domain.model.dzp.policy;

import hsl.domain.model.M;
import hsl.pojo.util.HSLConstants;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 联票政策销售信息
 *
 * @author zhurz
 * @since 1.8
 */
@Embeddable
@SuppressWarnings("serial")
public class DZPTicketPolicySellInfo implements HSLConstants.DZPWTicketPolicySellReturnRule, Serializable {

	/**
	 * 最低价格（冗余字段）
	 */
	@Column(name = "PRICE_MIN", columnDefinition = M.MONEY_COLUM)
	private Double priceMin = 0d;

	/**
	 * 退票规则
	 *
	 * @see HSLConstants.DZPWTicketPolicySellReturnRule
	 */
	@Column(name = "RETURN_RULE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer returnRule;
	
	/**
	 * 退票手续费
	 */
	@Column(name = "RETURN_COST", columnDefinition = M.MONEY_COLUM)
	private Double returnCost = 0d;
	
	/**
	 * 是否过期自动退款
	 */
	@Type(type = "yes_no")
	@Column(name = "AUTO_MATIC_REFUND")
	private Boolean autoMaticRefund;

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

	public Double getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(Double priceMin) {
		this.priceMin = priceMin;
	}
}
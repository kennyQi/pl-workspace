package hg.dzpw.pojo.command.platform.policy;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @类功能说明：运营端联票修改
 * @类修改者：
 * @修改日期：2015-3-16下午6:05:00
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-16下午6:05:00
 */
public class PlatformModifyGroupTicketPolicyCommand extends
		DZPWPlatformBaseCommand {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 联票政策ID
	 */
	private String ticketPolicyId;
	/**
	 * 产品编号
	 */
	private String key;
	/**
	 * 联票名称
	 */
	private String name;
	/**
	 * 联票介绍
	 */
	private String intro;
	/**
	 * 预定须知
	 */
	private String notice;
	/**
	 * 售卖协议
	 */
	private String saleAgreement;
	/**
	 * 市场价格(所有景区市场票面价之和)
	 */
	private Double rackRate;
	/**
	 * 游玩理财价(所有景区游玩理财价之和)
	 */
	private Double playPrice;
	/**
	 * 结算价(所有景区结算价之和)
	 */
	private Double settlementPrice;
	/**
	 * 库存数量
	 */
	private Integer remainingQty = 0;
	/**
	 * 使用的验证类型
	 */
	private Integer validUseDateType;
	/**
	 * 有效天数(单票可入园天数，联票自出票后的有效天数)
	 */
	private Integer validDays;
	/**
	 * 退票规则 1.不能退2.无条件退3.退票收取百分比手续费4.退票收取X元手续费
	 */
	private Integer returnRule;
	/**
	 * 退票手续费
	 */
	private Double returnCost;
	/**
	 * 过期自动退
	 */
	private Boolean autoMaticRefund = true;
	/**
	 * 联票中的单票
	 */
	private List<SingleTicketPolicy> singleTicketPolicies;

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Double getRackRate() {
		return rackRate;
	}

	public void setRackRate(Double rackRate) {
		this.rackRate = rackRate;
	}

	public Integer getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(Integer remainingQty) {
		this.remainingQty = remainingQty;
	}

	public Integer getValidUseDateType() {
		return validUseDateType;
	}

	public void setValidUseDateType(Integer validUseDateType) {
		this.validUseDateType = validUseDateType;
	}

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
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

	public List<SingleTicketPolicy> getSingleTicketPolicies() {
		if (singleTicketPolicies == null)
			singleTicketPolicies = new ArrayList<SingleTicketPolicy>();
		return singleTicketPolicies;
	}

	public void setSingleTicketPolicies(
			List<SingleTicketPolicy> singleTicketPolicies) {
		this.singleTicketPolicies = singleTicketPolicies;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getSaleAgreement() {
		return saleAgreement;
	}

	public void setSaleAgreement(String saleAgreement) {
		this.saleAgreement = saleAgreement;
	}

	public Double getPlayPrice() {
		return playPrice;
	}

	public void setPlayPrice(Double playPrice) {
		this.playPrice = playPrice;
	}

	public Double getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Double settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public Boolean getAutoMaticRefund() {
		return autoMaticRefund;
	}

	public void setAutoMaticRefund(Boolean autoMaticRefund) {
		this.autoMaticRefund = autoMaticRefund;
	}

}

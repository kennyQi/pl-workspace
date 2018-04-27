package hg.dzpw.pojo.command.merchant.policy;

import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;

/**
 * @类功能说明：创建独立单票政策（景区后台）
 * @类修改者：
 * @修改日期：2015-2-9下午5:31:27
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-9下午5:31:27
 */
@SuppressWarnings("serial")
public class ScenicspotCreateSingleTicketPolicyCommand extends
		DZPWMerchantBaseCommand {

	/**
	 * 独立单票编号
	 */
	private String key;
	/**
	 * 单票名称
	 */
	private String name;
	/**
	 * 单票介绍
	 */
	private String intro;
	/**
	 * 预订须知
	 */
	private String notice;
	/**
	 * 市场价格
	 */
	private Double rackRate;
	/**
	  * 连续游玩天数(单票可入园天数)
	 */
	private Integer validDays;
	/**
	 * 使用验证类型  2--有效天数
	 */
	private Integer validUseDateType;
	/**
	 * 剩余可售数量
	 */
	private Integer remainingQty = 0;
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
	private Boolean autoMaticRefund;
	/**
	 * 售卖协议
	 */
	private String saleAgreement;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Double getRackRate() {
		return rackRate;
	}

	public void setRackRate(Double rackRate) {
		this.rackRate = rackRate;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(Integer remainingQty) {
		this.remainingQty = remainingQty;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Boolean getAutoMaticRefund() {
		return autoMaticRefund;
	}

	public void setAutoMaticRefund(Boolean autoMaticRefund) {
		this.autoMaticRefund = autoMaticRefund;
	}

	public Integer getValidUseDateType() {
		return validUseDateType;
	}

	public void setValidUseDateType(Integer validUseDateType) {
		this.validUseDateType = validUseDateType;
	}

	public String getSaleAgreement() {
		return saleAgreement;
	}

	public void setSaleAgreement(String saleAgreement) {
		this.saleAgreement = saleAgreement;
	}

}

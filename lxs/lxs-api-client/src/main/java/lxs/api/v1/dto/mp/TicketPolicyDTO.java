package lxs.api.v1.dto.mp;

import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class TicketPolicyDTO extends BaseDTO {

	/** 门票政策类型：独立单票 */
	public final static Integer TICKET_POLICY_TYPE_SINGLE = 1;
	/** 门票政策类型：套票（联票） */
	public final static Integer TICKET_POLICY_TYPE_GROUP = 2;

	/** 退票规则：不能退 */
	public final static Integer RETURN_RULE_DISABLE = 1;
	/** 退票规则：无条件退 */
	public final static Integer RETURN_RULE_UNCONDITIONAL = 2;
	/** 退票规则：退票收取百分比手续费 */
	public final static Integer RETURN_RULE_COST_PERCENT = 3;
	/** 退票规则：退票收取X元手续费 */
	public final static Integer RETURN_RULE_COST_RMB_YUAN = 4;

	/**
	 * 政策ID
	 */
	private String ticketPolicyID;

	/**
	 * 政策类型
	 */
	private Integer type;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 版本
	 */
	private Integer version;

	/**
	 * 有效天数(独立单票可入园天数 or 联票自出票后的有效天数)
	 */
	private Integer validDays;

	/**
	 * 单票、联票门市价/市场票面价
	 */
	private Double rackRate;

	/**
	 * 联票(与经销商)游玩价
	 */
	private Double playPrice;

	/**
	 * 联票OR单票介绍
	 */
	private String intro;

	/**
	 * 预定须知
	 */
	private String notice;

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

	/**
	 * 包含景点(冗余字段)
	 */
	private String scenicSpotNameStr;
	
	
	public String getScenicSpotNameStr() {
		return scenicSpotNameStr;
	}

	public void setScenicSpotNameStr(String scenicSpotNameStr) {
		this.scenicSpotNameStr = scenicSpotNameStr;
	}

	public String getTicketPolicyID() {
		return ticketPolicyID;
	}

	public void setTicketPolicyID(String ticketPolicyID) {
		this.ticketPolicyID = ticketPolicyID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Double getRackRate() {
		return rackRate;
	}

	public void setRackRate(Double rackRate) {
		this.rackRate = rackRate;
	}

	public Double getPlayPrice() {
		return playPrice;
	}

	public void setPlayPrice(Double playPrice) {
		this.playPrice = playPrice;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
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

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}

}

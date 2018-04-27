package plfx.mp.tcclient.tc.domain;
/**
 * 价格明细
 * @author zhangqy
 *
 */
public class Policy {
	/**
	 * 价格策略id
	 */
	private Integer policyId;
	/**
	 * 价格策略名称
	 */
	private String policyName;
	/**
	 * 门票说明
	 */
	private String remark;
	/**
	 * 门市价格
	 */
	private Double price;
	/**
	 * 同程价格
	 */
	private Double tcPrice;
	/**
	 * 是否使用二代身份证 
	 */
	private Integer useCard;
	/**
	 * 支付方式
	 */
	private Integer pMode;
	/**
	 * 取票方式
	 */
	private String gMode;
	/**
	 * 最小票数
	 */
	private Integer minT;
	/**
	 * 最大票数
	 */
	private Integer maxT;
	/**
	 * 最大可用现金券
	 */
	private String dpPrize;
	/**
	 * 预订跳转
	 */
	private String orderUrl;
	/**
	 * 是否实名制 
	 */
	private Integer realName;
	/**
	 * 门票类型Id
	 */
	private Integer ticketId;
	/**
	 * 门票类型名称
	 */
	private String ticketName;
	/**
	 * 开始时间
	 */
	private String bDate;
	/**
	 * 结束时间
	 */
	private String eDate;
	/**
	 * 价格策略有效期类型
	 */
	private Integer openDateType;
	/**
	 * 价格策略具体有效期
	 */
	private String openDateValue;
	/**
	 * 价格策略里面的屏蔽节假日
	 */
	private String closeDate;
	private String advanceDay;
	private String timeLimit;
	public Integer getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTcPrice() {
		return tcPrice;
	}
	public void setTcPrice(Double tcPrice) {
		this.tcPrice = tcPrice;
	}
	public Integer getPMode() {
		return pMode;
	}
	public void setPMode(Integer pMode) {
		this.pMode = pMode;
	}
	public String getGMode() {
		return gMode;
	}
	public void setGMode(String gMode) {
		this.gMode = gMode;
	}
	public Integer getMinT() {
		return minT;
	}
	public void setMinT(Integer minT) {
		this.minT = minT;
	}
	public Integer getMaxT() {
		return maxT;
	}
	public void setMaxT(Integer maxT) {
		this.maxT = maxT;
	}
	public String getDpPrize() {
		return dpPrize;
	}
	public void setDpPrize(String dpPrize) {
		this.dpPrize = dpPrize;
	}
	public String getOrderUrl() {
		return orderUrl;
	}
	public void setOrderUrl(String orderUrl) {
		this.orderUrl = orderUrl;
	}
	public Integer getRealName() {
		return realName;
	}
	public void setRealName(Integer realName) {
		this.realName = realName;
	}
	public Integer getTicketId() {
		return ticketId;
	}
	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicketName() {
		return ticketName;
	}
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	public String getBDate() {
		return bDate;
	}
	public void setBDate(String bDate) {
		this.bDate = bDate;
	}
	public String getEDate() {
		return eDate;
	}
	public void setEDate(String eDate) {
		this.eDate = eDate;
	}
	public Integer getOpenDateType() {
		return openDateType;
	}
	public void setOpenDateType(Integer openDateType) {
		this.openDateType = openDateType;
	}
	public String getOpenDateValue() {
		return openDateValue;
	}
	public void setOpenDateValue(String openDateValue) {
		this.openDateValue = openDateValue;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public Integer getUseCard() {
		return useCard;
	}
	public void setUseCard(Integer useCard) {
		this.useCard = useCard;
	}
	public String getAdvanceDay() {
		return advanceDay;
	}
	public void setAdvanceDay(String advanceDay) {
		this.advanceDay = advanceDay;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	
}

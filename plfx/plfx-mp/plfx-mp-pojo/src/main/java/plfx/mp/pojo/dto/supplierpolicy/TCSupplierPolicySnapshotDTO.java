package plfx.mp.pojo.dto.supplierpolicy;

import java.util.Date;

import plfx.mp.pojo.dto.BaseMpDTO;

/**
 * 同城(供应商)价格政策快照
 * 
 * @author Administrator
 */
public class TCSupplierPolicySnapshotDTO extends BaseMpDTO {
	private static final long serialVersionUID = 1L;
	/**
	 * 政策id
	 */
	private String policyId;
	/**
	 * 是否最新快照
	 */
	private Boolean lastSnapshot;
	/**
	 * 政策名称
	 */
	private String name;
	/**
	 * 门票类型id
	 */
	private String ticketId;
	/**
	 * 门票类型名称
	 */
	private String ticketName;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 门市价
	 */
	private Double retailPrice;

	/**
	 * 同程价
	 */
	private Double tcPrice;
	/**
	 * 支付方式
	 */
	private Integer pMode;
	/**
	 * 取票方式
	 */
	private String ticketDelivery;
	/**
	 * 最小可购买数
	 */
	private Integer minTicket;
	/**
	 * 最大可购买数
	 */
	private Integer maxTicket;
	/**
	 * 最大可用现金券
	 */
	private Integer dpPrize;
	/**
	 * 是否实名
	 */
	private Boolean realNameNeed;
	/**
	 * 是否使用身份证
	 */
	private Boolean rseIdCardNeed;
	/**
	 * 票使用有效期开始时间
	 */
	private Date validityBeginDate;
	/**
	 * 票使用有效期结束时间
	 */
	private Date validityEndDate;
	/**
	 * 票有效期类型
	 */
	private Integer validityType;
	/**
	 * 票有效期明细
	 */
	private String validityValue;
	/**
	 * 票有效期屏蔽日期
	 */
	private String invalidDate;
	/**
	 * 政策须知
	 */
	private TCPolicyNoticeDTO notice;
	/**
	 * 快照日期
	 */
	private Date snapshotDate;
	/**
	 * 旅游景点
	 */
	public ScenicSpotSnapshotDTO scenicSpotSnapshot;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Boolean getLastSnapshot() {
		return lastSnapshot;
	}

	public void setLastSnapshot(Boolean lastSnapshot) {
		this.lastSnapshot = lastSnapshot;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Double getTcPrice() {
		return tcPrice;
	}

	public void setTcPrice(Double tcPrice) {
		this.tcPrice = tcPrice;
	}

	public Integer getpMode() {
		return pMode;
	}

	public void setpMode(Integer pMode) {
		this.pMode = pMode;
	}

	public String getTicketDelivery() {
		return ticketDelivery;
	}

	public void setTicketDelivery(String ticketDelivery) {
		this.ticketDelivery = ticketDelivery;
	}

	public Integer getMinTicket() {
		return minTicket;
	}

	public void setMinTicket(Integer minTicket) {
		this.minTicket = minTicket;
	}

	public Integer getMaxTicket() {
		return maxTicket;
	}

	public void setMaxTicket(Integer maxTicket) {
		this.maxTicket = maxTicket;
	}

	public Integer getDpPrize() {
		return dpPrize;
	}

	public void setDpPrize(Integer dpPrize) {
		this.dpPrize = dpPrize;
	}

	public Boolean getRealNameNeed() {
		return realNameNeed;
	}

	public void setRealNameNeed(Boolean realNameNeed) {
		this.realNameNeed = realNameNeed;
	}

	public Boolean getRseIdCardNeed() {
		return rseIdCardNeed;
	}

	public void setRseIdCardNeed(Boolean rseIdCardNeed) {
		this.rseIdCardNeed = rseIdCardNeed;
	}

	public Date getValidityBeginDate() {
		return validityBeginDate;
	}

	public void setValidityBeginDate(Date validityBeginDate) {
		this.validityBeginDate = validityBeginDate;
	}

	public Date getValidityEndDate() {
		return validityEndDate;
	}

	public void setValidityEndDate(Date validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	public Integer getValidityType() {
		return validityType;
	}

	public void setValidityType(Integer validityType) {
		this.validityType = validityType;
	}

	public String getValidityValue() {
		return validityValue;
	}

	public void setValidityValue(String validityValue) {
		this.validityValue = validityValue;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}

	public TCPolicyNoticeDTO getNotice() {
		return notice;
	}

	public void setNotice(TCPolicyNoticeDTO notice) {
		this.notice = notice;
	}

	public Date getSnapshotDate() {
		return snapshotDate;
	}

	public void setSnapshotDate(Date snapshotDate) {
		this.snapshotDate = snapshotDate;
	}

	public ScenicSpotSnapshotDTO getScenicSpotSnapshot() {
		return scenicSpotSnapshot;
	}

	public void setScenicSpotSnapshot(ScenicSpotSnapshotDTO scenicSpotSnapshot) {
		this.scenicSpotSnapshot = scenicSpotSnapshot;
	}

}
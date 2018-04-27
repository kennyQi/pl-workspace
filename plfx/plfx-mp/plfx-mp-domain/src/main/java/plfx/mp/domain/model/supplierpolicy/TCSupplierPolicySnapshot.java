package plfx.mp.domain.model.supplierpolicy;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import plfx.mp.domain.model.M;

/**
 * 同城(供应商)价格政策快照
 * 
 * @author Administrator
 */
@Entity
@Table(name = M.TABLE_PREFIX + "TC_POLICY_SNAPSHOT")
public class TCSupplierPolicySnapshot extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 政策id
	 */
	@Column(name = "POLICY_ID", length = 64)
	private String policyId;
	/**
	 * 是否最新快照
	 */
	@Type(type = "yes_no")
	@Column(name = "LAST_SNAPSHOT")
	private Boolean lastSnapshot;
	/**
	 * 政策名称
	 */
	@Column(name = "NAME", length = 128)
	private String name;
	/**
	 * 门票类型id
	 */
	@Column(name = "TICKET_ID", length = 64)
	private String ticketId;
	/**
	 * 门票类型名称
	 */
	@Column(name = "TICKET_NAME", length = 64)
	private String ticketName;
	/**
	 * 备注
	 */
	@Column(name = "REMARK", length = 1024)
	private String remark;

	/**
	 * 门市价
	 */
	@Column(name = "RETAIL_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double retailPrice;

	/**
	 * 同程价
	 */
	@Column(name = "TC_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double tcPrice;
	/**
	 * 支付方式
	 */
	@Column(name = "P_MODE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer pMode;
	/**
	 * 取票方式
	 */
	@Column(name = "TICKET_DELIVERY", length = 256)
	private String ticketDelivery;
	/**
	 * 最小可购买数
	 */
	@Column(name = "MIN_TICKET", columnDefinition = M.NUM_COLUM)
	private Integer minTicket;
	/**
	 * 最大可购买数
	 */
	@Column(name = "MAX_TICKET", columnDefinition = M.NUM_COLUM)
	private Integer maxTicket;
	/**
	 * 最大可用现金券
	 */
	@Column(name = "DP_PRIZE", columnDefinition = M.NUM_COLUM)
	private Integer dpPrize;
	/**
	 * 是否实名
	 */
	@Type(type = "yes_no")
	@Column(name = "REAL_NAME_NEED")
	private Boolean realNameNeed;
	/**
	 * 是否使用身份证
	 */
	@Type(type = "yes_no")
	@Column(name = "RSE_ID_CARD_NEED")
	private Boolean rseIdCardNeed;
	/**
	 * 票使用有效期开始时间
	 */
	@Column(name = "VALIDITY_BEGIN_DATE", columnDefinition = M.DATE_COLUM)
	private Date validityBeginDate;
	/**
	 * 票使用有效期结束时间
	 */
	@Column(name = "VALIDITY_END_DATE", columnDefinition = M.DATE_COLUM)
	private Date validityEndDate;
	/**
	 * 票有效期类型
	 */
	@Column(name = "VALIDITY_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer validityType;
	/**
	 * 票有效期明细
	 */
	@Column(name = "VALIDITY_VALUE", length = 4000)
	private String validityValue;
	/**
	 * 票有效期屏蔽日期
	 * 格式：[月-日],[月-日],...
	 */
	@Column(name = "INVALID_DATE", length = 512)
	private String invalidDate;
	/**
	 * 政策须知
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NOTICE_ID")
	private TCPolicyNotice notice;
	/**
	 * 快照日期
	 */
	@Column(name = "SNAPSHOT_DATE", columnDefinition = M.DATE_COLUM)
	private Date snapshotDate;
	/**
	 * 接口返回result转JSON后的MD5
	 */
	@Column(name = "RESULT_MD5", length = 64)
	private String resultMd5;
	/**
	 * 旅游景点
	 */
	@Embedded
	public ScenicSpotSnapshot scenicSpotSnapshot;
	
	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
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

	public TCPolicyNotice getNotice() {
		return super.getProperty(notice, TCPolicyNotice.class);
	}

	public void setNotice(TCPolicyNotice notice) {
		this.notice = notice;
	}

	public Date getSnapshotDate() {
		return snapshotDate;
	}

	public void setSnapshotDate(Date snapshotDate) {
		this.snapshotDate = snapshotDate;
	}

	public Boolean getLastSnapshot() {
		return lastSnapshot;
	}

	public void setLastSnapshot(Boolean lastSnapshot) {
		this.lastSnapshot = lastSnapshot;
	}

	public String getResultMd5() {
		return resultMd5;
	}

	public void setResultMd5(String resultMd5) {
		this.resultMd5 = resultMd5;
	}

	public ScenicSpotSnapshot getScenicSpotSnapshot() {
		if (scenicSpotSnapshot == null) {
			scenicSpotSnapshot = new ScenicSpotSnapshot();
		}
		return scenicSpotSnapshot;
	}

	public void setScenicSpotSnapshot(ScenicSpotSnapshot scenicSpotSnapshot) {
		this.scenicSpotSnapshot = scenicSpotSnapshot;
	}

}
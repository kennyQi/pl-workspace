package hsl.domain.model.mp.scenic;

import hg.common.component.BaseModel;
import hg.common.component.hibernate.dialect.ColumnDefinitionMysql;
import hsl.domain.model.M;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * 门票政策
 * 
 * @author yxx
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_HSL_MP+"POLICY")
public class MPPolicy extends BaseModel{

	/**
	 * 政策id
	 */
	@Column(name="POLICY_ID",length=64)
	private String policyId;
	
	/**
	 * 政策名称
	 */
	@Column(name="POLICY_NAME",length=128)
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
	 * 汇购平台价
	 */
	@Column(name = "PRICE", columnDefinition = M.MONEY_COLUM)
	private Double price;

	/**
	 * 支付方式,0是景区现付
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
	 */
	@Column(name = "INVALID_DATE", length = 512)
	private String invalidDate;

	/**
	 * 购票需知
	 */
	@Column(name="BUY_NOTICE",columnDefinition=ColumnDefinitionMysql.TEXT_COLUM)
	private String buyNotie;
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

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

	public String getBuyNotie() {
		return buyNotie;
	}

	public void setBuyNotie(String buyNotie) {
		this.buyNotie = buyNotie;
	}

}
package hg.fx.domain.fixedprice;

import java.util.Date;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;
import hg.fx.domain.rebate.RebateSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author yangkang
 * @date 2016-07-22
 * 返利月报实体
 * */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "FIXED_PRICE_REPORT")
public class FixedPriceReport extends BaseStringIdModel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品ID
	 * */
	@Column(name = "PROD_ID", length = 64)
	private String prodId;
	
	/**
	 * 商品名称
	 */
	@Column(name = "PROD_NAME", length = 32)
	private String prodName;	
	
	/**
	 * 商品所属渠道名称
	 * */
	@Column(name = "CHANNEL_NAME", length = 32)
	private String channelName;
	
	/**
	 * 商户ID
	 * */
	@Column(name = "DISTRIBUTOR_ID", length = 64)
	private String distributorId;
	
	/**
	 * 商户名称
	 */
	@Column(name="DISTRIBUTOR_NAME", length = 256)
	private String distributorName;
	
	/**
	 * 消费额
	 * */
	@Column(name="CONSUME_NUM", columnDefinition = O.LONG_NUM_COLUM)
	private Long consumeNum;
	
	/**
	 * 目标数（区间）
	 */
	@Column(name="TARGET_INTERVAL", length = 64)
	private String targetInterval;
	
	/**
	 * 当前折扣   字段需确认含义 或是否需要此字段
	 * */
	@Column(name = "CURRENT_PERCENT", columnDefinition = O.MONEY_COLUM)
	private Double currentPercent;
	 
	/**
	 * 实际折扣   字段需确认含义 或是否需要此字段
	 * */
	@Column(name = "FACT_PERCENT", columnDefinition = O.MONEY_COLUM)
	private Double factPercent;
	
	/**
	 * 报表对应执行规则ID
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FIXEDPRICE_SET_ID")
	private FixedPriceSet fixedPriceSet;
	
	/**
	 * 生成时间
	 */
	@Column(name="CREATE_DATE",columnDefinition=O.DATE_COLUM)
	private Date createDate;

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public Long getConsumeNum() {
		return consumeNum;
	}

	public void setConsumeNum(Long consumeNum) {
		this.consumeNum = consumeNum;
	}

	public String getTargetInterval() {
		return targetInterval;
	}

	public void setTargetInterval(String targetInterval) {
		this.targetInterval = targetInterval;
	}

	public Double getCurrentPercent() {
		return currentPercent;
	}

	public void setCurrentPercent(Double currentPercent) {
		this.currentPercent = currentPercent;
	}

	public Double getFactPercent() {
		return factPercent;
	}

	public void setFactPercent(Double factPercent) {
		this.factPercent = factPercent;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public FixedPriceSet getFixedPriceSet() {
		return fixedPriceSet;
	}

	public void setFixedPriceSet(FixedPriceSet fixedPriceSet) {
		this.fixedPriceSet = fixedPriceSet;
	}
	
}

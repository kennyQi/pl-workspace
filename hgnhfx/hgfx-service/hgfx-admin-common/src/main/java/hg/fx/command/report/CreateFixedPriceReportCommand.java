package hg.fx.command.report;

import hg.framework.common.base.BaseSPICommand;
import hg.fx.domain.fixedprice.FixedPriceSet;

public class CreateFixedPriceReportCommand extends BaseSPICommand{
	
	/**  */
	private static final long serialVersionUID = 1L;

	/**
	 * 商品ID
	 * */
	private String prodId;
	
	/**
	 * 商品名称
	 */
	private String prodName;	
	
	/**
	 * 商品所属渠道名称
	 * */
	private String channelName;
	
	/**
	 * 商户ID
	 * */
	private String distributorId;
	
	/**
	 * 商户名称
	 */
	private String distributorName;
	
	/**
	 * 消费额
	 * */
	private Long consumeNum;
	
	/**
	 * 目标数（区间）
	 */
	private String targetInterval;
	
	/**
	 * 当前折扣   字段需确认含义 或是否需要此字段
	 * */
	private Double currentPercent;
	 
	/**
	 * 实际折扣   字段需确认含义 或是否需要此字段
	 * */
	private Double factPercent;
	
	/**
	 * 报表对应执行规则ID
	 */
	private FixedPriceSet fixedPriceSet;

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

	public FixedPriceSet getFixedPriceSet() {
		return fixedPriceSet;
	}

	public void setFixedPriceSet(FixedPriceSet fixedPriceSet) {
		this.fixedPriceSet = fixedPriceSet;
	}
	

}

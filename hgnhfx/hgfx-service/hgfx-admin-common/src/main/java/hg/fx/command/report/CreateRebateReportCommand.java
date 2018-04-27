package hg.fx.command.report;

import java.util.Date;


import hg.framework.common.base.BaseSPICommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.ProductInUse;
import hg.fx.domain.rebate.RebateSet;

public class CreateRebateReportCommand extends BaseSPICommand{
	
	private static final long serialVersionUID = 1L;

	private ProductInUse productInUse;
	
	/**
	 * 消费额
	 * */
	private Long consumeNum;
	
	private Distributor distributor;
	
	/**
	 * 执行返利的区间级别
	 * */
	private String interval;
	
	/**
	 * 执行返利折扣
	 * */
	private Double percent;
	
	/**
	 * 返利额
	 * */
	private Long rebateNum;
	
	/**
	 * 报表对应执行规则ID
	 */
	private RebateSet rebateSet;

	public ProductInUse getProductInUse() {
		return productInUse;
	}

	public void setProductInUse(ProductInUse productInUse) {
		this.productInUse = productInUse;
	}

	public Long getConsumeNum() {
		return consumeNum;
	}

	public void setConsumeNum(Long consumeNum) {
		this.consumeNum = consumeNum;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Long getRebateNum() {
		return rebateNum;
	}

	public void setRebateNum(Long rebateNum) {
		this.rebateNum = rebateNum;
	}

	public RebateSet getRebateSet() {
		return rebateSet;
	}

	public void setRebateSet(RebateSet rebateSet) {
		this.rebateSet = rebateSet;
	}
	
}

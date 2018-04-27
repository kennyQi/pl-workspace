package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;

import java.util.Date;

@SuppressWarnings("serial")
public class FixedPriceReportSQO extends BaseSPIQO {

	/**
	 * 商品ID
	 * */
	private String prodId;

	/**
	 * 商户ID
	 * */
	private String distributorId;

	/**
	 * 开始时间
	 */
	private Date startDate;
	/**
	 * 结束时间
	 */
	private Date endDate;

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}

package lxs.pojo.qo.line;

import java.util.Date;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class DateSalePriceQO extends BaseQo {
	/**
	 * 线路ID
	 */
	private String lineID;
	/**
	 * 结束时间
	 */
	private Date startDate;
	/**
	 * 开始时间
	 */
	private Date endDate;

	/**
	 * 日期
	 */
	private String saleDate;

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
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

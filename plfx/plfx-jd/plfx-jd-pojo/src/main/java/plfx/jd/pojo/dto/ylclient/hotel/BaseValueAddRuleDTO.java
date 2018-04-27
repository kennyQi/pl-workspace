package plfx.jd.pojo.dto.ylclient.hotel;

import java.math.BigDecimal;

import plfx.jd.pojo.dto.ylclient.order.BaseRuleDTO;
@SuppressWarnings("serial")
public class BaseValueAddRuleDTO extends BaseRuleDTO {
	/**
	 * 业务代码
	 */
	protected String typeCode;
	/**
	 * 是否包含在房费中
	 */
	protected boolean isInclude;
	/**
	 * 包含的份数
	 */
	protected int amount;
	/**
	 * 货币代码
	 */
	protected String currencyCode;
	/**
	 * 单价默认选项
	 */
	protected String priceOption;
	/**
	 * 单价
	 */
	protected BigDecimal price;
	/**
	 * 是否单加
	 */
	protected boolean isExtAdd;
	/**
	 * 单加单价默认选项
	 */
	protected String extOption;
	/**
	 * 单加单价
	 */
	protected BigDecimal extPrice;
	/**
	 * 开始日期
	 */
	protected java.util.Date startDate;
	/**
	 * 结束日期
	 */
	protected java.util.Date endDate;
	/**
	 * 周有效设置
	 */
	protected String weekSet;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public boolean isInclude() {
		return isInclude;
	}

	public void setInclude(boolean isInclude) {
		this.isInclude = isInclude;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPriceOption() {
		return priceOption;
	}

	public void setPriceOption(String priceOption) {
		this.priceOption = priceOption;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isExtAdd() {
		return isExtAdd;
	}

	public void setExtAdd(boolean isExtAdd) {
		this.isExtAdd = isExtAdd;
	}

	public String getExtOption() {
		return extOption;
	}

	public void setExtOption(String extOption) {
		this.extOption = extOption;
	}

	public BigDecimal getExtPrice() {
		return extPrice;
	}

	public void setExtPrice(BigDecimal extPrice) {
		this.extPrice = extPrice;
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public String getWeekSet() {
		return weekSet;
	}

	public void setWeekSet(String weekSet) {
		this.weekSet = weekSet;
	}

}

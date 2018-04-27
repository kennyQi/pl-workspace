package hsl.pojo.dto.hotel.base;

import java.util.Date;

/**
 * @类功能说明：担保规则
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年3月12日上午10:31:16
 * @版本：V1.0
 * 
 */
public class BaseGuaranteeRuleDTO extends BaseRuleDTO {
	private static final long serialVersionUID = 4844381136649527917L;
	/**
	 * 日期类型
	 */
	protected String dateType;
	/**
	 * 起始日期
	 */
	protected Date startDate;
	/**
	 * 结束日期
	 */
	protected Date endDate;
	/**
	 * 周设置的有效 周一对应为1，周二对应为2， 依次类推;逗号分隔
	 */
	protected String weekSet;
	/**
	 * 是否到店时间担保
	 */
	protected boolean isTimeGuarantee;
	/**
	 * 每天起始时间
	 */
	protected String startTime;
	/**
	 * 每天结束时间
	 */
	protected String endTime;
	/**
	 * 到店担保的结束时间是否为第二天 ; 0为当天，1为次日
	 */
	protected boolean isTomorrow;
	/**
	 * 是否房量担保
	 */
	protected boolean isAmountGuarantee;
	/**
	 * 担保的房间数,预定几间房以上要担保
	 */
	protected int amount;
	/**
	 * 担保类型
	 */
	protected String guaranteeType;
	/**
	 * 变更规则
	 */
	protected String changeRule;
	/**
	 * 日期参数
	 */
	protected Date day;
	/**
	 * 时间参数
	 */
	protected String time;
	/**
	 * 小时参数
	 */
	protected int hour;

	/**
	 * Gets the value of the dateType property.
	 * 
	 * @return possible object is {@link EnumDateType }
	 * 
	 */
	public String getDateType() {
		return dateType;
	}

	/**
	 * Sets the value of the dateType property.
	 * 
	 * @param value
	 *            allowed object is {@link EnumDateType }
	 * 
	 */
	public void setDateType(String value) {
		this.dateType = value;
	}

	/**
	 * Gets the value of the startDate property.
	 * 
	 * @return possible object is {@link java.util.Date }
	 * 
	 */
	public java.util.Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the value of the startDate property.
	 * 
	 * @param value
	 *            allowed object is {@link java.util.Date }
	 * 
	 */
	public void setStartDate(java.util.Date value) {
		this.startDate = value;
	}

	/**
	 * Gets the value of the endDate property.
	 * 
	 * @return possible object is {@link java.util.Date }
	 * 
	 */
	public java.util.Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the value of the endDate property.
	 * 
	 * @param value
	 *            allowed object is {@link java.util.Date }
	 * 
	 */
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}

	/**
	 * Gets the value of the weekSet property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getWeekSet() {
		return weekSet;
	}

	/**
	 * Sets the value of the weekSet property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setWeekSet(String value) {
		this.weekSet = value;
	}

	/**
	 * Gets the value of the isTimeGuarantee property.
	 * 
	 */
	public boolean isIsTimeGuarantee() {
		return isTimeGuarantee;
	}

	/**
	 * Sets the value of the isTimeGuarantee property.
	 * 
	 */
	public void setIsTimeGuarantee(boolean value) {
		this.isTimeGuarantee = value;
	}

	/**
	 * Gets the value of the startTime property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * Sets the value of the startTime property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setStartTime(String value) {
		this.startTime = value;
	}

	/**
	 * Gets the value of the endTime property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * Sets the value of the endTime property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEndTime(String value) {
		this.endTime = value;
	}

	/**
	 * Gets the value of the isTomorrow property.
	 * 
	 */
	public boolean isIsTomorrow() {
		return isTomorrow;
	}

	/**
	 * Sets the value of the isTomorrow property.
	 * 
	 */
	public void setIsTomorrow(boolean value) {
		this.isTomorrow = value;
	}

	/**
	 * Gets the value of the isAmountGuarantee property.
	 * 
	 */
	public boolean isIsAmountGuarantee() {
		return isAmountGuarantee;
	}

	/**
	 * Sets the value of the isAmountGuarantee property.
	 * 
	 */
	public void setIsAmountGuarantee(boolean value) {
		this.isAmountGuarantee = value;
	}

	/**
	 * Gets the value of the amount property.
	 * 
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the value of the amount property.
	 * 
	 */
	public void setAmount(int value) {
		this.amount = value;
	}

	/**
	 * Gets the value of the guaranteeType property.
	 * 
	 * @return possible object is {@link EnumGuaranteeMoneyType }
	 * 
	 */
	public String getGuaranteeType() {
		return guaranteeType;
	}

	/**
	 * Sets the value of the guaranteeType property.
	 * 
	 * @param value
	 *            allowed object is {@link EnumGuaranteeMoneyType }
	 * 
	 */
	public void setGuaranteeType(String value) {
		this.guaranteeType = value;
	}

	/**
	 * Gets the value of the changeRule property.
	 * 
	 * @return possible object is {@link EnumGuaranteeChangeRule }
	 * 
	 */
	public String getChangeRule() {
		return changeRule;
	}

	/**
	 * Sets the value of the changeRule property.
	 * 
	 * @param value
	 *            allowed object is {@link EnumGuaranteeChangeRule }
	 * 
	 */
	public void setChangeRule(String value) {
		this.changeRule = value;
	}

	/**
	 * Gets the value of the day property.
	 * 
	 * @return possible object is {@link java.util.Date }
	 * 
	 */
	public java.util.Date getDay() {
		return day;
	}

	/**
	 * Sets the value of the day property.
	 * 
	 * @param value
	 *            allowed object is {@link java.util.Date }
	 * 
	 */
	public void setDay(java.util.Date value) {
		this.day = value;
	}

	/**
	 * Gets the value of the time property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Sets the value of the time property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTime(String value) {
		this.time = value;
	}

	/**
	 * Gets the value of the hour property.
	 * 
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * Sets the value of the hour property.
	 * 
	 */
	public void setHour(int value) {
		this.hour = value;
	}

}

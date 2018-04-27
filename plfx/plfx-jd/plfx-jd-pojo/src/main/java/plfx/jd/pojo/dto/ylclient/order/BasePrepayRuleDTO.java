package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;
import java.math.BigDecimal;

import plfx.jd.pojo.system.enumConstants.EnumDateType;
import plfx.jd.pojo.system.enumConstants.EnumPrepayChangeRule;
import plfx.jd.pojo.system.enumConstants.EnumPrepayCutPayment;
/**
 * 
 * @类功能说明：预付规则
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年3月12日上午10:38:16
 * @版本：V1.0
 *
 */
public class BasePrepayRuleDTO extends BaseRuleDTO implements Serializable{
	/**
	 * 日期类型
	 */
	protected String dateType;
	/**
	 * 起始日期
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
	/**
	 * 变更规则
	 */
	protected String changeRule;
	/**
	 * 时间点后扣款类型
	 */
	protected String cashScaleFirstAfter;
	/**
	 * 时间点前扣款类型
	 */
	protected String cashScaleFirstBefore;
	/**
	 * 具体取消时间日期部分
	 */
	protected java.util.Date dateNum;
	/**
	 * 具体取消时间小时部分
	 */
	protected String time;
	/**
	 * 在变更时间点后是否扣费
	 */
	protected int deductFeesAfter;
	/**
	 * 在变更时间点前是否扣费
	 */
	protected int deductFeesBefore;
	/**
	 * 时间点后扣费的金额或比例
	 */
	protected BigDecimal deductNumAfter;
	/**
	 * 时间点前扣费的金额或比例
	 */
	protected BigDecimal deductNumBefore;
	/**
	 * 第一阶段提前几小时
	 */
	protected int hour;
	/**
	 * 第二阶段提前几小时
	 */
	protected int hour2;
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
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
	public String getChangeRule() {
		return changeRule;
	}
	public void setChangeRule(String changeRule) {
		this.changeRule = changeRule;
	}
	public String getCashScaleFirstAfter() {
		return cashScaleFirstAfter;
	}
	public void setCashScaleFirstAfter(String cashScaleFirstAfter) {
		this.cashScaleFirstAfter = cashScaleFirstAfter;
	}
	public String getCashScaleFirstBefore() {
		return cashScaleFirstBefore;
	}
	public void setCashScaleFirstBefore(String cashScaleFirstBefore) {
		this.cashScaleFirstBefore = cashScaleFirstBefore;
	}
	public java.util.Date getDateNum() {
		return dateNum;
	}
	public void setDateNum(java.util.Date dateNum) {
		this.dateNum = dateNum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getDeductFeesAfter() {
		return deductFeesAfter;
	}
	public void setDeductFeesAfter(int deductFeesAfter) {
		this.deductFeesAfter = deductFeesAfter;
	}
	public int getDeductFeesBefore() {
		return deductFeesBefore;
	}
	public void setDeductFeesBefore(int deductFeesBefore) {
		this.deductFeesBefore = deductFeesBefore;
	}
	public BigDecimal getDeductNumAfter() {
		return deductNumAfter;
	}
	public void setDeductNumAfter(BigDecimal deductNumAfter) {
		this.deductNumAfter = deductNumAfter;
	}
	public BigDecimal getDeductNumBefore() {
		return deductNumBefore;
	}
	public void setDeductNumBefore(BigDecimal deductNumBefore) {
		this.deductNumBefore = deductNumBefore;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getHour2() {
		return hour2;
	}
	public void setHour2(int hour2) {
		this.hour2 = hour2;
	}

	
}

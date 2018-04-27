package hsl.pojo.qo.line;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("serial")
@QOConfig(daoBeanId = "dateSalePriceDAO")
public class DateSalePriceQO extends BaseQo {

	/**
	 * 属于哪个线路
	 */
	@QOAttr(name = "line.id")
	private String lineId;

	/**
	 * 属于哪天
	 */
	@QOAttr(name = "saleDate")
	private Date saleDate;

	/**
	 * 获取几个月的，包括当月
	 */
	private Integer fetchMonthCount;

	/**
	 * 需要提前的天数
	 * 比如今天 今天是 2015-10-21，beforeDay=4
	 * 查询结果就是 2015-10-26以后的价格日历，当天不算
	 */
	private Integer beforeDay;

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		if (saleDate != null)
			this.saleDate = DateUtils.truncate(saleDate, Calendar.DATE);
		else
			this.saleDate = null;
	}
	
	public Integer getFetchMonthCount() {
		return fetchMonthCount;
	}

	public void setFetchMonthCount(Integer fetchMonthCount) {
		this.fetchMonthCount = fetchMonthCount;
	}

	public Integer getBeforeDay() {
		return beforeDay;
	}

	public void setBeforeDay(Integer beforeDay) {
		this.beforeDay = beforeDay;
	}
}



package plfx.jd.pojo.dto.ylclient.hotel;

import java.math.BigDecimal;

import plfx.jd.pojo.dto.ylclient.order.BaseRuleDTO;
@SuppressWarnings("serial")
public class BaseDrrRuleDTO
    extends BaseRuleDTO
{
	/**
	 * 产品促销规则类型代码
	 */
    protected String typeCode;
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
     * 提前几天
     */
    protected int dayNum;
    /**
     * 连住几天
     */
    protected int checkInNum;
    /**
     * 每连住几晚
     */
    protected int everyCheckInNum;
    /**
     * 最后几天
     */
    protected int lastDayNum;
    /**
     * 第几晚及以后优惠
     */
    protected int whichDayNum;
    /**
     * 按金额或按比例来优惠
     */
    protected String cashScale;
    /**
     * 按金额或比例优惠的数值
     */
    protected BigDecimal deductNum;
    /**
     * 星期有效设置
     */
    protected String weekSet;
    /**
     * 价格类型
     */
    protected String feeType;
    /**
     * 房间类型id
     */
    protected String roomTypeIds;
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
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
	public int getDayNum() {
		return dayNum;
	}
	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}
	public int getCheckInNum() {
		return checkInNum;
	}
	public void setCheckInNum(int checkInNum) {
		this.checkInNum = checkInNum;
	}
	public int getEveryCheckInNum() {
		return everyCheckInNum;
	}
	public void setEveryCheckInNum(int everyCheckInNum) {
		this.everyCheckInNum = everyCheckInNum;
	}
	public int getLastDayNum() {
		return lastDayNum;
	}
	public void setLastDayNum(int lastDayNum) {
		this.lastDayNum = lastDayNum;
	}
	public int getWhichDayNum() {
		return whichDayNum;
	}
	public void setWhichDayNum(int whichDayNum) {
		this.whichDayNum = whichDayNum;
	}
	public String getCashScale() {
		return cashScale;
	}
	public void setCashScale(String cashScale) {
		this.cashScale = cashScale;
	}
	public BigDecimal getDeductNum() {
		return deductNum;
	}
	public void setDeductNum(BigDecimal deductNum) {
		this.deductNum = deductNum;
	}
	public String getWeekSet() {
		return weekSet;
	}
	public void setWeekSet(String weekSet) {
		this.weekSet = weekSet;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getRoomTypeIds() {
		return roomTypeIds;
	}
	public void setRoomTypeIds(String roomTypeIds) {
		this.roomTypeIds = roomTypeIds;
	}

    
}

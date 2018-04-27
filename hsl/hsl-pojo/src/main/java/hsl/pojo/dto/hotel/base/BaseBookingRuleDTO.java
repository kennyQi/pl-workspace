package hsl.pojo.dto.hotel.base;

import java.util.Date;

/**
 * @类功能说明：预定规则
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年3月12日上午10:27:04
 * @版本：V1.0
 */
public class BaseBookingRuleDTO extends BaseRuleDTO{
	private static final long serialVersionUID = 8425928198621985139L;
	/**
	 * 规则类型
	 */
    protected String typeCode;
    /**
     * 房间类型id
     */
    protected String roomTypeIds;
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
     * 每天开始时间
     */
    protected String startHour;
    /**
     * 每天结束时间
     */
    protected String endHour;
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getRoomTypeIds() {
		return roomTypeIds;
	}
	public void setRoomTypeIds(String roomTypeIds) {
		this.roomTypeIds = roomTypeIds;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
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
	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

    
}

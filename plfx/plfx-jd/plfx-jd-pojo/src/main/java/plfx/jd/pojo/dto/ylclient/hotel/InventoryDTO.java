package plfx.jd.pojo.dto.ylclient.hotel;

/**
 * 
 * @类功能说明：酒店库存
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年3月26日上午10:52:05
 * @版本：V1.0
 * 
 */
public class InventoryDTO {
	/**
	 * 酒店ID
	 */
	protected String hotelID;
	/**
	 * 酒店编码
	 */
	protected String hotelCode;
	/**
	 * 库存状态 False-不可用 True-可用
	 */
	protected boolean status;
	/**
	 * 超售状态 0---可超售，1—不可超售。可超售的时候即使Amount等于0也是可以继续销售的。
	 */
	protected int overBooking;
	/**
	 * 可用开始日期
	 */
	protected java.util.Date startDate;
	/**
	 * 可用结束日期
	 */
	protected java.util.Date endDate;
	/**
	 * 可用开始时间
	 */
	protected String startTime;
	/**
	 * 可用结束时间
	 */
	protected String endTime;
	/**
	 * 房型ID
	 */
	protected String roomTypeId;
	/**
	 * 库存时间
	 */
	protected java.util.Date date;
	/**
	 * 库存数量
	 */
	protected int amount;

	public String getHotelID() {
		return hotelID;
	}

	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getOverBooking() {
		return overBooking;
	}

	public void setOverBooking(int overBooking) {
		this.overBooking = overBooking;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}

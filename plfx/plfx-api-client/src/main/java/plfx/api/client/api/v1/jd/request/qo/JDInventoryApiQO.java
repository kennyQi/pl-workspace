package plfx.api.client.api.v1.jd.request.qo;

/***
 * 
 * @类功能说明：国内酒店房态库存QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月24日下午4:04:07
 * @版本：V1.0
 * 
 */
public class JDInventoryApiQO {

	/**
	 * 酒店编号
	 */
	protected String hotelIds;
	/**
	 * 房型编号
	 */
	protected String roomTypeId;
	/**
	 * 库存可用开始日期
	 */
	protected java.util.Date startDate;
	/**
	 * 库存可用结束日期
	 */
	protected java.util.Date endDate;

	public String getHotelIds() {
		return hotelIds;
	}

	public void setHotelIds(String hotelIds) {
		this.hotelIds = hotelIds;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
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

}

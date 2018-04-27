package plfx.api.client.api.v1.jd.request.qo;

import java.math.BigDecimal;
/***
 * 
 * @类功能说明：国内酒店数据验证QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月24日下午4:02:46
 * @版本：V1.0
 *
 */
public class JDValidateApiQO {

	/***
	 * 入住日期
	 */
	protected java.util.Date arrivalDate;
	/***
	 * 离店日期
	 */
	protected java.util.Date departureDate;
	/***
	 * 最早到店时间
	 */
	protected java.util.Date earliestArrivalTime;
	/***
	 * 最晚到店时间
	 */
	protected java.util.Date latestArrivalTime;
	/***
	 * 酒店编号
	 */
	protected String hotelId;
	/***
	 * 房型编号
	 */
	protected String roomTypeId;
	/***
	 * 产品编号
	 */
	protected int ratePlanId;
	/***
	 * 总价
	 */
	protected BigDecimal totalPrice;
	/***
	 * 房间数量
	 */
	protected int numberOfRooms;

	public java.util.Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(java.util.Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public java.util.Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(java.util.Date departureDate) {
		this.departureDate = departureDate;
	}

	public java.util.Date getEarliestArrivalTime() {
		return earliestArrivalTime;
	}

	public void setEarliestArrivalTime(java.util.Date earliestArrivalTime) {
		this.earliestArrivalTime = earliestArrivalTime;
	}

	public java.util.Date getLatestArrivalTime() {
		return latestArrivalTime;
	}

	public void setLatestArrivalTime(java.util.Date latestArrivalTime) {
		this.latestArrivalTime = latestArrivalTime;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public int getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(int ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

}

package plfx.jd.pojo.dto.ylclient.hotel;

import java.util.List;

public class RoomDTO {
	//---------------新增字段------------------
	/***
	 * 面积
	 */
	public String area;

	/***
	 * 上网情况
	 */
	public String broadnetAccess;

	/***
	 * 上网费用
	 */
	public String broadnetFee;

	/**
	 * 房间最大入住人数
	 */
	public String capacity;

	/***
	 * 房间设施
	 */
	public String facilities;
	
	//--------------新增字段---------------------
	/**
	 * 房间编号
	 */
	protected String roomId;
	/**
	 * 房间名称
	 */
	protected String name;
	/**
	 * 楼层
	 */
	protected String floor;
	/**
	 * 宽带
	 */
	protected String broadnet;
	/**
	 * 床型
	 */
	protected String bedType;
	/**
	 * 房间描述
	 */
	protected String description;
	/**
	 * 图片地址
	 */
	protected String imageUrl;
	/**
	 * 产品信息集
	 */
	protected List<ListRatePlan> ratePlans;
	/**
	 * 房间备注
	 */
	protected String comments;

	/**
	 * Gets the value of the roomId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRoomId() {
		return roomId;
	}

	/**
	 * Sets the value of the roomId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRoomId(String value) {
		this.roomId = value;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the floor property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFloor() {
		return floor;
	}

	/**
	 * Sets the value of the floor property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setFloor(String value) {
		this.floor = value;
	}

	/**
	 * Gets the value of the broadnet property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBroadnet() {
		return broadnet;
	}

	/**
	 * Sets the value of the broadnet property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBroadnet(String value) {
		this.broadnet = value;
	}

	/**
	 * Gets the value of the bedType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBedType() {
		return bedType;
	}

	/**
	 * Sets the value of the bedType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBedType(String value) {
		this.bedType = value;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * Gets the value of the imageUrl property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * Sets the value of the imageUrl property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setImageUrl(String value) {
		this.imageUrl = value;
	}

	/**
	 * Gets the value of the ratePlans property.
	 * 
	 * @return possible object is {@link List<ListRatePlan> }
	 * 
	 */
	public List<ListRatePlan> getRatePlans() {
		return ratePlans;
	}

	/**
	 * Sets the value of the ratePlans property.
	 * 
	 * @param value
	 *            allowed object is {@link List<ListRatePlan> }
	 * 
	 */
	public void setRatePlans(List<ListRatePlan> value) {
		this.ratePlans = value;
	}

	/**
	 * Gets the value of the comments property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Sets the value of the comments property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setComments(String value) {
		this.comments = value;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBroadnetAccess() {
		return broadnetAccess;
	}

	public void setBroadnetAccess(String broadnetAccess) {
		this.broadnetAccess = broadnetAccess;
	}

	public String getBroadnetFee() {
		return broadnetFee;
	}

	public void setBroadnetFee(String broadnetFee) {
		this.broadnetFee = broadnetFee;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getFacilities() {
		return facilities;
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}
	
	

}

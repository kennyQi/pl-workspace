package plfx.jd.domain.model.hotel;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import plfx.jd.domain.model.M;

/*****
 * 
 * @类功能说明：房间实体
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月15日上午10:34:52
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "HOTEL_ROOM")
public class YLHotelRoom extends BaseModel{

	/***
	 * 房型编号
	 */
	@Column(name = "ROOM_ID")
	public String roomId;

	/***
	 * 名称
	 */
	@Column(name = "NAME")
	public String name;

	/***
	 * 面积
	 */
	@Column(name = "AREA")
	public String area;

	/**
	 * 楼层
	 */
	@Column(name = "FLOOR")
	public String floor;

	/***
	 * 上网情况
	 */
	@Column(name = "BROADNETACCESS")
	public String broadnetAccess;

	/***
	 * 上网费用
	 */
	@Column(name = "BROADNETFEE")
	public String broadnetFee;

	/**
	 * 备注
	 */
	@Column(name = "COMMENTS",columnDefinition ="TEXT")
	public String comments;

	/***
	 * 描述
	 */
	@Column(name = "DESCRIPTION",columnDefinition ="TEXT")
	public String description;

	/***
	 * 床型
	 */
	@Column(name = "BEDTYPE")
	public String bedType;

	/**
	 * 房间最大入住人数
	 */
	@Column(name = "CAPACITY")
	public String capacity;

	/***
	 * 房间设施
	 */
	@Column(name = "FACILITES",columnDefinition ="TEXT")
	public String facilities;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "YL_HOTEL_ID")
	private YLHotel ylHotel;
	
	public YLHotel getYlHotel() {
		return ylHotel;
	}

	public void setYlHotel(YLHotel ylHotel) {
		this.ylHotel = ylHotel;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
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

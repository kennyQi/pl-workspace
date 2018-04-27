package plfx.jd.domain.model.hotel;

import hg.common.component.BaseModel;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import plfx.jd.domain.model.M;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "YL_HOTEL_Image")
public class YLHotelImage extends BaseModel{
	
	/**
	 * 关联的房型
	 */
	@Column(name = "ROOM_ID")
	private String roomId;
	/**
	 * 图片类型
	 */
	@Column(name = "TYPE")
	public String type;
	/***
	 * 作者类型
	 */
	@Column(name = "AUTHORTYPE")
	public String authorType;
	
	/***
	 * 图片地址
	 */
	@OneToMany(fetch = FetchType.LAZY,mappedBy="ylHotelImage",cascade={CascadeType.ALL})
	public List<YLLocation> LocationsList;
	
	/***
	 * 是否是首图
	 */
	@Column(name = "ISCOVERIMAGE")
	private String isCoverImage;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthorType() {
		return authorType;
	}

	public void setAuthorType(String authorType) {
		this.authorType = authorType;
	}

	public List<YLLocation> getLocationsList() {
		return LocationsList;
	}

	public void setLocationsList(List<YLLocation> locationsList) {
		LocationsList = locationsList;
	}

	public String getIsCoverImage() {
		return isCoverImage;
	}

	public void setIsCoverImage(String isCoverImage) {
		this.isCoverImage = isCoverImage;
	}
	
}

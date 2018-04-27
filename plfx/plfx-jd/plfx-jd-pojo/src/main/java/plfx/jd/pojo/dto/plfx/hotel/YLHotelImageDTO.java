package plfx.jd.pojo.dto.plfx.hotel;

import hg.common.component.BaseModel;

import java.util.List;





@SuppressWarnings("serial")
public class YLHotelImageDTO extends BaseModel{
	
	/**
	 * 关联的房型
	 */

	private String roomId;
	/**
	 * 图片类型
	 */

	public String type;
	/***
	 * 作者类型
	 */

	public String authorType;
	
	/***
	 * 图片地址
	 */

	public List<YLLocationDTO> locationsList;
	
	/***
	 * 是否是首图
	 */
	private String isCoverImage;
	
	private YLHotelDTO ylHotelDTO;
	
	public String getIsCoverImage() {
		return isCoverImage;
	}

	public void setIsCoverImage(String isCoverImage) {
		this.isCoverImage = isCoverImage;
	}

	public YLHotelDTO getYlHotelDTO() {
		return ylHotelDTO;
	}

	public void setYlHotelDTO(YLHotelDTO ylHotelDTO) {
		this.ylHotelDTO = ylHotelDTO;
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

	public List<YLLocationDTO> getLocationsList() {
		return locationsList;
	}

	public void setLocationsList(List<YLLocationDTO> locationsList) {
		this.locationsList = locationsList;
	}
}

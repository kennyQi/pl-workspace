package plfx.jd.pojo.dto.ylclient.hotel;

import java.util.List;
public class HotelImgDTO {
	/**
	 * 图片地址
	 */
	protected List<LocationDTO> locations;
	/**
	 * 房型编号
	 */
	protected String roomId;
	/**
	 * 图片类型
	 */
	protected int type;
    /**
     * 作者类型
     */
    protected String authorType;
    /**
     * 是否是封面图片
     */
    protected String isCoverImage;

	public List<LocationDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationDTO> value) {
		this.locations = value;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String value) {
		this.roomId = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int value) {
		this.type = value;
	}
	public String getAuthorType() {
		return authorType;
	}

	public void setAuthorType(String authorType) {
		this.authorType = authorType;
	}

	public String getIsCoverImage() {
		return isCoverImage;
	}

	public void setIsCoverImage(String isCoverImage) {
		this.isCoverImage = isCoverImage;
	}
}

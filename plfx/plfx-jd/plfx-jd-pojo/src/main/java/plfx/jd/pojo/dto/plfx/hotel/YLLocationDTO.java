package plfx.jd.pojo.dto.plfx.hotel;

import hg.common.component.BaseModel;



@SuppressWarnings("serial")

public class YLLocationDTO extends BaseModel {

	/**
	 * 是否有水印
	 */

	public int waterMark;

	/***
	 * 图片规格
	 */

	public String size;

	/***
	 * 图片url
	 */

	public String url;

	


	private YLHotelImageDTO ylHotelImageDTO;
	
	
	

	public YLHotelImageDTO getYlHotelImageDTO() {
		return ylHotelImageDTO;
	}

	public void setYlHotelImageDTO(YLHotelImageDTO ylHotelImageDTO) {
		this.ylHotelImageDTO = ylHotelImageDTO;
	}

	public int getWaterMark() {
		return waterMark;
	}

	public void setWaterMark(int waterMark) {
		this.waterMark = waterMark;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

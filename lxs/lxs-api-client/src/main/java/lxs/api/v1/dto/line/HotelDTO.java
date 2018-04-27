package lxs.api.v1.dto.line;

import hg.system.dto.BaseDTO;

@SuppressWarnings("serial")
public class HotelDTO extends BaseDTO{


	/**
	 * 住宿星级
	 */
	private Integer stayLevel;
	
	/**
	 * 酒店名称
	 */
	private String hotelName;

	public Integer getStayLevel() {
		return stayLevel;
	}

	public void setStayLevel(Integer stayLevel) {
		this.stayLevel = stayLevel;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	
	
}

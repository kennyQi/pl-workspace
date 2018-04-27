package hsl.pojo.dto.line;
import hsl.pojo.dto.BaseDTO;
@SuppressWarnings("serial")
public class HotelInfoDTO extends BaseDTO{
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

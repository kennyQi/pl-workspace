package plfx.jd.pojo.dto.ylclient.hotel;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class HotelListResultDTO implements Serializable{
	/**
	 * 酒店总数
	 */
	protected int count;
	/**
	 * 酒店结果集
	 */
	protected List<HotelDTO> hotels;

	public int getCount() {
		return count;
	}

	public void setCount(int value) {
		this.count = value;
	}

	public List<HotelDTO> getHotels() {
		return hotels;
	}

	public void setHotels(List<HotelDTO> value) {
		this.hotels = value;
	}

}

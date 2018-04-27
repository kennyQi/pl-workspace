package plfx.yl.ylclient.yl.dto;

import com.alibaba.fastjson.annotation.JSONField;

import elong.HotelList;

public class HotelListResultDTO extends BaseResultDTO {


	 @JSONField(name="Result")
	private HotelListDTO result;

	public HotelListDTO getResult() {
		return result;
	}

	public void setResult(HotelListDTO result) {
		this.result = result;
	}

	 
}

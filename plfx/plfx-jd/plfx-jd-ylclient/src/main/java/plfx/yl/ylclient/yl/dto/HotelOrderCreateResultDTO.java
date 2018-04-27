package plfx.yl.ylclient.yl.dto;

import com.alibaba.fastjson.annotation.JSONField;

import elong.CreateOrderResult;

public class HotelOrderCreateResultDTO extends BaseResultDTO {
	@JSONField(name="Result")
	private HotelOrderCreateDTO result;

	public HotelOrderCreateDTO getResult() {
		return result;
	}

	public void setResult(HotelOrderCreateDTO result) {
		this.result = result;
	}
	
	
}

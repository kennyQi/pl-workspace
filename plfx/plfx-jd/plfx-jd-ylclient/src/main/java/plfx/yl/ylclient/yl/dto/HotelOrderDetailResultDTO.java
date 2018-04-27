package plfx.yl.ylclient.yl.dto;

import com.alibaba.fastjson.annotation.JSONField;

import elong.OrderDetailResult;

public class HotelOrderDetailResultDTO extends BaseResultDTO {
	@JSONField(name="Result")
	HotelOrderDetailDTO result;

	public HotelOrderDetailDTO getResult() {
		return result;
	}

	public void setResult(HotelOrderDetailDTO result) {
		this.result = result;
	}
	
	
}

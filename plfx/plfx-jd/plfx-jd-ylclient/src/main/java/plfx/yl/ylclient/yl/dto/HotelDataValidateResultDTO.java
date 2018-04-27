package plfx.yl.ylclient.yl.dto;

import com.alibaba.fastjson.annotation.JSONField;

import elong.ValidateResult;;

public class HotelDataValidateResultDTO  extends BaseResultDTO {

	
	 @JSONField(name="Result")
	private HotelDataValidateDTO result;

	public HotelDataValidateDTO getResult() {
		return result;
	}

	public void setResult(HotelDataValidateDTO result) {
		this.result = result;
	}
	
	
}
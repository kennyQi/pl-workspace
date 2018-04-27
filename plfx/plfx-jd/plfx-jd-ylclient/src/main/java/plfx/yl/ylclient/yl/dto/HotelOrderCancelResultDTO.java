package plfx.yl.ylclient.yl.dto;

import com.alibaba.fastjson.annotation.JSONField;

import elong.CancelOrderResult;;

public class HotelOrderCancelResultDTO extends BaseResultDTO {
	 	@JSONField(name="Result")
		private HotelOrderCancelDTO result;

		public HotelOrderCancelDTO getResult() {
			return result;
		}

		public void setResult(HotelOrderCancelDTO result) {
			this.result = result;
		}
		
		
}

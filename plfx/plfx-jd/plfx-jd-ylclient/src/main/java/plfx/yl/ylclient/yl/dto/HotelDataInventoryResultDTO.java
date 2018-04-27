package plfx.yl.ylclient.yl.dto;

import com.alibaba.fastjson.annotation.JSONField;

import elong.InventoryResult;

public class HotelDataInventoryResultDTO extends BaseResultDTO {

	
		private HotelDataInventoryDTO result;

		public HotelDataInventoryDTO getResult() {
			return result;
		}

		public void setResult(HotelDataInventoryDTO result) {
			this.result = result;
		}
		
}

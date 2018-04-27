package plfx.yl.ylclient.yl.dto;

import com.alibaba.fastjson.annotation.JSONField;


public class BaseDTO {

	 	@JSONField(name="Code")
		private String code;
	 	
		 
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
}

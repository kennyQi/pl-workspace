package plfx.mp.tcclient.tc.dto.base;

import plfx.mp.tcclient.tc.dto.Dto;
import plfx.mp.tcclient.tc.util.ReadProperties;


public class BaseDto extends Dto{
	
	
	public BaseDto() {
		if("0".equals(ReadProperties.getTc_is_test())){
			this.setUrl(ReadProperties.getTc_base_url());
		}else{
			this.setUrl(ReadProperties.getTc_base_url_test());
		}
		//System.out.println(this.getUrl());
	}
	
	
	
}

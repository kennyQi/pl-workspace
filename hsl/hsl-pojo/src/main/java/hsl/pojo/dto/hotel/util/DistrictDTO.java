package hsl.pojo.dto.hotel.util;

import hsl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class DistrictDTO extends BaseDTO{
	private String districtId;
	private String name;
	
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}

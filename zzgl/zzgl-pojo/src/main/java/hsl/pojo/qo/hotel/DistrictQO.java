package hsl.pojo.qo.hotel;

import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class DistrictQO extends BaseQo{
private String hotelGeoId;
private String districtId;
public String getHotelGeoId() {
	return hotelGeoId;
}

public void setHotelGeoId(String hotelGeoId) {
	this.hotelGeoId = hotelGeoId;
}

public String getDistrictId() {
	return districtId;
}

public void setDistrictId(String districtId) {
	this.districtId = districtId;
}

}

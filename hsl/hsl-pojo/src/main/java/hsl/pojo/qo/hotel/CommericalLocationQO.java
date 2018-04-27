package hsl.pojo.qo.hotel;

import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class CommericalLocationQO extends BaseQo{
	private String hotelGeoId;

	private String businessZoneId;//商业区Id
	public String getHotelGeoId() {
		return hotelGeoId;
	}

	public void setHotelGeoId(String hotelGeoId) {
		this.hotelGeoId = hotelGeoId;
	}

	public String getBusinessZoneId() {
		return businessZoneId;
	}

	public void setBusinessZoneId(String businessZoneId) {
		this.businessZoneId = businessZoneId;
	}
	
}

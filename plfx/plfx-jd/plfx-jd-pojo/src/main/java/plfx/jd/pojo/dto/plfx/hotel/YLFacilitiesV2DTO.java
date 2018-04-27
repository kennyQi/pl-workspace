package plfx.jd.pojo.dto.plfx.hotel;

import plfx.jd.pojo.dto.ylclient.order.BaseJDDTO;


@SuppressWarnings("serial")
public class YLFacilitiesV2DTO extends BaseJDDTO{
	/***
	 * 酒店基础设施
	 */

	public String generalAmenities;
	/***
	 * 酒店休闲设施
	 */

	public String recreationAmenities;
	/***
	 * 酒店服务设施
	 */

	public String serviceAmenities;

	public String getGeneralAmenities() {
		return generalAmenities;
	}

	public void setGeneralAmenities(String generalAmenities) {
		this.generalAmenities = generalAmenities;
	}

	public String getRecreationAmenities() {
		return recreationAmenities;
	}

	public void setRecreationAmenities(String recreationAmenities) {
		this.recreationAmenities = recreationAmenities;
	}

	public String getServiceAmenities() {
		return serviceAmenities;
	}

	public void setServiceAmenities(String serviceAmenities) {
		this.serviceAmenities = serviceAmenities;
	}
	
	
}

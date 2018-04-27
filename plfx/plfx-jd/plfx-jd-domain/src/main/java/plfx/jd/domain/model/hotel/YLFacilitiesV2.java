package plfx.jd.domain.model.hotel;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class YLFacilitiesV2{
	/***
	 * 酒店基础设施
	 */
	@Column(name = "GENERALAMENITIES",columnDefinition ="TEXT")
	public String generalAmenities;
	/***
	 * 酒店休闲设施
	 */
	@Column(name = "RECREATIONAMENITIESS",columnDefinition ="TEXT")
	public String recreationAmenities;
	/***
	 * 酒店服务设施
	 */
	@Column(name = "SERVICEAMENITIES",columnDefinition ="TEXT")
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

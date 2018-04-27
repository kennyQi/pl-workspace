package lxs.api.v1.dto.line;

import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class HotDestinationCityDTO extends BaseDTO {

	private String destinationCity;

	private String destinationCityName;

	private Integer sum;

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getDestinationCityName() {
		return destinationCityName;
	}

	public void setDestinationCityName(String destinationCityName) {
		this.destinationCityName = destinationCityName;
	}

}

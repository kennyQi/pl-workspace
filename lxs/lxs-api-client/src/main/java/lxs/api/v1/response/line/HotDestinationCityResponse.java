package lxs.api.v1.response.line;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.line.HotDestinationCityDTO;

public class HotDestinationCityResponse extends ApiResponse{

	private List<HotDestinationCityDTO> hotDestinationCityDTOs;

	public List<HotDestinationCityDTO> getHotDestinationCityDTOs() {
		return hotDestinationCityDTOs;
	}

	public void setHotDestinationCityDTOs(
			List<HotDestinationCityDTO> hotDestinationCityDTOs) {
		this.hotDestinationCityDTOs = hotDestinationCityDTOs;
	}

}

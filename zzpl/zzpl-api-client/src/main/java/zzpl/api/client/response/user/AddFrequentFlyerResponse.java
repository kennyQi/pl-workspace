package zzpl.api.client.response.user;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.user.FrequentFlyerDTO;

public class AddFrequentFlyerResponse extends ApiResponse{

	private FrequentFlyerDTO frequentFlyerDTO;

	public FrequentFlyerDTO getFrequentFlyerDTO() {
		return frequentFlyerDTO;
	}

	public void setFrequentFlyerDTO(FrequentFlyerDTO frequentFlyerDTO) {
		this.frequentFlyerDTO = frequentFlyerDTO;
	}
}

package zzpl.api.client.response.user;

import java.util.List;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.user.FrequentFlyerDTO;

public class QueryFrequentFlyerResponse extends ApiResponse{

	private List<FrequentFlyerDTO> frequentFlyerDTOs;

	public List<FrequentFlyerDTO> getFrequentFlyerDTOs() {
		return frequentFlyerDTOs;
	}

	public void setFrequentFlyerDTOs(List<FrequentFlyerDTO> frequentFlyerDTOs) {
		this.frequentFlyerDTOs = frequentFlyerDTOs;
	}

}

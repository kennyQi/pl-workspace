package zzpl.api.client.response.user;

import java.util.List;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.user.CostCenterDTO;

public class QueryCostCenterResponse extends ApiResponse {
	private List<CostCenterDTO> costCenterDTOs;

	public List<CostCenterDTO> getCostCenterDTOs() {
		return costCenterDTOs;
	}

	public void setCostCenterDTOs(List<CostCenterDTO> costCenterDTOs) {
		this.costCenterDTOs = costCenterDTOs;
	}

}

package zzpl.api.client.response.workflow;

import java.util.List;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.workflow.StepDTO;

public class ChooseStepResponse extends ApiResponse {

	private List<StepDTO> stepDTOs;

	public List<StepDTO> getStepDTOs() {
		return stepDTOs;
	}

	public void setStepDTOs(List<StepDTO> stepDTOs) {
		this.stepDTOs = stepDTOs;
	}

}

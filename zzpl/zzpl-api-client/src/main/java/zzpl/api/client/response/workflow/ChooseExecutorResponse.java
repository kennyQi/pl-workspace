package zzpl.api.client.response.workflow;

import java.util.List;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.workflow.ExecutorDTO;

public class ChooseExecutorResponse extends ApiResponse {

	List<ExecutorDTO> executorDTOs;

	public List<ExecutorDTO> getExecutorDTOs() {
		return executorDTOs;
	}

	public void setExecutorDTOs(List<ExecutorDTO> executorDTOs) {
		this.executorDTOs = executorDTOs;
	}
	
	
}

package zzpl.api.client.response.workflow;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.workflow.WorkflowDTO;

public class WorkflowResponse extends ApiResponse{
	private List<WorkflowDTO> workflowDTOs;

	public List<WorkflowDTO> getWorkflowDTOs() {
		return workflowDTOs;
	}

	public void setWorkflowDTOs(List<WorkflowDTO> workflowDTOs) {
		this.workflowDTOs = workflowDTOs;
	}

	public static void main(String[] args) {
		WorkflowResponse workflowResponse = new WorkflowResponse();
		List<WorkflowDTO> workflowDTOs = new ArrayList<WorkflowDTO>();
		WorkflowDTO workflowDTO = new WorkflowDTO();
		workflowDTO.setWorkflowID("asdasjdgajkfasdfhajksdh");
		workflowDTO.setWorkflowName("购买机票");
		workflowDTOs.add(workflowDTO);
		workflowDTO.setWorkflowName("订酒店");
		workflowDTOs.add(workflowDTO);
		workflowResponse.setWorkflowDTOs(workflowDTOs);
		System.out.println(JSON.toJSON(workflowResponse));
	}
}


package zzpl.api.action.workflow;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.workflow.StepDTO;
import zzpl.api.client.request.workflow.ChooseStepCommand;
import zzpl.api.client.response.workflow.ChooseStepResponse;
import zzpl.app.service.local.workflow.WorkflowStepService;
import zzpl.domain.model.workflow.WorkflowStep;
import zzpl.pojo.qo.workflow.WorkflowStepQO;

import com.alibaba.fastjson.JSON;

@Component("ChooseStepAction")
public class ChooseStepAction implements CommonAction{

	@Autowired
	private WorkflowStepService workflowStepService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		ChooseStepResponse chooseStepResponse = new ChooseStepResponse();
		List<StepDTO> stepDTOs = new ArrayList<StepDTO>();
		try{
			ChooseStepCommand chooseStepCommand = JSON.parseObject(apiRequest.getBody().getPayload(), ChooseStepCommand.class);
			HgLogger.getInstance().info("cs", "【ChooseStepAction】"+"chooseStepCommand:"+JSON.toJSONString(chooseStepCommand));
			WorkflowStepQO workflowStepQO = new WorkflowStepQO();
			workflowStepQO.setStepNO(chooseStepCommand.getCurrentStepNO());
			workflowStepQO.setWorkflowID(chooseStepCommand.getWorkflowID());
			WorkflowStep workflowStep = workflowStepService.queryUnique(workflowStepQO);
			String[] nextSteps = workflowStep.getNextStepNO().split(";");
			for (int i = 0; i < nextSteps.length; i++) {
				StepDTO dto = new StepDTO();
				if(StringUtils.equals(nextSteps[i], "99")){
					dto.setStepName("结束流程");
					dto.setStepNO("99");
				}else{
					workflowStepQO = new WorkflowStepQO();
					workflowStepQO.setStepNO(Integer.parseInt(nextSteps[i]));
					workflowStepQO.setWorkflowID(chooseStepCommand.getWorkflowID());
					WorkflowStep nextWorkflowStep = workflowStepService.queryUnique(workflowStepQO);
					dto.setStepName(nextWorkflowStep.getStepName());
					dto.setStepNO(nextWorkflowStep.getStepNO().toString());
				}
				stepDTOs.add(dto);
			}
			chooseStepResponse.setStepDTOs(stepDTOs);
			chooseStepResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch (Exception e){
			HgLogger.getInstance().info("cs", "【ChooseStepAction】"+"异常，"+HgLogger.getStackTrace(e));
			chooseStepResponse.setMessage("获取下一步环节失败");
			chooseStepResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("cs", "【ChooseStepAction】"+"chooseStepResponse:"+JSON.toJSONString(chooseStepResponse));
		return JSON.toJSONString(chooseStepResponse);
	}
}

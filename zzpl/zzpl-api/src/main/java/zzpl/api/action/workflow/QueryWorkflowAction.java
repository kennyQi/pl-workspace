package zzpl.api.action.workflow;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.dto.workflow.WorkflowDTO;
import zzpl.api.client.request.workflow.WorkflowQO;
import zzpl.api.client.response.workflow.WorkflowResponse;
import zzpl.app.service.local.user.RoleWorkflowService;
import zzpl.domain.model.user.RoleWorkflow;
import zzpl.pojo.qo.user.RoleWorkflowQO;

import com.alibaba.fastjson.JSON;

@Component("QueryWorkflowAction")
public class QueryWorkflowAction implements CommonAction{
	
	@Autowired
	private RoleWorkflowService roleWorkflowService;

	@Override
	public String execute(ApiRequest apiRequest) {
		WorkflowQO workflowQO = JSON.parseObject(apiRequest.getBody().getPayload(), WorkflowQO.class);
		HgLogger.getInstance().info("cs", "【QueryWorkflowAction】"+"workflowQO:"+JSON.toJSONString(workflowQO));
		WorkflowResponse workflowResponse = new WorkflowResponse();
		try {
			List<WorkflowDTO> workflowDTO=new ArrayList<WorkflowDTO>();
			RoleWorkflowQO roleWorkflowQO=new RoleWorkflowQO();
			roleWorkflowQO.setCompanyID(workflowQO.getCompanyID());
			for (String roleID : workflowQO.getRoleList()) {
				roleWorkflowQO.setRoleID(roleID);
				List<RoleWorkflow> roleWorkflows = roleWorkflowService.getRoleWorkflowByQo(roleWorkflowQO);
				if (roleWorkflows!=null&&roleWorkflows.size()>0) {
					for (RoleWorkflow roleWorkflow : roleWorkflows) {
						boolean falg = true;
						WorkflowDTO dto=new WorkflowDTO();
						dto.setWorkflowID(roleWorkflow.getWorkflow().getId());
						dto.setWorkflowName(roleWorkflow.getWorkflow().getWorkflowName());
						for (WorkflowDTO dto1 : workflowDTO) {
							if (StringUtils.equals(dto1.getWorkflowID(), dto.getWorkflowID())) {
								falg = false;
								break;
							}
						}
						if (falg) {
							workflowDTO.add(dto);
						}
					}
					
				}
			}
			workflowResponse.setMessage("查询成功");
			workflowResponse.setWorkflowDTOs(workflowDTO);
			HgLogger.getInstance().info("cs", "【QueryWorkflowAction】"+"workflowResponse:"+JSON.toJSONString(workflowResponse));
		} catch (Exception e) {
			workflowResponse.setMessage("查询失败");
			return JSON.toJSONString(workflowResponse);
		}
		return JSON.toJSONString(workflowResponse);
	}
	
}

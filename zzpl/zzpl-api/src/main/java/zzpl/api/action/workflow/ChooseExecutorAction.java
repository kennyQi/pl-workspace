package zzpl.api.action.workflow;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.workflow.ExecutorDTO;
import zzpl.api.client.request.workflow.ChooseExecutorCommand;
import zzpl.api.client.response.workflow.ChooseExecutorResponse;
import zzpl.app.service.local.user.UserRoleService;
import zzpl.app.service.local.user.UserService;
import zzpl.app.service.local.workflow.WorkflowInstanceService;
import zzpl.app.service.local.workflow.WorkflowStepService;
import zzpl.domain.model.user.User;
import zzpl.domain.model.user.UserRole;
import zzpl.domain.model.workflow.WorkflowInstance;
import zzpl.domain.model.workflow.WorkflowStep;
import zzpl.pojo.qo.user.UserQO;
import zzpl.pojo.qo.user.UserRoleQO;
import zzpl.pojo.qo.workflow.WorkflowStepQO;

import com.alibaba.fastjson.JSON;

@Component("ChooseExecutorAction")
public class ChooseExecutorAction implements CommonAction{

	@Autowired
	private WorkflowStepService workflowStepService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private WorkflowInstanceService workflowInstanceService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		ChooseExecutorResponse chooseExecutorResponse = new ChooseExecutorResponse();
		List<ExecutorDTO> executorDTOs = new ArrayList<ExecutorDTO>();
		try{
			ChooseExecutorCommand chooseExecutorCommand = JSON.parseObject(apiRequest.getBody().getPayload(), ChooseExecutorCommand.class);
			HgLogger.getInstance().info("cs", "【ChooseExecutorAction】"+"chooseExecutorCommand:"+JSON.toJSONString(chooseExecutorCommand));
			WorkflowStepQO workflowStepQO = new WorkflowStepQO();
			workflowStepQO.setWorkflowID(chooseExecutorCommand.getWorkflowID());
			workflowStepQO.setStepNO(chooseExecutorCommand.getNextStepNO());
			WorkflowStep workflowStep = workflowStepService.queryUnique(workflowStepQO);
			ExecutorDTO executorDTO = new ExecutorDTO();
			switch (workflowStep.getChooseExecutorType()) {
			case WorkflowStep.END:
				executorDTO = new ExecutorDTO();
				executorDTO.setName("结束流程");
				executorDTOs.add(executorDTO);
				break;
			case WorkflowStep.PERSON:
				User user = userService.get(workflowStep.getExecutor());
				executorDTO = new ExecutorDTO();
				executorDTO.setName(user.getName());
				executorDTO.setUserID(user.getId());
				executorDTO.setDepartmentName(user.getDepartmentName());
				executorDTO.setDepartmentID(user.getDepartmentID());
				executorDTOs.add(executorDTO);
				break;
			case WorkflowStep.ROLE:
				UserRoleQO userRoleQO = new UserRoleQO();
				userRoleQO.setRoleID(workflowStep.getExecutor());
				if(StringUtils.equals(workflowStep.getExecutor(), SysProperties.getInstance().get("travleAdminID"))){
					userRoleQO.setCompanyIDLike(workflowStep.getCompanyID());
				}
				userRoleQO.setStatus(0);
				List<UserRole> userRoles = userRoleService.queryList(userRoleQO);
				for (UserRole userRole : userRoles) {
					executorDTO = new ExecutorDTO();
					executorDTO.setName(userRole.getUser().getName());
					executorDTO.setUserID(userRole.getUser().getId());
					executorDTO.setDepartmentName(userRole.getUser().getDepartmentName());
					executorDTO.setDepartmentID(userRole.getUser().getDepartmentID());
					executorDTOs.add(executorDTO);
				}
				break;
			case WorkflowStep.DEPT:
				UserQO userQO = new UserQO();
				userQO.setDepartmentID(workflowStep.getExecutor());
				List<User> users = userService.queryList(userQO);
				for (User user2 : users) {
					executorDTO = new ExecutorDTO();
					executorDTO.setName(user2.getName());
					executorDTO.setUserID(user2.getId());
					executorDTO.setDepartmentName(user2.getDepartmentName());
					executorDTO.setDepartmentID(user2.getDepartmentID());
					executorDTOs.add(executorDTO);
				}
				break;
			case WorkflowStep.CUSTOM:
				zzpl.pojo.dto.workflow.ExecutorDTO executorDTO2 = JSON.parseObject(workflowStep.getExecutor(), zzpl.pojo.dto.workflow.ExecutorDTO.class);
				if(executorDTO2.getUserFlag()!=null){
					for (Integer integer : executorDTO2.getUserFlag()) {
						if(integer==-99){
							WorkflowInstance workflowInstance = workflowInstanceService.get(chooseExecutorCommand.getWorkflowInstanceID());
							executorDTO = new ExecutorDTO();
							executorDTO.setName(workflowInstance.getUserName());
							executorDTO.setUserID(workflowInstance.getUserID());
							executorDTO.setDepartmentName(workflowInstance.getDepartmentName());
							executorDTO.setDepartmentID(workflowInstance.getDepartmentID());
							executorDTOs.add(executorDTO);
						}else if(integer==-1){
							User user2 = userService.get(chooseExecutorCommand.getUserID());
							executorDTO = new ExecutorDTO();
							executorDTO.setName(user2.getName());
							executorDTO.setUserID(user2.getId());
							executorDTO.setDepartmentName(user2.getDepartmentName());
							executorDTO.setDepartmentID(user2.getDepartmentID());
							executorDTOs.add(executorDTO);
						}
					}
				}
				break;
			}
			chooseExecutorResponse.setExecutorDTOs(executorDTOs);
			chooseExecutorResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch (Exception e){
			HgLogger.getInstance().info("cs", "【ChooseExecutorAction】"+"异常，"+HgLogger.getStackTrace(e));
			chooseExecutorResponse.setMessage("获取执行人失败");
			chooseExecutorResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("cs", "【ChooseExecutorAction】"+"chooseExecutorResponse:"+JSON.toJSONString(chooseExecutorResponse));
		return JSON.toJSONString(chooseExecutorResponse);
	}

}

package zzpl.app.service.local.convert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.GJFlightCabinDAO;
import zzpl.app.dao.jp.GJFlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.CostCenterDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.workflow.StepActionDAO;
import zzpl.app.dao.workflow.TasklistDAO;
import zzpl.app.dao.workflow.WorkflowDAO;
import zzpl.app.dao.workflow.WorkflowInstanceDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.dao.workflow.WorkflowStepActionDAO;
import zzpl.app.dao.workflow.WorkflowStepDAO;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.workflow.Tasklist;
import zzpl.domain.model.workflow.TasklistWait;
import zzpl.domain.model.workflow.Workflow;
import zzpl.domain.model.workflow.WorkflowInstance;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.domain.model.workflow.WorkflowStep;
import zzpl.domain.model.workflow.WorkflowStepAction;
import zzpl.pojo.dto.workflow.ActionDTO;
import zzpl.pojo.dto.workflow.QueryTasklistWaitDTO;
import zzpl.pojo.dto.workflow.StepDTO;
import zzpl.pojo.dto.workflow.WorkflowInstanceOrderDTO;
import zzpl.pojo.qo.workflow.TasklistQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceQO;
import zzpl.pojo.qo.workflow.WorkflowStepActionQO;
import zzpl.pojo.qo.workflow.WorkflowStepQO;
import hg.common.util.EntityConvertUtils;
import hg.log.util.HgLogger;

public class TasklistWaitConvert extends EntityConvertUtils {

	/**
	 * @Description: 待办流程DTO转换LIST
	 * @Title: converTasklistWaitDTO 
	 * @author guok
	 * 
	 * @Time 2015年7月10日 10:38:25
	 * @param tasklistWaits
	 * @param workflowInstanceDAO
	 * @param workflowStepDAO
	 * @param workflowStepActionDAO
	 * @param stepActionDAO
	 * @param workflowInstanceOrderDAO
	 * @return List<QueryTasklistWaitDTO> 设定文件
	 * @throws
	 */
	public static List<QueryTasklistWaitDTO> converTasklistWaitDTO(List<TasklistWait> tasklistWaits,WorkflowInstanceDAO workflowInstanceDAO,WorkflowDAO workflowDAO,
			WorkflowStepDAO workflowStepDAO,WorkflowStepActionDAO workflowStepActionDAO,StepActionDAO stepActionDAO,WorkflowInstanceOrderDAO workflowInstanceOrderDAO
			,FlightOrderDAO flightOrderDAO,GJFlightOrderDAO gjFlightOrderDAO,UserDAO userDAO,GJFlightCabinDAO gjFlightCabinDAO,PassengerDAO passengerDAO,CostCenterDAO costCenterDAO) {
		List<QueryTasklistWaitDTO> queryTasklistWaitDTOs = new ArrayList<QueryTasklistWaitDTO>();
		if (tasklistWaits.size()>0) {
//			HgLogger.getInstance().info("gk", "【TasklistWaitConvert】【converTasklistWaitDTO】【tasklistWaits】:"+JSON.toJSONString(tasklistWaits));
			for (TasklistWait tasklistWait : tasklistWaits) {
				//查询流程实例
				try {
					WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
					workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
					WorkflowInstance workflowInstance = workflowInstanceDAO.queryUnique(workflowInstanceQO);
					WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
					workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstance.getId());
					List<WorkflowInstanceOrder> workflowInstanceOrders = workflowInstanceOrderDAO.queryList(workflowInstanceOrderQO);
					//查询流程环节
					WorkflowStepQO workflowStepQO = new WorkflowStepQO();
					workflowStepQO.setStepNO(tasklistWait.getStepNO());
					workflowStepQO.setWorkflowID(workflowInstance.getWorkflowID());
					WorkflowStep workflowStep = workflowStepDAO.queryUnique(workflowStepQO);
					//查询流程
					Workflow workflow = workflowDAO.get(workflowInstance.getWorkflowID());
					//查询action
					WorkflowStepActionQO workflowStepActionQO = new WorkflowStepActionQO();
					workflowStepActionQO.setWorkflowStepID(workflowStep.getId());
					
					if (workflowInstanceOrders.size()>0&&workflowInstanceOrders!=null) {
						workflowStepActionQO.setOrderType(workflowInstanceOrders.get(0).getOrderType());
						
					}
					
					WorkflowStepAction workflowStepAction = workflowStepActionDAO.queryUnique(workflowStepActionQO);
					QueryTasklistWaitDTO dto = new QueryTasklistWaitDTO();
					dto.setId(tasklistWait.getId());
					if (StringUtils.isNotBlank(tasklistWait.getCurrentUserID())) {
						dto.setCurrentUserID(tasklistWait.getCurrentUserID());
					}
					if (StringUtils.isNotBlank(tasklistWait.getCurrentUserName())) {
						dto.setCurrentUserName(tasklistWait.getCurrentUserName());
					}
					if(workflowStepAction!=null){
						if (StringUtils.isNotBlank(workflowStepAction.getOrderType())) {
							if (workflowStepAction.getOrderType().equals(Passenger.GN_ORDER)) {
								dto.setOrderType("国内机票");
							}else if (workflowStepAction.getOrderType().equals(Passenger.GJ_ORDER)) {
								dto.setOrderType("国际机票");
							}
							
						}
					}
					
					if (StringUtils.isNotBlank(tasklistWait.getAction())) {
						dto.setAction(tasklistWait.getAction());
					}
					
					if (StringUtils.isNotBlank(tasklistWait.getNextUserID())) {
						dto.setNextUserID(tasklistWait.getNextUserID());
					}
					if (StringUtils.isNotBlank(tasklistWait.getNextUserName())) {
						dto.setNextUserName(tasklistWait.getNextUserName());
					}
					if (StringUtils.isNotBlank(tasklistWait.getOpinion())) {
						dto.setOpinion(tasklistWait.getOpinion());
					}
					if (StringUtils.isNotBlank(tasklistWait.getStepName())) {
						dto.setStepName(tasklistWait.getStepName());
					}
					
					if (StringUtils.isNotBlank(workflowInstance.getDepartmentName())) {
						dto.setDepartmentName(workflowInstance.getDepartmentName());
					}
					if (StringUtils.isNotBlank(workflowInstance.getUserName())) {
						dto.setUserName(workflowInstance.getUserName());
					}
					if (StringUtils.isNotBlank(workflowStep.getId())) {
						dto.setWorkflowStepID(workflowStep.getId());
					}
					if (StringUtils.isNotBlank(workflow.getWorkflowName())) {
						dto.setWorkflowName(workflow.getWorkflowName());
					}
					if (workflowInstanceOrders.size()>0&&workflowInstanceOrders!=null) {
						List<WorkflowInstanceOrderDTO> orderDTOs = WorkflowInstanceOrderConver.listConvert(workflowInstanceOrders, flightOrderDAO,gjFlightOrderDAO, userDAO,gjFlightCabinDAO,passengerDAO,costCenterDAO);
						dto.setWorkflowInstanceOrderDTOs(orderDTOs);
					}
					queryTasklistWaitDTOs.add(dto);
				} catch (Exception e) {
					HgLogger.getInstance().error("cs",	"【TasklistWaitConvert】【converTasklistWaitDTO】："+e.getMessage());
				}
			}
		}
		return queryTasklistWaitDTOs;
	}

	/**
	 * @Description: 查询单一审批单
	 * @Title: queryTasklistWaitDTO 
	 * @author guok
	 * @Description: 查询单一审批单
	 * @Time 2015年7月20日 08:49:50
	 * @param tasklistWait
	 * @param workflowInstanceDAO
	 * @param workflowStepDAO
	 * @param workflowStepActionDAO
	 * @param stepActionDAO
	 * @param workflowInstanceOrderDAO
	 * @param flightOrderDAO
	 * @return QueryTasklistWaitDTO 设定文件
	 * @throws
	 */
	public static QueryTasklistWaitDTO queryTasklistWaitDTO(TasklistWait tasklistWait,WorkflowInstanceDAO workflowInstanceDAO,WorkflowDAO workflowDAO,
			WorkflowStepDAO workflowStepDAO,WorkflowStepActionDAO workflowStepActionDAO,StepActionDAO stepActionDAO,WorkflowInstanceOrderDAO workflowInstanceOrderDAO,
			FlightOrderDAO flightOrderDAO,GJFlightOrderDAO gjFlightOrderDAO,UserDAO userDAO,GJFlightCabinDAO gjFlightCabinDAO,PassengerDAO passengerDAO,CostCenterDAO costCenterDAO,TasklistDAO tasklistDAO) {
		QueryTasklistWaitDTO dto = new QueryTasklistWaitDTO();
//		HgLogger.getInstance().info("gk", "【TasklistWaitConvert】【queryTasklistWaitDTO】【tasklistWait】:"+JSON.toJSONString(tasklistWait));
		if (tasklistWait != null) {
			//查询流程实例
			try {
				WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
				workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
				WorkflowInstance workflowInstance = workflowInstanceDAO.queryUnique(workflowInstanceQO);
				
				//查询order
				WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
				workflowInstanceOrderQO.setWorkflowInstanceID(tasklistWait.getWorkflowInstanceID());
				List<WorkflowInstanceOrder> workflowInstanceOrders = workflowInstanceOrderDAO.queryList(workflowInstanceOrderQO);
				dto.setWorkflowInstanceOrderDTOs(WorkflowInstanceOrderConver.listConvert(workflowInstanceOrders, flightOrderDAO,gjFlightOrderDAO,userDAO,gjFlightCabinDAO,passengerDAO,costCenterDAO));
				//查询之前环节审批人
				TasklistQO tasklistQO = new TasklistQO();
				tasklistQO.setStepNO(tasklistWait.getStepNO());
				tasklistQO.setWorkflowInstanceID(tasklistWait.getWorkflowInstanceID());
				List<Tasklist> tasklists = tasklistDAO.queryList(tasklistQO);
				tasklistQO = new TasklistQO();
				tasklistQO.setLeSendTime(tasklists.get(0).getReceiveTime());
				tasklistQO.setSendTimeAsc(true);
				
				tasklistQO.setWorkflowInstanceID(tasklistWait.getWorkflowInstanceID());
				tasklists = tasklistDAO.queryList(tasklistQO);
				
				List<String> previousUsers = new ArrayList<String>();
				for (Tasklist tasklist : tasklists) {
					if (StringUtils.isNotBlank(tasklist.getCurrentUserName())) {
						previousUsers.add(tasklist.getCurrentUserName());
					}
				}
				dto.setPreviousUserName(previousUsers);
				
				//查询流程环节
				WorkflowStepQO workflowStepQO = new WorkflowStepQO();
				workflowStepQO.setStepNO(tasklistWait.getStepNO());
				workflowStepQO.setWorkflowID(workflowInstance.getWorkflowID());
				WorkflowStep workflowStep = workflowStepDAO.queryUnique(workflowStepQO);
				//查询流程
				Workflow workflow = workflowDAO.get(workflowInstance.getWorkflowID());
				//查询action
				WorkflowStepActionQO workflowStepActionQO = new WorkflowStepActionQO();
				workflowStepActionQO.setWorkflowStepID(workflowStep.getId());
				/**
				 * 郭凯
				 */
				if (workflowInstanceOrders.size()>0&&workflowInstanceOrders!=null) {
					workflowStepActionQO.setOrderType(workflowInstanceOrders.get(0).getOrderType());
				}
				List<WorkflowStepAction> workflowStepActions = workflowStepActionDAO.queryList(workflowStepActionQO);
				
				dto.setId(tasklistWait.getId());
				if (StringUtils.isNotBlank(tasklistWait.getCurrentUserID())) {
					dto.setCurrentUserID(tasklistWait.getCurrentUserID());
				}
				if (StringUtils.isNotBlank(tasklistWait.getCurrentUserName())) {
					dto.setCurrentUserName(tasklistWait.getCurrentUserName());
				}
				//配置按钮
				if(workflowStepActions!=null&&workflowStepActions.size()>0){
					List<ActionDTO> actionDTOs = new ArrayList<ActionDTO>();
					for (WorkflowStepAction workflowStepAction : workflowStepActions) {
						ActionDTO actionDTO = new ActionDTO();
						if(!workflowStepAction.getStepAction().getActionValue().contains("/")){
							//表单提交按钮
							dto.setActionID(workflowStepAction.getStepAction().getId());
							dto.setActionName(workflowStepAction.getStepAction().getActionName());
							dto.setActionValue(workflowStepAction.getStepAction().getActionValue());
							dto.setButtonName(workflowStepAction.getStepAction().getButtonName());
						}
						
						actionDTO.setActionID(workflowStepAction.getStepAction().getId());
						if (StringUtils.isNotBlank(workflowStepAction.getStepAction().getActionName())) {
							actionDTO.setActionName(workflowStepAction.getStepAction().getActionName());
						}
						if (StringUtils.isNotBlank(workflowStepAction.getStepAction().getActionValue())) {
							actionDTO.setActionValue(workflowStepAction.getStepAction().getActionValue());
						}
						if(StringUtils.isNotBlank(workflowStepAction.getStepAction().getButtonName())){
							actionDTO.setButtonName(workflowStepAction.getStepAction().getButtonName());
						}
						actionDTOs.add(actionDTO);
					}
					dto.setActionDTOs(actionDTOs);
					
				}
				
				if (StringUtils.isNotBlank(tasklistWait.getNextUserID())) {
					dto.setNextUserID(tasklistWait.getNextUserID());
				}
				if (StringUtils.isNotBlank(tasklistWait.getNextUserName())) {
					dto.setNextUserName(tasklistWait.getNextUserName());
				}
				if (StringUtils.isNotBlank(tasklistWait.getOpinion())) {
					dto.setOpinion(tasklistWait.getOpinion());
				}
				if (StringUtils.isNotBlank(tasklistWait.getStepName())) {
					dto.setStepName(tasklistWait.getStepName());
				}
				if (tasklistWait.getStepNO()!=null&&tasklistWait.getStepNO()>0) {
					dto.setStepNO(tasklistWait.getStepNO());
				}
				
				if (StringUtils.isNotBlank(workflowInstance.getDepartmentName())) {
					dto.setDepartmentName(workflowInstance.getDepartmentName());
				}
				if (StringUtils.isNotBlank(workflowInstance.getUserName())) {
					dto.setUserName(workflowInstance.getUserName());
				}
				if (StringUtils.isNotBlank(workflowStep.getId())) {
					dto.setWorkflowStepID(workflowStep.getId());
				}
				if (StringUtils.isNotBlank(workflow.getWorkflowName())) {
					dto.setWorkflowName(workflow.getWorkflowName());
				}
				if (StringUtils.isNotBlank(tasklistWait.getPreviousUserID())) {
					dto.setPreviousUserID(tasklistWait.getPreviousUserID());
				}
				dto.setWorkflowID(workflowInstance.getWorkflowID());
				dto.setWorkflowInstanceID(workflowInstance.getId());
				
				//查找下一环节
				List<StepDTO> stepDTOs = new ArrayList<StepDTO>();
				String[] nextSteps = workflowStep.getNextStepNO().split(";");
				for (int i = 0; i < nextSteps.length; i++) {
					StepDTO stepDTO = new StepDTO();
					workflowStepQO = new WorkflowStepQO();
					workflowStepQO.setStepNO(Integer.parseInt(nextSteps[i]));
					workflowStepQO.setWorkflowID(workflowInstance.getWorkflowID());
					WorkflowStep nextWorkflowStep = workflowStepDAO.queryUnique(workflowStepQO);
					if (nextWorkflowStep!=null) {
						stepDTO.setStepName(nextWorkflowStep.getStepName());
						stepDTO.setStepNO(nextWorkflowStep.getStepNO().toString());
					}else {
						stepDTO.setStepName("结束");
						stepDTO.setStepNO("99");
					}
					
					stepDTOs.add(stepDTO);
				}
				dto.setStepDTOs(stepDTOs);
			} catch (Exception e) {
				HgLogger.getInstance().error("cs",	"【TasklistWaitConvert】【converTasklistWaitDTO】："+e.getMessage());
			}
			
		}
		return dto;
	}

	public static List<WorkflowInstanceOrderDTO> converPageTasklistWaitDTO(List<TasklistWait> tasklistWaits,WorkflowInstanceDAO workflowInstanceDAO,WorkflowDAO workflowDAO,
			WorkflowStepDAO workflowStepDAO,WorkflowStepActionDAO workflowStepActionDAO,StepActionDAO stepActionDAO,WorkflowInstanceOrderDAO workflowInstanceOrderDAO
			,FlightOrderDAO flightOrderDAO,GJFlightOrderDAO gjFlightOrderDAO,UserDAO userDAO,GJFlightCabinDAO gjFlightCabinDAO,PassengerDAO passengerDAO,CostCenterDAO costCenterDAO) {
		List<WorkflowInstanceOrderDTO> orderDTOs = new ArrayList<WorkflowInstanceOrderDTO>();
		if (tasklistWaits.size()>0) {
//			HgLogger.getInstance().info("gk", "【TasklistWaitConvert】【converTasklistWaitDTO】【tasklistWaits】:"+JSON.toJSONString(tasklistWaits));
			for (TasklistWait tasklistWait : tasklistWaits) {
				//查询流程实例
				try {
					WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
					workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
					WorkflowInstance workflowInstance = workflowInstanceDAO.queryUnique(workflowInstanceQO);
					WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
					workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstance.getId());
					List<WorkflowInstanceOrder> workflowInstanceOrders = workflowInstanceOrderDAO.queryList(workflowInstanceOrderQO);
					//查询流程
					Workflow workflow = workflowDAO.get(workflowInstance.getWorkflowID());
					
					if (workflowInstanceOrders.size()>0&&workflowInstanceOrders!=null) {
						List<WorkflowInstanceOrderDTO> orderDTO = WorkflowInstanceOrderConver.listConvert(workflowInstanceOrders, flightOrderDAO,gjFlightOrderDAO, userDAO,gjFlightCabinDAO,passengerDAO,costCenterDAO);
						for (WorkflowInstanceOrderDTO workflowInstanceOrderDTO : orderDTO) {
							workflowInstanceOrderDTO.setAction(tasklistWait.getAction());
							workflowInstanceOrderDTO.setWorkflowName(workflow.getWorkflowName());
							workflowInstanceOrderDTO.setId(tasklistWait.getId());
							workflowInstanceOrderDTO.setBuildFee(workflowInstanceOrderDTO.getBuildFee()+workflowInstanceOrderDTO.getOilFee());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Long time = new Long(workflowInstanceOrderDTO.getCreateTime().getTime());
							String createDate = sdf.format(time);
							workflowInstanceOrderDTO.setCreateDate(createDate);
							Long startTime = new Long(workflowInstanceOrderDTO.getStartTime().getTime());
							String startDate = sdf.format(startTime);
							workflowInstanceOrderDTO.setStartDate(startDate);
							orderDTOs.add(workflowInstanceOrderDTO);
						}
					}
				} catch (Exception e) {
					HgLogger.getInstance().error("cs",	"【TasklistWaitConvert】【converPageTasklistWaitDTO】："+e.getMessage());
				}
			}
		}
		return orderDTOs;
	}
	
}

package zzpl.app.service.local.workflow;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.GJFlightCabinDAO;
import zzpl.app.dao.jp.GJFlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.CostCenterDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.user.UserRoleDAO;
import zzpl.app.dao.workflow.StepActionDAO;
import zzpl.app.dao.workflow.TasklistDAO;
import zzpl.app.dao.workflow.TasklistWaitDAO;
import zzpl.app.dao.workflow.WorkflowDAO;
import zzpl.app.dao.workflow.WorkflowInstanceDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.dao.workflow.WorkflowStepActionDAO;
import zzpl.app.dao.workflow.WorkflowStepDAO;
import zzpl.app.service.local.convert.TasklistWaitConvert;
import zzpl.domain.model.user.User;
import zzpl.domain.model.user.UserRole;
import zzpl.domain.model.workflow.TasklistWait;
import zzpl.domain.model.workflow.WorkflowInstance;
import zzpl.domain.model.workflow.WorkflowStep;
import zzpl.pojo.command.workflow.ChooseExecutorCommand;
import zzpl.pojo.dto.workflow.ExecutorDTO;
import zzpl.pojo.dto.workflow.QueryTasklistWaitDTO;
import zzpl.pojo.qo.user.UserQO;
import zzpl.pojo.qo.user.UserRoleQO;
import zzpl.pojo.qo.workflow.TasklistWaitQO;
import zzpl.pojo.qo.workflow.WorkflowStepQO;

@Service
@Transactional
public class TasklistWaitService extends BaseServiceImpl<TasklistWait, TasklistWaitQO, TasklistWaitDAO> {

	@Autowired
	private WorkflowDAO workflowDAO;
	@Autowired
	private TasklistWaitDAO tasklistWaitDAO;
	@Autowired
	private TasklistDAO tasklistDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private WorkflowInstanceDAO workflowInstanceDAO;
	@Autowired
	private WorkflowStepDAO workflowStepDAO;
	@Autowired
	private WorkflowStepActionDAO workflowStepActionDAO;
	@Autowired
	private StepActionDAO stepActionDAO;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private WorkflowStepService workflowStepService;
	@Autowired
	private UserRoleDAO userRoleDao;
	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private GJFlightOrderDAO gjFlightOrderDAO;
	@Autowired
	private GJFlightCabinDAO gjFlightCabinDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired 
	private CostCenterDAO costCenterDAO;
	

	/**
	 * @Title: queryOrder 
	 * @author guok
	 * @Description: 待办列表转换convert
	 * @Time 2015年7月20日 09:09:16
	 * @param pagination
	 * @return Pagination 设定文件
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Pagination queryOrder(Pagination pagination) {
		Pagination pagination1 = tasklistWaitDAO.queryPagination(pagination);
		HgLogger.getInstance().info("gk", "【TasklistWaitService】【execute】"+JSON.toJSONString(pagination1.getList()));
		pagination1.setList(TasklistWaitConvert.converPageTasklistWaitDTO((List<TasklistWait>) pagination1.getList(), workflowInstanceDAO,workflowDAO, workflowStepDAO, workflowStepActionDAO, stepActionDAO,workflowInstanceOrderDAO, flightOrderDAO,gjFlightOrderDAO,userDAO,gjFlightCabinDAO,passengerDAO,costCenterDAO));
		return pagination1;
	}
	
	/**
	 * @Title: getByID 
	 * @author guok
	 * @Description: 待办详情【附带查询订单】
	 * @Time 2015年7月20日 09:09:09
	 * @param tasklistWaitID
	 * @return QueryTasklistWaitDTO 设定文件
	 * @throws
	 */
	public QueryTasklistWaitDTO getByID(String tasklistWaitID) {
		TasklistWait tasklistWait = tasklistWaitDAO.get(tasklistWaitID);
		HgLogger.getInstance().info("gk", "【TasklistWaitService】【getByID】"+JSON.toJSONString(tasklistWait));
		QueryTasklistWaitDTO dto = TasklistWaitConvert.queryTasklistWaitDTO(tasklistWait, workflowInstanceDAO,workflowDAO, workflowStepDAO, workflowStepActionDAO, stepActionDAO,workflowInstanceOrderDAO, flightOrderDAO,gjFlightOrderDAO,userDAO,gjFlightCabinDAO,passengerDAO,costCenterDAO,tasklistDAO);
		return dto;
	}
	
	/**
	 * @Title: chooseExecutor 
	 * @author guok
	 * @Description: 根据环节选择人选
	 * @Time 2015年8月12日下午1:19:39
	 * @param chooseExecutorCommand
	 * @return List<ExecutorDTO> 设定文件
	 * @throws
	 */
	public List<ExecutorDTO> chooseExecutor(ChooseExecutorCommand chooseExecutorCommand) {
		List<ExecutorDTO> executorDTOs = new ArrayList<ExecutorDTO>();
		WorkflowStepQO workflowStepQO = new WorkflowStepQO();
		workflowStepQO.setWorkflowID(chooseExecutorCommand.getWorkflowID());
		workflowStepQO.setStepNO(chooseExecutorCommand.getNextStepNO());
		WorkflowStep workflowStep = workflowStepService.queryUnique(workflowStepQO);
		ExecutorDTO executorDTO = new ExecutorDTO();
		if (workflowStep!=null) {
			switch (workflowStep.getChooseExecutorType()) {
			case WorkflowStep.END:
				executorDTO = new ExecutorDTO();
				executorDTO.setName("结束流程");
				executorDTOs.add(executorDTO);
				break;
			case WorkflowStep.PERSON:
				User user = userDAO.get(workflowStep.getExecutor());
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
				userRoleQO.setCompanyIDLike(chooseExecutorCommand.getCompanyID());
				userRoleQO.setStatus(0);
				List<UserRole> userRoles = userRoleDao.queryList(userRoleQO);
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
				List<User> users = userDAO.queryList(userQO);
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
							WorkflowInstance workflowInstance = workflowInstanceDAO.get(chooseExecutorCommand.getWorkflowInstanceID());
							executorDTO = new ExecutorDTO();
							executorDTO.setName(workflowInstance.getUserName());
							executorDTO.setUserID(workflowInstance.getUserID());
							executorDTO.setDepartmentName(workflowInstance.getDepartmentName());
							executorDTO.setDepartmentID(workflowInstance.getDepartmentID());
							executorDTOs.add(executorDTO);
						}else if(integer==0){
							User user2 = userDAO.get(chooseExecutorCommand.getUserID());
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
		}else {
			ExecutorDTO dto = new ExecutorDTO();
			dto.setName("办结");
			dto.setUserID("");
			executorDTOs.add(dto);
		}
		return executorDTOs;
	}
	
	@Override
	protected TasklistWaitDAO getDao() {
		return tasklistWaitDAO;
	}

}

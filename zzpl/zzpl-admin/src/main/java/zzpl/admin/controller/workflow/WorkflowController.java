package zzpl.admin.controller.workflow;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.EntityConvertUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import zzpl.admin.controller.BaseController;
import zzpl.app.service.local.user.CompanyService;
import zzpl.app.service.local.user.RoleService;
import zzpl.app.service.local.user.UserRoleService;
import zzpl.app.service.local.user.UserService;
import zzpl.app.service.local.workflow.StepActionService;
import zzpl.app.service.local.workflow.WorkflowService;
import zzpl.app.service.local.workflow.WorkflowStepActionService;
import zzpl.app.service.local.workflow.WorkflowStepService;
import zzpl.domain.model.user.Role;
import zzpl.domain.model.user.User;
import zzpl.domain.model.workflow.StepAction;
import zzpl.domain.model.workflow.Workflow;
import zzpl.domain.model.workflow.WorkflowStep;
import zzpl.domain.model.workflow.WorkflowStepAction;
import zzpl.pojo.command.workflow.AddWorkflowCommand;
import zzpl.pojo.command.workflow.ConfigWorkflowCommand;
import zzpl.pojo.command.workflow.DeleteWorkflowCommand;
import zzpl.pojo.command.workflow.ModifyWorkflowCommand;
import zzpl.pojo.command.workflow.ModifyWorkflowStepCommand;
import zzpl.pojo.dto.workflow.ExecutorDTO;
import zzpl.pojo.dto.workflow.WorkflowDTO;
import zzpl.pojo.qo.user.RoleQO;
import zzpl.pojo.qo.workflow.StepActionQO;
import zzpl.pojo.qo.workflow.WorkflowQO;
import zzpl.pojo.qo.workflow.WorkflowStepActionQO;
import zzpl.pojo.qo.workflow.WorkflowStepQO;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/workflow")
public class WorkflowController extends BaseController {

	/**
	 * 角色service
	 */
	@Autowired
	private RoleService roleService;
	/**
	 * 公司service
	 */
	@Autowired
	private CompanyService companyService;
	/**
	 * 人员service
	 */
	@Autowired
	private UserService userService;
	/**
	 * 流程service
	 */
	@Autowired
	private WorkflowService workflowService;
	/**
	 * Action service
	 */
	@Autowired
	private StepActionService stepActionService;

	@Autowired
	private WorkflowStepService workflowStepService;

	@Autowired
	private WorkflowStepActionService workflowStepActionService;
	
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * @Title: view
	 * @author guok
	 * @时间 2015年7月8日 13:56:04
	 * @Description: 跳转部门列表页
	 * @param request
	 * @param model
	 * @param roleQO
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/view")
	public String view(HttpServletRequest request, Model model,
			@ModelAttribute WorkflowQO workflowQO) {
		return "/content/workflow/workflow_list.html";
	}

	/**
	 * @Title: workflowList
	 * @author guok
	 * @Description: 列表展示
	 * @time 2015年7月8日 14:00:13
	 * @param request
	 * @param model
	 * @param workflowQO
	 * @param pageNo
	 * @param pageSize
	 * @param workflowName
	 * @return Pagination 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/workflowList")
	public Pagination workflowList(
			HttpServletRequest request,
			Model model,
			WorkflowQO workflowQO,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "workflowName", required = false) String workflowName) {

		HgLogger.getInstance()
				.error("cs", "【WorkflowController】【workflowList】");
		if (pageNo == null)
			pageNo = 1;
		if (pageSize == null)
			pageSize = 10;
		workflowQO.setWorkflowName(workflowName);
		// 获取当前登陆用户，以用于查找用户所属公司
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		workflowQO.setCompanyID(user.getCompanyID());
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(workflowQO);
		if (StringUtils.isNotBlank(user.getCompanyID())) {
			pagination = workflowService.queryPagination(pagination);
			pagination.setList(EntityConvertUtils.convertEntityToDtoList(
					(List<WorkflowDTO>) pagination.getList(), WorkflowDTO.class));
		}
		return pagination;
	}

	/**
	 * 
	 * @Title: deleteworkflow
	 * @author guok
	 * @Description: 删除
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public String deleteworkflow(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute DeleteWorkflowCommand command) {
		try {
			HgLogger.getInstance().error(
					"cs",
					"【WorkflowController】【deleteworkflow】,command："
							+ JSON.toJSONString(command));
			workflowService.deleteWorkflow(command);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,
					SysProperties.getInstance().get("workflowMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", "删除失败", null, "");
		}
	}

	/**
	 * @throws Exception 
	 * @Title: add
	 * @author guok
	 * @Description: 跳转添加页
	 * @param request
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) throws Exception {
		// 获取当前登陆用户，以用于查找用户所属公司
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		if(StringUtils.isBlank(user.getCompanyID())){
			throw new Exception("当前用户无权限");
		}
		RoleQO roleQO = new RoleQO();
		roleQO.setCompanyID(user.getCompanyID());
		List<Role> roles = roleService.queryList(roleQO);
		model.addAttribute("roles", roles);
		return "/content/workflow/workflow_add.html";
	}

	/**
	 * @Title: workflowAdd
	 * @author guok
	 * @Description: 添加
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return
	 * @throws String
	 *             设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/workflowAdd")
	public String workflowAdd(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute AddWorkflowCommand command) {
		try {
			// 获取当前登陆用户，以用于查找用户所属公司
			AuthUser user = (AuthUser) request.getSession().getAttribute(
					SecurityConstants.SESSION_USER_KEY);
			command.setUserID(user.getId());
			HgLogger.getInstance().error(
					"cs",
					"【WorkflowController】【workflowAdd】,command："
							+ JSON.toJSONString(command));
			workflowService.addWorkflow(command);
			return DwzJsonResultUtil.createJsonString("200", "添加成功",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,
					SysProperties.getInstance().get("workflowMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}
	}

	/**
	 * @Title: edit
	 * @author guok
	 * @Description: 跳转到编辑页
	 * @param request
	 * @param response
	 * @param model
	 * @param workflowID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "workflowID") String workflowID) {
		WorkflowQO qo = new WorkflowQO();
		qo.setWorkflowID(workflowID);
		Workflow workflow = workflowService.getWorkflowByQo(qo);
		model.addAttribute("workflow", workflow);

		// 获取当前登陆用户，以用于查找用户所属公司
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		RoleQO roleQO = new RoleQO();
		roleQO.setCompanyID(user.getCompanyID());
		List<Role> roles = roleService.queryList(roleQO);
		model.addAttribute("roles", roles);
		return "/content/workflow/workflow_edit.html";
	}

	@RequestMapping(value = "/editWorkflowStep")
	public String editWorkflowStep(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "workflowID") String workflowID,
			@RequestParam(value = "workflowStepNO") int workflowStepNO) {
		List<StepAction> stepActions = stepActionService
				.queryList(new StepActionQO());
		model.addAttribute("stepActions", stepActions);
		Workflow workflow = workflowService.get(workflowID);
		workflow.setRoleWorkflows(null);
		model.addAttribute("workflow", workflow);
		RoleQO roleQO = new RoleQO();
		List<Role> roles = roleService.queryList(roleQO);
		model.addAttribute("roles", roles);
		WorkflowStepQO workflowStepQO = new WorkflowStepQO();
		workflowStepQO.setWorkflowID(workflowID);
		workflowStepQO.setOrderByStepNOasc("orderByStepNOasc");
		List<WorkflowStep> workflowSteps = workflowStepService
				.queryList(workflowStepQO);
		for (WorkflowStep workflowStep : workflowSteps) {
			if (workflowStep.getChooseExecutorType() != null
					&& workflowStep.getChooseExecutorType() == 4) {
				ExecutorDTO executorDTO = JSON.parseObject(
						workflowStep.getExecutor(), ExecutorDTO.class);
				workflowStep.setExecutor(executorDTO.getUserFlag().get(0)
						.toString());
			}
			WorkflowStepActionQO workflowStepActionQO = new WorkflowStepActionQO();
			workflowStepActionQO.setWorkflowStepID(workflowStep.getId());
			workflowStep.setWorkflowStepActions(workflowStepActionService
					.queryList(workflowStepActionQO));
			if (workflowStep.getStepNO() == workflowStepNO) {
				model.addAttribute("workflowStep", workflowStep);
				AuthUser authUser = (AuthUser) request.getSession()
						.getAttribute(SecurityConstants.SESSION_USER_KEY);
				User user = userService.get(authUser.getId());
				if(workflowStep.getChooseExecutorType()!=null){
					switch (workflowStep.getChooseExecutorType()) {
					case 2:
						RoleQO roleQO1 = new RoleQO();
						roleQO1.setCompanyID(user.getCompanyID());
						List<Role> roles1 = roleService.queryList(roleQO1);
						for (Role role : roles1) {
							role.setUserRoles(null);
							role.setRoleWorkflows(null);
							role.setCompany(null);
							role.setRoleMenus(null);
						}
						Role role = roleService.get(SysProperties.getInstance().get("travleAdminID"));
						role.setUserRoles(null);
						role.setRoleWorkflows(null);
						role.setCompany(null);
						role.setRoleMenus(null);
						roles1.add(role);
						model.addAttribute("executors", roles1);
						break;
					case 4:
						List<Executor> executors = new ArrayList<WorkflowController.Executor>();
						Executor executor = new Executor();
						executor.setUserFlagkey(-99);
						executor.setUserFlagval("起草人");
						executors.add(executor);
						Executor executor2 = new Executor();
						executor2.setUserFlagkey(-1);
						executor2.setUserFlagval("上一环节处理人");
						executors.add(executor2);
						model.addAttribute("executors", executors);
						break;
					}
				}
			}
		}
		model.addAttribute("workflowSteps", workflowSteps);
		return "/content/workflow/workflowStep_edit.html";
	}

	/**
	 * @Title: workflowEdit
	 * @author guok
	 * @Description: 编辑
	 * @Time 2015年6月26日 08:52:15
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/workflowedit")
	public String workflowEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ModifyWorkflowCommand command) {
		try {
			HgLogger.getInstance().error(
					"cs",
					"【workflowController】【workflowEdit】,command："
							+ JSON.toJSONString(command));
			workflowService.modifyWorkflow(command);
			return DwzJsonResultUtil.createJsonString("200", "修改成功",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,
					SysProperties.getInstance().get("workflowMenuID"));
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}

	}

	/**
	 * @Title: detail
	 * @author guok
	 * @Description: 跳转到详情页
	 * @time 2015年7月9日 08:54:57
	 * @param request
	 * @param response
	 * @param model
	 * @param workflowID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/detail")
	public String detail(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "workflowID") String workflowID) {
		WorkflowQO qo = new WorkflowQO();
		qo.setWorkflowID(workflowID);
		Workflow workflow = workflowService.getWorkflowByQo(qo);
		model.addAttribute("workflow", workflow);
		return "/content/workflow/workflow_detail.html";
	}

	
	@RequestMapping(value = "/workflowConfigView")
	public String workflowConfigView(HttpServletRequest request, Model model,
			@ModelAttribute WorkflowQO workflowQO,
			@RequestParam(value = "workflowID") String workflowID) {
		Workflow workflow = workflowService.get(workflowID);
		model.addAttribute("workflow", workflow);
		return "/content/workflow/workflow_step_config.html";
	}
	
	/**
	 * @Title: stepAdd
	 * @author guok
	 * @Description: 跳转流程配置页
	 * @param request
	 * @param response
	 * @param model
	 * @param workflowID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/workflowConfig")
	@ResponseBody
	public Pagination stepAdd(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "workflowID") String workflowID) {
		// 事件列表
		/*List<StepAction> stepActions = stepActionService
				.queryList(new StepActionQO());
		model.addAttribute("stepActions", stepActions);
		Workflow workflow = workflowService.get(workflowID);
		model.addAttribute("workflow", workflow);
		RoleQO roleQO = new RoleQO();
		List<Role> roles = roleService.queryList(roleQO);
		model.addAttribute("roles", roles);*/
		WorkflowStepQO workflowStepQO = new WorkflowStepQO();
		workflowStepQO.setWorkflowID(workflowID);
		workflowStepQO.setOrderByStepNOasc("orderByStepNOasc");
		Pagination pagination = new Pagination();
		pagination.setCondition(workflowStepQO);
		pagination.setPageNo(1);
		pagination.setPageSize(100);
		List<WorkflowStep> workflowSteps = (List<WorkflowStep>) workflowStepService.queryPagination(pagination).getList();
		for (WorkflowStep workflowStep : workflowSteps) {
			if (workflowStep.getChooseExecutorType() != null	&& workflowStep.getChooseExecutorType() == 4) {
				ExecutorDTO executorDTO = JSON.parseObject(
						workflowStep.getExecutor(), ExecutorDTO.class);
				if(executorDTO.getUserFlag().get(0)==-99){
					workflowStep.setExecutor("起草人");
				}else if(executorDTO.getUserFlag().get(0)==-1){
					workflowStep.setExecutor("上一环节处理人");
				}
			}else if (workflowStep.getChooseExecutorType() != null	&& workflowStep.getChooseExecutorType() == 2) {
				if (StringUtils.isNotBlank(workflowStep.getExecutor())) {
					Role role = roleService.get(workflowStep.getExecutor());
					if(role!=null){
						workflowStep.setExecutor(role.getRoleName());
					}
				}
			}
			String[] nextStepNOs = workflowStep.getNextStepNO().split(";");
			String nextStepName = "";
			for (int i = 0; i < nextStepNOs.length; i++) {
				if(StringUtils.equals(nextStepNOs[i], "99")){
					nextStepName += "结束流程 ";
				}else if(StringUtils.isNotBlank(nextStepNOs[i])){
					WorkflowStepQO workflowStepQO2 = new WorkflowStepQO();
					workflowStepQO2.setWorkflowID(workflowStep.getWorkflowID());
					workflowStepQO2.setStepNO(Integer.parseInt(nextStepNOs[i]));
					WorkflowStep workflowStep2 = workflowStepService.queryUnique(workflowStepQO2);
					nextStepName += workflowStep2.getStepName() + " ";
				}
			}
			workflowStep.setNextStepNO(nextStepName);
			List<StepAction> stepActions = stepActionService.queryList(new StepActionQO());
			String actionName = "";
			WorkflowStepActionQO workflowStepActionQO = new WorkflowStepActionQO();
			workflowStepActionQO.setWorkflowStepID(workflowStep.getId());
			List<WorkflowStepAction> workflowStepActions = workflowStepActionService	.queryList(workflowStepActionQO);
			for (WorkflowStepAction workflowStepAction : workflowStepActions) {
				for(StepAction stepAction:stepActions){
					if(StringUtils.equals(stepAction.getId(),workflowStepAction.getStepAction().getId())){
						actionName+=stepAction.getActionName()+" ";
					}
				}
			}
			workflowStep.setCompanyName(actionName);
			workflowStep.setWorkflowStepActions(null);
		}
		model.addAttribute("workflowSteps", workflowSteps);
		pagination.setList(workflowSteps);
		return pagination;
	}

	
	/*@RequestMapping(value = "/workflowConfig")
	public String stepAdd(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "workflowID") String workflowID) {
		// 事件列表
		List<StepAction> stepActions = stepActionService
				.queryList(new StepActionQO());
		model.addAttribute("stepActions", stepActions);
		Workflow workflow = workflowService.get(workflowID);
		model.addAttribute("workflow", workflow);
		RoleQO roleQO = new RoleQO();
		List<Role> roles = roleService.queryList(roleQO);
		model.addAttribute("roles", roles);
		WorkflowStepQO workflowStepQO = new WorkflowStepQO();
		workflowStepQO.setWorkflowID(workflowID);
		workflowStepQO.setOrderByStepNOasc("orderByStepNOasc");
		List<WorkflowStep> workflowSteps = workflowStepService
				.queryList(workflowStepQO);
		for (WorkflowStep workflowStep : workflowSteps) {
			if (workflowStep.getChooseExecutorType() != null
					&& workflowStep.getChooseExecutorType() == 4) {
				ExecutorDTO executorDTO = JSON.parseObject(
						workflowStep.getExecutor(), ExecutorDTO.class);
				workflowStep.setExecutor(executorDTO.getUserFlag().get(0)
						.toString());
			}
			WorkflowStepActionQO workflowStepActionQO = new WorkflowStepActionQO();
			workflowStepActionQO.setWorkflowStepID(workflowStep.getId());
			workflowStep.setWorkflowStepActions(workflowStepActionService
					.queryList(workflowStepActionQO));
		}
		model.addAttribute("workflowSteps", workflowSteps);
		return "/content/workflow/workflow_step_config.html";
	}*/
	
	/**
	 * @Title: stepAdd
	 * @author guok
	 * @Description: 跳转流程配置页
	 * @param request
	 * @param response
	 * @param model
	 * @param workflowID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/workflowStepAdd")
	public String workflowStepAdd(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "workflowID") String workflowID) {
		// 事件列表
		List<StepAction> stepActions = stepActionService
				.queryList(new StepActionQO());
		model.addAttribute("stepActions", stepActions);
		Workflow workflow = workflowService.get(workflowID);
		model.addAttribute("workflow", workflow);
		RoleQO roleQO = new RoleQO();
		List<Role> roles = roleService.queryList(roleQO);
		model.addAttribute("roles", roles);
		WorkflowStepQO workflowStepQO = new WorkflowStepQO();
		workflowStepQO.setWorkflowID(workflowID);
		List<WorkflowStep> workflowSteps = workflowStepService
				.queryList(workflowStepQO);
		for (WorkflowStep workflowStep : workflowSteps) {
			if (workflowStep.getChooseExecutorType() != null
					&& workflowStep.getChooseExecutorType() == 4) {
				ExecutorDTO executorDTO = JSON.parseObject(
						workflowStep.getExecutor(), ExecutorDTO.class);
				workflowStep.setExecutor(executorDTO.getUserFlag().get(0)
						.toString());
			}
			WorkflowStepActionQO workflowStepActionQO = new WorkflowStepActionQO();
			workflowStepActionQO.setWorkflowStepID(workflowStep.getId());
			workflowStep.setWorkflowStepActions(workflowStepActionService
					.queryList(workflowStepActionQO));
		}
		model.addAttribute("workflowSteps", workflowSteps);
		return "/content/workflow/workflow_step_add.html";
	}

	/**
	 * @Title: workflowStepAdd
	 * @author guok
	 * @Description: 配置流程
	 * @Time 2015年7月30日下午1:32:18
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/addWorkflowStep")
	public String addWorkflowStep(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(value = "chooseExecutorType", required = false) int chooseExecutorType,
			@ModelAttribute ConfigWorkflowCommand command) {
		workflowService.config(command);
		return DwzJsonResultUtil.createJsonString("200", "添加成功", null, "steps");
	}
	
	/**
	 * @Title: workflowEdit
	 * @author guok
	 * @Description: 编辑
	 * @Time 2015年6月26日 08:52:15
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/workflowStepEdit")
	public String workflowStepEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ModifyWorkflowStepCommand command) {
		workflowStepService.update(command);
		return DwzJsonResultUtil.createJsonString("200", "修改成功", null, "steps");
	}

	/**
	 * 
	 * @Title: deleteworkflow
	 * @author guok
	 * @Description: 删除
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteWorkflowStep")
	public String deleteWorkflowStep(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "workflowStepID") String workflowStepID,
			@RequestParam(value = "workflowStepNO") String workflowStepNO) {
		if(StringUtils.equals(workflowStepNO,"1"))
			return DwzJsonResultUtil.createJsonString("300", "起草环节不能删除", null, "steps");
		workflowStepService.delStep(workflowStepID);
		String dd= DwzJsonResultUtil.createJsonString("200", "删除成功", null, "steps");
		System.out.println(dd);
		return dd;
	}


	/**
	 * 
	 * @方法功能说明：流程配置页面select框
	 * @修改者名字：cangs
	 * @修改时间：2015年8月12日下午12:49:27
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param chooseExecutorType
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/chooseExecutor")
	public String chooseExecutor(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "chooseExecutorType", required = false) int chooseExecutorType) {
		// 获取当前登陆用户，以用于查找用户所属公司
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		String json = "";
		switch (chooseExecutorType) {
		case 2:
			RoleQO roleQO = new RoleQO();
			roleQO.setCompanyID(user.getCompanyID());
			List<Role> roles = roleService.queryList(roleQO);
			for (Role role : roles) {
				role.setUserRoles(null);
				role.setRoleWorkflows(null);
				role.setCompany(null);
				role.setRoleMenus(null);
			}
			Role role = roleService.get(SysProperties.getInstance().get("travleAdminID"));
			role.setUserRoles(null);
			role.setRoleWorkflows(null);
			role.setCompany(null);
			role.setRoleMenus(null);
			roles.add(role);
			json = JSON.toJSONString(roles);
			break;
		case 4:
			List<Executor> executors = new ArrayList<WorkflowController.Executor>();
			Executor executor = new Executor();
			executor.setUserFlagkey(-99);
			executor.setUserFlagval("起草人");
			executors.add(executor);
			Executor executor2 = new Executor();
			executor2.setUserFlagkey(-1);
			executor2.setUserFlagval("上一环节处理人");
			executors.add(executor2);
			json = JSON.toJSONString(executors);
			break;
		}
		return json;

	}

	class Executor {
		private Integer userFlagkey;

		private String userFlagval;

		public Integer getUserFlagkey() {
			return userFlagkey;
		}

		public void setUserFlagkey(Integer userFlagkey) {
			this.userFlagkey = userFlagkey;
		}

		public String getUserFlagval() {
			return userFlagval;
		}

		public void setUserFlagval(String userFlagval) {
			this.userFlagval = userFlagval;
		}

	}

}

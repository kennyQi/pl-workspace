package zzpl.admin.controller.flight;

import java.util.ArrayList;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

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

import com.alibaba.fastjson.JSON;

import zzpl.admin.controller.BaseController;
import zzpl.app.service.local.jp.gn.FlightOrderService;
import zzpl.app.service.local.user.UserRoleService;
import zzpl.app.service.local.user.UserService;
import zzpl.app.service.local.workflow.TasklistService;
import zzpl.app.service.local.workflow.TasklistWaitService;
import zzpl.app.service.local.workflow.WorkflowInstanceOrderService;
import zzpl.app.service.local.workflow.WorkflowInstanceService;
import zzpl.app.service.local.workflow.WorkflowService;
import zzpl.app.service.local.workflow.WorkflowStepActionService;
import zzpl.app.service.local.workflow.WorkflowStepService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.user.User;
import zzpl.domain.model.workflow.Tasklist;
import zzpl.domain.model.workflow.TasklistWait;
import zzpl.domain.model.workflow.WorkflowInstance;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.jp.ApprovalFailedCommand;
import zzpl.pojo.command.jp.CancelGNTicketCommand;
import zzpl.pojo.command.jp.CancelManaulVoteTicketCommand;
import zzpl.pojo.command.jp.ManaulVoteTicketCommand;
import zzpl.pojo.command.workflow.AddCommentCommand;
import zzpl.pojo.command.workflow.CancelOrderCommentCommand;
import zzpl.pojo.command.workflow.SendCommand;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.dto.workflow.CommentDTO;
import zzpl.pojo.dto.workflow.QueryTasklistWaitDTO;
import zzpl.pojo.exception.workflow.WorkflowException;
import zzpl.pojo.qo.workflow.TasklistQO;
import zzpl.pojo.qo.workflow.TasklistWaitQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceQO;

@Controller
@RequestMapping(value = "/tasklistwait")
public class FlightWaitController extends BaseController {

	/**
	 * 流程service
	 */
	@Autowired
	private WorkflowService workflowService;
	/**
	 * 待办service
	 */
	@Autowired
	private TasklistWaitService tasklistWaitService;
	@Autowired
	private TasklistService tasklistService;
	/**
	 * 流程实例service
	 */
	@Autowired
	private WorkflowInstanceService workflowInstanceService;
	/**
	 * 流程环节与action关联表service
	 */
	@Autowired
	private WorkflowStepActionService workflowStepActionService;
	/**
	 * 流程环节service
	 */
	@Autowired
	private WorkflowStepService workflowStepService;
	@Autowired
	private WorkflowInstanceOrderService workflowInstanceOrderService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private FlightOrderService flightOrderService;
	/**
	 * 人员service
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * @Title: view 
	 * @author guok
	 * @时间  2015年7月8日 13:56:04
	 * @Description: 跳转订单待办列表页
	 * @param request
	 * @param model
	 * @param roleQO
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/view")
	public String view(HttpServletRequest request, Model model,
			@ModelAttribute TasklistWaitQO tasklistWaitQO,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "stepName", required = false) String stepName) {
		
		/*AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		if (pageNo == null)
			pageNo = 1;
		if (pageSize == null)
			pageSize = 20;
		tasklistWaitQO.setStepName(stepName);
		
		tasklistWaitQO.setCurrentUserID(authUser.getId());
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(tasklistWaitQO);
		pagination = tasklistWaitService.queryOrder(pagination);
		model.addAttribute("pagination", pagination);*/
		
		return "/content/workflow/examination_list.html";
	}
	
	/**
	 * @Title: tasklistWaitList 
	 * @author guok
	 * @Description: 列表展示
	 * @time 2015年7月10日 10:37:19
	 * @param request
	 * @param model
	 * @param roleQO
	 * @param pageNo
	 * @param pageSize
	 * @param roleName
	 * @return Pagination 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/tasklistWaitList")
	public Pagination tasklistWaitList(HttpServletRequest request, Model model,
			TasklistWaitQO tasklistWaitQO,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "stepName", required = false) String stepName) {
		
		HgLogger.getInstance().info("cs",	"【TasklistWaitController】【tasklistWaitList】");
		if (pageNo == null)
			pageNo = 1;
		if (pageSize == null)
			pageSize = 20;
		tasklistWaitQO.setStepName(stepName);
		//获取当前登陆用户
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		tasklistWaitQO.setCurrentUserID(authUser.getId());
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(tasklistWaitQO);
		pagination = tasklistWaitService.queryOrder(pagination);
		return pagination;
	}
	
	
	
	/**
	 * @throws WorkflowException 
	 * @Title: approval 
	 * @author guok
	 * @Description: 国内机票出票(自动出票)
	 * @Time 2015年7月10日 16:44:55
	 * @param request
	 * @param response
	 * @param model
	 * @param roleID
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/approvalGN")
	public String approval(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID,
			@RequestParam(value = "nextStepNO", required = false) Integer nextStepNO,
			@RequestParam(value = "nextUserIDs", required = false) String nextUserIDs) {
		HgLogger.getInstance().info("gk", "【TasklistWaitController】【approval】" + tasklistWaitID);
		//根据ID查询待办
		TasklistWait tasklistWait = tasklistWaitService.get(tasklistWaitID);
		if (tasklistWait!=null) {
			//查询流程实例
			WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
			workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
			WorkflowInstance workflowInstance = workflowInstanceService.queryUnique(workflowInstanceQO);
			WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
			workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstance.getId());
			WorkflowInstanceOrder workflowInstanceOrder = workflowInstanceOrderService.queryUnique(workflowInstanceOrderQO);
			FlightOrder flightOrder = flightOrderService.get(workflowInstanceOrder.getOrderID());
			if(Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_SELF&&flightOrder.getPayStatus()==FlightPayStatus.WAIT_USER_TO_PAY){
				return DwzJsonResultUtil.createJsonString("300", "等待用户付款",  DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, null);
			}
			SendCommand sendCommand=new SendCommand();
			sendCommand.setWorkflowID(workflowInstance.getWorkflowID());//流程ID
			sendCommand.setCurrentStepNO(tasklistWait.getStepNO());//当前环节
			sendCommand.setCurrentUserID(tasklistWait.getCurrentUserID());//当前环节处理人
			sendCommand.setNextStepNO(nextStepNO);//下一环节
			sendCommand.setWorkflowInstanceID(workflowInstance.getId());//流程实例ID
			
			String[] services = {"ApprovalGNFlightService"};//执行的操作
			try {
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【approval】【GNFlightException】"+JSON.toJSONString(sendCommand));
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【approval】【GNFlightException】"+JSON.toJSONString(services));
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【approval】【GNFlightException】"+JSON.toJSONString(sendCommand));
				workflowStepService.send(sendCommand,services, sendCommand);
				
			} catch (WorkflowException e) {
				HgLogger.getInstance().error("gk", "【TasklistWaitController】【approval】【WorkflowException】:" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString("300", e.getMessage(),DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("tasklistwaitMenuID"));
			}
			
		}
		return DwzJsonResultUtil.createJsonString("200", "出票中!",  DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("tasklistwaitMenuID"));
			
	}
	
	/**
	 * @Title: retirement 
	 * @author guok
	 * @Description: 国内机票退废票
	 * @Time 2015年7月15日 14:53:51
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/retirementGN")
	public String retirementGN(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID,
			@RequestParam(value = "nextStepNO") Integer nextStepNO,
			@RequestParam(value = "nextUserIDs") String nextUserIDs,
			@ModelAttribute CancelGNTicketCommand cancelGNTicketCommand) {
		HgLogger.getInstance().info("gk", "【TasklistWaitController】【retirement】" + tasklistWaitID);
		
		//根据ID查询待办
		TasklistWait tasklistWait = tasklistWaitService.get(tasklistWaitID);
		if (tasklistWait!=null) {
			//查询流程实例
			WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
			workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
			WorkflowInstance workflowInstance = workflowInstanceService.queryUnique(workflowInstanceQO);
			
			SendCommand sendCommand=new SendCommand();
			sendCommand.setWorkflowID(workflowInstance.getWorkflowID());//流程ID
			sendCommand.setCurrentStepNO(tasklistWait.getStepNO());//当前环节
			sendCommand.setCurrentUserID(tasklistWait.getCurrentUserID());//当前环节处理人
			sendCommand.setNextStepNO(nextStepNO);//下一环节
			sendCommand.setWorkflowInstanceID(workflowInstance.getId());//流程实例ID
			
			String[] services = {"CancelGNTicketService"};//执行的操作
			try {
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【approval】【sendCommand】"+JSON.toJSONString(sendCommand));
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【approval】【services】"+JSON.toJSONString(services));
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【approval】【refundTicketGNCommand】"+JSON.toJSONString(cancelGNTicketCommand));
				workflowStepService.send(sendCommand,services, cancelGNTicketCommand);
				
			} catch (WorkflowException e) {
				HgLogger.getInstance().error("gk", "【TasklistWaitController】【retirement】【WorkflowException】:" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
						null, "");
			}
		}
		return DwzJsonResultUtil.createJsonString("200", "退票中!", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("tasklistwaitMenuID"));
		
	}
	

	/**
	 * @throws WorkflowException 
	 * @Title: approval 
	 * @author guok
	 * @Description: 国际审批
	 * @Time 2015年7月24日 15:09:37
	 * @param request
	 * @param response
	 * @param model
	 * @param roleID
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/approvalGJ")
	public String approvalGJ(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID,
			@RequestParam(value = "nextStepNO") Integer nextStepNO,
			@RequestParam(value = "nextUserIDs") String nextUserIDs) {
		HgLogger.getInstance().info("gk", "【TasklistWaitGJController】【approval】" + tasklistWaitID);
		//根据ID查询待办
		TasklistWait tasklistWait = tasklistWaitService.get(tasklistWaitID);
		if (tasklistWait!=null) {
			//查询流程实例
			WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
			workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
			WorkflowInstance workflowInstance = workflowInstanceService.queryUnique(workflowInstanceQO);
			//查询流程环节
			
			SendCommand sendCommand=new SendCommand();
			sendCommand.setWorkflowID(workflowInstance.getWorkflowID());//流程ID
			sendCommand.setCurrentStepNO(tasklistWait.getStepNO());//当前环节
			sendCommand.setCurrentUserID(tasklistWait.getCurrentUserID());//当前环节处理人
			sendCommand.setNextStepNO(nextStepNO);//下一环节
			sendCommand.setWorkflowInstanceID(workflowInstance.getId());//流程实例ID
			
			String[] services = {"ApprovalGJFlightService"};//执行的操作
			try {
				HgLogger.getInstance().info("gk", "【TasklistWaitGJController】【approval】【GNFlightException】"+JSON.toJSONString(sendCommand));
				HgLogger.getInstance().info("gk", "【TasklistWaitGJController】【approval】【GNFlightException】"+JSON.toJSONString(services));
				HgLogger.getInstance().info("gk", "【TasklistWaitGJController】【approval】【GNFlightException】"+JSON.toJSONString(sendCommand));
				workflowStepService.send(sendCommand,services, sendCommand);
				
			} catch (WorkflowException e) {
				HgLogger.getInstance().error("gk", "【TasklistWaitGJController】【approval】【WorkflowException】:" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
						null, "");
			}
			
		}
		return DwzJsonResultUtil.createJsonString("200", "出票中!",  DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("tasklistwaitMenuID"));
			
	}
	
	/**
	 * @Title: retirement 
	 * @author guok
	 * @Description: 退票
	 * @Time 2015年7月24日 15:09:49
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/retirement")
	public String retirement(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID,
			@RequestParam(value = "nextStepNO") Integer nextStepNO,
			@RequestParam(value = "nextUserIDs") String nextUserIDs) {
		HgLogger.getInstance().info("gk", "【TasklistWaitGJController】【retirement】" + tasklistWaitID);
		CancelGNTicketCommand cancelGNTicketCommand = new CancelGNTicketCommand();
		//根据ID查询待办
		TasklistWait tasklistWait = tasklistWaitService.get(tasklistWaitID);
		if (tasklistWait!=null) {
			//查询流程实例
			WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
			workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
			WorkflowInstance workflowInstance = workflowInstanceService.queryUnique(workflowInstanceQO);
			
			SendCommand sendCommand=new SendCommand();
			sendCommand.setWorkflowID(workflowInstance.getWorkflowID());//流程ID
			sendCommand.setCurrentStepNO(tasklistWait.getStepNO());//当前环节
			sendCommand.setCurrentUserID(tasklistWait.getCurrentUserID());//当前环节处理人
			sendCommand.setNextStepNO(nextStepNO);//下一环节
			sendCommand.setWorkflowInstanceID(workflowInstance.getId());//流程实例ID
			
			String[] services = {"CancelGJTicketService"};//执行的操作
			try {
				HgLogger.getInstance().info("gk", "【TasklistWaitGJController】【approval】【sendCommand】"+JSON.toJSONString(sendCommand));
				HgLogger.getInstance().info("gk", "【TasklistWaitGJController】【approval】【services】"+JSON.toJSONString(services));
				HgLogger.getInstance().info("gk", "【TasklistWaitGJController】【approval】【refundTicketGNCommand】"+JSON.toJSONString(cancelGNTicketCommand));
				workflowStepService.send(sendCommand,services, cancelGNTicketCommand);
				
			} catch (WorkflowException e) {
				HgLogger.getInstance().error("gk", "【TasklistWaitGJController】【retirement】【WorkflowException】:" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
						null, "");
			}
		}
		return DwzJsonResultUtil.createJsonString("200", "已退票!", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("tasklistwaitMenuID"));
		
	}
	
	/**
	 * @throws WorkflowException 
	 * @Title: approval 
	 * @author guok
	 * @Description: 部门经理购票审批(同意/不同意)
	 * @Time 2015年7月10日 16:44:55
	 * @param request
	 * @param response
	 * @param model
	 * @param roleID
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/managerApproval")
	public String approval(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID,
			@ModelAttribute AddCommentCommand addCommentCommand) {
		try {
			HgLogger.getInstance().info("gk", "【TasklistWaitGJController】【departmentApproval】" + tasklistWaitID);
			//根据ID查询待办
			TasklistWait tasklistWait = tasklistWaitService.get(tasklistWaitID);
			if (tasklistWait!=null) {
				//查询流程实例
				WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
				workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
				WorkflowInstance workflowInstance = workflowInstanceService.queryUnique(workflowInstanceQO);
				//查询流程流转
				TasklistQO tasklistQO = new TasklistQO();
				tasklistQO.setWorkflowInstanceID(workflowInstance.getId());
				tasklistQO.setStepNO(tasklistWait.getStepNO());
				Tasklist tasklist = tasklistService.queryUnique(tasklistQO);
				
				SendCommand sendCommand=new SendCommand();
				sendCommand.setWorkflowID(workflowInstance.getWorkflowID());//流程ID
				sendCommand.setCurrentStepNO(tasklistWait.getStepNO());//当前环节
				sendCommand.setCurrentUserID(tasklistWait.getCurrentUserID());//当前环节处理人
				sendCommand.setWorkflowInstanceID(workflowInstance.getId());//流程实例ID
				sendCommand.setNextStepNO(addCommentCommand.getNextStepNO());
				
				
				if (StringUtils.equals(addCommentCommand.getOpinion(), "Y")) {
					if (StringUtils.isNotBlank(addCommentCommand.getNextUserIDs())) {
						List<String> nextUserIDs = new ArrayList<String>();
						nextUserIDs.add(addCommentCommand.getNextUserIDs());
						sendCommand.setNextUserIDs(nextUserIDs);
					}
					CommentDTO commentDTO = new CommentDTO();
					commentDTO.setAccount("同意");
					addCommentCommand.setCommentJSON(JSON.toJSONString(commentDTO));
					addCommentCommand.setCommentType(CommentDTO.class.getName());
				}else {
					CommentDTO commentDTO = new CommentDTO();
					commentDTO.setAccount(addCommentCommand.getComment());
					addCommentCommand.setCommentJSON(JSON.toJSONString(commentDTO));
					addCommentCommand.setCommentType(CommentDTO.class.getName());
				}
				
				String[] services = {"DepartmentApprovalService"};//执行的操作
				addCommentCommand.setCurrentUserID(tasklistWait.getCurrentUserID());
				addCommentCommand.setCurrentUserName(tasklistWait.getCurrentUserName());
				addCommentCommand.setStepNO(tasklistWait.getStepNO());
				addCommentCommand.setStepName(tasklistWait.getStepName());
				addCommentCommand.setTasklistID(tasklist.getId());
				
				
				HgLogger.getInstance().info("gk", "【DepartmentApprovalController】【departmentApproval】【sendCommand】"+JSON.toJSONString(sendCommand));
				HgLogger.getInstance().info("gk", "【DepartmentApprovalController】【departmentApproval】【services】"+JSON.toJSONString(services));
				HgLogger.getInstance().info("gk", "【DepartmentApprovalController】【departmentApproval】【addCommentCommand】"+JSON.toJSONString(addCommentCommand));
				workflowStepService.send(sendCommand,services, addCommentCommand);
				//发送信息
				User user = new User();
				user = userService.get(addCommentCommand.getNextUserIDs());
				if(user!=null&&user.getLinkMobile()!=null){
					workflowStepService.smsUser(user.getId());
				}
			}
			return DwzJsonResultUtil.createJsonString("200", "已确认，交由差旅管理员审批!",  DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("tasklistwaitMenuID"));
		} catch (WorkflowException e) {
			HgLogger.getInstance().error("gk", "【DepartmentApprovalController】【departmentApproval】【WorkflowException】:" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
					null, "");
		}
			
	}

	/**
	 * @throws WorkflowException 
	 * @Title: approval 
	 * @author guok
	 * @Description: 部门经理退票审批(同意/不同意)
	 * @Time 2015年7月10日 16:44:55
	 * @param request
	 * @param response
	 * @param model
	 * @param roleID
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/managerCancel")
	public String managerCancel(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID,
			@ModelAttribute CancelOrderCommentCommand cancelOrderCommentCommand) {
		HgLogger.getInstance().info("gk", "【TasklistWaitGJController】【departmentApproval】" + cancelOrderCommentCommand.getTasklistWaitID());
		//根据ID查询待办
		TasklistWait tasklistWait = tasklistWaitService.get(cancelOrderCommentCommand.getTasklistWaitID());
		if (tasklistWait!=null) {
			//查询流程实例
			WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
			workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
			WorkflowInstance workflowInstance = workflowInstanceService.queryUnique(workflowInstanceQO);
			//查询流程流转
			TasklistQO tasklistQO = new TasklistQO();
			tasklistQO.setWorkflowInstanceID(workflowInstance.getId());
			tasklistQO.setStepNO(tasklistWait.getStepNO());
			Tasklist tasklist = tasklistService.queryUnique(tasklistQO);
			
			SendCommand sendCommand=new SendCommand();
			sendCommand.setWorkflowID(workflowInstance.getWorkflowID());//流程ID
			sendCommand.setCurrentStepNO(tasklistWait.getStepNO());//当前环节
			sendCommand.setCurrentUserID(tasklistWait.getCurrentUserID());//当前环节处理人
			sendCommand.setNextStepNO(cancelOrderCommentCommand.getNextStepNO());//下一环节
			sendCommand.setWorkflowInstanceID(workflowInstance.getId());//流程实例ID
			if (StringUtils.equals(cancelOrderCommentCommand.getOpinion(), "Y")) {
				List<String> nextUserIDs=new ArrayList<String>();//下一环节处理人，如果是会签则为多人
				nextUserIDs.add(cancelOrderCommentCommand.getNextUserIDs());
				sendCommand.setNextUserIDs(nextUserIDs);
				CommentDTO commentDTO = new CommentDTO();
				commentDTO.setAccount("同意");
				cancelOrderCommentCommand.setCommentJSON(JSON.toJSONString(commentDTO));
				cancelOrderCommentCommand.setCommentType(CommentDTO.class.getSimpleName());
			}else {
				CommentDTO commentDTO = new CommentDTO();
				commentDTO.setAccount(cancelOrderCommentCommand.getComment());
				cancelOrderCommentCommand.setCommentJSON(JSON.toJSONString(commentDTO));
				cancelOrderCommentCommand.setCommentType(CommentDTO.class.getSimpleName());
			}
			String[] services = {"DepartmentCancelService"};//执行的操作
			cancelOrderCommentCommand.setCurrentUserID(tasklistWait.getCurrentUserID());
			cancelOrderCommentCommand.setCurrentUserName(tasklistWait.getCurrentUserName());
			cancelOrderCommentCommand.setStepNO(tasklistWait.getStepNO());
			cancelOrderCommentCommand.setStepName(tasklistWait.getStepName());
			cancelOrderCommentCommand.setTasklistID(tasklist.getId());
			try {
				HgLogger.getInstance().info("gk", "【DepartmentApprovalController】【departmentApproval】【sendCommand】"+JSON.toJSONString(sendCommand));
				HgLogger.getInstance().info("gk", "【DepartmentApprovalController】【departmentApproval】【services】"+JSON.toJSONString(services));
				HgLogger.getInstance().info("gk", "【DepartmentApprovalController】【departmentApproval】【cancelOrderCommentCommand】"+JSON.toJSONString(cancelOrderCommentCommand));
				workflowStepService.send(sendCommand,services, cancelOrderCommentCommand);
				
				workflowStepService.smsUser(cancelOrderCommentCommand.getNextUserIDs());
			} catch (WorkflowException e) {
				HgLogger.getInstance().error("gk", "【DepartmentApprovalController】【departmentApproval】【WorkflowException】:" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
						null, "");
			}
			
		}
		return DwzJsonResultUtil.createJsonString("200", "已确认，交由差旅管理员审批!",  DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("tasklistwaitMenuID"));
			
	}
	
	/**
	 * @Title: detail 
	 * @author guok
	 * @Description: 跳转到部门经理审批不通过页面
	 * @time 2015年8月12日 16:32:14
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/managerApproval/else")
	public String managerNotPass(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID) {
		QueryTasklistWaitDTO taskListWaitDto = tasklistWaitService.getByID(tasklistWaitID);
		model.addAttribute("taskListWaitDto", taskListWaitDto);
		
		//获取当前登陆用户
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		model.addAttribute("user", user);
		
		return "/content/workflow/manager_not_pass.html";
	}
	
	/**
	 * @Title: detail 
	 * @author guok
	 * @Description: 跳转到差旅管理员手动出票页面
	 * @time 2015年8月12日 16:32:28
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/approvalGN/else")
	public String manaulVote(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID) {
		QueryTasklistWaitDTO taskListWaitDto = tasklistWaitService.getByID(tasklistWaitID);
		model.addAttribute("taskListWaitDto", taskListWaitDto);
		
		//获取当前登陆用户
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		model.addAttribute("user", user);
		
		return "/content/workflow/manaul_vote.html";
	}
	
	/**
	 * @Title: manaulVote 
	 * @author guok
	 * @Description: 国内机票出票(手动出票)
	 * @Time 2015年8月14日下午2:52:16
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @param manaulVoteTicketCommand
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/manaulVote")
	public String manaulVote(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID,
			@ModelAttribute ManaulVoteTicketCommand manaulVoteTicketCommand) {
		HgLogger.getInstance().info("gk", "【TasklistWaitController】【manaulVote】" + tasklistWaitID);
		//根据ID查询待办
		TasklistWait tasklistWait = tasklistWaitService.get(manaulVoteTicketCommand.getTasklistWaitID());
		if (tasklistWait!=null) {
			//查询流程实例
			WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
			workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
			WorkflowInstance workflowInstance = workflowInstanceService.queryUnique(workflowInstanceQO);
			WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
			workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstance.getId());
			WorkflowInstanceOrder workflowInstanceOrder = workflowInstanceOrderService.queryUnique(workflowInstanceOrderQO);
			FlightOrder flightOrder = flightOrderService.get(workflowInstanceOrder.getOrderID());
			if(Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_SELF&&flightOrder.getPayStatus()==FlightPayStatus.WAIT_USER_TO_PAY){
				return DwzJsonResultUtil.createJsonString("300", "等待用户付款",  DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, null);
			}
			//查询流程流转
			TasklistQO tasklistQO = new TasklistQO();
			tasklistQO.setWorkflowInstanceID(workflowInstance.getId());
			tasklistQO.setStepNO(tasklistWait.getStepNO());
			Tasklist tasklist = tasklistService.queryUnique(tasklistQO);
			manaulVoteTicketCommand.setTasklistID(tasklist.getId());
			manaulVoteTicketCommand.setStepName(tasklistWait.getStepName());
			manaulVoteTicketCommand.setStepNO(tasklistWait.getStepNO());//当前环节
			manaulVoteTicketCommand.setCurrentUserID(tasklistWait.getCurrentUserID());//当前环节处理人
			manaulVoteTicketCommand.setCurrentUserName(tasklistWait.getCurrentUserName());
			
			SendCommand sendCommand=new SendCommand();
			sendCommand.setWorkflowID(workflowInstance.getWorkflowID());//流程ID
			sendCommand.setCurrentStepNO(tasklistWait.getStepNO());//当前环节
			sendCommand.setCurrentUserID(tasklistWait.getCurrentUserID());//当前环节处理人
			sendCommand.setNextStepNO(99);//下一环节
			sendCommand.setWorkflowInstanceID(workflowInstance.getId());//流程实例ID
			
			String[] services = {"ManaulGNFlightService"};//执行的操作
			try {
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【manaulVote】【GNFlightException】"+JSON.toJSONString(sendCommand));
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【manaulVote】【GNFlightException】"+JSON.toJSONString(services));
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【manaulVote】【GNFlightException】"+JSON.toJSONString(manaulVoteTicketCommand));
				workflowStepService.send(sendCommand,services, manaulVoteTicketCommand);
				
			} catch (WorkflowException e) {
				HgLogger.getInstance().error("gk", "【TasklistWaitController】【manaulVote】【WorkflowException】:" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
						null, "");
			}
			
		}
		return DwzJsonResultUtil.createJsonString("200", "已确认!",  DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("tasklistwaitMenuID"));
			
	}
	
	/**
	 * @Title: manaulVote 
	 * @author guok
	 * @Description: 取消手动出票
	 * @Time 2015年8月14日下午2:52:16
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @param manaulVoteTicketCommand
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/cancelManaulVote")
	public String cancelManaulVote(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID,
			@ModelAttribute CancelManaulVoteTicketCommand cancelManaulVoteTicketCommand) {
		HgLogger.getInstance().info("gk", "【TasklistWaitController】【cancelManaulVote】" + tasklistWaitID);
		//根据ID查询待办
		TasklistWait tasklistWait = tasklistWaitService.get(tasklistWaitID);
		if (tasklistWait!=null) {
			//查询流程实例
			WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
			workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
			WorkflowInstance workflowInstance = workflowInstanceService.queryUnique(workflowInstanceQO);
			//查询流程流转
			TasklistQO tasklistQO = new TasklistQO();
			tasklistQO.setWorkflowInstanceID(workflowInstance.getId());
			tasklistQO.setStepNO(tasklistWait.getStepNO());
			Tasklist tasklist = tasklistService.queryUnique(tasklistQO);
			cancelManaulVoteTicketCommand.setTasklistID(tasklist.getId());
			cancelManaulVoteTicketCommand.setStepName(tasklistWait.getStepName());
			cancelManaulVoteTicketCommand.setStepNO(tasklistWait.getStepNO());//当前环节
			cancelManaulVoteTicketCommand.setCurrentUserID(tasklistWait.getCurrentUserID());//当前环节处理人
			cancelManaulVoteTicketCommand.setCurrentUserName(tasklistWait.getCurrentUserName());
			
			SendCommand sendCommand=new SendCommand();
			sendCommand.setWorkflowID(workflowInstance.getWorkflowID());//流程ID
			sendCommand.setCurrentStepNO(tasklistWait.getStepNO());//当前环节
			sendCommand.setCurrentUserID(tasklistWait.getCurrentUserID());//当前环节处理人
			sendCommand.setNextStepNO(99);//下一环节
			sendCommand.setWorkflowInstanceID(workflowInstance.getId());//流程实例ID
			
			String[] services = {"CancelManaulGNFlightService"};//执行的操作
			try {
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【cancelManaulVote】【sendCommand】"+JSON.toJSONString(sendCommand));
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【cancelManaulVote】【services】"+JSON.toJSONString(services));
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【cancelManaulVote】【cancelManaulVoteTicketCommand】"+JSON.toJSONString(cancelManaulVoteTicketCommand));
				workflowStepService.send(sendCommand,services, cancelManaulVoteTicketCommand);
				
			} catch (WorkflowException e) {
				HgLogger.getInstance().error("gk", "【TasklistWaitController】【cancelManaulVote】【WorkflowException】:" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
						null, "");
			}
			
		}
		return DwzJsonResultUtil.createJsonString("200", "已确认!",  DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("tasklistwaitMenuID"));
			
	}

	/**
	 * @Title: detail 
	 * @author guok
	 * @Description: 跳转到部门经理审批不通过页面
	 * @time 2015年8月12日 16:32:14
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/managerCancel/else")
	public String managerCancel(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID) {
		HgLogger.getInstance().info("gk", "【TasklistWaitController】【managerCancel】" + tasklistWaitID);
		QueryTasklistWaitDTO taskListWaitDto = tasklistWaitService.getByID(tasklistWaitID);
		model.addAttribute("taskListWaitDto", taskListWaitDto);
		
		//获取当前登陆用户
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		model.addAttribute("user", user);
		
		return "/content/workflow/manager_not_pass.html";
	}
	
	/**
	 * @throws WorkflowException 
	 * @Title: approval 
	 * @author guok
	 * @Description: 国内机票出票(出票失败)
	 * @Time 2015年7月10日 16:44:55
	 * @param request
	 * @param response
	 * @param model
	 * @param roleID
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/approvalGN/failed")
	public String approvalFailed(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID,
			@ModelAttribute ApprovalFailedCommand approvalFailedCommand) {
		HgLogger.getInstance().info("gk", "【TasklistWaitController】【approvalFailed】" + tasklistWaitID);
		//根据ID查询待办
		TasklistWait tasklistWait = tasklistWaitService.get(approvalFailedCommand.getTasklistWaitID());
		if (tasklistWait!=null) {
			//查询流程实例
			WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
			workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
			WorkflowInstance workflowInstance = workflowInstanceService.queryUnique(workflowInstanceQO);
			WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
			
			workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstance.getId());
			WorkflowInstanceOrder workflowInstanceOrder = workflowInstanceOrderService.queryUnique(workflowInstanceOrderQO);
			FlightOrder flightOrder = flightOrderService.get(workflowInstanceOrder.getOrderID());
			if(Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_SELF&&flightOrder.getPayStatus()==FlightPayStatus.WAIT_USER_TO_PAY){
				return DwzJsonResultUtil.createJsonString("300", "等待用户付款",  DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, null);
			}
			SendCommand sendCommand=new SendCommand();
			sendCommand.setWorkflowID(workflowInstance.getWorkflowID());//流程ID
			sendCommand.setCurrentStepNO(tasklistWait.getStepNO());//当前环节
			sendCommand.setCurrentUserID(tasklistWait.getCurrentUserID());//当前环节处理人
//			sendCommand.setNextStepNO(nextStepNO);//下一环节
			sendCommand.setWorkflowInstanceID(workflowInstance.getId());//流程实例ID
			
			String[] services = {"ApprovalFailedGNFlightService"};//执行的操作
			try {
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【approvalFailed】【GNFlightException】"+JSON.toJSONString(sendCommand));
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【approvalFailed】【GNFlightException】"+JSON.toJSONString(services));
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【approvalFailed】【GNFlightException】"+JSON.toJSONString(sendCommand));
				workflowStepService.send(sendCommand,services, sendCommand);
				
			} catch (WorkflowException e) {
				HgLogger.getInstance().error("gk", "【TasklistWaitController】【approvalFailed】【WorkflowException】:" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
						null, "");
			}
			
		}
		return DwzJsonResultUtil.createJsonString("200", "流程办结!",  DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("tasklistwaitMenuID"));
			
	}
	
	/**
	 * @Title: taskProcessVoid 
	 * @author guok
	 * @Description: 订单流程作废
	 * @Time 2015年11月9日上午10:32:28
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/taskProcess/void")
	public String taskProcessVoid(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID) {
		HgLogger.getInstance().info("gk", "【TasklistWaitController】【taskProcessVoid】" + tasklistWaitID);
		TasklistWait tasklistWait = tasklistWaitService.get(tasklistWaitID);
		if (tasklistWait!=null) {
			//查询流程实例
			WorkflowInstanceQO workflowInstanceQO = new WorkflowInstanceQO();
			workflowInstanceQO.setId(tasklistWait.getWorkflowInstanceID());
			WorkflowInstance workflowInstance = workflowInstanceService.queryUnique(workflowInstanceQO);
			
			SendCommand sendCommand=new SendCommand();
			sendCommand.setWorkflowID(workflowInstance.getWorkflowID());//流程ID
			sendCommand.setCurrentStepNO(tasklistWait.getStepNO());//当前环节
			sendCommand.setCurrentUserID(tasklistWait.getCurrentUserID());//当前环节处理人
//			sendCommand.setNextStepNO(nextStepNO);//下一环节
			sendCommand.setWorkflowInstanceID(workflowInstance.getId());//流程实例ID
			
			String[] services = {"TaskProcessVoidGNFlightService"};//执行的操作
			try {
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【taskProcessVoid】【GNFlightException】"+JSON.toJSONString(sendCommand));
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【taskProcessVoid】【GNFlightException】"+JSON.toJSONString(services));
				HgLogger.getInstance().info("gk", "【TasklistWaitController】【taskProcessVoid】【GNFlightException】"+JSON.toJSONString(sendCommand));
				workflowStepService.send(sendCommand,services, sendCommand);
				
			} catch (WorkflowException e) {
				HgLogger.getInstance().error("gk", "【TasklistWaitController】【taskProcessVoid】【WorkflowException】:" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
						null, "");
			}
			
		}
		return DwzJsonResultUtil.createJsonString("200", "流程办结!",  DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("tasklistwaitMenuID"));
	}
}

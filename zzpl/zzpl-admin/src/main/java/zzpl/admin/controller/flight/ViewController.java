package zzpl.admin.controller.flight;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import zzpl.app.service.local.jp.PassengerService;
import zzpl.app.service.local.jp.gn.CLGLYFlightService;
import zzpl.app.service.local.jp.gn.FlightOrderService;
import zzpl.app.service.local.jp.gn.QueryPolicyService;
import zzpl.app.service.local.user.CostCenterService;
import zzpl.app.service.local.user.UserRoleService;
import zzpl.app.service.local.user.UserService;
import zzpl.app.service.local.workflow.TasklistService;
import zzpl.app.service.local.workflow.TasklistWaitService;
import zzpl.app.service.local.workflow.WorkflowInstanceService;
import zzpl.app.service.local.workflow.WorkflowService;
import zzpl.app.service.local.workflow.WorkflowStepService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.CostCenter;
import zzpl.domain.model.user.User;
import zzpl.domain.model.user.UserRole;
import zzpl.domain.model.workflow.Tasklist;
import zzpl.domain.model.workflow.TasklistWait;
import zzpl.domain.model.workflow.WorkflowInstance;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.workflow.CancelOrderCommentCommand;
import zzpl.pojo.command.workflow.ChooseExecutorCommand;
import zzpl.pojo.command.workflow.SendCommand;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.workflow.CommentDTO;
import zzpl.pojo.dto.workflow.ExecutorDTO;
import zzpl.pojo.dto.workflow.QueryTasklistWaitDTO;
import zzpl.pojo.exception.workflow.WorkflowException;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.user.UserRoleQO;
import zzpl.pojo.qo.workflow.TasklistQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceQO;
import zzpl.pojo.qo.workflow.WorkflowQO;

@Controller
@RequestMapping(value = "/view")
public class ViewController extends BaseController {

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
	 * 流程环节service
	 */
	@Autowired
	private WorkflowStepService workflowStepService;
	@Autowired
	private UserRoleService userRoleService;
	/**
	 * 人员service
	 */
	@Autowired
	private UserService userService;
	/**
	 * 国内订单service
	 */
	@Autowired
	private FlightOrderService flightOrderService;
	/**
	 * 成本中心
	 */
	@Autowired
	private CostCenterService costCenterService;
	
	@Autowired
	private PassengerService passengerService;
	
	@Autowired
	private QueryPolicyService queryPolicyService;
	
	@Autowired
	private CLGLYFlightService clglyFlightService;
	
	/**
	 * @Title: detail 
	 * @author guok
	 * @Description: 跳转到部门经理购票详情页
	 * @time 2015年8月12日 16:32:04
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/managerApproval")
	public String detail(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID) {
		HgLogger.getInstance().info("gk", "【OrderController】【detail】【tasklistWaitID】"+JSON.toJSONString(tasklistWaitID));
		
		QueryTasklistWaitDTO taskListWaitDto = tasklistWaitService.getByID(tasklistWaitID);
		model.addAttribute("taskListWaitDto", taskListWaitDto);
		HgLogger.getInstance().info("gk", "【OrderController】【detail】【taskListWaitDto】"+JSON.toJSONString(taskListWaitDto));
		//获取当前登陆用户
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		model.addAttribute("user", user);
		
		return "/content/workflow/approval_order_detail.html";
	}
	
	/**
	 * @Title: detail 
	 * @author guok
	 * @Description: 跳转到差旅管理员订票详情页
	 * @time 2015年8月12日 16:31:58
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/approvalGN")
	public String approvalGN(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID) {
		HgLogger.getInstance().info("gk", "【OrderController】【approvalGN】【tasklistWaitID】"+JSON.toJSONString(tasklistWaitID));
		
		QueryTasklistWaitDTO taskListWaitDto = tasklistWaitService.getByID(tasklistWaitID);
		queryPolicyService.printTicket(taskListWaitDto.getWorkflowInstanceID());
		taskListWaitDto = tasklistWaitService.getByID(tasklistWaitID);
		model.addAttribute("taskListWaitDto", taskListWaitDto);
		HgLogger.getInstance().info("gk", "【OrderController】【approvalGN】【taskListWaitDto】"+JSON.toJSONString(taskListWaitDto));
		//获取当前登陆用户
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		model.addAttribute("user", user);
		
		return "/content/workflow/approval_order_detail.html";
	}
	
	/**
	 * @Title: chooseExecutor 
	 * @author guok
	 * @Description: 选下一环节处理人
	 * @Time 2015年8月12日下午2:00:33
	 * @param request
	 * @param response
	 * @param model
	 * @param chooseExecutorCommand
	 * @return
	 * @throws IOException String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/chooseExecutor")
	public String chooseExecutor(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute ChooseExecutorCommand chooseExecutorCommand) throws IOException {
		
		HgLogger.getInstance().info("gk", "【OrderController】【chooseExecutor】" + JSON.toJSONString(chooseExecutorCommand));
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		chooseExecutorCommand.setUserID(authUser.getId());
		chooseExecutorCommand.setCompanyID(user.getCompanyID());
		List<ExecutorDTO> executorDTOs =tasklistWaitService.chooseExecutor(chooseExecutorCommand);
		
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(JSON.toJSONString(executorDTOs)); 
		return null;
	}
	
	/**
	 * @Title: detail 
	 * @author guok
	 * @Description: 跳转到部门经理退票详情页
	 * @time 2015年8月12日 16:31:58
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/managerCancel")
	public String managerCancel(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID) {
		HgLogger.getInstance().info("gk", "【OrderController】【managerCancel】【tasklistWaitID】"+JSON.toJSONString(tasklistWaitID));
		
		QueryTasklistWaitDTO taskListWaitDto = tasklistWaitService.getByID(tasklistWaitID);
		model.addAttribute("taskListWaitDto", taskListWaitDto);
		HgLogger.getInstance().info("gk", "【OrderController】【managerCancel】【managerCancel】"+JSON.toJSONString(taskListWaitDto));
		//获取当前登陆用户
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		model.addAttribute("user", user);
		
		return "/content/workflow/cancel_order_detail.html";
	}
	/**
	 * @Title: detail 
	 * @author guok
	 * @Description: 跳转到退票详情页
	 * @time 2015年8月12日 16:31:58
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/retirementGN")
	public String retirementGN(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID) {
		HgLogger.getInstance().info("gk", "【OrderController】【retirement】【tasklistWaitID】"+JSON.toJSONString(tasklistWaitID));
		
		QueryTasklistWaitDTO taskListWaitDto = tasklistWaitService.getByID(tasklistWaitID);
		model.addAttribute("taskListWaitDto", taskListWaitDto);
		HgLogger.getInstance().info("gk", "【OrderController】【retirementGN】【taskListWaitDto】"+JSON.toJSONString(taskListWaitDto));
		//获取当前登陆用户
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		model.addAttribute("user", user);
		String travleID = SysProperties.getInstance().get("travleAdminID");
		UserRoleQO userRoleQO = new UserRoleQO();
		userRoleQO.setUserID(user.getId());
		List<UserRole> userRoles = userRoleService.queryList(userRoleQO);
		String roleID = "";
		for (UserRole userRole : userRoles) {
			roleID += userRole.getRole().getId()+",";
		}
		if (roleID.contains(travleID)) {
			model.addAttribute("role", "success");
		}else {
			model.addAttribute("role", "fail");
		}
		
		return "/content/workflow/cancel_order_detail.html";
	}
	
	/**
	 * @Title: approvalGJ 
	 * @author guok
	 * @Description: 跳转差旅国际订票详情页
	 * @Time 2015年8月12日下午4:32:42
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/approvalGJ")
	public String approvalGJ(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID) {
		HgLogger.getInstance().info("gk", "【OrderController】【approvalGJ】【tasklistWaitID】"+JSON.toJSONString(tasklistWaitID));
		
		QueryTasklistWaitDTO taskListWaitDto = tasklistWaitService.getByID(tasklistWaitID);
		model.addAttribute("taskListWaitDto", taskListWaitDto);
		HgLogger.getInstance().info("gk", "【OrderController】【approvalGJ】【taskListWaitDto】"+JSON.toJSONString(taskListWaitDto));
		//获取当前登陆用户
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		model.addAttribute("user", user);
		
		return "/content/workflow/approval_order_detail.html";
	}
	
	/**
	 * @throws WorkflowException 
	 * @Title: approval 
	 * @author guok
	 * @Description: 部门经理退票审批
	 * @Time 2015年7月10日 16:44:55
	 * @param request
	 * @param response
	 * @param model
	 * @param roleID
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/manaul/managerCancel")
	public String managerCancel(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID,
			@ModelAttribute CancelOrderCommentCommand cancelOrderCommentCommand) {
		HgLogger.getInstance().info("gk", "【OrderController】【managerCancel】" + tasklistWaitID);
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
				HgLogger.getInstance().info("gk", "【OrderController】【managerCancel】【sendCommand】"+JSON.toJSONString(sendCommand));
				HgLogger.getInstance().info("gk", "【OrderController】【managerCancel】【services】"+JSON.toJSONString(services));
				HgLogger.getInstance().info("gk", "【OrderController】【managerCancel】【cancelOrderCommentCommand】"+JSON.toJSONString(cancelOrderCommentCommand));
				workflowStepService.send(sendCommand,services, cancelOrderCommentCommand);
				
				workflowStepService.smsUser(cancelOrderCommentCommand.getNextUserIDs());
			} catch (WorkflowException e) {
				HgLogger.getInstance().error("gk", "【OrderController】【managerCancel】【WorkflowException】:" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createJsonString("300", e.getMessage(),
						null, "");
			}
			
		}
		return DwzJsonResultUtil.createJsonString("200", "已确认，交由差旅管理员审批!",  DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, SysProperties.getInstance().get("tasklistwaitMenuID"));
			
	}
	
	/**
	 * @Title: retirement 
	 * @author guok
	 * @Description: 跳转取消手动出票页
	 * @Time 2015年8月12日下午4:32:42
	 * @param request
	 * @param response
	 * @param model
	 * @param tasklistWaitID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/manaul/retirementGN")
	public String retirement(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "tasklistWaitID") String tasklistWaitID) {
		HgLogger.getInstance().info("gk", "【OrderController】【retirement】【tasklistWaitID】"+JSON.toJSONString(tasklistWaitID));
		QueryTasklistWaitDTO taskListWaitDto = tasklistWaitService.getByID(tasklistWaitID);
		model.addAttribute("taskListWaitDto", taskListWaitDto);
		HgLogger.getInstance().info("gk", "【OrderController】【retirement】【taskListWaitDto】"+JSON.toJSONString(taskListWaitDto));
		//获取当前登陆用户
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		model.addAttribute("user", user);
		
		return "/content/workflow/cancel_manaul_vote.html";
	}
	
	/**
	 * @Title: orderDetail 
	 * @author guok
	 * @Description: 结算中心跳转订单详情页
	 * @Time 2015年8月25日上午9:05:47
	 * @param request
	 * @param response
	 * @param model
	 * @param orderID
	 * @param orderType
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value="/orderDetail")
	public String orderDetail(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "orderID") String orderID,
			@RequestParam(value = "orderType") String orderType) {
		HgLogger.getInstance().info("gk", "【OrderController】【orderDetail】【orderID】"+JSON.toJSONString(orderID));
		if (StringUtils.equals(orderType, WorkflowInstanceOrder.GN_FLIGHT_ORDER)) {
			AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
			User user=userService.get(authUser.getId());
			String travleID = SysProperties.getInstance().get("travleAdminID");
			UserRoleQO userRoleQO = new UserRoleQO();
			userRoleQO.setUserID(user.getId());
			List<UserRole> userRoles = userRoleService.queryList(userRoleQO);
			String roleID = "";
			for (UserRole userRole : userRoles) {
				roleID += userRole.getRole().getId()+",";
			}
			if (roleID.contains(travleID)) {
				model.addAttribute("role", "success");
			}else {
				model.addAttribute("role", "fail");
			}
			//订单
			FlightOrder flightOrder = flightOrderService.get(orderID);
			String statusMSG = "";
			//订单状态
			if (flightOrder.getStatus() == FlightOrderStatus.APPROVAL_SUCCESS) {
				statusMSG = FlightOrderStatus.APPROVAL_SUCCESS_MSG;
			} else if (flightOrder.getStatus() == FlightOrderStatus.PRINT_TICKET_SUCCESS){
				statusMSG = FlightOrderStatus.PRINT_TICKET_SUCCESS_MSG;
			}else if (flightOrder.getStatus() == FlightOrderStatus.PRINT_TICKET_FAILED){
				statusMSG = FlightOrderStatus.PRINT_TICKET_FAILED_MSG;
			}else if (flightOrder.getStatus() == FlightOrderStatus.CANCEL_APPROVAL_SUCCESS){
				statusMSG = FlightOrderStatus.CANCEL_APPROVAL_SUCCESS_MSG;
			}else if (flightOrder.getStatus() == FlightOrderStatus.CANCEL_TICKET_SUCCESS){
				statusMSG = FlightOrderStatus.CANCEL_TICKET_SUCCESS_MSG;
			}else if (flightOrder.getStatus() == FlightOrderStatus.CANCEL_TICKET_FAILED){
				statusMSG = FlightOrderStatus.CANCEL_TICKET_FAILED_MSG;
			}else if(flightOrder.getStatus()==FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS){
				statusMSG = FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS_MSG;
			}else if(flightOrder.getStatus()==FlightOrderStatus.CONFIRM_ORDER_SUCCESS){
				statusMSG = FlightOrderStatus.CONFIRM_ORDER_SUCCESS_MSG;
			}else if(flightOrder.getStatus()==FlightOrderStatus.CONFIRM_ORDER_FAILED){
				statusMSG = FlightOrderStatus.CONFIRM_ORDER_FAILED_MSG;
			}else if(flightOrder.getStatus()==FlightOrderStatus.ORDER_PROCESS_VOID){
				statusMSG = FlightOrderStatus.ORDER_PROCESS_VOID_MSG;
			}else if(flightOrder.getStatus()==FlightOrderStatus.CONFIRM_CANCEL_ORDER_FAILED){
				statusMSG = FlightOrderStatus.CONFIRM_CANCEL_ORDER_FAILED_MSG;
			}else if(flightOrder.getStatus()==FlightOrderStatus.REFUSE_TICKET_SUCCESS){
				statusMSG = FlightOrderStatus.REFUSE_TICKET_SUCCESS_MSG;
			}else {
				statusMSG = "订单异常";
			}
			model.addAttribute("statusMSG", statusMSG);
			PassengerQO passengerQO = new PassengerQO();
			passengerQO.setFlightOrderID(flightOrder.getId());
			List<Passenger> passengers = passengerService.queryList(passengerQO);
			model.addAttribute("flightOrder", flightOrder);
			model.addAttribute("passengers", passengers);
			//起草人
			User user1 = userService.get(flightOrder.getUserID());
			model.addAttribute("user", user1);
			//成本中心
			CostCenter costCenter = costCenterService.get(flightOrder.getCostCenterID());
			model.addAttribute("costCenter", costCenter);
		}else {
			//国际订单查询
		}
		
		return "/content/user/order_detail.html";
	}
	
	/**
	 * 
	 * @Description: 跳转订单列表页
	 * @Title: view 
	 * @author guok
	 * @Time 2015年8月26日下午4:10:58
	 * @param request
	 * @param model
	 * @param workflowQO
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/list")
	public String view(HttpServletRequest request, Model model,
			@ModelAttribute WorkflowQO workflowQO) {
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		String travleID = SysProperties.getInstance().get("travleAdminID");
		UserRoleQO userRoleQO = new UserRoleQO();
		userRoleQO.setUserID(user.getId());
		List<UserRole> userRoles = userRoleService.queryList(userRoleQO);
		String roleID = "";
		for (UserRole userRole : userRoles) {
			roleID += userRole.getRole().getId()+",";
		}
		if (roleID.contains(travleID)) {
			model.addAttribute("role", "success");
		}else {
			model.addAttribute("role", "fail");
		}
		return "/content/workflow/order_list.html";
	}

	/**
	 * @throws ParseException 
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
	@RequestMapping(value = "/orderList")
	public Pagination orderList(
			HttpServletRequest request,
			Model model,
			FlightOrderQO flightOrderQO,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "status", required = false) Integer status) throws ParseException {

		if (pageNo == null)
			pageNo = 1;
		if (pageSize == null)
			pageSize = 10;
		// 获取当前登陆用户，以用于查找用户所属公司
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		User user = userService.get(authUser.getId());
		flightOrderQO.setCompanyID(user.getCompanyID());
		flightOrderQO.setOrderByCreatTime("desc");
		UserRoleQO userRoleQO = new UserRoleQO();
		userRoleQO.setUserID(user.getId());
		List<UserRole> userRoles = userRoleService.queryList(userRoleQO);
		String roleID = "";
		for (UserRole userRole : userRoles) {
			roleID += userRole.getRole().getId()+",";
		}
		flightOrderQO.setRoleID(roleID);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//航班时间
		if (StringUtils.isBlank(flightOrderQO.getInTime())) {
			flightOrderQO.setStartTime(null);
		}else {
			flightOrderQO.setStartTime(sdf.parse(flightOrderQO.getInTime()));
		}
		if(StringUtils.isBlank(flightOrderQO.getToTime())){
			flightOrderQO.setEndTime(null);
		}else {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(sdf.parse(flightOrderQO.getToTime()));
			cal1.add(Calendar.DATE, 1);
			flightOrderQO.setEndTime(cal1.getTime());
		}
		
		//下单时间
		if (StringUtils.isBlank(flightOrderQO.getStartDate())) {
			flightOrderQO.setStartCreateTime(null);
		}else {
			flightOrderQO.setStartCreateTime(sdf.parse(flightOrderQO.getStartDate()));
		}
		if(StringUtils.isBlank(flightOrderQO.getEndDate())){
			flightOrderQO.setEndCreateTime(null);
		}else {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(sdf.parse(flightOrderQO.getEndDate()));
			cal1.add(Calendar.DATE, 1);
			flightOrderQO.setEndCreateTime(cal1.getTime());
		}
		
		HgLogger.getInstance().info("gk","【OrderController】【orderList】,flightOrderQO："+JSON.toJSONString(flightOrderQO));
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(flightOrderQO);
		if (StringUtils.isNotBlank(user.getCompanyID())) {
			pagination = clglyFlightService.getList(pagination);
		}
		return pagination;
	}
}

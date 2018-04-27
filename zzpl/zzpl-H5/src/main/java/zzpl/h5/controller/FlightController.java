package zzpl.h5.controller;

import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zzpl.api.client.dto.jp.GNCabinListDTO;
import zzpl.api.client.dto.jp.GNFlightListDTO;
import zzpl.api.client.dto.user.CostCenterDTO;
import zzpl.api.client.dto.user.FrequentFlyerDTO;
import zzpl.api.client.dto.user.UserDTO;
import zzpl.api.client.dto.workflow.StepDTO;
import zzpl.api.client.dto.workflow.WorkflowDTO;
import zzpl.api.client.request.jp.GNCabinQO;
import zzpl.api.client.request.jp.GNFlightQO;
import zzpl.api.client.request.jp.GNTeamBookCommand;
import zzpl.api.client.request.jp.GNTeamBookCommand.GNTeamPassengerDTO;
import zzpl.api.client.request.order.GetGNFlightOrderTotalPriceCommand;
import zzpl.api.client.request.user.CostCenterQO;
import zzpl.api.client.request.user.QueryFrequentFlyerQO;
import zzpl.api.client.request.workflow.ChooseExecutorCommand;
import zzpl.api.client.request.workflow.ChooseStepCommand;
import zzpl.api.client.request.workflow.WorkflowQO;
import zzpl.api.client.response.jp.GNBookResponse;
import zzpl.api.client.response.user.QueryFrequentFlyerResponse;
import zzpl.api.client.response.user.QueryUserResponse;
import zzpl.api.client.response.workflow.ChooseExecutorResponse;
import zzpl.h5.service.FlightService;
import zzpl.h5.service.OrderService;
import zzpl.h5.service.UserService;
import zzpl.h5.service.dto.GNFlightDTO;
import zzpl.h5.util.base.BaseController;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("flight")
public class FlightController extends BaseController{

	@Autowired
	private FlightService flightService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderController orderController;
	private String strCommandJSON;
	
	@RequestMapping("/view")
	public String flightView(HttpServletRequest request,HttpServletResponse response){
		return "fly_index.html";
	}
	
	/**
	 * @throws IOException 
	 * @throws ParseException 
	 * @Title: getFlightList 
	 * @author guok
	 * @Description: 航班列表
	 * @Time 2015年11月26日上午9:16:45
	 * @param request
	 * @param response
	 * @param model
	 * @param gnFlightQO
	 * @param startCity
	 * @param endCity
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/getFlightList")
	public String getFlightList(HttpServletRequest request,HttpServletResponse response,Model model,
			GNFlightQO gnFlightQO,String startCity,String endCity,String flyDay,String time) throws ParseException, IOException {
		
		if (time != null && StringUtils.isNotBlank(time)) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			gnFlightQO.setStartTime(sdf.parse(time));
		}else {
			gnFlightQO.setStartTime(null);
		}
		
/*		boolean flag = true;
		String flightQoJson = JSON.toJSONString(gnFlightQO);
		if (strGNFlightQOJSON != null && StringUtils.isNotBlank(strGNFlightQOJSON)) {
			if (StringUtils.equals(strGNFlightQOJSON, flightQoJson)) {
				flag = false;
			}else {
				flag = true;
			}
		}
		if (flag) {
			HgLogger.getInstance().info("gk", "【FlightController】【getFlightList】"+"gnFlightQO:"+flightQoJson);
			strGNFlightQOJSON = flightQoJson;*/
			//往返城市
			model.addAttribute("gnFlightQO", gnFlightQO);
			model.addAttribute("startCity", startCity);
			model.addAttribute("endCity", endCity);
			model.addAttribute("flyDay", flyDay);
			model.addAttribute("time", time);
			
			//sessionID
			String sessionID = request.getSession().getAttribute("USER_SESSION_ID").toString();
			List<GNFlightDTO> dtos = JSON.parseArray(JSON.toJSONString(flightService.getFlightList(gnFlightQO,sessionID)), GNFlightDTO.class);
			for (GNFlightDTO flightGNDTO : dtos) {
				//时间差
				long endTime= flightGNDTO.getEndTime().getTime();
				long startTime = flightGNDTO.getStartTime().getTime();
				long difference = endTime - startTime;
				long day=difference/(24*60*60*1000);   
				long hour=(difference/(60*60*1000)-day*24);   
				long min=((difference/(60*1000))-day*24*60-hour*60);
				String  flightDuration = "";
				if(day!=0){
					flightDuration+=day+"天";
				}
				if(hour!=0){
					flightDuration+=hour+"小时";
				}
				if(min!=0){
					flightDuration+=min+"分";
				}
				flightGNDTO.setDateDiff(flightDuration);
				flightGNDTO.setOrgCityName(startCity);
				flightGNDTO.setDstCityName(endCity);
				String flightJson = JSON.toJSONString(flightGNDTO);
				flightGNDTO.setFlightJSON(flightJson);
			}
			model.addAttribute("flightGNListDTOs", dtos);
			return "fly_list.html";
	/*	}else {
			strGNFlightQOJSON = flightQoJson;
			
			return "redirect:/view";
		}*/
		
	}
	
	/**
	 * @throws IOException 
	 * @Title: getCabinList 
	 * @author guok
	 * @Description: 舱位列表
	 * @Time 2015年11月26日上午9:17:01
	 * @param request
	 * @param response
	 * @param gnCabinQO
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/getCabinList")
	public String getCabinList(HttpServletRequest request,HttpServletResponse response,
			GNCabinQO gnCabinQO) throws IOException {
		HgLogger.getInstance().info("gk", "【FlightController】【getCabinList】"+"gnCabinQO:"+JSON.toJSONString(gnCabinQO));
		//sessionID
		String sessionID = request.getSession().getAttribute("USER_SESSION_ID").toString();
		
		List<GNCabinListDTO> dtos = flightService.getCabinList(gnCabinQO, sessionID);
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(JSON.toJSONString(dtos)); 
		return null;
	}
	
	/**
	 * @Title: writeOrder 
	 * @author guok
	 * @Description: 跳转到填写订单页
	 * @Time 2015年11月27日上午9:48:09
	 * @param request
	 * @param response
	 * @param model
	 * @param flightJSON
	 * @param cabinInfo
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/writeOrder")
	public String writeOrder(HttpServletRequest request,HttpServletResponse response,Model model,
			String flightJSON,String cabinInfo) {
		HgLogger.getInstance().info("gk", "【FlightController】【writeOrder】"+"flightJSON:"+flightJSON);
		HgLogger.getInstance().info("gk", "【FlightController】【writeOrder】"+"cabinInfo:"+flightJSON);
		GNCabinListDTO cabinListDTO;
		GNFlightDTO flightGNDTO;
		try {
			cabinListDTO = JSON.parseObject(cabinInfo, GNCabinListDTO.class);
			flightGNDTO = JSON.parseObject(flightJSON, GNFlightDTO.class);
			flightGNDTO.setCabinCode(cabinListDTO.getCabinCode());
			flightGNDTO.setCabinDiscount(cabinListDTO.getCabinDiscount());
			flightGNDTO.setCabinName(cabinListDTO.getCabinName());
			flightGNDTO.setEncryptString(cabinListDTO.getEncryptString());
			flightGNDTO.setIbePrice(cabinListDTO.getIbePrice());
			//时间修饰
			SimpleDateFormat simpleDateFormat=  new SimpleDateFormat("MMdd");
			int startDay = Integer.parseInt(simpleDateFormat.format(flightGNDTO.getStartTime()));
			int endDay = Integer.parseInt(simpleDateFormat.format(flightGNDTO.getEndTime()));
			int now = Integer.parseInt(simpleDateFormat.format(new Date()));
			if(startDay-now==0){
				model.addAttribute("startCHN", "今天");
			}else if(startDay-now==1){
				model.addAttribute("startCHN", "明天");
			}else if(startDay-now==2){
				model.addAttribute("startCHN", "后天");
			}
			if(endDay-now==0){
				model.addAttribute("endCHN", "今天");
			}else if(endDay-now==1){
				model.addAttribute("endCHN", "明天");
			}else if(endDay-now==2){
				model.addAttribute("endCHN", "后天");
			}
			
			model.addAttribute("flightGNDTO", flightGNDTO);
			flightJSON = JSON.toJSONString(flightGNDTO);
		} catch (Exception e) {
			HgLogger.getInstance().error("gk", "【FlightController】【writeOrder】"+e.getMessage());
		}
		//sessionID
		String sessionID = request.getSession().getAttribute("USER_SESSION_ID").toString();
		//当前登陆人
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		QueryUserResponse userResponse = userService.getUserInfo(authUser.getId());
		UserDTO userDTO = userResponse.getUserDTO();
		model.addAttribute("userDTO", userDTO);
		
		//查询成本中心
		CostCenterQO costCenterQO = new CostCenterQO();
		costCenterQO.setCompanyID(userDTO.getCompanyID());
		List<CostCenterDTO> costCenterDTOs = flightService.queryCostCenter(costCenterQO,sessionID).getCostCenterDTOs();
		model.addAttribute("costCenterDTOs", costCenterDTOs);
		model.addAttribute("costCenterDTO", costCenterDTOs.get(0));
		
		//查询流程
		WorkflowQO workflowQO = new WorkflowQO();
		workflowQO.setCompanyID(userDTO.getCompanyID());
		workflowQO.setRoleList(userDTO.getRoleList());
		List<WorkflowDTO> workflowDTOs = flightService.queryWorkflow(workflowQO, sessionID).getWorkflowDTOs();
		model.addAttribute("workflowDTOs", workflowDTOs);
		
		model.addAttribute("flightJSON", flightJSON);
		
		//查询常用联系人列表
		QueryFrequentFlyerQO queryFrequentFlyerQO = new QueryFrequentFlyerQO();
		queryFrequentFlyerQO.setUserID(authUser.getId());
		HgLogger.getInstance().info("gk", "【FlightController】【queryFrequentFlyer】"+"queryFrequentFlyerQO:"+JSON.toJSONString(queryFrequentFlyerQO));
		QueryFrequentFlyerResponse queryFrequentFlyerResponse = flightService.queryFrequentFlyer(queryFrequentFlyerQO, sessionID);
		List<FrequentFlyerDTO> dtos = queryFrequentFlyerResponse.getFrequentFlyerDTOs();
		model.addAttribute("users", dtos);
		return "fly_reserve.html";
	}
	
	/**
	 * @Title: queryFrequentFlyer 
	 * @author guok
	 * @Description: 查询常用联系人列表
	 * @Time 2015年11月27日下午2:52:39
	 * @param request
	 * @param response
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/queryFrequentFlyer")
	public String queryFrequentFlyer(HttpServletRequest request,HttpServletResponse response,Model model) {
		QueryFrequentFlyerQO queryFrequentFlyerQO = new QueryFrequentFlyerQO();
		//当前登陆人
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		queryFrequentFlyerQO.setUserID(authUser.getId());
		HgLogger.getInstance().info("gk", "【FlightController】【queryFrequentFlyer】"+"queryFrequentFlyerQO:"+JSON.toJSONString(queryFrequentFlyerQO));
		//sessionID
		String sessionID = request.getSession().getAttribute("USER_SESSION_ID").toString();
		QueryFrequentFlyerResponse queryFrequentFlyerResponse = flightService.queryFrequentFlyer(queryFrequentFlyerQO, sessionID);
		List<FrequentFlyerDTO> dtos = queryFrequentFlyerResponse.getFrequentFlyerDTOs();
		model.addAttribute("users", dtos);
		return "fly_connect.html";
	}
	
	/**
	 * @throws IOException 
	 * @Title: queryWorkflowStep 
	 * @author guok
	 * @Description: 查询流程相关环节
	 * @Time 2015年11月27日下午4:06:07
	 * @param request
	 * @param response
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/queryWorkflowStep")
	public String queryWorkflowStep(HttpServletRequest request,HttpServletResponse response,
			String workflowID) throws IOException {
		HgLogger.getInstance().info("gk", "【FlightController】【queryFrequentFlyer】"+"workflowID:"+JSON.toJSONString(workflowID));
		//sessionID
		String sessionID = request.getSession().getAttribute("USER_SESSION_ID").toString();
		ChooseStepCommand chooseStepCommand = new ChooseStepCommand();
		chooseStepCommand.setCurrentStepNO(1);
		chooseStepCommand.setWorkflowID(workflowID);
		List<StepDTO> stepDTOs = flightService.queryStep(chooseStepCommand, sessionID).getStepDTOs();
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(JSON.toJSONString(stepDTOs)); 
		return null;
	}
	
	/**
	 * @throws IOException 
	 * @Title: queryExecutor 
	 * @author guok
	 * @Description: 获取下一步执行人
	 * @Time 2015年11月30日上午9:13:30
	 * @param request
	 * @param response
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/queryExecutor")
	public String queryExecutor(HttpServletRequest request,HttpServletResponse response,ChooseExecutorCommand chooseExecutorCommand) throws IOException {
		HgLogger.getInstance().info("gk", "【FlightController】【queryExecutor】"+"chooseExecutorCommand:"+JSON.toJSONString(chooseExecutorCommand));
		//sessionID
		String sessionID = request.getSession().getAttribute("USER_SESSION_ID").toString();
		ChooseExecutorResponse chooseExecutorResponse = flightService.queryExecutors(chooseExecutorCommand, sessionID);
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(JSON.toJSONString(chooseExecutorResponse.getExecutorDTOs())); 
		return null;
	}
	
	/**
	 * @throws Exception 
	 * @Title: toPay 
	 * @author guok
	 * @Description: 跳转支付页面
	 * @Time 2015年11月30日下午2:05:47
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @param flightJSON
	 * @param nextUserID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/toPay")
	public String toPay(HttpServletRequest request,HttpServletResponse response,Model model,GNTeamBookCommand command,
			String flightJSON,String nextUserID,String passengerID,String passengerTel,String passengerName
			,String costCenterIDs,String passengeridCardNO) throws Exception {
		
		String[] passengerNameList = passengerName.split(",");
		String[] telephoneList = passengerTel.split(",");
		String[] idNOList = passengeridCardNO.split(",");
		String[] costCenterIdList = costCenterIDs.split(",");
		//sessionID
		String sessionID = request.getSession().getAttribute("USER_SESSION_ID").toString();
		
		//航班信息
		GNFlightDTO flightGNDTO = JSON.parseObject(flightJSON, GNFlightDTO.class);
		GNFlightListDTO flightGNListDTO = JSON.parseObject(flightJSON, GNFlightListDTO.class);
		command.setFlightGNListDTO(flightGNListDTO);
		//下一步执行人
		List<String> nextUserIDs = new ArrayList<String>();
		nextUserIDs.add(nextUserID);
		command.setNextUserIDs(nextUserIDs);
		//当前登陆人
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		command.setUserID(authUser.getId());
		//乘机人
		List<GNTeamPassengerDTO> gnPassengerDTOs = new ArrayList<GNTeamPassengerDTO>();
		UserDTO userDTO = new UserDTO();
		for (int i = 0; i < passengerNameList.length; i++) {
			GNTeamBookCommand.GNTeamPassengerDTO gnPassengerDTO = new GNTeamBookCommand().new GNTeamPassengerDTO();
				gnPassengerDTO.setPassengerName(passengerNameList[i]);
				gnPassengerDTO.setIdType("0");
				gnPassengerDTO.setTelephone(telephoneList[i]);
				gnPassengerDTO.setIdNO(idNOList[i]);
				gnPassengerDTO.setPassengerType("1");
				gnPassengerDTO.setCostCenterID(costCenterIdList[i]);
				gnPassengerDTOs.add(gnPassengerDTO);
			
		}
		command.setTeamPassengerDTOs(gnPassengerDTOs);
		HgLogger.getInstance().info("gk", "【FlightController】【toPay】"+"GNTeamBookCommand:"+JSON.toJSONString(command));
		Boolean flag = true;
		//对比JSON
		String strJson = JSON.toJSONString(command);
		if (strCommandJSON != null && StringUtils.isNotBlank(strCommandJSON)) {
			if(StringUtils.equals(strJson, strCommandJSON)){
				flag = false;
				
			}else {
				flag = true;
			}
		}
		if (flightGNDTO.getEndTime()!=null&&flightGNDTO.getStartTime()!=null) {
			long endTime= flightGNDTO.getEndTime().getTime();
			long startTime = flightGNDTO.getStartTime().getTime();
			long difference = endTime - startTime;
			long day=difference/(24*60*60*1000);   
			long hour=(difference/(60*60*1000)-day*24);   
			long min=((difference/(60*1000))-day*24*60-hour*60);
			String  flightDuration = "";
			if(day!=0){
				flightDuration+=day+"天";
			}
			if(hour!=0){
				flightDuration+=hour+"小时";
			}
			if(min!=0){
				flightDuration+=min+"分";
			}
			model.addAttribute("times", flightDuration);
		}else {
			model.addAttribute("times", "");
		}
		
		
		if (flag) {
			strCommandJSON = strJson;
			//创建订单
			GNBookResponse gnBookResponse = flightService.bookOrder(command, sessionID);
			
			model.addAttribute("gnBookResponse", gnBookResponse);
			
			//查询成本中心
			CostCenterQO costCenterQO = new CostCenterQO();
			costCenterQO.setCompanyID(userDTO.getCompanyID());
			List<CostCenterDTO> costCenterDTOs = flightService.queryCostCenter(costCenterQO,sessionID).getCostCenterDTOs();
			model.addAttribute("costCenterDTOs", costCenterDTOs);
			for (CostCenterDTO costCenterDTO : costCenterDTOs) {
				for (GNTeamPassengerDTO passengerDTO : command.getTeamPassengerDTOs()) {
					if (StringUtils.equals(passengerDTO.getCostCenterID(), costCenterDTO.getId())) {
						passengerDTO.setCostCenterID(costCenterDTO.getCostCenterName());
					}
				}
			}
			model.addAttribute("command", command);
			model.addAttribute("orderType", "unionOrder");
			if(Integer.parseInt(command.getPayType())==GNTeamBookCommand.PAY_BY_SELF){
				GetGNFlightOrderTotalPriceCommand getGNFlightOrderTotalPriceCommand = new GetGNFlightOrderTotalPriceCommand();
				getGNFlightOrderTotalPriceCommand.setOrderNO(gnBookResponse.getOrderNO());
				orderService.getGNFlightOrderTotalPrice(getGNFlightOrderTotalPriceCommand, request);
				model.addAttribute("type", 0);
				model.addAttribute("orderNO", gnBookResponse.getOrderNO());
				return "fly_topay.html";
			}else{
				return orderController.orderView(request, response, model, 1);
			}
		}else {
			strCommandJSON = strJson;
			model.addAttribute("message", "请不要重复提交订单");
			return "error-page/orderError.html";
		}
		
		
	}
	
}

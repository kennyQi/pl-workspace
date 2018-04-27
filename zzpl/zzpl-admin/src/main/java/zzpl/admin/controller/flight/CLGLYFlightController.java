package zzpl.admin.controller.flight;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.admin.controller.BaseController;
import zzpl.api.client.dto.jp.GNPassengerDTO;
import zzpl.api.client.request.jp.GNTeamBookCommand;
import zzpl.app.service.local.jp.CityAirCodeService;
import zzpl.app.service.local.jp.PassengerService;
import zzpl.app.service.local.jp.gn.CLGLYFlightService;
import zzpl.app.service.local.jp.gn.FlightOrderService;
import zzpl.app.service.local.jp.gn.GNFlightService;
import zzpl.app.service.local.user.CompanyService;
import zzpl.app.service.local.user.CostCenterService;
import zzpl.app.service.local.user.UserService;
import zzpl.app.service.local.util.OrderUtil;
import zzpl.domain.model.order.CityAirCode;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.CostCenter;
import zzpl.domain.model.user.User;
import zzpl.pojo.command.jp.BookGNFlightCommand;
import zzpl.pojo.command.jp.CancelGNTicketCommand;
import zzpl.pojo.dto.jp.plfx.gn.FlightGNDTO;
import zzpl.pojo.dto.jp.plfx.gn.JPQueryFlightListGNDTO;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.dto.user.CostCenterDTO;
import zzpl.pojo.dto.user.UserDTO;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.jp.plfx.gn.JPFlightGNQO;
import zzpl.pojo.qo.sys.CityAirCodeQO;
import zzpl.pojo.qo.user.CompanyQO;
import zzpl.pojo.qo.user.CostCenterQO;
import zzpl.pojo.qo.user.UserQO;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/CLGLYBookFlight")
public class CLGLYFlightController extends BaseController{
	
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private GNFlightService gnFlightService;
	@Autowired
	private CityAirCodeService cityAirCodeService;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private CLGLYFlightService clglyFlightService;
	@Autowired
	private FlightOrderService flightOrderService;
	@Autowired
	private CostCenterService costCenterService;
	@Autowired
	private PassengerService passengerService;
	

	@RequestMapping(value = "/view")
	public ModelAndView view(HttpServletRequest request, Model model) {
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		model.addAttribute("user", user);
		ModelAndView view = new ModelAndView();
        view.setViewName("/content/new-web/fly_index.html");
        return view;
	}
	
	/**
	 * @Title: companyList 
	 * @author guok
	 * @Description: 差旅管理员的公司列表
	 * @Time 2015年10月8日下午2:15:49
	 * @param request
	 * @param model
	 * @param companyQO
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/company")
	public String companyList(HttpServletRequest request, Model model,
			@ModelAttribute CompanyQO companyQO) {
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		String[] companyIDs = user.getCompanyID().split(",");
		List<Company> companys = new ArrayList<Company>();
		for (String string : companyIDs) {
			companyQO.setId(string);
			Company company = companyService.queryUnique(companyQO);
			companys.add(company);
		}
		model.addAttribute("companys", companys);
		
		return "/content/new-web/company_list.html";
	}
	
	/**
	 * @Title: userList 
	 * @author guok
	 * @Description: 根据公司查询员工
	 * @Time 2015年10月8日下午2:37:38
	 * @param request
	 * @param model
	 * @param userQO
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/companyUsers")
	public String userList(HttpServletRequest request, Model model,
			@ModelAttribute UserQO userQO) {
		List<User> users = userService.queryList(userQO);
		List<User> userList = new ArrayList<User>();
		for (User user : users) {
			if (StringUtils.isNotBlank(user.getIdCardNO())||StringUtils.isNotBlank(user.getLinkMobile())) {
				userList.add(user);
			}
		}
		model.addAttribute("users", userList);
		return "/content/new-web/user_list.html";
	}
	
	/**
	 * @throws ParseException 
	 * @Title: ordersByUserID 
	 * @author guok
	 * @Description: 差旅管理员订单列表
	 * @Time 2015年10月8日下午2:40:32
	 * @param request
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/orders")
	public ModelAndView ordersByUserID(HttpServletRequest request, Model model,FlightOrderQO flightOrderQO,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) throws ParseException {
		
		if (pageNo == null)
			pageNo = 1;
		if (pageSize == null)
			pageSize = 20;
		
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		flightOrderQO.setReplacePerson(user.getId());
		flightOrderQO.setOrderByCreatTime("123");
		flightOrderQO.setRoleID(SysProperties.getInstance().get("travleAdminID"));
		if(flightOrderQO.getCompanyName() != null && StringUtils.isNotBlank(flightOrderQO.getCompanyName())){
			CompanyQO companyQO = new CompanyQO();
			companyQO.setCompanyName(flightOrderQO.getCompanyName());
			List<Company> companys = companyService.queryList(companyQO);
			if (companys != null && companys.size()>0) {
				for (Company company : companys) {
					if(user.getCompanyID().contains(company.getId())){
						flightOrderQO.setCompanyID(company.getId()+",");
					}
				}
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isBlank(flightOrderQO.getInTime())) {
			flightOrderQO.setStartCreateTime(null);
		}else {
			flightOrderQO.setStartCreateTime(sdf.parse(flightOrderQO.getInTime()));
		}
		if(StringUtils.isBlank(flightOrderQO.getToTime())){
			flightOrderQO.setEndCreateTime(null);
		}else {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(sdf.parse(flightOrderQO.getToTime()));
			cal1.add(Calendar.DATE, 1);
			flightOrderQO.setEndCreateTime(cal1.getTime());
		}
		
		HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【ordersByUserID】"+"BookGNFlightCommand:"+JSON.toJSONString(flightOrderQO));
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(flightOrderQO);
		if (StringUtils.isNotBlank(user.getCompanyID())) {
			pagination = clglyFlightService.getList(pagination);
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("flightOrderQO", flightOrderQO);
		Integer sum = pagination.getTotalPage();
		model.addAttribute("sum", sum);
		model.addAttribute("user", user);
		ModelAndView view = new ModelAndView();
        view.setViewName("/content/new-web/fly_myOrders.html");
        return view;
	}
	
	/**
	 * @Title: orderDatil 
	 * @author guok
	 * @Description: 订单详情
	 * @Time 2015年10月19日上午9:33:01
	 * @param request
	 * @param model
	 * @param flightOrderQO
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/orderDatil")
	public String orderDatil(HttpServletRequest request, Model model,
			@ModelAttribute FlightOrderQO flightOrderQO) {
		HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【orderDatil】"+"BookGNFlightCommand:"+JSON.toJSONString(flightOrderQO));
		//订单
		FlightOrder flightOrder = flightOrderService.get(flightOrderQO.getId());
		PassengerQO passengerQO = new PassengerQO();
		passengerQO.setFlightOrderID(flightOrder.getId());
		List<Passenger> passengers = passengerService.queryList(passengerQO);
		model.addAttribute("flightOrder", flightOrder);
		model.addAttribute("passengers", passengers);
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
		//公司
		Company company = companyService.get(flightOrder.getCompanyID());
		model.addAttribute("company", company);
		//起草人
		User user = userService.get(flightOrder.getReplacePerson());
		model.addAttribute("user", user);
		//成本中心
		CostCenter costCenter = costCenterService.get(flightOrder.getCostCenterID());
		model.addAttribute("costCenter", costCenter);
		return "/content/new-web/fly_details.html";
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Title: seachFlight 
	 * @author guok
	 * @Description: 查询航班
	 * @Time 2015年10月8日下午2:59:37
	 * @param request
	 * @param model
	 * @param jpFlightGNQO
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/queryFlightGN")
	public String seachFlight(HttpServletRequest request, Model model,
			@ModelAttribute JPFlightGNQO jpFlightGNQO,
			@RequestParam(value = "company", required = false) String company,@RequestParam(value = "airlines", required = false) String airlines,@RequestParam(value = "searchcond", required = false) String searchcond) throws UnsupportedEncodingException {
		HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【order】"+"BookGNFlightCommand:"+JSON.toJSONString(jpFlightGNQO));
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		model.addAttribute("user", user);
		//查询机场三字码
		String orgCity = jpFlightGNQO.getOrgCity();
		String dstCity = jpFlightGNQO.getDstCity();
		CityAirCodeQO cityAirCodeQO = new CityAirCodeQO();
		cityAirCodeQO.setName(orgCity);
		CityAirCode cityAirCode = cityAirCodeService.queryUnique(cityAirCodeQO);
		if(cityAirCode==null)
			return "/content/new-web/fly_list.html";
		jpFlightGNQO.setOrgCity(cityAirCode.getAirCode());
		cityAirCodeQO = new CityAirCodeQO();
		cityAirCodeQO.setName(dstCity);
		cityAirCode = cityAirCodeService.queryUnique(cityAirCodeQO);
		jpFlightGNQO.setDstCity(cityAirCode.getCityAirCode());
		//往返城市
		model.addAttribute("orgCity", orgCity);
		model.addAttribute("dstCity", dstCity);
		//起飞时间
		model.addAttribute("time", jpFlightGNQO.getStartDate());
		try {
			List<FlightGNDTO> flightGNDTOs = new ArrayList<FlightGNDTO>();
			//查询航班
			JPQueryFlightListGNDTO jpQueryFlightListGNDTO = gnFlightService.queryGNFlight(jpFlightGNQO);
			for (FlightGNDTO flightGNDTO : jpQueryFlightListGNDTO.getFlightList()) {
				//时间差
				Long diff = flightGNDTO.getEndTime().getTime()-flightGNDTO.getStartTime().getTime();
				Long result = diff/(1000*60);
				String dates = result/60+"小时"+result%60+"分钟";
				flightGNDTO.setDateDiff(dates);
				if (company != null && StringUtils.isNotBlank(company)) {
					if (StringUtils.equals(company, "全部")) {
						flightGNDTOs.add(flightGNDTO);
					}else if (StringUtils.equals(flightGNDTO.getAirCompanyName(), company)) {
						flightGNDTOs.add(flightGNDTO);
					}
					
				}else {
					flightGNDTOs.add(flightGNDTO);
				}
			}
			jpQueryFlightListGNDTO.setFlightList(flightGNDTOs);
			model.addAttribute("jpQueryFlightListGNDTO", jpQueryFlightListGNDTO);
			
			model.addAttribute("company", company);
			model.addAttribute("airlines", airlines);
			if(jpQueryFlightListGNDTO.getFlightList().size()==0){
				model.addAttribute("company", "");
				model.addAttribute("airlines", "");
			}
			model.addAttribute("searchcond", searchcond);
		} catch (Exception e) {
			HgLogger.getInstance().error("gk", "【CLGLYBookFlightController】【order】"+e.getMessage());
			return "/content/new-web/fly_list.html";
		}
		
		return "/content/new-web/fly_list.html";
	}
	
	/**
	 * @throws ParseException 
	 * @Title: order 
	 * @author guok
	 * @Description: 跳转预订页
	 * @Time 2015年10月9日下午4:09:48
	 * @param request
	 * @param model
	 * @param command
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/order")
	public String order(HttpServletRequest request, Model model,
			@ModelAttribute BookGNFlightCommand command,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) throws ParseException {
		HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【order】"+"BookGNFlightCommand:"+JSON.toJSONString(command));
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		command.setStartTime(sdFormat.parse(startTime));
		command.setEndTime(sdFormat.parse(endTime));
		if (command.getOilFee() == null) {
			command.setOilFee(0.0d);
		}
		command.setTotalPrice(command.getBuildFee()+command.getOilFee()+new Double(command.getIbePrice()));
		Long diff = command.getEndTime().getTime()-command.getStartTime().getTime();
		Long result = diff/(1000*60);
		String dates = result/60+"小时"+result%60+"分钟";
		CompanyQO companyQO = new CompanyQO();
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		String[] companyIDs = user.getCompanyID().split(",");
		List<Company> companys = new ArrayList<Company>();
		for (String string : companyIDs) {
			companyQO.setId(string);
			Company company = companyService.queryUnique(companyQO);
			companys.add(company);
		}
		CityAirCodeQO cityAirCodeQO = new CityAirCodeQO();
		cityAirCodeQO.setName(command.getOrgCityName());
		CityAirCode cityAirCode = cityAirCodeService.queryUnique(cityAirCodeQO);
		command.setOrgCity(cityAirCode.getAirCode());
		cityAirCodeQO = new CityAirCodeQO();
		cityAirCodeQO.setName(command.getDstCityName());
		cityAirCode = cityAirCodeService.queryUnique(cityAirCodeQO);
		command.setDstCity(cityAirCode.getCityAirCode());
		
		model.addAttribute("user", user);
		model.addAttribute("companys", companys);
		model.addAttribute("command", command);
		model.addAttribute("dates", dates);
		model.addAttribute("commandJSON", JSON.toJSONString(command));
		return "/content/new-web/fly_order.html";
	}
	
	/**
	 * @Title: selectUsers 
	 * @author guok
	 * @Description: 预订页查询员工
	 * @Time 2015年10月9日下午4:29:39
	 * @param request
	 * @param model
	 * @param response
	 * @param userQO
	 * @throws IOException void 设定文件
	 * @throws
	 */
	@RequestMapping("/selectUsers")
	public String selectUsers(HttpServletRequest request, Model model,
			HttpServletResponse response,@ModelAttribute UserQO userQO) throws IOException {
		List<UserDTO> userDTOs = userService.queryUserList(userQO);
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(JSON.toJSONString(userDTOs)); 
		return null;
	}
	/**
	 * @Title: selectCost 
	 * @author guok
	 * @Description: 预订页成本中心
	 * @Time 2015年10月12日下午2:06:10
	 * @param request
	 * @param model
	 * @param response
	 * @param costCenterQO
	 * @return
	 * @throws IOException String 设定文件
	 * @throws
	 */
	@RequestMapping("/selectCost")
	public String selectCost(HttpServletRequest request, Model model,
			HttpServletResponse response,@ModelAttribute CostCenterQO costCenterQO) throws IOException {
		List<CostCenterDTO> costCenterDTOs = costCenterService.queryCostCentList(costCenterQO);
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(JSON.toJSONString(costCenterDTOs)); 
		return null;
	}
	
	/**
	 * @Title: commitOrder 
	 * @author guok
	 * @Description: 差旅管理员自有订票提交本地订单
	 * @Time 2015年10月10日上午9:42:14
	 * @param request
	 * @param model
	 * @param response
	 * @param bookGNFlightCommand
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/commitOrder")
	public String commitOrder(HttpServletRequest request, Model model,
			HttpServletResponse response,@ModelAttribute GNTeamBookCommand gnTeamBookCommand,
			@RequestParam(value = "commandJSON", required = false) String commandJSON,
			@RequestParam(value = "passengerName", required = false) String passengerName,
			@RequestParam(value = "telephone", required = false) String telephone,
			@RequestParam(value = "idNO", required = false) String idNO,
			@RequestParam(value = "costCenterIDs", required = false) String costCenterIDs,
			@RequestParam(value = "dates", required = false) String dates,
			@RequestParam(value = "companyID", required = false) String companyID) {
		HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【commitOrder】"+"commandJSON:"+JSON.toJSONString(commandJSON));
		HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【commitOrder】"+"gnTeamBookCommand:"+JSON.toJSONString(gnTeamBookCommand));
		try{
			BookGNFlightCommand command = JSON.parseObject(commandJSON, BookGNFlightCommand.class);
			String[] linkNameList = gnTeamBookCommand.getLinkName().split(",");
			String[] linkTelephoneList = gnTeamBookCommand.getLinkTelephone().split(",");
			String[] costCenterIDList = costCenterIDs.split(",");
			String[] passengerNameList = passengerName.split(",");
			String[] userIDList = gnTeamBookCommand.getUserID().split(",");
			String[] telephoneList = telephone.split(",");
			String[] idNOList = idNO.split(",");
			
			HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【commitOrder】"+"userIDList.length:"+userIDList.length);
			
			List<GNPassengerDTO> gnPassengerDTOs = new ArrayList<GNPassengerDTO>();
			for (int i = 0; i < userIDList.length; i++) {
				if (StringUtils.equals(userIDList[i], "not")) {
					GNPassengerDTO gnPassengerDTO = new GNPassengerDTO();
					gnPassengerDTO.setPassengerName(passengerNameList[i]);
					gnPassengerDTO.setIdType("0");
					gnPassengerDTO.setTelephone(telephoneList[i]);
					gnPassengerDTO.setIdNO(idNOList[i]);
					gnPassengerDTO.setPassengerType("1");
					gnPassengerDTOs.add(gnPassengerDTO);
				}else {
					User user = userService.get(userIDList[i]);
					GNPassengerDTO gnPassengerDTO = new GNPassengerDTO();
					gnPassengerDTO.setPassengerName(user.getName());
					gnPassengerDTO.setIdType("0");
					gnPassengerDTO.setTelephone(user.getLinkMobile());
					gnPassengerDTO.setIdNO(user.getIdCardNO());
					gnPassengerDTO.setPassengerType("1");
					gnPassengerDTOs.add(gnPassengerDTO);
				}
				
			}
			HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【commitOrder】"+"gnPassengerDTOs:"+JSON.toJSONString(gnPassengerDTOs));
			
			String unionOrderNO ="";
			String orderNO = "";
			//为了判断是否要生成订单号
			boolean flag = false;
			if(gnPassengerDTOs.size()==1){
				orderNO = OrderUtil.createOrderNo(new Date(), this.getOrderSequence(), 1, 0);
				HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】"+"orderNO【1】:"+JSON.toJSONString(orderNO));
				//创建下一个序列号
				this.setNextOrderSequence();
			}else if(gnPassengerDTOs.size()>1){
				flag = true;
				unionOrderNO = "UN_"+OrderUtil.createOrderNo(new Date(), this.getOrderSequence(), 1, 0);
				HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】"+"unionOrderNO【2】:"+JSON.toJSONString(unionOrderNO));
				//创建下一个序列号
				this.setNextOrderSequence();
			}
			List<FlightOrder> flightOrders = new ArrayList<FlightOrder>();
			AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
			User user=userService.get(authUser.getId());
			for (int i = 0; i < gnPassengerDTOs.size(); i++) {
				if(flag){
					orderNO = OrderUtil.createOrderNo(new Date(), this.getOrderSequence(), 1, 0);
					HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】"+"orderNO【3】:"+JSON.toJSONString(orderNO));
					//创建下一个序列号
					this.setNextOrderSequence();
				}
				command.setOrderNO(orderNO);
				BigDecimal ibePrice = new BigDecimal(command.getIbePrice());
				BigDecimal sum = new BigDecimal(0);
				if(command.getOilFee()!=null&&StringUtils.isNotBlank(command.getOilFee().toString())){
					BigDecimal oilFee = new BigDecimal(command.getOilFee());
					sum=sum.add(oilFee);
				}
				if(command.getBuildFee()!=null&&StringUtils.isNotBlank(command.getBuildFee().toString())){
					BigDecimal buildFee = new BigDecimal(command.getBuildFee());
					sum=sum.add(buildFee);
				}
				command.setCommitPrice(ibePrice.add(sum).doubleValue());
				command.setTotalPrice(ibePrice.add(sum).doubleValue());
				GNPassengerDTO dto = BeanMapperUtils.getMapper().map(gnPassengerDTOs.get(i), GNPassengerDTO.class);
				List<GNPassengerDTO> dtos = new ArrayList<GNPassengerDTO>();
				dtos.add(dto);
				command.setPassengerListJSON(JSON.toJSONString(dtos));
				command.setLinkName(linkNameList[i]);
				command.setLinkTelephone(linkTelephoneList[i]);
				command.setCreateTime(new Date());
				
				if (userIDList.length > 0 && userIDList[i] != null && StringUtils.isNotBlank(userIDList[i])) {
					command.setUserID(userIDList[i]);
				}
				command.setCompanyID(companyID);
				if (!StringUtils.equals(userIDList[i], "not")) {
					User passenger = userService.getById(userIDList[i]);
					if (passenger != null) {
						command.setDepartmentID(passenger.getDepartmentID());
					}
				}
				
				command.setUserName(gnPassengerDTOs.get(i).getPassengerName());
				command.setPlatTotalPrice(ibePrice.add(sum).doubleValue());
				command.setUnionOrderNO(unionOrderNO);
				command.setCostCenterID(costCenterIDList[i]);
				command.setReplaceBuy(FlightOrder.REPLACE_BUY_OK);
				command.setReplacePerson(user.getId());
				command.setPayStatus(FlightPayStatus.WAIT_TO_PAY);
				//本地订单
				FlightOrder flightOrder = new FlightOrder() ;
				try {
					HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【commitOrder】"+"command:"+JSON.toJSONString(command));
					flightOrder = clglyFlightService.clglyBookFlight(command);
					HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【commitOrder】"+"flightOrder:"+JSON.toJSONString(flightOrder));
					//分销订单
					flightOrder = clglyFlightService.clglyPrintTicket(flightOrder.getId());
					HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【commitOrder】"+"flightOrder:"+JSON.toJSONString(flightOrder));
				} catch (Exception e) {
					HgLogger.getInstance().error("gk", "【CLGLYBookFlightController】"+e.getMessage());
				}
				model.addAttribute("flightOrder", flightOrder);
				flightOrders.add(flightOrder);
			}
			model.addAttribute("gnPassengerDTOs", gnPassengerDTOs);
			Company company = companyService.get(command.getCompanyID());
			model.addAttribute("company", company);
			//订单信息
			model.addAttribute("flightOrderJSON", JSON.toJSONString(flightOrders));
			HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【commitOrder】"+"flightOrders:"+JSON.toJSONString(flightOrders));
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setUnionOrderNO(unionOrderNO);
			if (StringUtils.isBlank(unionOrderNO)) {
				flightOrderQO.setOrderNO(orderNO);
			}
			
			List<FlightOrder> flightOrders2 = clglyFlightService.selectOrderList(flightOrderQO);
			model.addAttribute("flightOrders", flightOrders2);
			CostCenterQO costCenterQO = new CostCenterQO();
			List<CostCenterDTO> costCenterDTOs = costCenterService.queryCostCentList(costCenterQO);
			model.addAttribute("costCenterDTOs", costCenterDTOs);
			model.addAttribute("dates", dates);//时间差
			model.addAttribute("user", user);
			
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【CLGLYBookFlightController】【commitOrder】"+"异常，"+HgLogger.getStackTrace(e));
		}
		return "/content/new-web/fly_pay.html";
	}
	
	/**
	 * @throws ParseException 
	 * @Title: payOrder 
	 * @author guok
	 * @Description: 差旅管理员自有订票分销支付
	 * @Time 2015年10月12日下午5:54:33
	 * @param request
	 * @param model
	 * @param response
	 * @param flightOrderJSON
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/payOrder")
	public ModelAndView payOrder(HttpServletRequest request, Model model,
			HttpServletResponse response,@RequestParam(value = "flightOrderJSON", required = false) String flightOrderJSON) throws ParseException {
		HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】"+"payOrder:"+flightOrderJSON);
		String msg = "下单成功";
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		model.addAttribute("user", user);
		try {
			List<FlightOrder> flightOrders = JSON.parseArray(flightOrderJSON, FlightOrder.class);
			for (FlightOrder flightOrder : flightOrders) {
				flightOrder=flightOrderService.get(flightOrder.getId());
				if(flightOrder.getStatus()==FlightOrderStatus.PRINT_TICKET_SUCCESS||flightOrder.getPayStatus()==FlightPayStatus.PAY_SUCCESS){
					throw new Exception();
				}
				clglyFlightService.payOrder(flightOrder.getId());
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("gk", "【CLGLYBookFlightController】【payOrder】"+e.getMessage());
			msg = "下单失败";
		}
		model.addAttribute("msg", msg);
//		return "/content/new-web/fly_result.html";
		return ordersByUserID(request,model,new FlightOrderQO(),1,20) ;
	}
	
	/**
	 * @throws ParseException 
	 * @Title: refundTicket 
	 * @author guok
	 * @Description: 差旅管理员自有订票退票
	 * @Time 2015年10月14日上午9:25:51
	 * @param request
	 * @param model
	 * @param response
	 * @param id
	 * @param cancelGNTicketCommand
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/refundTicket")
	public ModelAndView refundTicket(HttpServletRequest request, Model model,
			@ModelAttribute CancelGNTicketCommand cancelGNTicketCommand) throws ParseException {
		String msg = "退票成功";
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user=userService.get(authUser.getId());
		model.addAttribute("user", user);
		try {
			clglyFlightService.clglyRefundTicket(cancelGNTicketCommand);
		} catch (Exception e) {
			HgLogger.getInstance().error("gk", "【CLGLYBookFlightController】【refundTicket】"+e.getMessage());
			msg = "退票失败";
		}
		model.addAttribute("msg", msg);
//		return "/content/new-web/fly_result.html";
		return ordersByUserID(request,model,new FlightOrderQO(),1,20) ;
	}
	
	/**
	 * @Title: toPay 
	 * @author guok
	 * @Description: 订单列表跳转支付
	 * @Time 2015年10月19日上午10:13:43
	 * @param request
	 * @param model
	 * @param response
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/toPay")
	public String toPay(HttpServletRequest request, Model model,
			HttpServletResponse response,@RequestParam(value = "orderID", required = false) String orderID) {
		HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【toPay】"+"orderID:"+JSON.toJSONString(orderID));
		FlightOrder flightOrder = clglyFlightService.queryOrder(orderID);
		PassengerQO passengerQO = new PassengerQO();
		passengerQO.setFlightOrderID(flightOrder.getId());
		List<GNPassengerDTO> passengers = passengerService.getList(passengerQO);
		String passenger = JSON.toJSONString(passengers);
		model.addAttribute("flightOrder", flightOrder);
		model.addAttribute("gnPassengerDTOs", passenger);
		
		//公司
		Company company = companyService.get(flightOrder.getCompanyID());
		model.addAttribute("company", company);
		
		List<FlightOrder> flightOrders2 = new ArrayList<FlightOrder>();
		flightOrders2.add(flightOrder);
		model.addAttribute("flightOrders", flightOrders2);//订单列表
		model.addAttribute("flightOrderJSON", JSON.toJSONString(flightOrders2));
		HgLogger.getInstance().info("gk", "【CLGLYBookFlightController】【toPay】"+"flightOrders:"+JSON.toJSONString(flightOrders2));
		//成本中心
		List<CostCenterDTO> costCenterDTOs = costCenterService.queryCostCentList(new CostCenterQO());
		model.addAttribute("costCenterDTOs", costCenterDTOs);
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		User user1=userService.get(authUser.getId());
		model.addAttribute("user", user1);
		
		Long diff = flightOrder.getEndTime().getTime()-flightOrder.getStartTime().getTime();
		Long result = diff/(1000*60);
		String dates = result/60+"小时"+result%60+"分钟";
		model.addAttribute("dates", dates);//时间差
		return "/content/new-web/fly_pay.html";
	}
	
	// 获取流水号
	public long getSequence() {
		Jedis jedis = null;
		String value = "1";
		try {
			jedis = jedisPool.getResource();
			value = jedis.get("zzpl_jp_tk_sequence");
			String date = jedis.get("zzpl_jp_tk_sequence_date");
	
			if (StringUtils.isBlank(value)
					|| StringUtils.isBlank(date)
					|| date.equals(String.valueOf(Calendar.getInstance().getTime().getTime()))) {
				value = "1";
				HgLogger.getInstance().info("cs", "【NotifyController】"+"流水号方法流水数字重置");
			}
			
			long v = Long.parseLong(value);
			v++;
			
			jedis.set("zzpl_jp_tk_sequence", String.valueOf(v));
			jedis.set("zzpl_jp_tk_sequence_date", String.valueOf(Calendar.getInstance().getTime().getTime()));
		} catch(RuntimeException e){
			HgLogger.getInstance().info("cs", "【NotifyController】"+"流水号方法"+ "获取流水号异常"+HgLogger.getStackTrace(e));
		} finally {
			jedisPool.returnResource(jedis);
		}
	
		return Long.parseLong(value);
	}
	/**
	 * @功能说明：从redis拿取序列号，也许该加个同步锁
	 * @修改者：
	 * @修改日期：
	 * @修改说明：
	 * @创建者 zhuxy
	 * @创建时间：2015年2月28日
	 */
	public int getOrderSequence() {
		Jedis jedis = null;
		try {
	
			jedis = jedisPool.getResource();
			String value = jedis.get("zx_flightorder_sequence");
			String date = jedis.get("zx_flightorder_sequence_date");
			HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】【getOrderSequence】"+"value:"+JSON.toJSONString(value));
			HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】【getOrderSequence】"+"时间比较"+JSON.toJSONString(date)+String.valueOf(Calendar.getInstance().getTime().getTime()));
			if (StringUtils.isBlank(value)|| StringUtils.isBlank(date)||date.equals(String.valueOf(Calendar.getInstance().getTime().getTime()))) {
				HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】【getOrderSequence】"+"重置value");
				value = "0";
			}
	
			return Integer.parseInt(value);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	// 设置下一条订单流水号
	public String setNextOrderSequence() {
		HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】【setNextOrderSequence】"+"设置下一个流水号");
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			int value = this.getOrderSequence();
	
			if (value >= 9999) {
				value = 0;
			} else {
				value++;
			}
	
			HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】【setNextOrderSequence】"+"value:"+JSON.toJSONString(value));
			HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】【setNextOrderSequence】"+"Calendar.getInstance().getTime().getTime():"+String.valueOf(Calendar.getInstance().getTime().getTime()));
			jedis.set("zx_flightorder_sequence", String.valueOf(value));
			jedis.set("zx_flightorder_sequence_date",String.valueOf(Calendar.getInstance().getTime().getTime()));
		} finally {
			jedisPool.returnResource(jedis);
		}
		return null;
	}
}

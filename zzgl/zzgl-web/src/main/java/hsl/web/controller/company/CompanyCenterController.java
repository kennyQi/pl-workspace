package hsl.web.controller.company;
import com.alibaba.fastjson.JSON;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.MD5HashUtil;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.cache.AddrProjectionCacheManager;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;
import hsl.domain.model.coupon.CouponActivity;
import hsl.domain.model.coupon.CouponStatus;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.pojo.command.CommonContact.CreateCommonContactCommand;
import hsl.pojo.command.CommonContact.UpdateCommonContactCommand;
import hsl.pojo.command.*;
import hsl.pojo.command.account.HoldUserSnapshotCommand;
import hsl.pojo.command.account.PayCodeCommand;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.dto.account.AccountDTO;
import hsl.pojo.dto.commonContact.CommonContactDTO;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.company.DepartmentDTO;
import hsl.pojo.dto.company.MemberDTO;
import hsl.pojo.dto.company.TravelDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.jp.FlightOrderDTO;
import hsl.pojo.dto.jp.status.JPOrderStatusConstant;
import hsl.pojo.dto.line.LineSnapshotDTO;
import hsl.pojo.dto.line.order.LineOrderDTO;
import hsl.pojo.dto.line.order.LineOrderTravelerDTO;
import hsl.pojo.dto.line.order.XLOrderStatusConstant;
import hsl.pojo.dto.mp.MPOrderDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.AccountException;
import hsl.pojo.exception.CommonContactException;
import hsl.pojo.exception.MPException;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.CommonContact.CommonContactQO;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;
import hsl.pojo.qo.account.AccountQO;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.company.HslDepartmentQO;
import hsl.pojo.qo.company.HslMemberQO;
import hsl.pojo.qo.company.HslTravelQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.hotel.HotelOrderQO;
import hsl.pojo.qo.jp.FlightOrderQO;
import hsl.pojo.qo.line.HslLineOrderQO;
import hsl.pojo.qo.mp.HslMPOrderQO;
import hsl.pojo.qo.user.HslSMSCodeQO;
import hsl.pojo.qo.user.HslUserBindAccountQO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.account.AccountConsumeSnapshotService;
import hsl.spi.inter.account.AccountService;
import hsl.spi.inter.account.PayCodeService;
import hsl.spi.inter.commonContact.CommonContactService;
import hsl.spi.inter.company.CompanyService;
import hsl.spi.inter.jp.JPService;
import hsl.spi.inter.line.HslLineOrderService;
import hsl.spi.inter.mp.MPOrderService;
import hsl.spi.inter.user.UserService;
import hsl.web.controller.BaseController;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @类功能说明：个人中心控制器
 * @类修改者：
 * @修改日期：2015年4月8日下午2:36:07
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年4月8日下午2:36:07
 */
@Controller
@RequestMapping("/company")
@SuppressWarnings({"unchecked","rawtypes"})
public class CompanyCenterController extends BaseController{
	@Resource
	private CompanyService companyService;
	@Resource
	private ProvinceService provinceService;
	@Resource
	private MPOrderService mpOrderService;
	@Resource
	private JPService jpService;
	@Autowired
	private UserService userService;
	@Autowired
	private AddrProjectionCacheManager addrProjectionCacheManager;
	@Autowired
	private CouponService couponService;
	@Resource
	private HslLineOrderService hslLineOrderService;
	@Resource
	private CityService cityService;

	@Resource
	private AccountService accountService;

	@Autowired
	private PayCodeService payCodeService;

	@Autowired
	private CommonContactService commonContactService;

	@Autowired
	private AccountConsumeSnapshotService accountConsumeSnapshotService;
	/**
	 * 账户中心主页
	 * @return
	 */
	@RequestMapping("/main")
	public String main(HttpServletRequest request,Model model){
		HgLogger.getInstance().debug("zhuxy","账户中心主页");
		UserDTO user=getUserBySession(request);

		model.addAttribute("user", user);
		int noPay = 0;

		// 计算未付款订单数量,门票没有代付款

		HgLogger.getInstance().debug("zhuxy","账户中心主页,查询未付款订单");
		FlightOrderQO hslJPOrderQO = new FlightOrderQO();
		hslJPOrderQO.setUserID(user.getId());
		hslJPOrderQO.setStatus(Integer.valueOf(JPOrderStatusConstant.SHOP_PAY_WAIT));
		hslJPOrderQO.setPayStatus(Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));
		List<FlightOrderDTO> jpList = jpService.queryOrder(hslJPOrderQO);
		if(jpList!=null&&jpList.size()>0){
			noPay = noPay + jpList.size();
		}

		HslLineOrderQO lineOrderQO=new HslLineOrderQO();
		lineOrderQO.setUserId(user.getId());
		lineOrderQO.setOrderStatus(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
		lineOrderQO.setPayStatus(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY);
		List<LineOrderDTO> lineOrderList= hslLineOrderService.queryList(lineOrderQO);
		if(lineOrderList!=null&&lineOrderList.size()>0){
			noPay = noPay + lineOrderList.size();
		}
		HgLogger.getInstance().info("zhuxy","账户中心主页，为付款订单数："+noPay);
		model.addAttribute("noPay", noPay);


		// 查出所有卡券的数量

		HslCouponQO hslCouponQO = new HslCouponQO();
		hslCouponQO.setUserId(user.getId());
		hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
		Pagination pagination = new Pagination();
		pagination.setPageSize(1);
		pagination.setPageNo(1);
		pagination.setCondition(hslCouponQO);
		pagination = couponService.queryPagination(pagination);
		model.addAttribute("couponNum", pagination.getTotalCount());


		// 查询出所有公司

		if(user!=null&&user.getBaseInfo()!=null&&user.getBaseInfo().getType()==2){
			HgLogger.getInstance().debug("zhuxy","账户中心主页，查询所有账户用户拥有的公司");
			HslCompanyQO hslCompanyQO = new HslCompanyQO();
			hslCompanyQO.setUserId(user.getId());//这里替换成用户的id，公司查询条件
			List<CompanyDTO> companyList = companyService.getCompanys(hslCompanyQO);
			model.addAttribute("companyList", companyList);
			HgLogger.getInstance().info("zhuxy","账户中心主页，所有公司："+JSON.toJSONString(companyList));
		}

		//查询当前用户的余额
		if(user!=null) {
			AccountQO aQo = new AccountQO();
			aQo.setUserID(user.getId());
			aQo.setConsumeOrderSnapshots(true);
			AccountDTO accountDTO = accountService.queryUnique(aQo);
			if (accountDTO != null) {
				Double consumptionBalance = 0.00;
				model.addAttribute("balance", accountDTO.getBalance());
				HgLogger.getInstance().info("chenxy", "toRecharge--查询账户余额->account->" + JSON.toJSONString(accountDTO));
				if (accountDTO != null && accountDTO.getConsumeOrderSnapshots() != null) {
					for (AccountConsumeSnapshotDTO consumeOrderSnapshots : accountDTO.getConsumeOrderSnapshots()) {
						if (consumeOrderSnapshots.getStatus() == 1) {
							consumptionBalance += consumeOrderSnapshots.getPayPrice();
						}
					}
				}
				double newBanlance = accountDTO.getBalance() - consumptionBalance;
				if (newBanlance <= 0) {
					newBanlance = 0.00;
				}
				model.addAttribute("balance", newBanlance);
			}
		}
		return "company/index.html";
	}

	/**
	 * 订单管理页面（目前只有一个订单管理页面，tab页切换两种订单管理页面）
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/orderManage")
	public String orderManage(HttpServletRequest request,Model model) throws ParseException{
		HgLogger.getInstance().debug("zhuxy", "订单管理页面");
		UserDTO user=getUserBySession(request);
		String userId = "";
		if(user!=null){
			userId = user.getId();
		}
		String image_host=SysProperties.getInstance().get("image_host");
		HgLogger.getInstance().info("renfeng","线路订单分页，图片服务器IP："+image_host);
		model.addAttribute("image_host", image_host);
		model.addAttribute("user", user);
		//获取当前查看的订单
		String sel= request.getParameter("sel");
		model.addAttribute("sel", sel);
		if(StringUtils.isBlank(sel)||sel.equals("1")){
			//查询所有的机票订单
			HgLogger.getInstance().debug("zhuxy", "订单管理页面，机票订单查询");
			String status=request.getParameter("J_status");
			model.addAttribute("J_status", status);
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setUserID(userId);
			Pagination jPagination = new Pagination();

			jPagination.setCondition(flightOrderQO);
			//设置机票的QO
			setJPQO(jPagination,request);
			HgLogger.getInstance().info("zhuxy", "订单管理页面，机票查询QO："+JSON.toJSONString(flightOrderQO));
			HashMap<String, String> jpStatusMap = JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_MAP;
			List<KeyValue> jpStatusList = JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_LIST;
			model.addAttribute("jpStatusMap", jpStatusMap);
			model.addAttribute("jpStatusList", jpStatusList);
			model.addAttribute("WAIT", JPOrderStatusConstant.SHOP_PAY_WAIT);
			model.addAttribute("NOPAY", JPOrderStatusConstant.SHOP_TICKET_NO_PAY);
			model.addAttribute("TICKET_SUCC", JPOrderStatusConstant.SHOP_TICKET_SUCC);
			model.addAttribute("BACK_SUCC_REFUND", JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC);
			model.addAttribute("nowDate", new Date());
			jPagination = jpService.queryPagination(jPagination);
			List<FlightOrderDTO> jpOrderList = BeanMapperUtils.getMapper().mapAsList(jPagination.getList(), FlightOrderDTO.class);
			HgLogger.getInstance().info("zhaows", "机票查询QO详情"+JSON.toJSONString(jpOrderList));
			model.addAttribute("jpOrderList", jpOrderList);
			model.addAttribute("hslJPOrderQO", flightOrderQO);
			model.addAttribute("jPagination",jPagination);
			return "company/order.html";
		}else if(sel.equals("3")){
			//查询所有的线路订单
			Pagination lPagination= new Pagination();
			HslLineOrderQO lineOrderQO=new HslLineOrderQO();
			lineOrderQO.setUserId(userId);
			lPagination.setCondition(lineOrderQO);
			String lineStatus=request.getParameter("L_orderStatus");
			model.addAttribute("L_orderStatus", lineStatus);
			String linePayStatus=request.getParameter("L_payStatus");
			model.addAttribute("L_payStatus", linePayStatus);
			//设置线路QO
			setLineQO(lPagination, request);
			lPagination=hslLineOrderService.queryPagination(lPagination);
			HgLogger.getInstance().info("renfeng","线路分页列表数据（lPagination）："+JSON.toJSONString(lPagination));
			System.out.println("线路分页数据："+JSON.toJSONString(lPagination));
			List<LineOrderDTO> lineOrders=(List<LineOrderDTO>) lPagination.getList();

			for(LineOrderDTO lineOrder:lineOrders){
				LineSnapshotDTO lineSnapshotDTO=lineOrder.getLineSnapshot();
				CityQo cityQo = new CityQo();
				cityQo.setId(lineSnapshotDTO.getStarting());
				City city= cityService.queryUnique(cityQo);
				lineSnapshotDTO.setCityName(city.getName());
				lineOrder.setLineSnapshot(lineSnapshotDTO);
				Set<LineOrderTravelerDTO> travelers =lineOrder.getTravelers();
				lineOrder.setTravelerList(new ArrayList<LineOrderTravelerDTO>(travelers));
			}

			HgLogger.getInstance().info("renfeng","线路分页列表数据（lineOrders）："+JSON.toJSONString(lineOrders));
			System.out.println("线路分页数据："+JSON.toJSONString(lineOrders));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date daydate=new Date();
			model.addAttribute("currentData",sdf.format(daydate));
			model.addAttribute("lineOrderList", lineOrders);
			model.addAttribute("lineOrderStatusList", XLOrderStatusConstant.SHOP_XLORDER_STATUS_LIST);
			model.addAttribute("linePayStatusList", XLOrderStatusConstant.SHOP_XLORDER_PAY_STATUS_LIST);
			model.addAttribute("linePayStatusMap", XLOrderStatusConstant.SHOP_XLORDER_PAY_STATUS_MAP);
			model.addAttribute("lineOrderStatusMap", XLOrderStatusConstant.SHOP_XLORDER_STATUS_MAP);
			model.addAttribute("lineOrderQO", lineOrderQO);
			model.addAttribute("lPagination",lPagination);
			return "company/order_xl.html";
		}

		//HgLogger.getInstance().info("zhuxy", "订单管理页面，机票订单："+JSON.toJSONString(jpOrderList));

		//查询所有的门票订单
		/*HgLogger.getInstance().debug("zhuxy", "订单管理页面，门票订单查询");
			Pagination mPagination = new Pagination();
			HslMPOrderQO hslMPOrderQO = new HslMPOrderQO();
			mPagination.setCondition(hslMPOrderQO);
			String mpStatus=request.getParameter("M_orderStatus");
			model.addAttribute("M_orderStatus", mpStatus);
			String mpPayStatus=request.getParameter("M_payStatus");
			model.addAttribute("M_payStatus", mpPayStatus);
			//设置门票的QO
			setMPQO(mPagination,request);
			HgLogger.getInstance().info("zhuxy", "订单管理页面，门票订单查询");
			hslMPOrderQO.setUserId(userId);
			hslMPOrderQO.setDetail(true);
			hslMPOrderQO.setWithPolicy(true);
			hslMPOrderQO.setWithScenicSpot(true);
			hslMPOrderQO.setScenicSpotsNameLike(true);
			try {
				//map内容（dto:List<MPOrderDTO>,count:总条数）
				mPagination = mpOrderService.queryPagination(mPagination);
				Map mpOrderMap = new HashMap();
				mpOrderMap.put("dto", mPagination.getList());
				mpOrderMap.put("count", mPagination.getTotalCount());
				model.addAttribute("mpOrderMap", mpOrderMap);
				HgLogger.getInstance().info("zhuxy", "订单管理页面，门票订单："+JSON.toJSONString(mpOrderMap));
			} catch (Exception e) {
				e.printStackTrace();
				HgLogger.getInstance().error("zhangka", "CompanyCenterController->orderManage->exception:" + HgLogger.getStackTrace(e));
				model.addAttribute("mpNotFind", e.getMessage());
				Map mpOrderMap = new HashMap();
				mpOrderMap.put("dto",new ArrayList<MPOrderDTO>());
				model.addAttribute("mpOrderMap", mpOrderMap);
			}
			model.addAttribute("hslMPOrderQO", hslMPOrderQO);
			model.addAttribute("mPagination",mPagination);*/


		return "company/order.html";
	}

	/**
	 * @方法功能说明：设置酒店QO
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月10日上午10:39:03
	 * @修改内容：
	 * @参数：@param hotelPagination
	 * @参数：@param request
	 * @return:void
	 * @throws
	 */
	private void setHotelQO(final Pagination hotelPagination,final HttpServletRequest request){
		HotelOrderQO hotelOrderQO=(HotelOrderQO) hotelPagination.getCondition();
		if(StringUtils.isNotBlank(request.getParameter("dealerOrderNo"))){
			hotelOrderQO.setDealerOrderNo(request.getParameter("dealerOrderNo"));
		}

		if(StringUtils.isNotBlank(request.getParameter("showStatus"))){
			hotelOrderQO.setShowStatus(request.getParameter("showStatus"));
		}
		String pageSize = request.getParameter("pageSize");
		String pageNo = request.getParameter("page");
		if(!StringUtils.isBlank(pageNo)){
			hotelPagination.setPageNo(Integer.valueOf(pageNo));
		}
		if(!StringUtils.isBlank(pageSize)){
			hotelPagination.setPageSize(Integer.valueOf(pageSize));
		}else{
			hotelPagination.setPageSize(5);
		}
	}
	/**
	 * @方法功能说明：设置线路QO
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月10日下午5:51:12
	 * @修改内容：
	 * @参数：@param lPagination
	 * @参数：@param request
	 * @return:void
	 * @throws
	 */
	private void setLineQO(final Pagination lPagination,final HttpServletRequest request) {
		HslLineOrderQO hslLineOrderQO=(HslLineOrderQO) lPagination.getCondition();
		String orderNum = request.getParameter("L_orderId");
		String lineNumber = request.getParameter("L_number");
		String beginTime = request.getParameter("L_beginTime");
		String endTime = request.getParameter("L_endTime");
		String payStatus = request.getParameter("L_payStatus");
		String orderStatus = request.getParameter("L_orderStatus");
		String pageSize = request.getParameter("pageSize");
		String pageNo = request.getParameter("page");
		if(!StringUtils.isBlank(orderNum)){
			hslLineOrderQO.setDealerOrderNo(orderNum.trim());;
		}
		if(!StringUtils.isBlank(lineNumber)){
			hslLineOrderQO.setLineNumber(lineNumber.trim());
		}
		if(!StringUtils.isBlank(beginTime)){
			hslLineOrderQO.setStartTime(beginTime.trim());;
		}
		if(!StringUtils.isBlank(endTime)){
			hslLineOrderQO.setEndTime(endTime.trim());;
		}
		if(!StringUtils.isBlank(payStatus)){
			hslLineOrderQO.setPayStatus(payStatus);
		}
		if(!StringUtils.isBlank(orderStatus)){
			hslLineOrderQO.setOrderStatus(orderStatus);
		}
		if(!StringUtils.isBlank(pageNo)){
			lPagination.setPageNo(Integer.valueOf(pageNo));
		}
		if(!StringUtils.isBlank(pageSize)){
			lPagination.setPageSize(Integer.valueOf(pageSize));
		}else{
			lPagination.setPageSize(5);
		}
	}
	/**
	 * 获取门票查询信息
	 * @param request
	 */
	private void setMPQO(final Pagination mPagination,final HttpServletRequest request) {
		HslMPOrderQO hslMPOrderQO = (HslMPOrderQO)mPagination.getCondition();
		String orderNum = request.getParameter("M_orderId");
		String scenicSpotsName = request.getParameter("M_scenicSpotsName");
		String beginTime = request.getParameter("M_beginTime");
		String endTime = request.getParameter("M_EndTime");
		String bookMan = request.getParameter("M_bookMan");
		String player = request.getParameter("M_player");
		String status = request.getParameter("M_orderStatus");
		String pageSize = request.getParameter("pageSize");
		String pageNo = request.getParameter("page");
		if(!StringUtils.isBlank(orderNum)){
			hslMPOrderQO.setDealerOrderCode(orderNum);
		}
		if(!StringUtils.isBlank(scenicSpotsName)){
			hslMPOrderQO.setScenicSpotsName(scenicSpotsName);
		}
		if(!StringUtils.isBlank(beginTime)){
			hslMPOrderQO.setBeginTime(beginTime);
		}
		if(!StringUtils.isBlank(endTime)){
			hslMPOrderQO.setEndTime(endTime);
		}
		if(!StringUtils.isBlank(bookMan)){
			hslMPOrderQO.setBookMan(bookMan);
		}
		if(!StringUtils.isBlank(player)){
			hslMPOrderQO.setPlayer(player);
		}
		if(!StringUtils.isBlank(status)){
			hslMPOrderQO.setOrderStatus(Integer.valueOf(status));
		}
		if(!StringUtils.isBlank(pageNo)){
			mPagination.setPageNo(Integer.valueOf(pageNo));
		}else{
			mPagination.setPageNo(1);
		}
		if(!StringUtils.isBlank(pageSize)){
			mPagination.setPageSize(Integer.valueOf(pageSize));
		}else{
			mPagination.setPageSize(5);
		}

	}

	/**
	 * 获取机票的查询信息
	 * @param request
	 * @throws ParseException
	 */
	private void setJPQO(final Pagination jPagination,final HttpServletRequest request) throws ParseException {
		FlightOrderQO flightOrderQO = (FlightOrderQO)jPagination.getCondition();
		String orderNum = request.getParameter("J_orderNum");
		String beginTime = request.getParameter("J_beginTime");
		String endTime = request.getParameter("J_endTime");
		String status = request.getParameter("J_status");
		String bookMan = request.getParameter("J_bookMan");
		//String pnr = request.getParameter("J_PNR");
		String passenger = request.getParameter("J_Passenger");
		String pageSize = request.getParameter("pageSize");
		String pageNo = request.getParameter("page");
		if(!StringUtils.isBlank(orderNum)){
			flightOrderQO.setOrderNO(orderNum);
		}
		flightOrderQO.setBeginDateTime(beginTime);
		flightOrderQO.setEndDateTime(endTime);
		if(!StringUtils.isBlank(status)){
			if(status.startsWith("sp")){
				flightOrderQO.setSpecial(status);
				if(status.equals("sp1")){//特殊状态1——已取消
					Integer[] sts = {Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_CANCEL)};
					flightOrderQO.setSts(sts);
					Integer[] paySts = {Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_NO_PAY),Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC)};
					flightOrderQO.setPaySts(paySts);
				}
				if(status.equals("sp2")){//特殊状态2——出票失败待退款
					Integer[] sts = {Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_FAIL)};
					flightOrderQO.setSts(sts);
					Integer[] paySts = {Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT),Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_TO_BE_BACK_WAIT)};
					flightOrderQO.setPaySts(paySts);
				}
			}else if((Integer.valueOf(status)!=0)){
				flightOrderQO.setStatus(Integer.valueOf(JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_TO_TICKET_MAP.get(status)));
				flightOrderQO.setPayStatus(Integer.valueOf(JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_TO_PAY_MAP.get(status)));
			}
		}
		//下单人姓名不需要了
		if(!StringUtils.isBlank(bookMan)){
			flightOrderQO.setLoginName(bookMan);
		}
		/*if(!StringUtils.isBlank(pnr)){
			hslJPOrderQO.setPnr(pnr);
		}*/
		if(!StringUtils.isBlank(passenger)){
			flightOrderQO.setActName(passenger);
		}
		if(!StringUtils.isBlank(pageNo)){
			jPagination.setPageNo(Integer.valueOf(pageNo));
		}else{
			jPagination.setPageNo(1);
		}
		if(!StringUtils.isBlank(pageSize)){
			jPagination.setPageSize(Integer.valueOf(pageSize));
		}else{
			jPagination.setPageSize(5);
		}
		//hslJPOrderQO.setPageNo(1);
		//hslJPOrderQO.setPageSize(Integer.MAX_VALUE);
	}

	/**
	 * 取消机票
	 * @return
	 *//*
	@RequestMapping("/orderManage/cancelJp")
	public Object cancelJpOrder(@RequestParam(value="id",required = true) String id,
			  @RequestParam(value = "ticketNumbers", required = false) String[] ticketNumbers, 
			  @RequestParam(value = "refundType", required = false) String refundType, 
			  @RequestParam(value = "backPolicy", required = false) String backPolicy, 
			  @RequestParam(value = "backAmount", required = false) String backAmount, 
			  @RequestParam(value = "cancelReason", required = false) String cancelReason,
			  RedirectAttributes attr,
			  HttpServletRequest request){
		JPCancelTicketCommand command = new JPCancelTicketCommand();
		command.setOrderId(id);
		if(StringUtils.isBlank(cancelReason)){
			cancelReason = "用户自己退票";
		}
		command.setCancelReason(cancelReason);
		JPOrderDTO jpOrderDTO = jpService.cancelTicket(command);
		if (jpOrderDTO == null){
			attr.addFlashAttribute("cancelMsg", "订单取消失败");
		}
		else 	{
			attr.addFlashAttribute("cancelMsg", "订单取消成功");
		}
		return new RedirectView("/company/orderManage",true); 
	}*/

	/**
	 * 取消门票
	 * @return
	 */
	@RequestMapping("/orderManage/cancelMp")
	public Object cancelMpOrder(@RequestParam(value="id",required = true) String id,RedirectAttributes attr){
		MPOrderCancelCommand command = new MPOrderCancelCommand();
		command.setOrderId(id);
		command.setReason(4);//其他原因
		mpOrderService.cancelMPOrder(command);
		return new RedirectView("/company/orderManage?sel=2",true);
	}

	/**
	 * 机票订单详细页面（这个及以下的也可能只返回数据）
	 * @return
	 */
	@RequestMapping("/orderManage/airplane")
	public String airplane(@RequestParam(value="id",required=true) String id,Model model,HttpServletRequest request){
		HgLogger.getInstance().debug("zhuxy", "机票订单详细页面");
		UserDTO user = getUserBySession(request);
		try{
			//如果没有条件，查询所有的机票订单
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setId(id);
			FlightOrderDTO flightOrderDTO = jpService.queryOrderDetail(flightOrderQO);
			if(!(flightOrderDTO.getJpOrderUser().getUserId().equals(user.getId()))){
				return "/error/error.jsp";
			}
			HashMap<String, String> jpStatusMap = JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_MAP;
			model.addAttribute("jpStatusMap", jpStatusMap);
			if(flightOrderDTO!=null&&StringUtils.isNotBlank(flightOrderDTO.getOrderType())) {
				if (!flightOrderDTO.getOrderType().trim().equals("4")){
					model.addAttribute("flightOrderDTO", flightOrderDTO);
					HslCouponQO qo = new HslCouponQO();
					if (JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC.equals(flightOrderDTO.getPayStatus().toString())) {
						qo.setUseEvent(true);
						HgLogger.getInstance().info("zhuxy", newLog("查询订单详情", "已退款的情况查询卡券"));
					}
					qo.setOrderId(flightOrderDTO.getOrderNO());
					List<CouponDTO> couponDTOs = couponService.queryList(qo);
					Double couponPrice = 0d;
					if (couponDTOs != null && couponDTOs.size() > 0) {
						for (CouponDTO coupon : couponDTOs) {
							couponPrice = couponPrice + coupon.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
						}
					}
					//查询余额使用
					AccountConsumeSnapshotQO bQo = new AccountConsumeSnapshotQO();
				/*查询帐号消费记录时判断机票支付状态 如果是 已退款 则按照 退款订单ID即实体的id查询*/
					if (flightOrderDTO.getPayStatus() == Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC)) {
						bQo.setRefundOrderId(flightOrderDTO.getId());
					} else {
						bQo.setOrderId(flightOrderDTO.getOrderNO());
					}
					bQo.setOrderType(AccountConsumeSnapshot.ORDERTYPE_JP);
					bQo.setStatus(flightOrderDTO.getPayStatus());//传订单的支付状态
					HgLogger.getInstance().info("zhuxy", "airplane--bQo" + JSON.toJSONString(bQo));
					List<AccountConsumeSnapshotDTO> accountList = accountConsumeSnapshotService.queryList(bQo);
					HgLogger.getInstance().info("zhuxy", "airplane--查询消费快照" + JSON.toJSONString(accountList));
					double balance = 0d;
					double refundPrice = 0d;
					if (accountList != null && accountList.size() > 0) {
						for (AccountConsumeSnapshotDTO account : accountList) {
							if (account.getRefundPrice() != null) {
								refundPrice += account.getRefundPrice();
							}
							balance += account.getPayPrice();
						}
					}
					model.addAttribute("balance", balance);
					model.addAttribute("refundPrice", refundPrice);
					model.addAttribute("couponPrice", couponPrice);
					model.addAttribute("NOPAY", JPOrderStatusConstant.SHOP_TICKET_NO_PAY);
					model.addAttribute("REBACK", JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC);
					model.addAttribute("WAIT", JPOrderStatusConstant.SHOP_PAY_WAIT);
					HgLogger.getInstance().info("zhuxy", "机票订单详细页面，机票订单详情：" + JSON.toJSONString(flightOrderDTO));
				}
			}else{
				HgLogger.getInstance().info("zhuxy", "机票订单详细页面，机票订单详情：（此用户）没有此机票订单");
				throw new RuntimeException("权限错误");
			}
		}catch(Exception e){
			HgLogger.getInstance().debug("zhuxy", "查询机票订单详情出错"+HgLogger.getStackTrace(e));
			return "/error/error.jsp";
		}
		return "company/airplane.html";
	}

	/**
	 * 门票订单详细页面
	 * @return
	 */
	@RequestMapping("/orderManage/ticket")
	public String ticket(@RequestParam(value="id",required=true) String id,
						 HttpServletRequest request,
						 Model model){
		HgLogger.getInstance().debug("zhuxy", "门票订单详情页面");
		UserDTO user = getUserBySession(request);
		HslMPOrderQO hslMPOrderQO = new HslMPOrderQO();
		hslMPOrderQO.setDealerOrderCode(id);
		hslMPOrderQO.setDetail(true);
		hslMPOrderQO.setWithPolicy(true);
		hslMPOrderQO.setWithScenicSpot(true);
		hslMPOrderQO.setScenicSpotsNameLike(true);
		hslMPOrderQO.setUserId(user.getId());
		try {
			//map内容（dto:List<MPOrderDTO>,count:总条数）
			Map mpOrderMap = mpOrderService.queryMPOrderList(hslMPOrderQO);
			model.addAttribute("mpOrderDetail", ((List<MPOrderDTO>)mpOrderMap.get("dto")).get(0));
			HgLogger.getInstance().info("zhuxy", "门票订单详情页面,详情信息："+JSON.toJSONString(((List<MPOrderDTO>)mpOrderMap.get("dto")).get(0)));
		} catch (MPException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhangka", "CompanyCenterController->ticket->exception:" + HgLogger.getStackTrace(e));
			model.addAttribute("mpNotFind", e.getMessage());
			throw new RuntimeException("权限错误");
		}
		return "company/order_view.html";
	}
	@RequestMapping("/orderManage/lineOrderDetails")
	public String lineOrderDetails(@RequestParam(value="id",required=true) String id,HttpServletRequest request,Model model){
		HgLogger.getInstance().debug("chenxy", "线路订单详情页面");
		UserDTO user = getUserBySession(request);
		HslLineOrderQO hslLineOrderQO=new HslLineOrderQO();
		hslLineOrderQO.setDealerOrderNo(id);
		try {
			// map内容（dto:List<MPOrderDTO>,count:总条数）
			LineOrderDTO lineOrderDTO = hslLineOrderService.queryUnique(hslLineOrderQO);
			if(!lineOrderDTO.getLineOrderUser().getUserId().equals(user.getId())){
				return "/error/error.jsp";
			}
			LineSnapshotDTO lineSnapshotDTO = lineOrderDTO.getLineSnapshot();
			CityQo cityQo = new CityQo();
			cityQo.setId(lineSnapshotDTO.getStarting());
			City city = cityService.queryUnique(cityQo);
			lineSnapshotDTO.setCityName(city.getName());
			lineOrderDTO.setLineSnapshot(lineSnapshotDTO);
			HslCouponQO qo = new HslCouponQO();
			qo.setOrderId(lineOrderDTO.getBaseInfo().getDealerOrderNo());
			List<CouponDTO> couponDTOs = couponService.queryList(qo);
			Double couponPrice = 0d ;
			if(couponDTOs!=null&&couponDTOs.size()>0){
				for(CouponDTO coupon : couponDTOs){
					try{
						couponPrice = couponPrice + coupon.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
					}catch(NullPointerException e){
						HgLogger.getInstance().debug("chenxy", "线路订单详细页面：卡券id为"+coupon.getId()+"的面值查不出来");
					}
				}
			}

			//查询余额使用
			AccountConsumeSnapshotQO bQo=new AccountConsumeSnapshotQO();
			bQo.setOrderId(lineOrderDTO.getBaseInfo().getDealerOrderNo());
			bQo.setOrderType(AccountConsumeSnapshot.ORDERTYPE_XL);
			bQo.setStatus(lineOrderDTO.getStatus().getPayStatus());
			List<AccountConsumeSnapshotDTO> accountList=accountConsumeSnapshotService.queryList(bQo);
			double balance=0d;
			if(accountList!=null&&accountList.size()>0){
				for(AccountConsumeSnapshotDTO account:accountList){
					balance+=account.getPayPrice();
				}
			}
			model.addAttribute("balance", balance);
			model.addAttribute("couponPrice", couponPrice);
			model.addAttribute("lineOrderDTO", lineOrderDTO);
			model.addAttribute("linePayStatusMap",XLOrderStatusConstant.SHOP_XLORDER_PAY_STATUS_MAP);
			model.addAttribute("lineOrderStatusMap",XLOrderStatusConstant.SHOP_XLORDER_STATUS_MAP);
			HgLogger.getInstance().info("chenxy","线路订单详情页面,详情信息：" + JSON.toJSONString(lineOrderDTO));
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy","CompanyCenterController->lineOrderDetails:"+ HgLogger.getStackTrace(e));
			model.addAttribute("mpNotFind", e.getMessage());
		}
		return "company/lineOrder_details.html";
	}
	/**
	 * 差旅管理
	 * @return
	 */
	@RequestMapping("/travelManage")
	public String travel(HttpServletRequest request,HslTravelQO hslTravelQO,Model model){
		try {
			HgLogger.getInstance().debug("zhuxy", "差旅管理页面");
			UserDTO user=getUserBySession(request);
			model.addAttribute("user", user);
			String userId = user.getId();
			hslTravelQO.setId(userId);
			/**
			 * 查询所有部门
			 */
			HgLogger.getInstance().debug("zhuxy", "差旅管理页面，查询所有部门");
			if("0".equals(hslTravelQO.getDepartmentId())){
				hslTravelQO.setDepartmentId(null);
			}
			/**
			 * 查询所有成员
			 */
			HgLogger.getInstance().debug("zhuxy", "差旅管理页面，查询所有成员");
			if("0".equals(hslTravelQO.getMemberId())){
				hslTravelQO.setMemberId(null);
			}
			/**
			 * 查询所有公司
			 */
			HgLogger.getInstance().debug("zhuxy", "差旅管理页面，查询所有公司");
			if("0".equals(hslTravelQO.getCompanyId())){
				hslTravelQO.setCompanyId(null);
			}

			if(StringUtils.isNotBlank(request.getParameter("page"))){
				hslTravelQO.setPageNo(Integer.parseInt(request.getParameter("page")));
			}else{
				hslTravelQO.setPageNo(1);
			}
			if(StringUtils.isNotBlank(request.getParameter("pageSize"))){
				hslTravelQO.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
			}else{
				hslTravelQO.setPageSize(5);
			}

			model.addAttribute("hslTravelQO",hslTravelQO);
			Pagination pagination = companyService.getTravelInfoPage(hslTravelQO);
			model.addAttribute("travelList", pagination.getList());
			model.addAttribute("pagination", pagination);
			HgLogger.getInstance().info("zhaows", "差旅管理页面,差旅列表："+JSON.toJSONString(pagination));
			//计算总价
			if(pagination.getList()!=null&&pagination.getList().size()>0){
				Double totalPrice = 0d;
				for(TravelDTO item : (List<TravelDTO>)pagination.getList()){
					totalPrice = item.getPrice()+totalPrice;
				}
				model.addAttribute("totalPrice", totalPrice);
			}

			//获取用户拥有的公司
			//查询所有公司的数据
			HslCompanyQO hslCompanyQO = new HslCompanyQO();
			hslCompanyQO.setUserId(userId);//这里替换成用户的id，公司查询条件

			List<CompanyDTO> companyList = companyService.getCompanys(hslCompanyQO);
			model.addAttribute("companyList", companyList);
			//保存查询条件
			model.addAttribute("hslTravelQO", hslTravelQO);
		} catch (NullPointerException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhuxy",newLog("差旅管理出现异常", HgLogger.getStackTrace(e)));
		}
		return "company/travel.html";
	}

	/**
	 * 导出差旅excel
	 * @return
	 */
	@RequestMapping("/travelManage/excel")
	public String getTravelExcel(HttpServletRequest request,HslTravelQO hslTravelQO,HttpServletResponse response){
		HgLogger.getInstance().debug("zhuxy", "导出差旅管理Excel");
		UserDTO user=getUserBySession(request);
		String userId = user.getId();
		hslTravelQO.setId(userId);
		hslTravelQO.setPageNo(1);
		hslTravelQO.setPageSize(Integer.MAX_VALUE);
		/**
		 * 查询所有部门
		 */
		HgLogger.getInstance().debug("zhuxy", "差旅管理导出页面，查询所有部门");
		if("0".equals(hslTravelQO.getDepartmentId())){
			hslTravelQO.setDepartmentId(null);
		}
		/**
		 * 查询所有成员
		 */
		HgLogger.getInstance().debug("zhuxy", "差旅管理导出页面，查询所有成员");
		if("0".equals(hslTravelQO.getMemberId())){
			hslTravelQO.setMemberId(null);
		}
		/**
		 * 查询所有公司
		 */
		HgLogger.getInstance().debug("zhuxy", "差旅管理导出页面，查询所有公司");
		if("0".equals(hslTravelQO.getCompanyId())){
			hslTravelQO.setCompanyId(null);
		}
		OutputStream os;
		WritableWorkbook workbook;
		//设置内容格式
		response.setHeader("Content-Type","application/x-xls;charset=utf-8" );
		//这个用来提示下载的文件名
		response.setHeader("Content-Disposition", "attachment; filename="+"travel.xls");
		//jxl前面是列号，后面行号
		try {
			os = response.getOutputStream();
			workbook = Workbook.createWorkbook(os);
			Pagination pagination = companyService.getTravelInfoPage(hslTravelQO);
			List<TravelDTO> travelList = (List<TravelDTO>)pagination.getList();
			//			List<TravelDTO> travelList = companyService.getTravelInfo(hslTravelQO);
			HgLogger.getInstance().info("zhuxy", "导出差旅管理Excel，要导出的差旅列表："+JSON.toJSONString(travelList));
			//			List<TravelDTO> travelList = mockTravelList();
			if(travelList.size()>0){
				WritableSheet ws = workbook.createSheet("差旅记录表", 0);
				Label head1 = new Label(0,0,"订单号");
				ws.addCell(head1);
				Label head2 = new Label(1,0,"公司");
				ws.addCell(head2);
				Label head3 = new Label(2,0,"部门");
				ws.addCell(head3);
				Label head4 = new Label(3,0,"职位");
				ws.addCell(head4);
				Label head5 = new Label(4,0,"差旅项目");
				ws.addCell(head5);
				Label head6 = new Label(5,0,"目的地");
				ws.addCell(head6);
				Label head7 = new Label(6,0,"金额");
				ws.addCell(head7);
				int i = 1;
				Double totalPirce = 0d;
				for(TravelDTO travel : travelList){
					Label orderNum = new Label(0, i, travel.getOrderNum());
					ws.addCell(orderNum);
					Label companyName = new Label(1,i,travel.getCompanyName());
					ws.addCell(companyName);
					Label deptName = new Label(2,i,travel.getDeptName());
					ws.addCell(deptName);
					Label job = new Label(3,i,travel.getJob());
					ws.addCell(job);
					Label type = new Label(4, i, travel.getProjectType()==1?"机票":"门票");
					ws.addCell(type);
					Label destination = new Label(5,i,travel.getDestination());
					ws.addCell(destination);
					Label price = new Label(6,i,travel.getPrice().toString());
					ws.addCell(price);
					totalPirce = totalPirce + travel.getPrice();
					//					Label job = new Label(3,i,travel.getJob());
					//					ws.addCell(job);
					i++;
				}
				//添加合计金额
				Label orderNum = new Label(0,i,"合计");
				ws.addCell(orderNum);
				Label price = new Label(6,i,totalPirce.toString());
				ws.addCell(price);

				workbook.write();
				workbook.close();
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhangka", "CompanyCenterController->getTravelExcel->exception:" + HgLogger.getStackTrace(e));
		}
		return null;
	}


	/**
	 * 部门员工通讯录
	 * @return
	 */
	@RequestMapping("/organizeManage")
	public String organize(@RequestParam(value="id",required=false)String companyId,
						   @RequestParam(value="deptId",required=false)String deptId,
						   HttpServletRequest request,
						   Model model){
		HgLogger.getInstance().debug("zhuxy", newHeader("部门员工通讯录页面开始"));
		UserDTO user=getUserBySession(request);
		if(user!=null&&user.getBaseInfo().getType()==1){
			HgLogger.getInstance().error("zhuxy", newLog("权限错误", "个人用户尝试刷新企业用户的页面"));
			throw new RuntimeException("权限错误");
		}
		model.addAttribute("user", user);
		String userId = user.getId();

		//查询所有公司的数据
		HgLogger.getInstance().debug("zhuxy", "部门员工通讯录页面，查询公司数据");
		HslCompanyQO hslCompanyQO = new HslCompanyQO();
		hslCompanyQO.setUserId(userId);//这里替换成用户的id，公司查询条件

		List<CompanyDTO> companyList = companyService.getCompanys(hslCompanyQO);
		model.addAttribute("companyList", companyList);

		//查询指定公司的部门的数据
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		try{
			if(companyId==null){
				//没有传入公司ID，默认选中第一个公司
				HgLogger.getInstance().debug("zhuxy", "部门员工通讯录页面，没有传入公司ID，默认选中第一个公司");
				if(companyList!=null&&companyList.size()>0){
					model.addAttribute("defaultCompanyId", companyList.get(0).getId());
					hslCompanyQO.setId(companyList.get(0).getId());
					hslDepartmentQO.setCompanyQO(hslCompanyQO);//部门查询条件
				}
			}else{
				model.addAttribute("defaultCompanyId",companyId);
				hslCompanyQO.setId(companyId);
				hslDepartmentQO.setCompanyQO(hslCompanyQO);//部门查询条件
			}
			if(hslDepartmentQO.getCompanyQO()!=null){

				List<DepartmentDTO> departmentList =  companyService.getDepartments(hslDepartmentQO);
				model.addAttribute("departmentList", departmentList);
				if(departmentList!=null&&departmentList.size()>0){
					HgLogger.getInstance().debug("zhuxy", "部门员工通讯录页面，部门存在，查询第一个部门下的员工");
					//查询第一个部门的员工
					HslMemberQO hslMemberQO = new HslMemberQO();
					if(StringUtils.isNotBlank(deptId)){
						hslMemberQO.setDepartmentId(deptId);
						model.addAttribute("deptId", deptId);
					}else{
						hslMemberQO.setDepartmentId(departmentList.get(0).getId());//成员查询呢条件
						model.addAttribute("deptId", departmentList.get(0).getId());
					}
					Pagination memberPagination = new Pagination();

					if(StringUtils.isNotBlank(request.getParameter("page"))){
						memberPagination.setPageNo(Integer.parseInt(request.getParameter("page")));
					}else{
						memberPagination.setPageNo(1);
					}
					if(StringUtils.isNotBlank(request.getParameter("pageSize"))){
						memberPagination.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
					}else{
						memberPagination.setPageSize(5);
					}
					memberPagination.setCondition(hslMemberQO);

					Pagination memberPage = companyService.getMemberPagination(memberPagination);
					model.addAttribute("memberPage", memberPage);

				}
			}
		}catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy", "查询部门通讯录出错"+HgLogger.getStackTrace(e));
		}
		HgLogger.getInstance().debug("zhuxy", newHeader("部门员工通讯录页面结束"));
		return "company/organize.html";
	}

	/**
	 * 导入成员列表
	 * @throws IOException
	 *
	 */
	@RequestMapping("/organizeManage/upload")
	public RedirectView uploadMembers(@RequestParam(value = "file", required = false) MultipartFile file,
									  @RequestParam(value="deptId") String deptId,
									  RedirectAttributes attr,
									  HttpServletRequest request,
									  Model model) throws Exception{
		HgLogger.getInstance().debug("zhuxy", newHeader("部门员工通讯录页面，导入成员开始"));
		Pattern namep = Pattern.compile("[\u4E00-\u9FA5]{1,10}");
		Pattern jobp = Pattern.compile("[\u4E00-\u9FA5]{1,20}");
		Pattern telp = Pattern.compile("0?(13|15|18)[0-9]{9}");
		Pattern idp = Pattern.compile("[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])(\\d{4}$|\\d{3}[Xx]{1})");
		int i = 1;
		int j = 0;
		//获取文件输入流
		try {
			InputStream  is = file.getInputStream();
			Workbook wb = Workbook.getWorkbook(is);
			//开始校验excel
			Sheet se = wb.getSheet(0);
			List<CreateMemberCommand> cmds = new ArrayList<CreateMemberCommand>();
			CreateMemberCommand cmd ;
			String name;
			String job;
			String tel;
			String id;
			while(i<se.getRows()&&!StringUtils.isBlank(se.getCell(0,i).getContents())){
				cmd = new CreateMemberCommand();
				cmd.setDeptId(deptId);
				name = se.getCell(0,i).getContents();
				Matcher nm =  namep.matcher(name);
				if(nm.matches()){
					cmd.setName(name);
				}else{
					j=0;
					throw new Exception();
				}
				job = se.getCell(1,i).getContents();
				Matcher jm =  jobp.matcher(job);
				if(jm.matches()){
					cmd.setJob(job);
				}else{
					j=1;
					throw new Exception();
				}
				tel = se.getCell(2,i).getContents();
				Matcher tm =  telp.matcher(tel);
				if(tm.matches()){
					cmd.setMobilePhone(tel);
				}else{
					j=2;
					throw new Exception();
				}
				id = se.getCell(3,i).getContents();
				Matcher im =  idp.matcher(id);
				if(im.matches()){
					cmd.setCertificateID(id);
				}else{
					j=3;
					throw new Exception();
				}
				cmds.add(cmd);
				i++;
			}
			HgLogger.getInstance().info("zhuxy",newLog("成员列表的数据信息", JSON.toJSONString(cmds)));
			companyService.importMembers(cmds);
			model.addAttribute("msg", "success");
		} catch (Exception e1) {
			e1.printStackTrace();
			attr.addFlashAttribute("msg", "导入数据出错！！！！");
			attr.addFlashAttribute("row", i+1);
			attr.addFlashAttribute("col", j+1);
			HgLogger.getInstance().error("zhuxy", newLog("错误信息", HgLogger.getStackTrace(e1)));
		}
		HgLogger.getInstance().debug("zhuxy", newHeader("部门员工通讯录页面，导入成员开始"));
		return new RedirectView("/company/organizeManage",true);
	}
	/**
	 * 导出成员列表
	 * @return
	 */
	@RequestMapping("/organizeManage/excel")
	public String getOrganizeExcel(HttpServletResponse response,@RequestParam("id") String deptId){
		HgLogger.getInstance().debug("zhuxy", "部门员工通讯录页面，导出成员");
		HslMemberQO hslMemberQO = new HslMemberQO();
		hslMemberQO.setDepartmentId(deptId);
		List<MemberDTO> memberList = companyService.getMembers(hslMemberQO);
		HgLogger.getInstance().info("zhuxy", "部门员工通讯录页面，成员列表："+JSON.toJSONString(memberList));
		OutputStream os;
		WritableWorkbook workbook;
		//设置内容格式
		response.setHeader("Content-Type","application/x-xls;charset=utf-8" );
		//这个用来提示下载的文件名
		response.setHeader("Content-Disposition", "attachment; filename="+"members.xls");
		//jxl前面是列号，后面行号
		try {
			os = response.getOutputStream();
			workbook = Workbook.createWorkbook(os);
			if(memberList.size()>0){
				WritableSheet ws = workbook.createSheet("差旅记录表", 0);
				Label head1 = new Label(0,0,"姓名");
				ws.addCell(head1);
				Label head2 = new Label(1,0,"职务");
				ws.addCell(head2);
				Label head3 = new Label(2,0,"手机");
				ws.addCell(head3);
				Label head4 = new Label(3,0,"身份证号");
				ws.addCell(head4);
				int i = 1;
				//				Double totalPirce = 0d;
				for(MemberDTO item : memberList){
					Label name = new Label(0, i, item.getName());
					ws.addCell(name);
					Label companyName = new Label(1,i,item.getJob());
					ws.addCell(companyName);
					Label deptName = new Label(2,i,item.getMobilePhone());
					ws.addCell(deptName);
					Label job = new Label(3,i,item.getCertificateID());
					ws.addCell(job);
					i++;
				}
				workbook.write();
				workbook.close();
				os.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhangka", "CompanyCenterController->getOrganizeExcel->exception:" + HgLogger.getStackTrace(e));
		}
		return null;
	}


	/**
	 * 根据部门Id获取员工
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value="/organizeManage/getMembers")
	@ResponseBody
	public String getMembers(@RequestParam("deptId") String deptId){
		HgLogger.getInstance().debug("zhuxy", "Ajax查询员工数据");
		HslMemberQO hslMemberQO = new HslMemberQO();
		hslMemberQO.setDepartmentId(deptId);
		List<MemberDTO> memberList = companyService.getMembers(hslMemberQO);
		HgLogger.getInstance().info("zhuxy", "Ajax查询员工数据，成员列表:"+JSON.toJSONString(memberList));
		return JSON.toJSONString(memberList);
	}
	/**
	 * @方法功能说明：根据部门ID 查询成员
	 * @修改者名字：zhuxy
	 * @修改时间：2015年4月8日下午2:33:44
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/organizeManage/members")
	@ResponseBody
	public String getMembers(Model model,HttpServletRequest request){
		String pageSize = request.getParameter("pageSize");
		String pageNo = request.getParameter("pageNo");
		String deptId = request.getParameter("deptId");
		HslMemberQO hslMemberQO = new HslMemberQO();
		hslMemberQO.setDepartmentId(deptId);
		Pagination pagination = new Pagination();
		pagination.setCondition(hslMemberQO);
		try{
			pagination.setPageNo(Integer.valueOf(pageNo));
			pagination.setPageSize(Integer.valueOf(pageSize));
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		pagination = companyService.getMemberPagination(pagination);
		return JSON.toJSONString(pagination);
	}

	/**
	 * 根据公司Id获取部门
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value="/organizeManage/getDepartments")
	@ResponseBody
	public String getDepartments(@RequestParam("companyId") String companyId){
		HgLogger.getInstance().debug("zhuxy", "Ajax查询部门数据");
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		HslCompanyQO hslCompanyQO = new HslCompanyQO();
		hslCompanyQO.setId(companyId);
		hslDepartmentQO.setCompanyQO(hslCompanyQO);
		List<DepartmentDTO> departmentList = companyService.getDepartments(hslDepartmentQO);
		HgLogger.getInstance().info("zhuxy", "Ajax查询员工数据,部门数据："+JSON.toJSONString(departmentList));
		return JSON.toJSONString(departmentList);
	}


	/**
	 * 添加公司
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/organizeManage/addCompany")
	public RedirectView addCompany(HttpServletRequest request,CreateCompanyCommand createCompanyCommand,Model model) throws UnsupportedEncodingException{
		createCompanyCommand.setCompanyName(java.net.URLDecoder.decode(createCompanyCommand.getCompanyName(),"UTF-8"));
		HgLogger.getInstance().debug("zhuxy", "添加公司");
		UserDTO user=getUserBySession(request);
		String userId = user.getId();
		//放入用户id
		createCompanyCommand.setUserId(userId);
		companyService.addCompany(createCompanyCommand);
		return new RedirectView("/company/organizeManage", true);
	}

	/**
	 * 添加部门
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/organizeManage/addDepartMent")
	@ResponseBody
	public String addDepartMent(CreateDepartmentCommand createDepartmentCommand,Model model) throws UnsupportedEncodingException{
		createDepartmentCommand.setDeptName(java.net.URLDecoder.decode(createDepartmentCommand.getDeptName(),"UTF-8"));
		HgLogger.getInstance().debug("zhuxy", "Ajax添加部门");
		DepartmentDTO departmentDTO = companyService.addDepartment(createDepartmentCommand);
		Map map = new HashMap();
		map.put("dto", departmentDTO);
		map.put("status","success");
		HgLogger.getInstance().info("zhuxy", "Ajax添加部门,结果："+JSON.toJSONString(map));
		return JSON.toJSONString(map);
	}

	/**
	 * 添加成员
	 */
	@RequestMapping("/organizeManage/addMember")
	@ResponseBody
	public String addMember(CreateMemberCommand createMemberCommand,Model model){
		HgLogger.getInstance().debug("zhuxy", "Ajax添加成员");
		MemberDTO memberDTO = companyService.addMember(createMemberCommand);
		Map map = new HashMap();
		map.put("dto", memberDTO);
		map.put("status","success");
		HgLogger.getInstance().info("zhuxy", "Ajax添加成员,结果："+JSON.toJSONString(map));
		return JSON.toJSONString(map);
	}

	/**
	 * 删除成员
	 */
	@RequestMapping("/organizeManage/deleteMember")
	@ResponseBody
	public String deleteMember(@RequestParam("id") String id,Model model){
		HgLogger.getInstance().debug("zhuxy", "Ajax删除成员");
		DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand();
		if(StringUtils.isNotBlank(id)){
			String[] id_arr=id.split(",");
			for(String mid:id_arr ){
				deleteMemberCommand.setUserId(mid);
				companyService.delMember(deleteMemberCommand);
			}

		}

		Map map = new HashMap();
		map.put("id", id);
		map.put("status","success");
		HgLogger.getInstance().info("zhuxy", "Ajax删除成员,结果："+JSON.toJSONString(map));
		return JSON.toJSONString(map);
	}

	/**
	 * 卡券管理
	 */
	@RequestMapping("/couponList")
	public String couponList(@RequestParam(value="tab",required=false) Integer tab,
							 @RequestParam(value="pageNum",required=false) Integer pageNo,
							 @RequestParam(value="pageSize",required=false) Integer pageSize,
							 Model model,HttpServletRequest request,
							 HslCouponQO hslCouponQO
	){

		String page="company/coupon.html";
		if(tab==null||tab==0){
			tab = 1;

		}
		model.addAttribute("tab", tab);
		UserDTO user=getUserBySession(request);
		model.addAttribute("user", user);
		hslCouponQO.setUserId(user.getId());
		//此处放入用户的id
		switch(tab){
			case 1:{
				hslCouponQO.setOverdue(true);
				hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
				//page="company/coupon.html";
				break;
			}
			case 2:{
				hslCouponQO.setCurrentValue(CouponStatus.TYPE_ISUSED);
				//page="company/coupon_userful.html";
				break;
			}
			case 3:{
				Object[] statusTypes={3,4};
				hslCouponQO.setStatusTypes(statusTypes);
				//page="company/coupon_expired.html";
				break;
			}
		}
		Pagination pagination = new Pagination();
		pageNo=pageNo==null?new Integer(1):pageNo;
		pageSize=pageSize==null?new Integer(5):pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(hslCouponQO);
		pagination = couponService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("conditionMap", CouponActivity.conditionMap);
		return page;
	}
	/**
	 * @方法功能说明：账户资料管理页面
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月10日下午3:47:15
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/infoManage")
	public String info(HttpServletRequest request,HttpServletResponse response,Model model){
		HgLogger.getInstance().info("chenxy", "跳转到账户资料管理页面");
		ProvinceQo provinceQo = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(provinceQo);
		model.addAttribute("provinces", provinceList);
		HslUserBindAccountQO userbaqo=new HslUserBindAccountQO();
		HslUserQO userqo=new HslUserQO();
		UserDTO user=getUserBySession(request);
		userqo.setId(user.getId());
		userqo.setLoginName(user.getAuthInfo().getLoginName());
		userbaqo.setUserQO(userqo);
		UserDTO userDTO=null;
		try {
			userDTO=userService.queryUser(userbaqo);
			model.addAttribute("user", userDTO);
		} catch (UserException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy", "CompanyCenterController->info->exception:" + HgLogger.getStackTrace(e));
		}
		if(userDTO!=null&&userDTO.getBaseInfo().getType()==1){
			return "company/person_info.html";
		}else{
			return "company/info.html";
		}
	}

	/**
	 * 查询市
	 * @param request
	 * @param model
	 * @param province
	 * @return
	 */
	@RequestMapping(value = "/searchCity")
	@ResponseBody
	public String searchCity(HttpServletRequest request, Model model,
							 @RequestParam(value = "province", required = false) String province) {
		// 查询市
		CityQo cityQo = new CityQo();
		cityQo.setProvinceCode(province);
		List<City> cityList = cityService.queryList(cityQo);
		return JSON.toJSONString(cityList);
	}

	/**
	 * @方法功能说明：修改账户资料
	 * @修改者名字：chenxy
	 * @修改时间：2014年9月25日下午2:18:38
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@param attr
	 * @参数：@return
	 * @return:RedirectView
	 * @throws
	 */
	@RequestMapping("/infoManage/update")
	public RedirectView updateInfo(HttpServletRequest request,HttpServletResponse response,Model model,
								   @ModelAttribute UpdateCompanyUserCommand command,RedirectAttributes attr){
		UserDTO user=getUserBySession(request);
		command.setUserId(user.getId());
		UserDTO userDTO=userService.updateCompanyUser(command);
		putUserToSession(request,userDTO);
		attr.addFlashAttribute("user", userDTO);
		attr.addFlashAttribute("succ", "修改成功");
		HgLogger.getInstance().info("chenxy", "修改账户资料");
		return new RedirectView("/company/infoManage",true);
	}
	/**
	 * 验证邮箱
	 * @return
	 */
	@RequestMapping("/infoManage/validateMail")
	public String validateMail(HttpServletRequest request,HttpServletResponse response,Model model){
		return null;
	}

	/**
	 * 验证电话
	 * @return
	 */
	@RequestMapping("/infoManage/validateTel")
	@ResponseBody
	public String validateTel(HttpServletRequest request,HttpServletResponse response,Model model,@ModelAttribute HslSMSCodeQO hslSMSCodeQO){
		HgLogger.getInstance().info("chenxy", "验证手机号码");
		Map<String,String> result=new HashMap<String, String>();
		UserDTO user=getUserBySession(request);
		String mobile=request.getParameter("mobile");
		user.getContactInfo().setMobile(mobile);
		try {
			String code=userService.querySendValidCode(hslSMSCodeQO);
			if(null!=mobile&&!mobile.equals("")){
				putUserToSession(request,user);
				HgLogger.getInstance().info("chenxy", "修改绑定手机号码"+mobile);
				UpdateBindMobileCommand command=new UpdateBindMobileCommand();
				command.setUserId(user.getId());
				command.setMobile(mobile);
				userService.updateBindMobile(command);
			}
			HgLogger.getInstance().info("chenxy", "验证手机号码成功");
			result.put("flag",code);
		} catch (UserException e) {
			HgLogger.getInstance().error("chenxy", "验证手机号码失败>>"+e.getMessage());
			result.put("flag",e.getMessage());
		}

		return JSON.toJSONString(result);
	}

	/**
	 * 跳转到修改密码页面
	 * @return
	 */
	@RequestMapping("/passwordManage")
	public String password(HttpServletRequest request,HttpServletResponse response,Model model){
		HgLogger.getInstance().info("chenxy", "跳转到修改密码页面");
		return "company/passwordManage.html";
	}

	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping("/passwordManage/update")
	public RedirectView updatePassword(HttpServletRequest request,HttpServletResponse response,Model model,
									   @ModelAttribute UserUpdatePasswordCommand command,RedirectAttributes attr){
		UserDTO user=getUserBySession(request);
		command.setUserId(user.getId());
		command.setNewPwd(MD5HashUtil.toMD5(command.getNewPwd()));
		command.setOldPwd(MD5HashUtil.toMD5(command.getOldPwd()));
		try {
			UserDTO userDTO=userService.updatePassword(command);
			HgLogger.getInstance().info("chenxy", "修改密码成功");
			attr.addFlashAttribute("user", userDTO);
			attr.addFlashAttribute("succ", "修改成功");
		} catch (UserException e) {
			attr.addFlashAttribute("error",e.getMessage());
			HgLogger.getInstance().error("chenxy", "修改密码失败");
		}
		return new RedirectView("/company/passwordManage",true);
	}
	/**
	 * 修改头像
	 * @return
	 */
	@RequestMapping("/infoManage/updateHeadImage")
	public String updateHeadImage(HttpServletRequest request,HttpServletResponse response,Model model,
								  @ModelAttribute UpdateHeadImageCommand command,RedirectAttributes attr){
		UserDTO user = getUserBySession(request);
		command.setUserId(user.getId());
		UserDTO userDTO = userService.updateHeadImage(command);
		putUserToSession(request, userDTO);
		return "company/index.html";
	}
	/**
	 * 根据手机号查询用户名
	 */
	@RequestMapping("/mobilequeryname")
	@ResponseBody
	public String queryname1(HttpServletRequest request,HttpServletResponse response,
							 @RequestParam(value="mobile",required = true) String mobile){
		Map map=new HashMap();
		try {
			HgLogger.getInstance().debug("zhuaows", "Ajax查询用户名");
			HslUserQO hsluserQo=new HslUserQO();
			hsluserQo.setMobile(mobile);
			List<UserDTO> hsluser=userService.queryList(hsluserQo);
			if(hsluser.size()>0){
				map.put("name", hsluser.get(0).getAuthInfo().getLoginName());
				map.put("passivityuserid", hsluser.get(0).getId());
			}else{
				map.put("name", "所查询的用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(map);
	}

	/**
	 * @方法功能说明：跳转重置页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月6日下午3:31:06
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param code
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/toRecharge")
	public String toRecharge(HttpServletRequest request,Model model,@RequestParam(value = "code", required = false) String code) {
		try {
			HgLogger.getInstance().info("chenxy", "toRecharge--recharge(兑换码充值):code" + code);
			UserDTO user = getUserBySession(request);
			if (user != null) {
				//查询当前用户的余额
				AccountQO aQo = new AccountQO();
				aQo.setUserID(user.getId());
				aQo.setConsumeOrderSnapshots(true);
				AccountDTO accountDTO = accountService.queryUnique(aQo);
				if (accountDTO != null) {
					Double consumptionBalance = 0.00;
					model.addAttribute("balance", accountDTO.getBalance());
					HgLogger.getInstance().info("chenxy", "toRecharge--查询账户余额->account->" + JSON.toJSONString(accountDTO));
					if (accountDTO!=null&&accountDTO.getConsumeOrderSnapshots() != null) {
						for (AccountConsumeSnapshotDTO consumeOrderSnapshots : accountDTO.getConsumeOrderSnapshots()) {
							if (consumeOrderSnapshots.getStatus() == 1) {
								consumptionBalance += consumeOrderSnapshots.getPayPrice();
							}
						}
					}
					double newBanlance = accountDTO.getBalance() - consumptionBalance;
					if (newBanlance <= 0) {
						newBanlance = 0.00;
					}
					model.addAttribute("balance", newBanlance);
					model.addAttribute("code", code);
				}
			}else {
				return "/company/login.html";
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "查询账户余额--->出错" + HgLogger.getStackTrace(e));
			return "/company/recharge.html";
		}
		return "/company/recharge.html";
	}
	/**
	 * @throws Exception
	 * @方法功能说明：充值
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月6日下午3:30:36
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */

	@RequestMapping("/recharge")
	public RedirectView recharge(HttpServletRequest request,Model model,
								 @ModelAttribute PayCodeCommand command,RedirectAttributes attr) throws Exception{

		HgLogger.getInstance().info("renfeng", "recharge(兑换码充值):command"+JSON.toJSONString(command));

		String balance=request.getParameter("balance");
		model.addAttribute("balance",balance);

		HoldUserSnapshotCommand userCmd=new HoldUserSnapshotCommand();
		UserDTO user=getUserBySession(request);
		userCmd.setUserId(user.getId());
		userCmd.setLoginName(user.getAuthInfo().getLoginName());
		userCmd.setRealName(user.getBaseInfo().getName());
		userCmd.setMobile(user.getContactInfo().getMobile());
		userCmd.setEmail(user.getContactInfo().getEmail());

		command.setHoldUserSnapshotCommand(userCmd);
		try {
			String msg=payCodeService.recharge(command);
			HgLogger.getInstance().info("renfeng", "recharge(兑换码充值)结果："+msg);
			if("success".equals(msg)){
				attr.addFlashAttribute("balance", balance);
				attr.addFlashAttribute("succ", "充值成功");
			}

		} catch (AccountException e) {
			HgLogger.getInstance().info("renfeng", "recharge(兑换码充值)出错："+e.getCode());
			if(e.getCode().equals(AccountException.NOT_ACCOUNT)){
				attr.addFlashAttribute("succ", "账户不存在");
			}else if(e.getCode().equals(AccountException.NOT_PAYCODE)){
				attr.addFlashAttribute("succ", " 充值码不存在");
			}else if(e.getCode().equals(AccountException.NOT_AVAILABLE)){
				attr.addFlashAttribute("succ", "充值码不可用");
			}else if(e.getCode().equals(AccountException.ALREADY_PAYCODE)){
				attr.addFlashAttribute("succ", "充值码已使用");
			}
			e.printStackTrace();
		}



		return new RedirectView("/company/toRecharge",true);

	}


	/**
	 * @方法功能说明：常用联系人列表
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月6日下午4:30:00
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param qo
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/contactList")
	public String contactList(HttpServletRequest request,Model model,
							  @ModelAttribute CommonContactQO qo,
							  @RequestParam(value = "pageNum", required = false) Integer pageNo,
							  @RequestParam(value = "numPerPage",required = false) Integer pageSize){
		UserDTO user=getUserBySession(request);
		qo.setUserId(user.getId());
		//添加分页查询条件
		Pagination pagination = new Pagination();
		pagination.setCondition(qo);
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 5 : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		HgLogger.getInstance().info("renfeng", "常用联系人分页查询,qo:"+JSON.toJSONString(qo));
		pagination=this.commonContactService.queryPagination(pagination);
		HgLogger.getInstance().info("renfeng", "常用联系人分页查询结果,pagination:"+JSON.toJSONString(pagination));
		model.addAttribute("pagination", pagination);
		model.addAttribute("contactQo", qo);

		return "/company/commonContact.html";
	}


	/**
	 * @方法功能说明：跳转编辑页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月6日上午8:54:16
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/editCommonContact")
	public String eidtCommonContact(HttpServletRequest request,Model model,
									@ModelAttribute CommonContactQO qo){
		HgLogger.getInstance().info("renfeng", "Ajax编辑常用联系人,结果："+JSON.toJSONString(qo));
		CommonContactDTO contact=this.commonContactService.queryUnique(qo);
		model.addAttribute("contact", contact);
		Map map = new HashMap();
		map.put("contact", JSON.toJSON(contact));
		map.put("status","success");
		HgLogger.getInstance().info("renfeng", "Ajax编辑常用联系人,结果："+JSON.toJSONString(map));
		return JSON.toJSONString(map);

	}
	/**
	 * @方法功能说明：保存常用联系人
	 * @修改者名字：renfeng
	 * @修改时间：2015年9月6日上午8:56:46
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/saveCommonContact")
	public String saveCommonContact(HttpServletRequest request,Model model,
									@ModelAttribute CreateCommonContactCommand command){
		HgLogger.getInstance().info("renfeng", "保存常用联系人,command:"+JSON.toJSONString(command));

		Map map = new HashMap();
		UserDTO user=getUserBySession(request);
		try {

			if(StringUtils.isNotBlank(request.getParameter("id"))){
				HgLogger.getInstance().info("renfeng", "Ajax编辑常用联系人,参数："+request.getParameter("id"));
				UpdateCommonContactCommand updateCmd=BeanMapperUtils.map(command, UpdateCommonContactCommand.class);
				updateCmd.setId(request.getParameter("id"));
				updateCmd.setUserId(user.getId());
				this.commonContactService.updateCommonContact(updateCmd);

			}else{
				HgLogger.getInstance().info("renfeng", "Ajax添加常用联系人,参数："+JSON.toJSONString(command));
				command.setUserId(user.getId());
				this.commonContactService.addCommonContact(command);

			}

			map.put("status","success");

		} catch (CommonContactException e) {
			map.put("status",e.getMessage());
			e.printStackTrace();
		}

		HgLogger.getInstance().info("renfeng", "Ajax保存常用联系人,结果："+JSON.toJSONString(map));

		return JSON.toJSONString(map);

	}


	@ResponseBody
	@RequestMapping(value="/deleteCommonContact")
	public String deleteCommonContact(HttpServletRequest request,Model model,
									  @RequestParam(value = "id", required = false) String id){
		this.commonContactService.delCommonContact(id);

		Map map = new HashMap();
		map.put("id", id);
		map.put("status","success");
		HgLogger.getInstance().info("renfeng", "Ajax删除常用联系人,结果："+JSON.toJSONString(map));
		return JSON.toJSONString(map);

	}
}
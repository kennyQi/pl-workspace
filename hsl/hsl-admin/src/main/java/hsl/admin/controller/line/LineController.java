package hsl.admin.controller.line;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;
import hsl.admin.common.CouponActivityParam;
import hsl.pojo.command.line.CancelLineOrderCommand;
import hsl.pojo.command.line.InitLineCommand;
import hsl.pojo.dto.line.order.XLOrderStatusConstant;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.line.LineConstants;
import hsl.pojo.dto.line.order.LineOrderDTO;
import hsl.pojo.dto.line.order.LineOrderTravelerDTO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.line.HslLineOrderQO;
import hsl.pojo.qo.line.HslLineQO;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.line.HslLineOrderService;
import hsl.spi.inter.line.HslLineService;
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

/**
 * @类功能说明：线路控制器
 * @类修改者：
 * @修改日期：2015年2月3日下午5:25:57
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年2月3日下午5:25:57
 */
@Controller
@RequestMapping(value="/line")
public class LineController {
	@Autowired
	private HslLineService hslLineService;
	@Autowired
	private HslLineOrderService hslLineOrderService;
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	public static final String PAGE_ORDERLIST = "line/order_list.html";
	public static final String PAGE_ORDER_DETAIL = "line/order_detail.html";
	
	@Autowired
	private  CouponService  couponService;
	/**
	 * @方法功能说明：跳转线路主页
	 * @修改者名字：chenxy
	 * @修改时间：2015年2月3日下午5:26:37
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/main")
	public String main(HttpServletRequest request,
			@RequestParam(value="pageNum",required=false) Integer pageNo, @RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model,@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@ModelAttribute HslLineQO hslLineQO) {
		
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null ? new Integer(1) : pageNo);
		pagination.setPageSize(pageSize == null ? new Integer(20) : pageSize);

		if (hslLineQO == null) {
			hslLineQO = new HslLineQO();
		}

		if (StringUtils.isNotBlank(startTime)) {
			hslLineQO.setBeginDateTime(DateUtil.dateStr2BeginDate(startTime));
		}
		if (StringUtils.isNotBlank(endTime)) {
			hslLineQO.setEndDateTime(DateUtil.dateStr2EndDate(endTime));
		}
		//查询所有城市信息
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
		
		pagination.setCondition(hslLineQO);
		pagination = this.hslLineService.queryPagination(pagination);
		
		
		model.addAttribute("statusMap", LineConstants.statusMap); // 线路状态
		model.addAttribute("typeMap", LineConstants.typeMap); // 线路类型
		model.addAttribute("pagination", pagination);
		model.addAttribute("hslLineQO", hslLineQO);
		model.addAttribute("cityList", cityList);
		
		return "/line/lineList.html";
	}
	
	@RequestMapping(value="/sync")
	public String syncLine(HttpServletRequest request,
			@RequestParam(value="pageNum",required=false) Integer pageNo, @RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model,@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@ModelAttribute HslLineQO hslLineQO) {
		
		InitLineCommand initLineCommand=new InitLineCommand();
		if(StringUtils.isNotBlank(startTime)){
			initLineCommand.setStartDate(DateUtil.parseDate3(startTime));
		}
		if(StringUtils.isNotBlank(endTime)){
			initLineCommand.setEndDate(DateUtil.parseDate3(endTime));
		}
		try {
			hslLineService.initLineData(initLineCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null ? new Integer(1) : pageNo);
		pagination.setPageSize(pageSize == null ? new Integer(20) : pageSize);

		if (hslLineQO == null) {
			hslLineQO = new HslLineQO();
		}

		if (StringUtils.isNotBlank(startTime)) {
			hslLineQO.setBeginDateTime(DateUtil.dateStr2BeginDate(startTime));
		}
		if (StringUtils.isNotBlank(endTime)) {
			hslLineQO.setEndDateTime(DateUtil.dateStr2EndDate(endTime));
		}
		//查询所有城市信息
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
		
		pagination.setCondition(hslLineQO);
		pagination = this.hslLineService.queryPagination(pagination);
		
		
		model.addAttribute("statusMap", LineConstants.statusMap); // 线路状态
		model.addAttribute("typeMap", LineConstants.typeMap); // 线路类型
		model.addAttribute("pagination", pagination);
		model.addAttribute("hslLineQO", hslLineQO);
		model.addAttribute("cityList", cityList);
			
		
		return "/line/lineList.html";
	}
	
	/**
	 * @方法功能说明：查询订单列表
	 * @修改者名字：yuqz
	 * @修改时间：2015年2月27日下午2:52:27
	 * @修改内容：
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param model
	 * @参数：@param hslLineOrderQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/orderList")
	public String orderList(
			@RequestParam(value="pageNum",required=false) Integer pageNo, @RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model,@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@ModelAttribute HslLineOrderQO hslLineOrderQO){
			
			// 添加分页参数
			Pagination pagination = new Pagination();
			pageNo = (pageNo == null) ? new Integer(1) : pageNo;
			pageSize = (pageSize == null) ? new Integer(20) : pageSize;
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			
			pagination.setCondition(hslLineOrderQO);
			pagination = this.hslLineOrderService.queryPagination(pagination);
			
			CityQo cityQo = new CityQo();
			List<City> cityList = cityService.queryList(cityQo);
			//查询所有省信息
			ProvinceQo province = new ProvinceQo();
			List<Province> provinceList = provinceService.queryList(province);
			
			model.addAttribute("hslLineOrderQO", hslLineOrderQO);
			model.addAttribute("pagination", pagination);
			model.addAttribute("cityList", cityList);
			model.addAttribute("provinceList", provinceList);
			//订单状态
			model.addAttribute("orderStatusList", XLOrderStatusConstant.SHOP_XLORDER_STATUS_LIST); 
			//订单支付状态
			model.addAttribute("payStatusList", XLOrderStatusConstant.SHOP_XLORDER_PAY_STATUS_LIST); 
			
			return PAGE_ORDERLIST;

		
	}
	
	/**
	 * @方法功能说明：查询订单详情
	 * @修改者名字：yuqz
	 * @修改时间：2015年2月27日下午2:56:51
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/orderDetail")
	public String orderDetail(Model model, @RequestParam(value="id", required=false)String id,
			@RequestParam(value="dealerOrderNo", required=false)String dealerOrderNo){
		
		HslLineOrderQO qo = new HslLineOrderQO();
		if(StringUtils.isNotBlank(id)){
			qo.setId(id);
		}else if(StringUtils.isNotBlank(dealerOrderNo)){
			qo.setDealerOrderNo(dealerOrderNo);
		}
		LineOrderDTO dto = hslLineOrderService.queryUnique(qo);
		model.addAttribute("lineOrderDTO", dto);
		
		HslCouponQO conponQo=new HslCouponQO();
		conponQo.setOrderId(dto.getBaseInfo().getDealerOrderNo());
		//CouponDTO coupon=this.couponService.queryUnique(conponQo);
		List<CouponDTO> couponList=couponService.queryList(conponQo);
		
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
		
		model.addAttribute("cityList", cityList);
		model.addAttribute("couponList", couponList); 
		model.addAttribute("issueWayMap", CouponActivityParam.getIssueWayMap());
		model.addAttribute("couponTypeMap", CouponActivityParam.getCouponTypeMap());
		//订单状态
		model.addAttribute("orderStatusList", XLOrderStatusConstant.SHOP_XLORDER_STATUS_LIST); 
		//订单支付状态
		model.addAttribute("payStatusList", XLOrderStatusConstant.SHOP_XLORDER_PAY_STATUS_LIST); 
		
		return PAGE_ORDER_DETAIL;
	}
	
	/**
	 * @方法功能说明：跳转取消订单页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年5月13日下午3:16:34
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineOrderID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/tocancle")
	public String toCancleLineOrder(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineOrderID){
		
		HslLineOrderQO qo = new HslLineOrderQO();
		qo.setDealerOrderNo(lineOrderID);
		LineOrderDTO dto = hslLineOrderService.queryUnique(qo);
		Set<LineOrderTravelerDTO> travellerList = dto.getTravelers();
		Iterator<LineOrderTravelerDTO> iterator = travellerList.iterator();
		while(iterator.hasNext()){
			LineOrderTravelerDTO traveller = iterator.next();
			//订单已取消的游客不显示在取消列表里
			if(XLOrderStatusConstant.SHOP_ORDER_CANCEL.equals(traveller.getLineOrderStatus().getOrderStatus()  + "")){ 
				iterator.remove();
			}
		}
		model.addAttribute("id", lineOrderID);
		model.addAttribute("travellerList", travellerList);
		return "line/order_cancle.html";
	}
	/**
	 * @方法功能说明：取消订单
	 * @修改者名字：renfeng
	 * @修改时间：2015年5月13日下午3:16:19
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/cancle")
	@ResponseBody
	public String cancleLineOrder(HttpServletRequest request,HttpServletResponse response, Model model){
		String[] lineOrderTravelers=request.getParameterValues("lineOrderTravelers");
		try{
			CancelLineOrderCommand command=new CancelLineOrderCommand();
			command.setLineOrderID(request.getParameter("lineOrderID"));
			command.setTravelerIDs(lineOrderTravelers);
			
			this.hslLineOrderService.cancelLineOrder(command);
			
			
		}catch(Exception e){
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "取消失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineOrder");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "取消成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineOrder");
	}
}

package hsl.h5.control;

import com.alibaba.fastjson.JSON;

import hg.common.util.BeanMapperUtils;
import hg.common.util.MoneyUtil;
import hg.log.util.HgLogger;
import hsl.app.component.task.SysProperties;
import hsl.app.service.local.commonContact.CommonContactLocalService;
import hsl.app.service.local.line.DateSalePriceLocalService;
import hsl.app.service.local.line.LineLocalService;
import hsl.app.service.local.line.LineOrderLocalService;
import hsl.app.service.local.user.TravelerLocalService;
import hsl.domain.model.commonContact.CommonContact;
import hsl.domain.model.coupon.CouponStatus;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.domain.model.xl.DateSalePrice;
import hsl.domain.model.xl.Line;
import hsl.domain.model.xl.order.LineOrder;
import hsl.domain.model.xl.order.LineOrderTraveler;
import hsl.h5.alipay.bean.AlipayLineOrderFormBuilder;
import hsl.h5.base.utils.DateUtils;
import hsl.h5.exception.HslapiException;
import hsl.pojo.command.CommonContact.CreateCommonContactCommand;
import hsl.pojo.command.CommonContact.UpdateCommonContactCommand;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.line.ApplyToPayLineOrderCommand;
import hsl.pojo.command.line.ApplyToPayLineOrderResult;
import hsl.pojo.command.line.HslH5CreateLineOrderCommand;
import hsl.pojo.command.line.UpdateLineOrderPayInfoCommand;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.dto.account.AccountDTO;
import hsl.pojo.dto.commonContact.CommonContactDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.line.DayRouteDTO;
import hsl.pojo.dto.line.HotelInfoDTO;
import hsl.pojo.dto.line.LineDTO;
import hsl.pojo.dto.line.order.LineOrderDTO;
import hsl.pojo.dto.line.order.XLOrderStatusConstant;
import hsl.pojo.exception.CommonContactException;
import hsl.pojo.exception.CouponException;
import hsl.pojo.exception.LineException;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.qo.CommonContact.CommonContactQO;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;
import hsl.pojo.qo.account.AccountQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.line.DateSalePriceQO;
import hsl.pojo.qo.line.HslLineOrderQO;
import hsl.pojo.qo.line.HslLineQO;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.account.AccountConsumeSnapshotService;
import hsl.spi.inter.account.AccountService;
import hsl.spi.inter.commonContact.CommonContactService;
import hsl.spi.inter.line.HslLineService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

import static  hsl.h5.alipay.ctl.AlipayLineOrderCtl.*;
/**
 *
 * @类功能说明：H5线路Controller
 * @类修改者：hgg
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：hgg
 * @创建时间：2015年9月29日上午10:50:55
 *
 */
@Controller
@RequestMapping(value="/hslH5/line")
public class LineController extends HslCtrl{


	private String                                     devName = "renfeng";
	@Autowired
	private HslLineService                             hslLineService;
	
	@Autowired
	private LineOrderLocalService                      lineOrderLocalService;
	
	@Autowired
	private TravelerLocalService                       travelerLocalService;
	
	@Autowired
	private DateSalePriceLocalService                  dateSalePriceLocalService;
	
	@Autowired
	private CouponService                              couponService;
	
	@Autowired
	private LineLocalService                           lineLocalService;
	
	final   Integer                                    lineStatus = 1;
	
	/**
	 * 订单列表每次下拉加载更多的数量
	 */
	final   Integer                                    fectNum = 20;

	@Autowired
	private CommonContactService commonContactService;
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountConsumeSnapshotService accountConsumeSnapshotService;
	/**
	 *
	 * @方法功能说明：线路详情
	 * @修改者名字：hgg
	 * @修改时间：2015年9月29日上午10:54:03
	 * @修改内容：
	 * @参数：@param hslLineQO
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	@RequestMapping(value="/lineDetail")
	public Object lineDetail(String id){
		
		ModelAndView mav = new ModelAndView("line/lineDetail");
		
		HslLineQO hslLineQO = new HslLineQO();
		hslLineQO.setId(id);
		hslLineQO.setGetPictures(true);
		
		LineDTO lineDTO = hslLineService.queryUnique(hslLineQO);
		
		if(null == lineDTO){
			RedirectView rv= new RedirectView("list",true);
			return rv;
		}
		
		
		try{
			List<DayRouteDTO> dayRoutes = lineDTO.getRouteInfo().getDayRouteList();
			
			for(DayRouteDTO dayDto: dayRoutes){
				List<HotelInfoDTO> list = JSON.parseArray(dayDto.getHotelInfoJson(), HotelInfoDTO.class);
				dayDto.setHotelList(list);
			}
			
		}catch(NullPointerException e){
			HgLogger.getInstance().error("hgg", JSON.toJSONString(e));
		}
		String image_host=SysProperties.getInstance().get("image_host");
		mav.addObject("image_host", image_host);
		mav.addObject("line", lineDTO);
		mav.addObject("cityMap", getCityMap());
		mav.addObject("lineImages", lineDTO.getLineImageList());
		
		return mav;
	}
	
	/**
	 *
	 * @方法功能说明：跳转到线路订单列表页面
	 * @修改者名字：hgg
	 * @修改时间：2015年10月15日上午10:24:32
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/lineOrderListPage")
	public String lineOrderPage(Model model){
		
		return "line/lineOrderList";
	}
	
	/**
	 *
	 * @方法功能说明：下拉加载更多线路订单列表
	 * @修改者名字：hgg
	 * @修改时间：2015年10月15日上午10:13:09
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param queryNum
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/loadMoreOrderList")
	@ResponseBody
	public String lineOrderList(Model model,Integer queryNum,HttpServletRequest request){
		
		List<String>  strList = new ArrayList<String>();
		
		try {
			String userId = getUserId(request);
			if(StringUtils.isNotBlank(userId)){
//				HslLineOrderQO lineOrderQO = new HslLineOrderQO();
//				lineOrderQO.setUserId(userId);
				List<LineOrder> lineOrders = lineOrderLocalService.queryList(userId,queryNum,fectNum);
				for (LineOrder lineOrder : lineOrders) {
					String str = lineOrder.getBaseInfo().getDealerOrderNo()+","+lineOrder.getLineSnapshot().getLineName()+","+DateUtils.getYearMonthDay(lineOrder.getBaseInfo().getTravelDate())+","+lineOrder.getBaseInfo().getSalePrice()+","+lineOrder.getStatus().getPayStatus();
					strList.add(str);
				}
			}
			
		} catch (HslapiException e) {
			HgLogger.getInstance().error(devName, "下拉加载更多线路订单列表,发生异常："+e.getMessage());
		}
		
		if(CollectionUtils.isNotEmpty(strList)){
			return JSON.toJSONString(strList);
		}else{
			return "[]";
		}
	}
	
	/**
	 *
	 * @方法功能说明：线路日期明细
	 * @修改者名字：hgg
	 * @修改时间：2015年9月29日下午2:33:22
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/dateDetail")
	public Object lineOrderDate(Model model,HttpServletRequest request,String lineId){
		
		ModelAndView mav = new ModelAndView("line/lineOrderDate");
		
		HslLineQO hslLineQO = new HslLineQO();
		hslLineQO.setGetDateSalePrice(true);
		hslLineQO.setId(lineId);
		
		LineDTO lineDTO = hslLineService.queryUnique(hslLineQO);
		
		if(null == lineDTO){
			RedirectView rv= new RedirectView("list",true);
			return rv;
		}
		//获取线路价格日历近5个月的数据
		DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
		dateSalePriceQO.setFetchMonthCount(5);
		dateSalePriceQO.setLineId(lineId);
		dateSalePriceQO.setBeforeDay(lineDTO.getPayInfo().getLastPayTotalDaysBeforeStart());
		List<DateSalePrice> dateSalePrices = dateSalePriceLocalService.queryList(dateSalePriceQO);
		
		mav.addObject("datePrices",dateSalePrices);
		mav.addObject("lineId",lineDTO.getId());
		
		return mav;
	}
	
	/**
	 *
	 * @方法功能说明：填写线路订单页面
	 * @修改者名字：hgg
	 * @修改时间：2015年10月8日下午1:30:54
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/lineOrderPage")
	public String lineOrderPage(Model model,HslH5CreateLineOrderCommand hslH5CreateLineOrderCommand,HttpServletRequest request){
		//从request中取得userId
		try {
			//设置上一次订单的联系人
			String userId = getUserId(request);
			if(StringUtils.isNotBlank(userId)){
				List<LineOrder> lineOrders = lineOrderLocalService.queryList(userId, 0, 1);
				if(CollectionUtils.isNotEmpty(lineOrders)){
					LineOrder lineOrder = lineOrders.get(0);
					hslH5CreateLineOrderCommand.setLinkName(lineOrder.getLinkInfo().getLinkName());
					hslH5CreateLineOrderCommand.setLinkMobile(lineOrder.getLinkInfo().getLinkMobile());
					hslH5CreateLineOrderCommand.setLinkEmail(lineOrder.getLinkInfo().getEmail());
				}
				//通用方法,处理跳转到填写订单页面的数据
				orderPayTemp(model,hslH5CreateLineOrderCommand,0D,lineStatus,"");
			}
		} catch (HslapiException e) {
			HgLogger.getInstance().info(devName, "跳转到填写线路订单页面发生异常:"+HgLogger.getStackTrace(e));
		}
		return "line/orderPay";
	}
	
	/**
	 *
	 * @方法功能说明：游客列表
	 * @修改者名字：hgg
	 * @修改时间：2015年10月8日下午3:14:39
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/personList")
	public String personList(Model model,HttpServletRequest request){
		
		List<CommonContactDTO> commonContacts = null;
		
		try {
			//从request中取得userId
			String userId = getUserId(request);
			
			CommonContactQO commonContactQO=new CommonContactQO();
			commonContactQO.setUserId(userId);
			
			if(StringUtils.isNotBlank(userId)){
				commonContacts = commonContactService.queryList(commonContactQO);
			}
			model.addAttribute("travelers", commonContacts);
		} catch (HslapiException e) {
			HgLogger.getInstance().info(devName, "获取用户ID发送异常："+e.getMessage());
		}
		
		return "line/personList";
	}
	
	/**
	 *
	 * @方法功能说明：判断当前来源用户是否关联有游客记录【有游客记录跳转到游客列表页面,没有游客记录跳转到新增游客页面】
	 * @修改者名字：hgg
	 * @修改时间：2015年10月9日下午3:38:59
	 * @修改内容：(任风)原来是从游客表（Traveler）中获取新游客,现在更改为从当前用户的常用联系人中添加游客
	 * @参数：@param model
	 * @参数：@param lineOrderCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/addPersonPage")
	public String addTraveler(Model model,HslH5CreateLineOrderCommand lineOrderCommand,HttpServletRequest request){
		
		List<CommonContactDTO> commonContacts = null;
		
		try {
			//从request中取得userId
			String userId = getUserId(request);
			
			model.addAttribute("lineOrderCommand", lineOrderCommand);
			CommonContactQO commonContactQO=new CommonContactQO();
			commonContactQO.setUserId(userId);
			
			if(StringUtils.isNotBlank(userId)){
				commonContacts = commonContactService.queryList(commonContactQO);
			}
			
		} catch (HslapiException e) {
			HgLogger.getInstance().error(devName, "获取用户发送异常：" + e.getMessage());
		}
		
		//有游客列表
		if(CollectionUtils.isNotEmpty(commonContacts)){
			
			model.addAttribute("travelers", commonContacts);
			return "line/personList";
		}else{//跳转到添加游客页面
			return "line/addPerson";
		}
	}
	
	/**
	 *
	 * @方法功能说明：直接跳转到添加游玩人页面
	 * @修改者名字：hgg
	 * @修改时间：2015年10月10日下午5:51:28
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param lineOrderCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/addTraveler")
	public String addPerson(Model model,HslH5CreateLineOrderCommand lineOrderCommand){

		model.addAttribute("lineOrderCommand", lineOrderCommand);

		return "line/addPerson";
	}
	
	/**
	 *
	 * @方法功能说明：编辑游玩人页面
	 * @修改者名字：hgg
	 * @修改时间：2015年10月10日下午5:51:28
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param lineOrderCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/editTravelerPage")
	public String editPerson(Model model,HslH5CreateLineOrderCommand lineOrderCommand,String travelerId){

		
		if(StringUtils.isNotBlank(travelerId)){
			/*TravelerQO travelerQO = new TravelerQO();
			travelerQO.setId(travelerId);
			Traveler traveler = travelerLocalService.queryUnique(travelerQO);*/
			CommonContactQO commonContactQO=new CommonContactQO();
			commonContactQO.setId(travelerId);
			
			CommonContactDTO commonContact=this.commonContactService.queryUnique(commonContactQO);
			
			model.addAttribute("traveler", commonContact);
		}

		model.addAttribute("lineOrderCommand", lineOrderCommand);

		return "line/editPerson";
	}
	
	/**
	 *
	 * @方法功能说明：添加游客
	 * @修改者名字：hgg
	 * @修改时间：2015年10月10日上午9:37:25
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/addPerson")
	public void addPerson(Model model,CreateCommonContactCommand commonContactCommand,HslH5CreateLineOrderCommand hslH5CreateLineOrderCommand,
			HttpServletRequest request,PrintWriter out){
		Map<String,String> map=new HashMap<String,String>();
		map.put("status", "success");
		String message = "";
		List<CommonContactDTO> commonContacts =null;
		try {
			String userId = getUserId(request);
			if(StringUtils.isNotBlank(userId)){
				
				CommonContactQO commonContactQO=new CommonContactQO();
				commonContactQO.setUserId(userId);
			    commonContacts = commonContactService.queryList(commonContactQO);
			
				commonContactCommand.setUserId(userId);
				CommonContactDTO contact=this.commonContactService.addCommonContact(commonContactCommand);
				if(contact!=null){
					commonContacts.add(BeanMapperUtils.map(contact, CommonContactDTO.class));
				}
				
				
			}
			
		}  catch (CommonContactException e) {
			HgLogger.getInstance().error(devName, "添加常用联系人 发生异常："+e.getMessage());
			message = e.getMessage();
			map.put("status", "error");
		} catch (HslapiException e) {
			HgLogger.getInstance().error(devName, "获取用户ID 发生异常："+e.getMessage());
			message = e.getMessage();
			map.put("status", "error");
		}
		
		map.put("lineOrderCommand", JSON.toJSONString(hslH5CreateLineOrderCommand));
		map.put("message", message);
		
		out.print(JSON.toJSONString(map));
	}
	
	/**
	 *
	 * @方法功能说明：保存编辑游客
	 * @修改者名字：hgg
	 * @修改时间：2015年10月10日上午9:37:25
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/savaEditPerson")
	public void saveEditPerson(Model model,UpdateCommonContactCommand updateCommonContactCommand,
			HslH5CreateLineOrderCommand hslH5CreateLineOrderCommand,HttpServletRequest request,PrintWriter out){
		Map<String,String> map=new HashMap<String,String>();
		map.put("status", "success");
		String message = "";
		
		try {
			String userId = getUserId(request);
			
			if(StringUtils.isNotBlank(userId)){
				
				updateCommonContactCommand.setUserId(userId);
				this.commonContactService.updateCommonContact(updateCommonContactCommand);
								
			}
			
		} catch (ShowMessageException e) {
			HgLogger.getInstance().info(devName, "保存编辑游客异常:" + e.getMessage());
			message = e.getMessage();
			map.put("status", "error");
		} catch (HslapiException e) {
			HgLogger.getInstance().info(devName, "保存编辑游客异常:" + e.getMessage());
			message = e.getMessage();
			map.put("status", "error");
		} catch (CommonContactException e) {
			HgLogger.getInstance().info(devName, "保存编辑游客异常:" + e.getMessage());
			message = e.getMessage();
			map.put("status", "error");
			e.printStackTrace();
		}
		
		
		map.put("lineOrderCommand", JSON.toJSONString(hslH5CreateLineOrderCommand));
		map.put("message", message);
		
		out.print(JSON.toJSONString(map));
	}
	
	/**
	 *
	 * @方法功能说明：在游玩人列表,选择游玩人
	 * @修改者名字：hgg
	 * @修改时间：2015年10月10日下午2:20:13
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param hslH5CreateLineOrderCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/savePerson")
	public String savaPerson(Model model,HslH5CreateLineOrderCommand hslH5CreateLineOrderCommand){
		
		//通用方法,处理跳转到填写订单页面的数据
		orderPayTemp(model, hslH5CreateLineOrderCommand, 0D, lineStatus, "");

		return "line/orderPay";
	}
	
	/**
	 *
	 * @方法功能说明：创建 线路订单
	 * @修改者名字：hgg
	 * @修改时间：2015年10月9日下午2:48:01
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param lineOrderCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/creatLineOrder")
	public String creatLineOrder(Model model,HslH5CreateLineOrderCommand lineOrderCommand,HttpServletRequest request){
		
		String message = "";
		String url ="line/orderPayType";
		LineOrderDTO lineOrderDTO = null;
		//【1】创建线路订单
		try {
			String userId = getUserId(request);
			
			if(StringUtils.isNotBlank(userId)){
				lineOrderCommand.setUserId(userId);
				lineOrderDTO = lineOrderLocalService.createLineOrders(lineOrderCommand);
				url="redirect:/hslH5/line/orderPay?orderId="+lineOrderDTO.getId();
				//【2】设置到返回页面的参数{1：无需支付定金,2：使用折扣券后,无需要支付定金,3:100%支付预定金,4:100%支付尾款,5需要支付尾款和预定金}
				Integer type = orderPayTemp(model,lineOrderCommand,0D,lineStatus,lineOrderDTO.getBaseInfo().getDealerOrderNo());
				HgLogger.getInstance().info(devName, "创建 线路订单:type:"+type);
				if(type==2 || type==4){
					url="redirect:/hslH5/line/success?dealerOrderNo="+lineOrderDTO.getBaseInfo().getDealerOrderNo();
				}
			}
			
		} catch (ShowMessageException e) {
			message = e.getMessage();
			HgLogger.getInstance().error(devName, "创建线路订单发生异常:"+message);
			url = "line/orderPay";
			model.addAttribute("lineOrderCommand", lineOrderCommand);
		} catch (HslapiException e) {
			message = e.getMessage();
			HgLogger.getInstance().error(devName, "创建线路订单发生异常:"+message);
			url = "line/orderPay";
			model.addAttribute("lineOrderCommand", lineOrderCommand);
		} catch (LineException e) {
			e.printStackTrace();
			message = e.getMessage();
			HgLogger.getInstance().error(devName, "创建线路订单发生异常:"+message);
			url = "line/orderPay";
			model.addAttribute("lineOrderCommand", lineOrderCommand);
		}
		
		model.addAttribute("message", message);
		
		return url;
	}
	
	/**
	 *
	 * @方法功能说明：跳转到支付页面
	 * @修改者名字：hgg
	 * @修改时间：2015年10月23日下午2:23:42
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/orderPay")
	public String orderPayPage(HttpServletRequest request,Model model,String orderId){
		
		String url ="line/orderPayType";
		String message="";
		model.addAttribute("lineOrderId", orderId);
		
		//【1】查询线路订单
		HslLineOrderQO hslLineOrderQO = new HslLineOrderQO();
		hslLineOrderQO.setId(orderId);
		LineOrder lineOrder= lineOrderLocalService.queryUnique(hslLineOrderQO);
		String userId="";
		try {
			userId = getUserId(request);
		} catch (HslapiException e) {
			e.printStackTrace();
		}
		if(lineOrder==null){
			message="订单不存在";
			url="line/orderPay";
		}else if(!lineOrder.getLineOrderUser().getUserId().equals(userId)){
			message="非当前用户订单";
			lineOrder=null;
			url="line/orderPay";
		}else{
			//【2】赋值给订单command
			HslH5CreateLineOrderCommand lineOrderCommand = setOrderCommand(lineOrder);

			//【2】设置到返回页面的参数{1：无需支付定金,2：使用折扣券后,无需要支付定金,3:需要预定金}
			Integer type = orderPayTemp(model,lineOrderCommand,0D,lineStatus,lineOrder.getBaseInfo().getDealerOrderNo());
			
			if(type == 1){//无需支付定金或者尾款
				model.addAttribute("type", 2);
			}else{
				if(type == 2){//使用折扣券后,无需要支付定金或者尾款
					model.addAttribute("type", 2);
				}else{//需要预定金或者尾款
					model.addAttribute("type", 3);
				}
			}
		}
		
		model.addAttribute("message", message);	
		return url;
	}
	
	/**
	 *
	 * @方法功能说明：线路订单详情
	 * @修改者名字：hgg
	 * @修改时间：2015年10月12日下午3:21:06
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param orderId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/lineOrderDetail")
	public String lineOrderDetail(Model model,String dealerOrderNo,HttpServletRequest request){
		String message="";
		if(StringUtils.isBlank(dealerOrderNo)){
			//抛异常页面
			return "";
		}
		try{
			//【1】查询线路订单
			HslLineOrderQO hslLineOrderQO = new HslLineOrderQO();
			hslLineOrderQO.setDealerOrderNo(dealerOrderNo);
			List<LineOrder> lineOrders = lineOrderLocalService.queryList(hslLineOrderQO);

			LineOrder lineOrder = lineOrders.get(0);
			String userId = getUserId(request);
			if(lineOrder==null){
				message="订单不存在";
			}else if(!lineOrder.getLineOrderUser().getUserId().equals(userId)){
				message="非当前用户订单";
				lineOrder=null;
			}else{
				//【2】赋值给订单command
				HslH5CreateLineOrderCommand hslH5CreateLineOrderCommand = setOrderCommand(lineOrder);

				//【3】设置线路订单详情参数
				//用户订单状态检查,1:需要支付预定金,2：需要支付尾款,3,不需要支付
				Integer status = lineOrderStatusCheck(lineOrder);

				//【4】设置到返回页面的参数{1：无需支付定金,2：使用折扣券后,无需要支付定金,3:需要预定金}
				orderPayTemp(model,hslH5CreateLineOrderCommand,0D,status,dealerOrderNo);

				//用户订单支付状态待【支付订金,已收到订金,等待支付尾款】时,订单详情页面才显示立即提交按钮
				Integer lineStatus = orderStatus(lineOrder);
				model.addAttribute("lineStatus", lineStatus);
			}

			model.addAttribute("lineOrder", lineOrder);
		
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("renfeng","H5查询订单出错：");
		}
		model.addAttribute("message", message);
		return "line/orderDetail";
	}
	
	/**
	 *
	 * @方法功能说明：确认支付订单
	 * @修改者名字：hgg
	 * @修改时间：2015年10月13日上午10:06:32
	 * @修改内容：
	 * @参数：@param lineOrderCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/payLineOrder")
	public String payLineOrder(Model model,ApplyToPayLineOrderCommand lineOrderCommand,HslH5CreateLineOrderCommand hslH5CreateLineOrderCommand,HttpServletRequest request){
		
		HgLogger.getInstance().info(devName, "H5线路支付payLineOrder()-->lineOrderCommand:"+JSON.toJSONString(lineOrderCommand));
		HgLogger.getInstance().info(devName, "H5线路支付payLineOrder()-->hslH5CreateLineOrderCommand:" + JSON.toJSONString(hslH5CreateLineOrderCommand));
		
		LineOrder lineOrder = null;
		String url = "";
		String message = "";
		model.addAttribute("lineOrderId",lineOrderCommand.getLineOrderId());
		try {
			//从request中取得userId
			String userId = getUserId(request);
			lineOrderCommand.setUserId(userId);
			hslH5CreateLineOrderCommand.setUserId(userId);
			
			HslLineOrderQO hslLineOrderQO = new HslLineOrderQO();
			hslLineOrderQO.setId(lineOrderCommand.getLineOrderId());
			lineOrder = lineOrderLocalService.queryUnique(hslLineOrderQO);
			
			
			if(lineOrder==null){
				message="订单不存在";
				url="line/orderPayType";
			}else if(!lineOrder.getLineOrderUser().getUserId().equals(userId)){
				message="操作用户异常";
				lineOrder=null;
				
				url="line/orderPayType";
			}/*else if(this.checkHasTravelerCancelOrder(lineOrder)){
				message="该订单中存在已取消的";
				lineOrder=null;
				if(lineOrder!=null){
					HslH5CreateLineOrderCommand command = setOrderCommand(lineOrder);
					Integer type = orderPayTemp(model,command,0D,lineStatus,"");
					model.addAttribute("type", type);
				}
				
				url="line/orderPayType";
			}*/else{
				//支付成功后,回调URL
				String returnUrl = getWebAppPath(request);
				lineOrderCommand.setPaymentFormHtmlBuilder(new AlipayLineOrderFormBuilder(returnUrl));
				
				//解绑当前订单占用的卡券
				OrderRefundCommand com = new OrderRefundCommand();
				com.setOrderId(lineOrder.getBaseInfo().getDealerOrderNo());
				couponService.orderRefund(com);
				
				//更新当前订单 已经占用的余额快照为“作废”状态
				this.updateAccount(userId, lineOrder.getBaseInfo().getDealerOrderNo());
				
				//去支付
				ApplyToPayLineOrderResult orderResult = lineOrderLocalService.applyToPayLineOrder(lineOrderCommand,lineOrder,SUFFIX);
				HgLogger.getInstance().info(devName, "H5线路支付payLineOrder()-->orderResult:"+JSON.toJSONString(orderResult));
				
				model.addAttribute("payForm", orderResult.getFormHtml());
				if(orderResult.getResultCode() == 1 || orderResult.getResultCode() == 2){
					HgLogger.getInstance().info("chenxy","跳转支付宝页面"+orderResult.getFormHtml());
					return "line/orderPayForm";
				}
				
				url = "redirect:/hslH5/line/success?dealerOrderNo="+lineOrder.getBaseInfo().getDealerOrderNo();
			}
			
		}  catch (Exception e) {
			HgLogger.getInstance().info(devName, "确认支付订单发生异常_原因:"+HgLogger.getStackTrace(e));
			message = e.getMessage();
			if(lineOrder!=null){
				HslH5CreateLineOrderCommand command = setOrderCommand(lineOrder);
				Integer type = orderPayTemp(model,command,0D,lineStatus,lineOrder.getBaseInfo().getDealerOrderNo());
				model.addAttribute("type", type);
			}
			
			url="line/orderPayType";
		}
		HgLogger.getInstance().info(devName, "H5线路支付payLineOrder()-->message:"+message);
		model.addAttribute("message", message);
		
		return url;
	}
	
	/**
	 * @方法功能说明：支付前检查要支付的游客订单中，是否有已取消的，如果有，不支付
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月18日下午3:15:37
	 * @修改内容：
	 * @参数：@param lineOrder
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	private boolean checkHasTravelerCancelOrder(LineOrder lineOrder){
		List<LineOrderTraveler> travelerList=lineOrder.getTravelerList();
		for(LineOrderTraveler traveler:travelerList){
			if(traveler.getLineOrderStatus().getOrderStatus().equals(Integer.parseInt(XLOrderStatusConstant.SHOP_ORDER_CANCEL))){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 *
	 * @方法功能说明：获取用户所有的卡券
	 * @修改者名字：hgg
	 * @修改时间：2015年10月13日上午10:55:26
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/userCouponList")
	public String queryUserCoupon(Model model,String orderId,HttpServletRequest request){
		
		//从request中取得userId
		try {
			String userId = getUserId(request);
			
			
			
			//【1】查询订单
			HslLineOrderQO hslLineOrderQO = new HslLineOrderQO();
			hslLineOrderQO.setOrderId(orderId);
			List<LineOrder> lineOrders = lineOrderLocalService.queryList(hslLineOrderQO);
			
			//解绑当前订单占用的卡券
			OrderRefundCommand com = new OrderRefundCommand();
			com.setOrderId(lineOrders.get(0).getBaseInfo().getDealerOrderNo());
			couponService.orderRefund(com);
			
			//【2】赋值订单command
//			HslH5CreateLineOrderCommand hslH5CreateLineOrderCommand = setOrderCommand(lineOrders.get(0));
			
			//【3】根据用户的id号获取用户所拥有的可用的卡券
			HslCouponQO  hslCouponQO = new HslCouponQO();
			hslCouponQO.setUserId(userId);
			
			//【4】只查询未使用的卡券并且满足使用条件的卡券
			hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
			hslCouponQO.setOrderPrice(lineOrders.get(0).getBaseInfo().getSalePrice());
			List<CouponDTO> couponList = couponService.queryList(hslCouponQO);
			
			model.addAttribute("couponList", couponList);
			
			model.addAttribute("orderId", orderId);
			
		} catch (HslapiException e) {
			HgLogger.getInstance().error(devName, "获取用户所有的卡券__异常:" + e.getMessage());
		} catch (CouponException e) {
			HgLogger.getInstance().error(devName, "获取用户所有的卡券__异常:" + e.getMessage());
			e.printStackTrace();
		}
		
		return "line/lineOrderCouponList";
	}
	
	/**
	 *
	 * @方法功能说明：选择卡券后,返回到订单支付页面
	 * @修改者名字：hgg
	 * @修改时间：2015年10月13日下午1:09:51
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param orderId
	 * @参数：@param couponId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/saveSelectCoupon")
	public String saveSelectCoupon(Model model,String orderId,String couponId){
		
		//【1】查询订单
		HslLineOrderQO hslLineOrderQO = new HslLineOrderQO();
		hslLineOrderQO.setId(orderId);
		List<LineOrder> lineOrders = lineOrderLocalService.queryList(hslLineOrderQO);
		LineOrder lineOrder = lineOrders.get(0);
		
		//【2】赋值订单command
		HslH5CreateLineOrderCommand hslH5CreateLineOrderCommand = setOrderCommand(lineOrder);
		
		HslCouponQO  hslCouponQO = new HslCouponQO();
		hslCouponQO.setId(couponId);
		CouponDTO couponDTO = couponService.queryUnique(hslCouponQO);
		Double discount = couponDTO.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
		
		if(StringUtils.isBlank(couponId)){
			discount =0D;
		}
		//【3】设置跳转到支付页面的参数
		//订单状态检查,1:需要支付预定金,2：需要支付尾款,3,不需要支付
		Integer status = lineOrderStatusCheck(lineOrder);
		//【2】设置到返回页面的参数{1：无需支付定金,2：使用折扣券后,无需要支付定金,3:需要预定金}
		Integer type = orderPayTemp(model, hslH5CreateLineOrderCommand, discount, status, lineOrder.getBaseInfo().getDealerOrderNo());
		
		model.addAttribute("discount", discount);
		model.addAttribute("lineOrderId", orderId);
		model.addAttribute("type", type);
		model.addAttribute("couponId", couponId);
		
		return "line/orderPayType";
	}
	
	/**
	 *
	 * @方法功能说明：订单详情页面提交表单,跳转到支付页面
	 * @修改者名字：hgg
	 * @修改时间：2015年10月14日下午2:39:20
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param orderId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/detailToPayPage")
	public String orderDetailSubmit(HttpServletRequest request,Model model,String orderId){
		String url="line/orderPayType";
		String message="";
		model.addAttribute("lineOrderId", orderId);
		//【1】查询订单
		HslLineOrderQO hslLineOrderQO = new HslLineOrderQO();
		hslLineOrderQO.setId(orderId);
		List<LineOrder> lineOrders = lineOrderLocalService.queryList(hslLineOrderQO);
		LineOrder lineOrder = lineOrders.get(0);
		
		String userId="";
		try {
			userId = getUserId(request);
		} catch (HslapiException e) {
			e.printStackTrace();
		}
		
		if(lineOrder==null){
			message="订单不存在";
			url="line/orderDetail";
		}else if(!lineOrder.getLineOrderUser().getUserId().equals(userId)){
			message="该订单不属于当前用户";
			url="line/orderDetail";
		}
		else{
			//删除当前订单 已经占用的余额
			this.updateAccount(userId, lineOrder.getBaseInfo().getDealerOrderNo());
			
			//【2】赋值订单command
			HslH5CreateLineOrderCommand hslH5CreateLineOrderCommand = setOrderCommand(lineOrder);
			
			//【2】设置到返回页面的参数{1：无需支付定金,2：使用折扣券后,无需要支付定金,3:需要预定金}
			Integer type = orderPayTemp(model,hslH5CreateLineOrderCommand,0D,0,lineOrder.getBaseInfo().getDealerOrderNo());
			
			if(type == 1){//无需支付定金
				url= "line/orderSuccess";
			}else{
				
				if(type == 2){//使用折扣券后,无需要支付定金
					model.addAttribute("type", 2);
				}else{//需要预定金
					model.addAttribute("type", 3);
				}
				
			}
		}
		
		model.addAttribute("message", message);
		HgLogger.getInstance().info(devName, "跳转订单支付页面消息:" +message);
		return url;
		
	}
	
	/**
	 *
	 * @方法功能说明：通用方法,处理跳转到填写订单页面的数据
	 * @修改者名字：hgg
	 * @修改时间：2015年10月10日上午9:46:53
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param hslH5CreateLineOrderCommand
	 * @return:{1：代表无需支付定金,2：需要支付定金}
	 * @throws
	 */
	private Integer  orderPayTemp(Model model,HslH5CreateLineOrderCommand hslH5CreateLineOrderCommand,Double discount,Integer type,String dealerOrderNo){
		
		Integer lineType = 1;
		//当前订单状态
		Integer status = 1;
		//当前支付类型
		Integer orderType = 1;
		Double discountBefo = discount;
		//总折扣的金额
		Double totalDisCount = 0D;
		//折扣总金额(预定金折扣金额+尾款折扣金额)
		Double discountMoney = 0D;
		
		Double totalMoney = 0D;
		//中间值
		Integer newType = 0;
		
		//是否用了卡券
		boolean usedCoupon = false;
		
		//预定金比例
		Double bookRate = 0D;
		
		//折扣后的全款金额
		Double discountTotalMoney = 0D;
		
		LineOrder lineOrder = null;
		
		
		HslLineQO hslLineQO = new HslLineQO();
		hslLineQO.setId(hslH5CreateLineOrderCommand.getLineId());
		Line line = lineLocalService.queryUnique(hslLineQO);
		if(line != null){
			//预定金比例
			bookRate = line.getPayInfo().getDownPayment();
		}
		
		//【1】设置游客列表:游客使用常用联系人
			
		CommonContactQO commonContactQO=new CommonContactQO();
		List<CommonContactDTO> travelers = new ArrayList<CommonContactDTO>();
		
		List<String> ids = hslH5CreateLineOrderCommand.getTravelerIds();
		for (String id : ids) {
			commonContactQO.setId(id);
			CommonContactDTO commonContact=this.commonContactService.queryUnique(commonContactQO);
			travelers.add(commonContact);
		}
		
		DateSalePrice dateSalePrice = new DateSalePrice();
		//【2】如果线路订单不为空
		if(StringUtils.isNotBlank(dealerOrderNo)){
			HslLineOrderQO hslLineOrderQO = new HslLineOrderQO();
			hslLineOrderQO.setDealerOrderNo(dealerOrderNo);
			List<LineOrder> lineOrders = lineOrderLocalService.queryList(hslLineOrderQO);
			lineOrder = lineOrders.get(0);
			orderType = lineOrderStatusCheck(lineOrder);
			Line lineSnap = JSON.parseObject(lineOrder.getLineSnapshot().getAllInfoLineJSON(), Line.class);
			HgLogger.getInstance().info(devName, "A000："+lineOrder.getLineSnapshot().getAllInfoLineJSON());
			//从快照里去 预定金比例
			bookRate = lineSnap.getPayInfo().getDownPayment();
			HgLogger.getInstance().info(devName, "从快照里去 预定金比例：bookRate："+bookRate);

			//totalMoney=lineOrder.getBaseInfo().getSalePrice();
			//计算所有非取消状态的游客订单金额
			for(LineOrderTraveler traveler:lineOrder.getTravelerList()){
				if(!traveler.getLineOrderStatus().getOrderStatus().equals(Integer.parseInt(XLOrderStatusConstant.SHOP_ORDER_CANCEL))){
					totalMoney+=traveler.getSingleSalePrice();
				}
			}
			model.addAttribute("dealerOrderNo", lineOrder.getBaseInfo().getDealerOrderNo());
		}else{//
			DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
			dateSalePriceQO.setSaleDate(hslH5CreateLineOrderCommand.getTravelDate());
			dateSalePriceQO.setLineId(hslH5CreateLineOrderCommand.getLineId());
			List<DateSalePrice> dateSalePrices = dateSalePriceLocalService.queryList(dateSalePriceQO);
			dateSalePrice = dateSalePrices.get(0);
			totalMoney = dateSalePrice.getAdultPrice()*hslH5CreateLineOrderCommand.getAdultNo()+dateSalePrice.getChildPrice()*hslH5CreateLineOrderCommand.getChildNo();
			HgLogger.getInstance().info(devName, "totalMoney："+totalMoney);
		}
		
		//【3】计算预定金,尾款
		
		//取总金额保留2位小数
		totalMoney = MoneyUtil.round(totalMoney, 2);
		
		//尾款比例
		Double retainageRate = (100 -bookRate);
		
		HgLogger.getInstance().info(devName, "尾款比例：retainageRate："+retainageRate);
		//预定金
		Double bookMoney = bookRate*totalMoney/100;
		HgLogger.getInstance().info(devName, "预定金：bookMoney："+bookMoney);
		bookMoney = MoneyUtil.round(bookMoney, 2);
		//尾款
		Double retainageBfore = retainageRate*totalMoney/100;
		retainageBfore = MoneyUtil.round(retainageBfore, 2);
		HgLogger.getInstance().info(devName, "尾款：retainageBfore："+retainageBfore);
		//尾款折扣金额
		Double retainageMoney = retainageBfore;
		retainageMoney = MoneyUtil.round(retainageMoney, 2);
		//预定金折扣金额
		Double discountBookMoney = bookMoney;
		discountBookMoney = MoneyUtil.round(discountBookMoney, 2);
		
		if(StringUtils.isNotBlank(dealerOrderNo)){
			//折扣金额从卡券中获取
			HslCouponQO hslCouponQO = new HslCouponQO();
			hslCouponQO.setOrderId(dealerOrderNo);
			List<CouponDTO> coupons = couponService.queryList(hslCouponQO);
			HgLogger.getInstance().info(devName, "A00,卡券数量大小:"+coupons.size());
			if(CollectionUtils.isNotEmpty(coupons)){
				for (CouponDTO coupon : coupons) {
					totalDisCount += discount = coupon.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
					Double money1 = coupon.getConsumeOrder().getPayPrice();
					Double money = coupon.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
					HgLogger.getInstance().info(devName, "A01,卡券面额:"+money+",money1"+money1);
					money = MoneyUtil.round(money, 2);
					money1 = MoneyUtil.round(money1, 2);
					//当前订单金额,如果支付预定金就是预定金额,如果是尾款就是尾款金额
					BigDecimal data1 = new BigDecimal(money1);
					//需要支付的预定金金额
					BigDecimal data2 = new BigDecimal(bookMoney);
					//需要支付的尾款金额
					BigDecimal data3 = new BigDecimal(retainageBfore);
					//如果优惠券订单快照金额 等于 预定金的金额
					if(data1.compareTo(data2) == 0){//支付的是预定金
						discount = coupon.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
						newType = 1;
						//如果优惠券订单快照金额 等于 尾款的金额
						HgLogger.getInstance().info(devName, "支付的是预定金：newType："+1+",折扣："+discount);
					}else if(data1.compareTo(data3) == 0){//支付的是尾款
						discount = coupon.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
						newType = 2;
						HgLogger.getInstance().info(devName, "支付的是预定金：newType："+2+",折扣："+discount);
					}
					
					if(discountBefo != 0D){
						totalDisCount +=discountBefo;
					}
				}
				
			}else{
				totalDisCount = discountBefo;
				discount = discountBefo;
			}
		}
		
		//如果是预定金已经使用了卡券,那么应该是 支付尾款
		if(orderType  == 1 || orderType  == 2){
			type =1;
			HgLogger.getInstance().info(devName, "A1："+1);
		}else if(orderType == 3 ||orderType == 4 || orderType == 5){
			if(newType == 1){
				discount =discountBefo;
			}
			HgLogger.getInstance().info(devName, "A2："+2+",discount:"+discount);
			type=2;
		}
		

		//如果用户使用的优惠券金额大于订单总金额
		totalDisCount = MoneyUtil.round(totalDisCount, 2);
		totalMoney = MoneyUtil.round(totalMoney, 2);
	
		
		if(type == 1 || type ==4){//折扣预定金
			discountBookMoney = bookMoney - discount;
			discountBookMoney = MoneyUtil.round(discountBookMoney, 2);
			if(discountBookMoney <= 0D){
				usedCoupon = true;
				discountBookMoney = 0D;
				HgLogger.getInstance().info(devName, "A3");
			}
			
			if(totalDisCount > bookMoney){
				totalDisCount = bookMoney;
			}
			
		}else if(type == 2){//折扣尾款
			retainageMoney = retainageMoney - discount;
			retainageMoney = MoneyUtil.round(retainageMoney, 2);
			if(retainageMoney <= 0D){
				usedCoupon = true;
				retainageMoney = 0D;
				HgLogger.getInstance().info(devName, "A4");
			}
			HgLogger.getInstance().info(devName, "A5");
			status = 2;
			if(orderType == 5){
				if(totalDisCount > totalMoney){
					totalDisCount = totalMoney;
				}
			}else{
				if(totalDisCount > bookMoney){
					totalDisCount = bookMoney;
				}
			}
		}else if(type==3){
			retainageMoney = retainageMoney - discount;
			retainageMoney = MoneyUtil.round(retainageMoney, 2);
			status = 2;
			
			if(totalDisCount > totalMoney){
				totalDisCount = totalMoney;
			}
		}
		
		discountMoney = totalMoney - discountBookMoney - retainageMoney;
		discountMoney = MoneyUtil.round(discountMoney, 2);
		HgLogger.getInstance().info(devName, "A7,discountMoney:"+discountMoney);
		model.addAttribute("totalMoney", totalMoney);
		model.addAttribute("bookRate", bookRate);
		model.addAttribute("retainageRate", retainageRate);
		model.addAttribute("bookMoney", bookMoney);
		retainageMoney = MoneyUtil.round(retainageMoney, 2);
		model.addAttribute("retainageMoney", retainageMoney);
		model.addAttribute("retainageBfore", retainageBfore);
		
		model.addAttribute("lineSalePrice", dateSalePrice);
		model.addAttribute("lineDto", line);
		model.addAttribute("travelers", travelers);
		model.addAttribute("lineOrderCommand", hslH5CreateLineOrderCommand);
		discountBookMoney = MoneyUtil.round(discountBookMoney, 2);
		model.addAttribute("discountBookMoney", discountBookMoney);
		HgLogger.getInstance().info(devName, "A20,totalMoney:"+totalMoney+",bookRate:"+bookRate+",retainageRate"+retainageRate);
		HgLogger.getInstance().info(devName, "A20,bookMoney:"+bookMoney+",retainageMoney:"+retainageMoney+",retainageBfore"+retainageBfore);
		HgLogger.getInstance().info(devName, "A13,discountMoney:"+discountMoney+",discountBookMoney:"+discountBookMoney);
		model.addAttribute("discountMoney", totalDisCount);
		HgLogger.getInstance().info(devName, "A9,bookMoney:"+bookMoney+",retainageMoney:"+retainageMoney);
		if(bookMoney == 0D || retainageMoney == 0D){//无预定金或者尾款
			if(bookMoney != 0D){
				HgLogger.getInstance().info(devName, "A10,bookMoney:"+usedCoupon);
				if(usedCoupon){
					lineType = 2;
				}else{
					lineType = 3;//100%支付预定金
				}
			}else if(retainageMoney != 0D){
				if(orderType == 1){
					lineType = 2;
				}else{
					lineType = 4;//100%支付尾款
				}
			}else{
				lineType = 2;
			}
			HgLogger.getInstance().info(devName, "A11,orderType:"+orderType+",lineType:"+lineType);
		}else{
			//使用折扣券后,所需预定金或者尾款小于等于0
			if(discountBookMoney == 0 ||retainageMoney == 0){
				if(discountBookMoney != 0D){
					lineType = 3;//100%支付预定金
				}else if(retainageMoney != 0D){
					if(orderType ==1){
						lineType = 2;
					}else{
						lineType = 4;//100%支付尾款
					}
				}else{
					lineType = 2;
				}
			}else{
				//需要预定金或者尾款
				lineType = 5;
			}
			HgLogger.getInstance().info(devName, "A12,orderType:"+orderType+",lineType:"+lineType);
		}
		
		//判断是否需要全款支付
		if(lineOrder != null && lineOrder.needPayAll()){
			HgLogger.getInstance().info(devName, "A14,需要支付全款:discountBookMoney:"+discountBookMoney+",retainageMoney"+retainageMoney);
			if(usedCoupon){
				discountTotalMoney = totalMoney - discount;
				discountTotalMoney = MoneyUtil.round(discountTotalMoney, 2);
				if(discountTotalMoney <= 0D){
					discountTotalMoney = 0D;
				}
			}else{
				discountTotalMoney = discountBookMoney + retainageMoney;
			}
			discountTotalMoney = MoneyUtil.round(discountTotalMoney, 2);
			if(discountTotalMoney == 0D){
				lineType = 2;
			}else{
				lineType = 3;
			}
			status = 3;
			HgLogger.getInstance().info(devName, "A15,需要支付全款:lineType:"+lineType+",status"+status+",discountTotalMoney:"+discountTotalMoney);
		}
		HgLogger.getInstance().info(devName, "A16,lineType:"+lineType+",status"+status+",discountTotalMoney:"+discountTotalMoney);
		HgLogger.getInstance().info(devName, "A111,discount:"+discount);
		model.addAttribute("status", status);
		model.addAttribute("discountTotalMoney", discountTotalMoney);
		
		return lineType;
	}
	
	
	/**
	 *
	 * @方法功能说明：支付成功
	 * @修改者名字：hgg
	 * @修改时间：2015年10月16日上午9:12:48
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param orderId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/success")
	public String success(Model model,String dealerOrderNo){
		
		//【1】查询订单
		HslLineOrderQO hslLineOrderQO = new HslLineOrderQO();
		hslLineOrderQO.setDealerOrderNo(dealerOrderNo);
		List<LineOrder> lineOrders = lineOrderLocalService.queryList(hslLineOrderQO);
		LineOrder lineOrder = lineOrders.get(0);
		
		HgLogger.getInstance().info(devName, "支付成功开始");
		//【3】设置订单command
		HslH5CreateLineOrderCommand hslH5CreateLineOrderCommand = setOrderCommand(lineOrder);
		
		//【2】设置到返回页面的参数{1：无需支付定金,2：使用折扣券后,无需要支付定金,3:需要预定金}
		orderPayTemp(model,hslH5CreateLineOrderCommand,0D,0,dealerOrderNo);
		
		HgLogger.getInstance().info(devName, "支付成功结束");
		HgLogger.getInstance().info(devName, "经销商编号:"+ lineOrder.getBaseInfo().getDealerOrderNo());
		model.addAttribute("dealerOrderNo", lineOrder.getBaseInfo().getDealerOrderNo());
		
		return "line/orderSuccess";
	}
	
	/**
	 *
	 * @方法功能说明：设置HslH5CreateLineOrderCommand
	 * @修改者名字：hgg
	 * @修改时间：2015年10月12日下午5:07:21
	 * @修改内容：
	 * @参数：@param lineOrder
	 * @参数：@return
	 * @return:HslH5CreateLineOrderCommand
	 * @throws
	 */
	private HslH5CreateLineOrderCommand setOrderCommand(LineOrder lineOrder){
		
		HslH5CreateLineOrderCommand hslH5CreateLineOrderCommand = new HslH5CreateLineOrderCommand();
		hslH5CreateLineOrderCommand.setLineId(lineOrder.getLineSnapshot().getLine().getId());
		hslH5CreateLineOrderCommand.setAdultNo(lineOrder.getBaseInfo().getAdultNo());
		hslH5CreateLineOrderCommand.setChildNo(lineOrder.getBaseInfo().getChildNo());
		hslH5CreateLineOrderCommand.setTravelDate(lineOrder.getBaseInfo().getTravelDate());
		
		return hslH5CreateLineOrderCommand;
	}
	
	/**
	 * @方法功能说明：用户订单状态检查
	 * @修改者名字：hgg
	 * @修改时间：2015年10月14日下午3:07:20
	 * @修改内容：
	 * @参数：@param lineOrder
	 * @参数：@return
	 * @return:Integer
	 * @throws
	 */
	public Integer lineOrderStatusCheck(LineOrder lineOrder){
		
		Integer status = 2101;
		List<LineOrderTraveler> travelerList = lineOrder.getTravelerList();
		if(CollectionUtils.isNotEmpty(travelerList)){
			//遍历订单游客状态，如果有未取消状态的游客，获取该游客的订单支付状态
			for(LineOrderTraveler traveler:travelerList){
				Integer orderStatus=traveler.getLineOrderStatus().getOrderStatus();
				if(!Integer.valueOf(XLOrderStatusConstant.SHOP_ORDER_CANCEL).equals(orderStatus)){
					status = traveler.getLineOrderStatus().getPayStatus();
					break;
				}
			}
			
			
		}
		// 状态检查
		if (Integer.valueOf(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY).equals(status)) {
			// 等待支付预订金
			return 1;
		} else if (Integer.valueOf(XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY).equals(status)) {
			// 等待支付尾款
			return 3;
		} else if(Integer.valueOf(XLOrderStatusConstant.SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX).equals(status)){
			//已支付预定金
			return 2;
		}else if(Integer.valueOf(XLOrderStatusConstant.SHOP_RECEIVE_BALANCE_MONEY_WAIT_PAY_FX).equals(status)){
			//已收到尾款
			return 4;
		}
		
		return 5;
	}
	
	
	/**
	 * @方法功能说明：判断用户是否已经支付了尾款
	 * 用户订单状态待【支付订金,已收到订金,等待支付尾款】时,订单详情页面才显示立即提交按钮
	 * @修改者名字：hgg
	 * @修改时间：2015年10月16日下午5:38:51
	 * @修改内容：
	 * @参数：@param lineOrder
	 * @参数：@return
	 * @return:Integer
	 * @throws
	 */
	private Integer orderStatus(LineOrder lineOrder){
		List<LineOrderTraveler> travelerList = lineOrder.getTravelerList();
		if(CollectionUtils.isNotEmpty(travelerList)){
			LineOrderTraveler traveler = travelerList.get(0);
			Integer payStatus = traveler.getLineOrderStatus().getPayStatus();
			Integer orderStatus = traveler.getLineOrderStatus().getOrderStatus();
			if ((Integer.valueOf(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY).equals(payStatus)||
					Integer.valueOf(XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY).equals(payStatus)) && !Integer.valueOf(XLOrderStatusConstant.SHOP_ORDER_CANCEL).equals(orderStatus)) {
				return 1;
			} else{
				return 2;
			}
		}
		
		return 1;
	}
	
	/**
	 * 获取webapp路径
	 * @return
	 */
	private String getWebAppPath(HttpServletRequest request) {
		Boolean isRoot = Boolean.parseBoolean(SysProperties.getInstance().get("root"));
		String proj = request.getContextPath();
		String port = SysProperties.getInstance().get("port");
		String system = "http://" + SysProperties.getInstance().get("host") +
				("80".equals(port) ? "" : ":" + port);
		if (!isRoot) {
			system += proj;
		}
		return system;
	}

	/**
	 * 修改占用状态的账户余额
	 * @param userId
	 * @param OrderNo
	 */
	public  void updateAccount(String userId,String OrderNo){
		AccountConsumeSnapshotQO qo = new AccountConsumeSnapshotQO();
		AccountQO accountQO = new AccountQO();
		accountQO.setUserID(userId);
		AccountDTO dto = accountService.queryUnique(accountQO);
		if(dto!=null){
			qo.setAccountId(dto.getId());
			qo.setAccountstatus(AccountConsumeSnapshot.STATUS_ZY);
			qo.setOrderId(OrderNo);
			HgLogger.getInstance().info("zhaows", "支付平台生成订单查询是否有消费快照QO--pay" + JSON.toJSONString(qo));
			List<AccountConsumeSnapshotDTO> consumeSnapshotList = accountConsumeSnapshotService.queryList(qo);
			HgLogger.getInstance().info("zhaows", "支付平台生成订单查询是否有消费快照实体--pay" + JSON.toJSONString(consumeSnapshotList));
			String ids="";
			for (AccountConsumeSnapshotDTO consumeSnapshot : consumeSnapshotList) {
				ids+=consumeSnapshot.getId()+",";
			}
			accountConsumeSnapshotService.update(ids);
		}
	}
}

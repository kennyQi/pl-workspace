package slfx.admin.controller.traveline;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.multipart.MultipartFile;

import slfx.admin.common.AdminConfig;
import slfx.admin.controller.BaseController;
import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import slfx.xl.pojo.command.line.XLUpdateOrderSalePriceMessageApiCommand;
import slfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;
import slfx.xl.pojo.command.order.CancleLineOrderCommand;
import slfx.xl.pojo.command.order.ChangeLineOrderStatusCommand;
import slfx.xl.pojo.command.order.ModifyPaymentLineOrderCommand;
import slfx.xl.pojo.command.order.UploadLineOrderFileCommand;
import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.dto.order.LineOrderDTO;
import slfx.xl.pojo.dto.order.LineOrderFileDTO;
import slfx.xl.pojo.dto.order.LineOrderPaymentDTO;
import slfx.xl.pojo.dto.order.LineOrderTravelerDTO;
import slfx.xl.pojo.dto.order.XLOrderStatusDTO;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.pojo.qo.LineOrderFileQO;
import slfx.xl.pojo.qo.LineOrderPaymentQO;
import slfx.xl.pojo.qo.LineOrderQO;
import slfx.xl.pojo.system.LineConstants;
import slfx.xl.pojo.system.LineOrderConstants;
import slfx.xl.pojo.system.XLOrderStatusConstant;
import slfx.xl.spi.inter.LineOrderFileService;
import slfx.xl.spi.inter.LineOrderService;
import slfx.xl.spi.inter.LineService;
import slfx.xl.spi.inter.LineSnapshotService;

import com.alibaba.fastjson.JSON;

/**
 *@类功能说明：线路订单Controller
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江票量云科技股份有限公司
 *@部门：技术部
 *@作者：tandeng
 *@创建时间：2014年12月9日下午3:41:53
 */
@Controller
@RequestMapping("/traveline/order")
public class LineOrderController extends BaseController{

	@Autowired
	private LineOrderService lineOrderService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private LineOrderFileService lineOrderFileService;
	
	@Autowired
	private LineSnapshotService lineSnapshotService;
	
	@Autowired
	private LineService lineService;
	
	/**
	 * 
	 * @方法功能说明：线路订单列表查询
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月16日下午3:23:58
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param lineOrderQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/list")
	public String queryLineOrderList(HttpServletRequest request, Model model,
			@ModelAttribute LineOrderQO lineOrderQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
			) {
		
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = (pageNo == null) ? new Integer(1) : pageNo;
		pageSize = (pageSize == null) ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		//查询条件
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			lineOrderQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			lineOrderQO.setCreateDateTo(DateUtil.dateStr2EndDate(endTimeStr));
		}
		
		if (StringUtils.isBlank(lineOrderQO.getStartingDepart())) {
			lineOrderQO.setStartingProvinceID(null);
			lineOrderQO.setStartingCityID(null);
		}
		
		if (StringUtils.isBlank(lineOrderQO.getDomesticLine())) {
			lineOrderQO.setCityOfType(null);
			lineOrderQO.setProvinceOfType(null);
		}
		
		//按创建时间倒序排序
		lineOrderQO.setCreateDateAsc(false);
		
		pagination.setCondition(lineOrderQO);
		
		pagination = lineOrderService.queryPagination(pagination);
		
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
		//查询所有省信息
		ProvinceQo province = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(province);
		//有出发城市查询条件则查询该城市所在省份的所有城市
		if(StringUtils.isNotBlank(lineOrderQO.getStartingCityID())){
			CityQo startCityQo = new CityQo();
			startCityQo.setProvinceCode(lineOrderQO.getStartingProvinceID());
			List<City> startCityList = cityService.queryList(startCityQo);
			model.addAttribute("startCityList", startCityList);
		}
				
		//有线路类型城市编号查询条件则查询该城市所在省份的所有城市
		if(StringUtils.isNotBlank(lineOrderQO.getCityOfType())){
			CityQo typeCityQo = new CityQo();
			typeCityQo.setProvinceCode(lineOrderQO.getProvinceOfType());
			List<City> typeCityList = cityService.queryList(typeCityQo);
			model.addAttribute("typeCityList", typeCityList);
		}
		
		
		
		model.addAttribute("lineOrderQO", lineOrderQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("cityList", cityList);
		model.addAttribute("provinceList", provinceList);
		//订单状态
		model.addAttribute("statusMap", XLOrderStatusConstant.SLFX_XLORDER_STATUS_MAP); 
		//订单支付状态
		model.addAttribute("payStatusMap", XLOrderStatusConstant.SLFX_XLORDER_PAY_STATUS_MAP); 
		//线路类型
		model.addAttribute("typeMap", LineConstants.typeMap); 
		
		
		return "/traveline/order/order_list.html";
	}
	
	/**
	 * 
	 * @方法功能说明：线路列表中查询订单列表
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月16日下午3:23:58
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param lineOrderQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/list_by_line")
	public String queryLineOrderListByLine(HttpServletRequest request, Model model,
			@ModelAttribute LineOrderQO lineOrderQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
			) {
		
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = (pageNo == null) ? new Integer(1) : pageNo;
		pageSize = (pageSize == null) ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		//查询条件
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			lineOrderQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			lineOrderQO.setCreateDateTo(DateUtil.dateStr2BeginDate(endTimeStr));
		}
		
		//按创建时间倒序排序
		lineOrderQO.setCreateDateAsc(false);
		
		pagination.setCondition(lineOrderQO);
		
		pagination = lineOrderService.queryPagination(pagination);
		
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
		
		model.addAttribute("lineOrderQO", lineOrderQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("citylist", cityList);
		//订单状态
		model.addAttribute("statusMap", XLOrderStatusConstant.SLFX_XLORDER_STATUS_MAP); 
		//订单支付状态
		model.addAttribute("payStatusMap", XLOrderStatusConstant.SLFX_XLORDER_PAY_STATUS_MAP); 
		//线路类型
		model.addAttribute("typeMap", LineConstants.typeMap); 
		
		
		return "/traveline/order/order_list_use_by_line.html";
	}
	
	/**
	 * @方法功能说明：线路订单基本信息详情
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月19日上午9:42:20
	 * @修改内容：
	 * @参数：id 线路的id号
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/detail")
	public String lineOrderDetail(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineOrderId) {
		LineOrderQO qo = new LineOrderQO();
		qo.setId(lineOrderId);
		LineOrderDTO lineOrderDTO = lineOrderService.queryOrderDetail(qo);
		
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
		
		model.addAttribute("lineOrderDTO", lineOrderDTO);
		model.addAttribute("citylist", cityList);
		//订单状态
		model.addAttribute("statusMap", XLOrderStatusConstant.SLFX_XLORDER_STATUS_MAP); 
		//订单支付状态
		model.addAttribute("payStatusMap", XLOrderStatusConstant.SLFX_XLORDER_PAY_STATUS_MAP);
		//游客类别
		model.addAttribute("TTAVELER_TYPE_MAP",LineOrderConstants.TTAVELER_TYPE_MAP);
		//证件类型
		model.addAttribute("TTAVELER_ID_TYPE_MAP",LineOrderConstants.TTAVELER_ID_TYPE_MAP);

		return "/traveline/order/order_detail.html";
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到修改订单页面
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日上午9:27:46
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineOrderId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/toedit")
	public String toEdit(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineOrderId){
		
		LineOrderQO qo = new LineOrderQO();
		qo.setId(lineOrderId);
		LineOrderDTO lineOrderDTO = lineOrderService.queryOrderDetail(qo);
		model.addAttribute("lineOrderDTO", lineOrderDTO);
		return "/traveline/order/order_edit.html";
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到订单取消页面
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日下午2:12:53
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/tocancle")
	public String toCancleLineOrder(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineOrderID){
		
		LineOrderQO lineOrderQO = new LineOrderQO();
		lineOrderQO.setId(lineOrderID);
		LineOrderDTO lineOrderDTO = lineOrderService.queryOrderDetail(lineOrderQO);
		Set<LineOrderTravelerDTO> travellerList = lineOrderDTO.getTravelers();
		Iterator<LineOrderTravelerDTO> iterator = travellerList.iterator();
		while(iterator.hasNext()){
			LineOrderTravelerDTO traveller = iterator.next();
			//订单已取消的游客不显示在取消列表里
			if(XLOrderStatusConstant.SLFX_ORDER_CANCEL.equals(traveller.getXlOrderStatus().getStatus()  + "")){ 
				iterator.remove();
			}
		}
		model.addAttribute("lineOrderDTO", lineOrderDTO);
		model.addAttribute("id", lineOrderID);
		model.addAttribute("travellerList", travellerList);
		return "/traveline/order/order_cancle.html";
	}
	/**
	 * 
	 * @方法功能说明：取消订单
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日上午11:10:39
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
	public String cancleLineOrder(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute CancleLineOrderCommand command){
		
		String message = "";
		String statusCode = "";
		try{
			
			if(StringUtils.isBlank(command.getLineOrderTravelers())){
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "操作成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineorder");
			}
			Boolean result = lineOrderService.cancleLineOrder(command);
			if(result){
				message = "取消成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
				
				//根据订单id获取线路订单并通知 
				LineOrderQO lineOrderQO = new LineOrderQO();
				lineOrderQO.setId(command.getLineOrderID());
				HgLogger.getInstance().info("yuqz", "LineOrderController->cancleLineOrder->通知开始----lineOrderQO:" + JSON.toJSONString(lineOrderQO));
				
				LineOrderDTO lineOrderDTO = lineOrderService.queryOrderDetail(lineOrderQO);
				
				HgLogger.getInstance().info("yuqz", "LineOrderController->cancleLineOrder->通知修改订单状态开始>>>>>");
				XLUpdateOrderStatusMessageApiCommand apiCommand = new XLUpdateOrderStatusMessageApiCommand();
				//设置线路id
				apiCommand.setLineOrderID(lineOrderDTO.getBaseInfo().getDealerOrderNo());
				//设置游玩人信息
				LineOrderQO newLineOrderQO = new LineOrderQO();
				HgLogger.getInstance().info("yuqz", "LineOrderController->cancleLineOrder->线路订单id=" + lineOrderDTO.getId());
				newLineOrderQO.setId(lineOrderDTO.getId());
				LineOrderDTO newLineOrderDTO = lineOrderService.queryUnique(newLineOrderQO);
				HgLogger.getInstance().info("yuqz", "LineOrderController->cancleLineOrder->newLineOrderDTO=" + JSON.toJSONString(newLineOrderDTO));
				if(null != newLineOrderDTO.getTravelers()){
				
					for(LineOrderTravelerDTO lineOrderTravelerDTO:newLineOrderDTO.getTravelers()){
						LineOrderDTO lineOrderDTO2=new LineOrderDTO();
						XLOrderStatusDTO xlOrderStatus = new XLOrderStatusDTO();
						xlOrderStatus.setStatus(lineOrderTravelerDTO.getXlOrderStatus().getStatus() - 1000);
						xlOrderStatus.setPayStatus(lineOrderTravelerDTO.getXlOrderStatus().getPayStatus() - 1000);
						lineOrderDTO2.setId(lineOrderTravelerDTO.getLineOrder().getId());
						lineOrderTravelerDTO.setXlOrderStatus(xlOrderStatus);
						lineOrderTravelerDTO.setLineOrder(lineOrderDTO2);
					}
					apiCommand.setTravelers(newLineOrderDTO.getTravelers());
					apiCommand.setOrderNo(newLineOrderDTO.getBaseInfo().getOrderNo());
					//通知经销商游玩人订单状态改变
					lineOrderService.sendLineOrderUpdateMessage(apiCommand);
					HgLogger.getInstance().info("yuqz", "LineOrderController->cancleLineOrder->通知经销商游玩人订单状态改变");
				}
				//通知同步线路
				LineDTO lineDTO = new LineDTO();
				lineDTO.setId(lineOrderDTO.getLineSnapshot().getLine().getId());
				lineSnapshotService.createLineSnapshot(lineDTO);
				XLUpdateLineMessageApiCommand xlUpdateLineMessageApiCommand = new XLUpdateLineMessageApiCommand();
				xlUpdateLineMessageApiCommand.setLineId(lineOrderDTO.getLineSnapshot().getLine().getId());
				lineService.sendLineUpdateMessage(xlUpdateLineMessageApiCommand);
			}else{
				message = "取消失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
//				HgLogger.getInstance().error("luoyun", "旅游线路管理-线路订单-取消订单失败" + JSON.toJSONString(command));
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineorder");
			
		}catch(SlfxXlException e){
			message = e.getMessage();
			statusCode = DwzJsonResultUtil.STATUS_CODE_300;
//			HgLogger.getInstance().error("luoyun", "旅游线路管理-线路订单-取消订单失败" + e.getMessage());
			return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineorder");
		}catch(Exception e){
			message = "取消失败";
			statusCode = DwzJsonResultUtil.STATUS_CODE_300;
//			HgLogger.getInstance().error("luoyun", "旅游线路管理-线路订单-取消订单失败" + e.getMessage());
			return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineorder");
		}
		
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到更改订单状态页面
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日下午4:17:34
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/tochange")
	public String toChangeLineOrderStatus(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineOrderID){
		
		LineOrderQO lineOrderQO = new LineOrderQO();
		lineOrderQO.setId(lineOrderID);
		LineOrderDTO lineOrderDTO = lineOrderService.queryOrderDetail(lineOrderQO);
		Set<LineOrderTravelerDTO> travellerList = lineOrderDTO.getTravelers();
		Iterator<LineOrderTravelerDTO> iterator = travellerList.iterator();
		while(iterator.hasNext()){
			LineOrderTravelerDTO traveller = iterator.next();
//			当订单状态为下单成功未订位， 支付状态为已支付订金时才可更改订单状态
//			if(XLOrderStatusConstant.SLFX_CREATE_SUCCESS_NOT_RESERVE .equals(traveller.getXlOrderStatus().getStatus() + "")
//					&& XLOrderStatusConstant.SLFX_PAY_BARGAIN_MONEY.equals(traveller.getXlOrderStatus().getPayStatus() + "")){ 
			//当订单状态为下单成功未订位,支付状态为已支付订金或全款时才可以通知直销可以付尾款
			if(XLOrderStatusConstant.SLFX_CREATE_SUCCESS_NOT_RESERVE .equals(traveller.getXlOrderStatus().getStatus() + "")
					&& (XLOrderStatusConstant.SLFX_PAY_BARGAIN_MONEY.equals(traveller.getXlOrderStatus().getPayStatus() + "")
					|| XLOrderStatusConstant.SLFX_PAY_SUCCESS.equals(traveller.getXlOrderStatus().getPayStatus() + ""))){ 
				
			}else{
				iterator.remove();
			}
		}
		//让前台显示平台订单号
		model.addAttribute("lineOrderDTO", lineOrderDTO);
		model.addAttribute("id", lineOrderID);
		model.addAttribute("travellerList", travellerList);
		return "/traveline/order/order_change.html";
	}
	
	/**
	 * 
	 * @方法功能说明：更改订单状态
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日下午4:24:29
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/change")
	@ResponseBody
	public String changeOrderStatus(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute ChangeLineOrderStatusCommand command){
		HgLogger.getInstance().info("yuqz", "LineOrderController->changeOrderStatus->ChangeLineOrderStatusCommand:" + JSON.toJSONString(command));
		String message = "";
		String statusCode = "";
		try{
			
			if(StringUtils.isBlank(command.getLineOrderTravelers())){
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "操作成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineorder");
			}
			Boolean result = lineOrderService.changeLineOrderStatus(command);
			if(result){
				message = "更改订单状态成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
				
				//根据订单id获取线路订单并通知 
				LineOrderQO lineOrderQO = new LineOrderQO();
				lineOrderQO.setId(command.getLineOrderID());
				HgLogger.getInstance().info("yuqz", "LineOrderController->changeOrderStatus->通知开始----lineOrderQO:" + JSON.toJSONString(lineOrderQO));
				
				LineOrderDTO lineOrderDTO = lineOrderService.queryOrderDetail(lineOrderQO);
				
				HgLogger.getInstance().info("yuqz", "LineOrderController->changeOrderStatus->通知修改订单状态开始>>>>>");
				XLUpdateOrderStatusMessageApiCommand apiCommand = new XLUpdateOrderStatusMessageApiCommand();
				//设置线路id
				apiCommand.setLineOrderID(lineOrderDTO.getBaseInfo().getDealerOrderNo());
				//设置游玩人信息
				LineOrderQO newLineOrderQO = new LineOrderQO();
				HgLogger.getInstance().info("yuqz", "LineOrderController->changeOrderStatus->线路订单id=" + lineOrderDTO.getId());
				newLineOrderQO.setId(lineOrderDTO.getId());
				LineOrderDTO newLineOrderDTO = lineOrderService.queryUnique(newLineOrderQO);
				HgLogger.getInstance().info("yuqz", "LineOrderController->changeOrderStatus->newLineOrderDTO=" + JSON.toJSONString(newLineOrderDTO));
				if(null != newLineOrderDTO.getTravelers()){
				
					for(LineOrderTravelerDTO lineOrderTravelerDTO:newLineOrderDTO.getTravelers()){
						LineOrderDTO lineOrderDTO2=new LineOrderDTO();
						XLOrderStatusDTO xlOrderStatus = new XLOrderStatusDTO();
						xlOrderStatus.setStatus(lineOrderTravelerDTO.getXlOrderStatus().getStatus() - 1000);
						xlOrderStatus.setPayStatus(lineOrderTravelerDTO.getXlOrderStatus().getPayStatus() - 1000);
						lineOrderDTO2.setId(lineOrderTravelerDTO.getLineOrder().getId());
						lineOrderTravelerDTO.setXlOrderStatus(xlOrderStatus);
						lineOrderTravelerDTO.setLineOrder(lineOrderDTO2);
					}
					apiCommand.setTravelers(newLineOrderDTO.getTravelers());
					apiCommand.setOrderNo(newLineOrderDTO.getBaseInfo().getOrderNo());
					//通知经销商游玩人订单状态改变
					lineOrderService.sendLineOrderUpdateMessage(apiCommand);
					HgLogger.getInstance().info("yuqz", "LineOrderController->changeOrderStatus->通知结束");
				}
			}else{
				message = "更改订单状态失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
//				HgLogger.getInstance().error("luoyun", "旅游线路管理-线路订单-更改订单状态失败" + JSON.toJSONString(command));
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineorder");
			
		}catch(SlfxXlException e){
			message = e.getMessage();
			statusCode = DwzJsonResultUtil.STATUS_CODE_300;
//			HgLogger.getInstance().error("luoyun", "旅游线路管理-线路订单-更改订单状态失败" + e.getMessage());
			return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineorder");
		}catch(Exception e){
			message = "更改订单状态失败";
			statusCode = DwzJsonResultUtil.STATUS_CODE_300;
//			HgLogger.getInstance().error("luoyun", "旅游线路管理-线路订单-更改订单状态失败" + e.getMessage());
			return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineorder");
		}
		
	}
	
	/**
	 * 
	 * @方法功能说明：查询订单支付信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日下午5:05:12
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/payinfo")
	public String queryLineOrderPayInfo(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute LineOrderPaymentQO qo){
		
		List<LineOrderPaymentDTO>  payDTOList = lineOrderService.queryLineOrderPayInfo(qo);
		model.addAttribute("payDTOList", payDTOList);
		model.addAttribute("PAY_TYPE_MAP", LineOrderConstants.PAY_TYPE_MAP);
		return "/traveline/order/order_payinfo.html";
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到线路订单文件页面
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月24日上午9:13:12
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/filelist")
	public String queryLineOrderFileList(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute LineOrderFileQO qo){
		
		List<LineOrderFileDTO> fileList = new ArrayList<LineOrderFileDTO>();
		fileList = lineOrderFileService.queryList(qo);
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("qo", qo);
		return "/traveline/order/order_file.html";
	}

	/**
	 * 
	 * @方法功能说明：上传线路订单文件
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月24日上午9:44:19
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String uploadLineOrderFile(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute UploadLineOrderFileCommand command,
			@RequestParam(value = "file")MultipartFile file){
		
			double size = file.getSize()/1024.0/1024.0;
			if(size == 0){
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "上传的文件大小不能为0",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineorderfile");
			}
			    
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
			command.setFileName(file.getOriginalFilename());
			FdfsFileInfo fileInfo=null;
			try {
				FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
				fileInfo = FdfsFileUtil.upload(fileInputStream,fileType, null);
			} catch (IOException e) {
//				HgLogger.getInstance().error("tuhualiang", "旅游线路管理-线路订单-线路订单文件上传失败"+HgLogger.getStackTrace(e));
				e.printStackTrace();
			}
			//文件服务器地址
			String fileSaveIp = AdminConfig.imageHost;
			command.setFilePath(fileSaveIp + fileInfo.getUri());
		
			String message = "";
			String statusCode = "";
			try{
				
				Boolean result = lineOrderFileService.uploadLineOrderFile(command);
				
				if(result){
					message = "上传文件成功";
					statusCode = DwzJsonResultUtil.STATUS_CODE_200;
				}else{
					message = "上传文件失败";
					statusCode = DwzJsonResultUtil.STATUS_CODE_300;
	//				HgLogger.getInstance().error("luoyun", "旅游线路管理-线路订单-上传文件失败" + JSON.toJSONString(command));
				}
				return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.FLUSH_FORWARD, "lineorderfile");
				
			}catch(Exception e){
				message = "上传文件失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
	//			HgLogger.getInstance().error("luoyun", "旅游线路管理-线路订单-上传文件失败" + e.getMessage());
				return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.FLUSH_FORWARD, "lineorderfile");
				
			}
		
		
	}
	
	/***
	 * 
	 * @方法功能说明：跳转到修改单人总金额的页面
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年5月29日上午10:05:21
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineOrderId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/toEditPayment")
	public String toEditPayment(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "lineOrderId") String lineOrderId,@RequestParam(value = "travelerId") String travelerId){
		
		LineOrderQO qo = new LineOrderQO();
		qo.setId(lineOrderId);
		LineOrderDTO lineOrderDTO = lineOrderService.queryOrderDetail(qo);
		model.addAttribute("lineOrderDTO", lineOrderDTO);
		model.addAttribute("travelerId", travelerId);
		return "/traveline/order/order_payment_edit.html";
	}
	
	
	
	
	/***
	 * 
	 * @方法功能说明：批量修改单人总金额的页面
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年5月29日上午10:05:21
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineOrderId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/toBatchEditPayment")
	public String toBatchEditPayment(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "lineOrderId") String lineOrderId){
		
		LineOrderQO qo = new LineOrderQO();
		qo.setId(lineOrderId);
		LineOrderDTO lineOrderDTO = lineOrderService.queryOrderDetail(qo);
		model.addAttribute("lineOrderDTO", lineOrderDTO);
		
		return "/traveline/order/order_batch_payment_edit.html";
	}
	/***
	 * 
	 * @方法功能说明：修改支付全款金额
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年5月28日下午3:49:35
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/modifyPayment")
	@ResponseBody
	public String modifyPaymentLineOrder(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute ModifyPaymentLineOrderCommand command){
		   Map<String ,Object> map=new HashMap<String ,Object>();
		   map.put("message", "修改失败");
		   map.put("status", 2);
		   HgLogger.getInstance().info("yaosanfeng", "LineOrderController->modifyPaymentLineOrder->ModifyPaymentLineOrderCommand"+JSON.toJSONString(command));
		   try{
				Boolean result = lineOrderService.modifyPaymentLineOrder(command);
				HgLogger.getInstance().info("yaosanfeng", "LineOrderController->modifyPaymentLineOrder->ModifyPaymentLineOrderCommand->result:"+result);
				if(result){
					map.put("message", "修改成功");
					map.put("status", 1);
					LineOrderQO lineOrderQO = new LineOrderQO();
					lineOrderQO.setId(command.getLineOrderId());
					HgLogger.getInstance().info("yaosanfeng", "LineOrderController->modifyPaymentLineOrder->lineOrderQO:" + JSON.toJSONString(lineOrderQO));
					LineOrderDTO lineOrderDTO = lineOrderService.queryOrderDetail(lineOrderQO);
					HgLogger.getInstance().info("yaosanfeng", "LineOrderController->modifyPaymentLineOrder->通知修改支付全款金额开始--LineOrderDTO:"+JSON.toJSONString(lineOrderDTO));
					XLUpdateOrderSalePriceMessageApiCommand apiCommand = new XLUpdateOrderSalePriceMessageApiCommand();
					//设置线路id
					apiCommand.setLineOrderID(lineOrderDTO.getBaseInfo().getDealerOrderNo());
					apiCommand.setTravelers(lineOrderDTO.getTravelers());//传游玩人信息
					apiCommand.setSalePrice(lineOrderDTO.getBaseInfo().getSalePrice());
					apiCommand.setOrderNo(lineOrderDTO.getBaseInfo().getOrderNo());
					lineOrderService.sendLineOrderUpdateSalePriceMessage(apiCommand);
				    HgLogger.getInstance().info("yaosanfeng", "LineOrderController->modifyPaymentLineOrder->通知修改支付全款金额结束" + JSON.toJSONString(apiCommand));
					return JSON.toJSONString(map);
				}
		   }catch (SlfxXlException e) {
			   map.put("message", "修改失败");
			   map.put("status", 2);
			   e.printStackTrace();
			   HgLogger.getInstance().error("yaosanfeng", "LineOrderController->modifyPaymentLineOrder->error:" + HgLogger.getStackTrace(e));
		 }
		   HgLogger.getInstance().info("yaosanfeng", "LineOrderController->modifyPaymentLineOrder->通知修改支付全款金额结束map:" + JSON.toJSONString(map));   
		return JSON.toJSONString(map);
	}
	
	@RequestMapping("/batchModifyPayment")
	@ResponseBody
	public String batchModifyPayment(HttpServletRequest request,HttpServletResponse response, Model model,
			@ModelAttribute ModifyPaymentLineOrderCommand command){
		   Map<String ,Object> map=new HashMap<String ,Object>();
		   map.put("message", "修改失败");
		   map.put("status", 2);
		   HgLogger.getInstance().info("yaosanfeng", "LineOrderController->batchModifyPayment->ModifyPaymentLineOrderCommand"+JSON.toJSONString(command));
		   try{
			   if(command.getSingleSalePrice() == null){
				   return JSON.toJSONString(map);
			   }
			   Boolean result = lineOrderService.batchModifyPaymentLineOrder(command);
			   if(result){
					map.put("message", "修改成功");
					map.put("status", 1);
					LineOrderQO lineOrderQO = new LineOrderQO();
					lineOrderQO.setId(command.getLineOrderId());
					HgLogger.getInstance().info("yaosanfeng", "LineOrderController->batchModifyPayment->lineOrderQO:" + JSON.toJSONString(lineOrderQO));
					LineOrderDTO lineOrderDTO = lineOrderService.queryOrderDetail(lineOrderQO);
					HgLogger.getInstance().info("yaosanfeng", "LineOrderController->batchModifyPayment->通知修改支付全款金额开始--LineOrderDTO:"+JSON.toJSONString(lineOrderDTO));
					XLUpdateOrderSalePriceMessageApiCommand apiCommand = new XLUpdateOrderSalePriceMessageApiCommand();
					//设置线路id
					apiCommand.setLineOrderID(lineOrderDTO.getBaseInfo().getDealerOrderNo());
					apiCommand.setTravelers(lineOrderDTO.getTravelers());//传游玩人信息
					apiCommand.setSalePrice(lineOrderDTO.getBaseInfo().getSalePrice());
					apiCommand.setOrderNo(lineOrderDTO.getBaseInfo().getOrderNo());
					lineOrderService.sendLineOrderUpdateSalePriceMessage(apiCommand);
					HgLogger.getInstance().info("yaosanfeng", "LineOrderController->batchModifyPayment->通知修改支付全款金额结束" + JSON.toJSONString(apiCommand));
					return JSON.toJSONString(map);
			   }
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		   return JSON.toJSONString(map);
	}
	
}
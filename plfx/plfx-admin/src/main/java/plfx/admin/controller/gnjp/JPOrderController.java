package plfx.admin.controller.gnjp;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.log.domainevent.DomainEventDao;
import hg.log.util.HgLogger;

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
import org.springframework.web.bind.annotation.ResponseBody;

import plfx.admin.controller.BaseController;
import plfx.gnjp.app.service.local.JPWebLocalService;
import plfx.jp.spi.inter.JPOrderLogService;
import plfx.jp.spi.inter.JPPlatformOrderService;
import plfx.jp.spi.inter.JPWebService;
import plfx.yeexing.inter.TimeoutCancelJPOrderService;
import plfx.yeexing.pojo.command.order.JPCancelTicketSpiCommand;
import plfx.yeexing.pojo.command.order.JPRefundQueryOrderStatusSpiCommand;
import plfx.yeexing.pojo.command.order.JPRefundTicketSpiCommand;
import plfx.yeexing.pojo.dto.flight.YeeXingCancelTicketDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingRefundTicketDTO;
import plfx.yeexing.pojo.dto.order.JPOrderDTO;
import plfx.yeexing.pojo.dto.order.JPOrderLogDTO;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
import plfx.yeexing.pojo.dto.order.YeeXingRefundQueryOrderDTO;
import plfx.yeexing.qo.admin.JPOrderLogQO;
import plfx.yeexing.qo.admin.PlatformOrderQO;

import com.alibaba.fastjson.JSON;



/****
 * 
 * @类功能说明：admin机票管理
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月1日下午2:32:16
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value="/jp/order")
public class JPOrderController extends BaseController{
	
	@Autowired
	private DomainEventDao domainEventDao;
	@Autowired
	private  JPPlatformOrderService jpPlatformOrderService;
	@Autowired
	private  JPWebService jPWebService;
	@Autowired
	private JPOrderLogService jPOrderLogService;
	@Autowired
	private JPWebLocalService jPWebLocalService;

    @Autowired
	private TimeoutCancelJPOrderService timeoutCancelJPOrderService;
	/****
	 * 
	 * @方法功能说明：查询订单列表
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月9日上午9:28:02
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param platformOrderQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/list")
	public String queryOrderList(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
			) {
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			platformOrderQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			platformOrderQO.setCreateDateTo(DateUtil.dateStr2EndDate(endTimeStr));
		}
		//按照platformOrderQO传过来的订单状态查询
		if (null != platformOrderQO && platformOrderQO.getTempStatus() != null) {
			platformOrderQO.setStatus(platformOrderQO.getTempStatus());
		}
        //按创建时间倒序排序
		platformOrderQO.setCreateDateAsc(false);
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		//添加查询条件
		pagination.setCondition(platformOrderQO);
		pagination = jpPlatformOrderService.queryFJPOrderList(pagination);
		model.addAttribute("pagination", pagination);
		//订单状态
		model.addAttribute("statusList", JPOrderStatusConstant.PLFX_JPORDER_STATUS_LIST);
		//支付状态
		model.addAttribute("orderPayStatusList", JPOrderStatusConstant.PLFX_JPORDER_PAY_STATUS_LIST);
		//查询条件
		model.addAttribute("platformOrderQO", platformOrderQO);
		//证件类型
		model.addAttribute("DOCUMENT_TYPE_MAP", JPOrderStatusConstant.DOCUMENT_TYPE_MAP);
		//乘客类型
		model.addAttribute("PASSENGER_TYPE_MAP", JPOrderStatusConstant.PASSENGER_TYPE_MAP);
		model.addAttribute("CURRENT_DATE", new Date());
		//成功出票
		model.addAttribute("TICKET_SUCC", JPOrderStatusConstant.PLFX_TICKET_SUCC);
		//退废失败
		model.addAttribute("TICKET_REFUND_FAIL", JPOrderStatusConstant.PLFX_TICKET_REFUND_FAIL);
		//等待支付
		model.addAttribute("PAY_WAIT", JPOrderStatusConstant.PLFX_PAY_WAIT);
		//订单种类
		model.addAttribute("JP_ORDER_TYPE",JPOrderStatusConstant.JP_ORDER_TYPE);
		HgLogger.getInstance().info("yaosanfeng", "机票分销平台-机票管理-订单管理-查询订单列表成功");
		return "/airticket/order/order_list.html";
	}
	

	/****
	 * 
	 * @方法功能说明：订单详情查询
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月9日上午9:28:26
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param platformOrderQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/detail")
	public String queryOrderDetail(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO){
		//根据QO查询一个订单详情
		//platformOrderQO.setRefundType("A");
		JPOrderDTO dto = jpPlatformOrderService.queryUnique(platformOrderQO);
		
		model.addAttribute("orderDetail", dto);
		//订单状态
		model.addAttribute("STATUS_MAP", JPOrderStatusConstant.PLFX_JPORDER_STATUS_MAP);
		//订单支付状态
		model.addAttribute("PAY_STATUS_MAP", JPOrderStatusConstant.PLFX_JPORDER_PAY_STATUS_MAP);
		//查询条件
		model.addAttribute("platformOrderQO", platformOrderQO);
		//异常/普通订单
		model.addAttribute("TYPE_MAP", JPOrderStatusConstant.JPORDER_TYPE_MAP);
	    //日志查询
		if(dto != null && StringUtils.isNotBlank(dto.getId())){
			JPOrderLogQO qo = new JPOrderLogQO();
			qo.setOrderId(dto.getId());
			List<JPOrderLogDTO> logs = jPOrderLogService.queryList(qo);
			//保存日志
			model.addAttribute("LOGS", logs);
			
		}
		//证件类型
		model.addAttribute("DOCUMENT_TYPE_MAP", JPOrderStatusConstant.DOCUMENT_TYPE_MAP);
		HgLogger.getInstance().info("yaosanfeng", "机票分销平台-机票管理-订单管理-查询订单详情成功");
		
		return "/airticket/order/order_detail.html";
	}

	/****
	 * 
	 * @方法功能说明：跳转到取消订单页面
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月9日上午9:28:38
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@param platformOrderQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="toCancel")
	public String toCancelOrder(HttpServletRequest request,Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO 
			){
		JPOrderDTO jPOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);	
		model.addAttribute("JPOrderDTO", jPOrderDTO);
		return "/airticket/order/order_cancel.html";
	}
	
	/****
	 * 
	 * @方法功能说明：取消订单
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月9日上午9:28:49
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/cancel")
	@ResponseBody
	public String adminCancelOrder(
			HttpServletRequest request, 
			Model model,
			JPCancelTicketSpiCommand command){
		try {
		    //组装乘机人用^隔开
			command.setPassengerName(command.getPassengerName().replaceAll(",","^"));
			YeeXingCancelTicketDTO yeeXingCancelTicketDTO = jPWebService.plfxCancelTicket(command);
			HgLogger.getInstance().info("yaosanfeng","JPOrderController->adminCancelOrder->yeeXingCancelTicketDTO:"+ JSON.toJSONString(yeeXingCancelTicketDTO));
			if(yeeXingCancelTicketDTO.getIs_success().equals("T")){
		       
				return DwzJsonResultUtil.createJsonString("200", "取消订单成功", "closeCurrent", "order");
			}else{
				
				return DwzJsonResultUtil.createJsonString("300", "取消订单失败", "closeCurrent", "order");
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("yaosanfeng", "机票分销平台-机票管理-订单管理-取消订单失败"+HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString("300", "取消订单失败", "closeCurrent", "order");
		}
	}	
	
	/****
	 * 
	 * @方法功能说明：跳转到退废订单页面
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月9日上午9:29:07
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@param platformOrderQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/toRefund")
	public String toRefundOrder(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO){
		JPOrderDTO orderDetail=jpPlatformOrderService.queryUnique(platformOrderQO);	
		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute("REFUND_TYPE_MAP", JPOrderStatusConstant.REFUND_TYPE_MAP);
		return "/airticket/order/order_refund_back.html";
	}
	
	/****
	 * 
	 * @方法功能说明：退废订单
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月9日上午9:29:20
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/refund")
	@ResponseBody
	public String adminRefundOrder(HttpServletRequest request,@ModelAttribute JPRefundTicketSpiCommand command){
		HgLogger.getInstance().info("yuqz", "JPOrderController->adminRefundOrder->command:" + JSON.toJSONString(command));
		try {
			if(command.getPassengerInfos() == null || command.getPassengerInfos().equals("")){
				return DwzJsonResultUtil.createJsonString("300", "退废乘客信息为空", "closeCurrent", "order");
			}
			//组装乘客信息用^隔开
			//组装票号用^隔开
			String passengerInfos = command.getPassengerInfos();
			String[] passengerInfo = passengerInfos.split(",");
			String passenger = "";
			String airId = "";
			for(int i = 0; i < passengerInfo.length; i++){
				if(passenger.equals("")){
					passenger = passenger + passengerInfo[i].split("\\|")[0];
				}else{
					passenger = passenger + "^" + passengerInfo[i].split("\\|")[0];
				}
				if(airId.equals("")){
					airId = airId + passengerInfo[i].split("\\|")[1];
				}else{
					airId = airId + "^" + passengerInfo[i].split("\\|")[1];
				}
				
			}
			command.setPassengerName(passenger);
			command.setAirId(airId);
			YeeXingRefundTicketDTO yeeXingRefundTicketDTO = jPWebService.plfxRefundTicket(command);
			HgLogger.getInstance().info("yaosanfeng","JPOrderController->adminRefundOrder->yeeXingCancelTicketDTO:"+ JSON.toJSONString(yeeXingRefundTicketDTO));
			if(yeeXingRefundTicketDTO.getIs_success().equals("T")){
				
				return DwzJsonResultUtil.createJsonString("200", "退废成功", "closeCurrent", "order");
			}else{
				return DwzJsonResultUtil.createJsonString("300", "退废失败", "closeCurrent", "order");
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("yaosanfeng", "机票分销平台-机票管理-订单管理-退废订单失败"+HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString("300", "退废订单失败", "closeCurrent", "order");
		}
	}	
	
	/**
	 * 
	 * @方法功能说明：定时 扫描 机票订单，超过20分钟未支付状态的订单，执行取消订单操作
	 * @修改者名字：yuqz
	 * @修改时间：2015年8月7日上午10:05:46
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @return:void
	 * @throws
	 */
	@RequestMapping("/timeoutCancelJPOrderTask")
	public void timeoutCancelJPOrderTask(HttpServletRequest request,HttpServletResponse response){
		//定时 扫描 机票订单，超过20分钟未支付状态的订单，执行取消订单操作
		timeoutCancelJPOrderService.timeoutCancelJPOrderTask();
		
	}
	
	
	/*****
	 * 
	 * @方法功能说明：跳转到同步退票状态页面(出票成功,退票处理中,退票失败,退票成功)
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年9月16日下午1:46:04
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param platformOrderQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/toRefundQueryOrder")
	public String toRefundQueryOrder(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO){
		JPOrderDTO jPOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);	
		model.addAttribute("JPOrderDTO", jPOrderDTO);

		return "/airticket/order/order_refund_query.html";
	}
	
	/*****
	 * 
	 * @方法功能说明：查询退票状态(只支持单人查询)
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年9月16日上午9:37:13
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/refundQueryOrder")
	@ResponseBody
	public String adminRefundQueryOrder(HttpServletRequest request,@ModelAttribute JPRefundQueryOrderStatusSpiCommand command,@ModelAttribute PlatformOrderQO platformOrderQO){
		HgLogger.getInstance().info("yaosanfeng", "JPOrderController->adminRefundQueryOrder->command:" + JSON.toJSONString(command));
		try {
			if(StringUtils.isBlank(command.getOrderid()) || StringUtils.isBlank(command.getId()) || StringUtils.isBlank(command.getPassengername())){
				return DwzJsonResultUtil.createJsonString("300", "同步退票状态参数为空", "closeCurrent", "order");
			}
			//根据易行订单号查询订单信息
//			JPOrderDTO jPOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);
			//组装乘客信息用^隔开
			//组装票号用^隔开
//			StringBuilder passengers = new StringBuilder();
//			StringBuilder airIds = new StringBuilder();
//			for(int i = 0 ; i < jPOrderDTO.getPassengerList().size() ; i++){
//				if(i != 0){
//					passengers.append("^");
//					airIds.append("^");
//				}
//				passengers.append(jPOrderDTO.getPassengerList().get(i).getName());
//				airIds.append(jPOrderDTO.getPassengerList().get(i).getTicket().getTicketNo());
//			}
			
//			command.setPassengername(passengers.toString());
//			command.setAirId(airIds.toString());
			YeeXingRefundQueryOrderDTO yeeXingRefundQueryOrderDTO = jPWebService.refundQueryOrder(command);
			HgLogger.getInstance().info("yaosanfeng","JPOrderController->adminRefundQueryOrder->YeeXingRefundQueryOrderDTO:"+ JSON.toJSONString(yeeXingRefundQueryOrderDTO));
			if(yeeXingRefundQueryOrderDTO != null && yeeXingRefundQueryOrderDTO.getIs_success().equals("T")){
				
				return DwzJsonResultUtil.createJsonString("200", "同步退票状态成功", "closeCurrent", "order");
			}else{
				return DwzJsonResultUtil.createJsonString("300", "同步退票状态失败" + yeeXingRefundQueryOrderDTO.getError(), "closeCurrent", "order");
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("yaosanfeng", "机票分销平台-机票管理-订单管理-同步退票状态失败"+HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString("300", "同步退票状态失败", "closeCurrent", "order");
		}
	}
}

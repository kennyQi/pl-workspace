package plfx.admin.controller.gjjp;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import plfx.admin.controller.BaseController;
import plfx.gjjp.app.pojo.qo.GJJPOrderLogQo;
import plfx.gjjp.app.pojo.qo.GJJPOrderQo;
import plfx.gjjp.app.service.local.DataTransService;
import plfx.gjjp.app.service.local.GJJPOrderLocalService;
import plfx.gjjp.app.service.local.GJJPOrderLogLocalService;
import plfx.gjjp.app.service.local.TimeOutCancelGJJPService;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.gjjp.domain.model.GJJPOrder;
import plfx.gjjp.domain.model.GJJPOrderLog;
import plfx.jp.command.admin.gj.ApplyCancelGJJPOrderCommand;
import plfx.jp.command.admin.gj.ApplyCancelNoPayGJJPOrderCommand;
import plfx.jp.command.admin.gj.ApplyRefundGJJPTicketCommand;
import plfx.jp.pojo.exception.PLFXJPException;
import plfx.yxgjclient.common.util.YIGJConstant;



/****
 * 
 * @类功能说明：国际机票订单管理
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015年7月15日上午11:18:15
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value="/gjjp/order")
public class GJJPOrderController extends BaseController{
	
	@Autowired
	private GJJPOrderLogLocalService gjjpOrderLogService;
	@Autowired
	private GJJPOrderLocalService gjjpOrderLocalService;
	@Autowired
	private DataTransService dataTransService;
	@Autowired
	private TimeOutCancelGJJPService timeOutCancelGJJPService;
	/****
	 * 
	 * @方法功能说明：查询国际机票订单列表
	 * @修改者名字：guotx
	 * @修改时间：2015年7月20日上午10:22:40
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param gjjpOrderQo
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
			@ModelAttribute GJJPOrderQo gjjpOrderQo,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
			) {
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			gjjpOrderQo.setCreateDateBegin(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			gjjpOrderQo.setCreateDateEnd(DateUtil.dateStr2EndDate(endTimeStr));
		}
		if (gjjpOrderQo.getDealerOrderIds()!=null&&gjjpOrderQo.getDealerOrderIds().get(0).equals("")) {
			gjjpOrderQo.setDealerOrderIds(null);
		}
        //按创建时间倒序排序
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		//初始化全部数据
		gjjpOrderQo.setInitAll(true);
		//添加查询条件
		pagination.setCondition(gjjpOrderQo);
		pagination = gjjpOrderLocalService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		//订单状态
		model.addAttribute("orderStatusMap", dataTransService.getIntMap(GJJPConstants.ORDER_STATUS_MAP));
		//支付状态
		model.addAttribute("orderPayStatusMap", dataTransService.getIntMap(GJJPConstants.ORDER_PAY_STATUS_MAP));
		//查询条件
		model.addAttribute("gjjpOrderQo", gjjpOrderQo);
		//证件类型
		model.addAttribute("IDTYPE_MAP", dataTransService.getIntMap(GJJPConstants.IDTYPE_MAP));
		//乘客类型
		model.addAttribute("PASSENGER_TYPE_MAP", dataTransService.getIntMap(GJJPConstants.PASSENGER_TYPE_MAP));
		//model.addAttribute("CURRENT_DATE", new Date());
		//成功出票
		model.addAttribute("OUT_TICKET_ALREADY", GJJPConstants.ORDER_STATUS_OUT_TICKET_ALREADY);
		//等待支付
		model.addAttribute("PAY_WAIT", GJJPConstants.ORDER_STATUS_PAY_WAIT);
		//待出票
		model.addAttribute("TICKET_WAIT",GJJPConstants.ORDER_STATUS_OUT_TICKET_WAIT);
		//订单种类
		//model.addAttribute("JP_ORDER_TYPE",JPOrderStatusConstant.JP_ORDER_TYPE);
		//机场缓存查询
		model.addAttribute("dataTransService",dataTransService);
		
		HgLogger.getInstance().info("guotianxiang", "国际机票-订单查询");
		return "/airticket/order/gj_order_list.html";
	}
	

	/****
	 * 
	 * @方法功能说明：订单详情查询
	 * @修改者名字：guotx
	 * @修改时间：2015年7月22日下午13:32:28
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
			@ModelAttribute GJJPOrderQo gjjpOrderQo){
		gjjpOrderQo.setInitAll(true);
		//根据QO查询一个订单详情
		GJJPOrder gjjpOrder = gjjpOrderLocalService.queryUnique(gjjpOrderQo);
		model.addAttribute("orderDetail", gjjpOrder);
		//订单状态
		model.addAttribute("orderStatusMap", dataTransService.getIntMap(GJJPConstants.ORDER_STATUS_MAP));
		//订单支付状态
		model.addAttribute("orderPayStatusMap", dataTransService.getIntMap(GJJPConstants.ORDER_PAY_STATUS_MAP));
		//查询条件
		model.addAttribute("gjjpOrderQo", gjjpOrderQo);
		//机场缓存查询
		model.addAttribute("dataTransService",dataTransService);
		//证件类型
		model.addAttribute("ID_TYPE_MAP", dataTransService.getIntMap(GJJPConstants.IDTYPE_MAP));
		//等待支付
		model.addAttribute("PAY_WAIT", GJJPConstants.ORDER_STATUS_PAY_WAIT);
		//待出票
		model.addAttribute("TICKET_WAIT",GJJPConstants.ORDER_STATUS_OUT_TICKET_WAIT);
		//已出票
		model.addAttribute("TICKET_ALREADY",GJJPConstants.ORDER_STATUS_OUT_TICKET_ALREADY);
	    //日志查询
		GJJPOrderLogQo qo = new GJJPOrderLogQo();
		qo.setJpOrderQo(gjjpOrderQo);
		List<GJJPOrderLog> logs = gjjpOrderLogService.queryList(qo);
		//保存日志
		model.addAttribute("LOGS", logs);
		
		return "/airticket/order/gj_order_detail.html";
	}

	/****
	 * 
	 * @方法功能说明：跳转到取消订单页面，未支付
	 * @修改者名字：guotx
	 * @修改时间：2015年7月25日 13:21:53
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param gjjpOrderQo
	 * @参数：@return
	 * @return:String
	 */
	@RequestMapping(value="toCancel")
	public String toCancel(HttpServletRequest request,Model model,
			@ModelAttribute GJJPOrderQo gjjpOrderQo ){
		gjjpOrderQo.setInitAll(true);
		//根据QO查询一个订单详情
		GJJPOrder orderDetail = gjjpOrderLocalService.queryUnique(gjjpOrderQo);	
		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute("CANCEL_ORDER_TYPE",YIGJConstant.GJ_CANCEL_ORDER_TYPE);
		return "/airticket/order/gj_order_cancel.html";
	}
	
	/****
	 * 
	 * @方法功能说明：取消订单，未支付
	 * @修改者名字：guotx
	 * @修改时间：2015年7月9日上午9:28:49
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param params 取消订单参数
	 * @参数：@return
	 * @return:String json数据
	 * @throws
	 */
	@RequestMapping(value="/cancel")
	@ResponseBody
	public String cancel(HttpServletRequest request,
			ApplyCancelNoPayGJJPOrderCommand command){
		try {
			gjjpOrderLocalService.applyCancelNoPayOrder(command);
		} catch (PLFXJPException e) {
			return DwzJsonResultUtil.createJsonString("300", "取消订单失败,"+e.getStackTrace(), "closeCurrent", "order");
		}
		return DwzJsonResultUtil.createJsonString("200", "取消订单成功", "closeCurrent", "order");
	}	
	/****
	 * 
	 * @方法功能说明：跳转到退票页面,已支付未出票
	 * @修改者名字：guotx
	 * @修改时间：2015年7月30日 13:21:53
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param gjjpOrderQo
	 * @参数：@return
	 * @return:String
	 */
	@RequestMapping(value="/toCancelOrder")
	public String toCancelOrder(HttpServletRequest request,Model model,
			@ModelAttribute GJJPOrderQo gjjpOrderQo ){
		gjjpOrderQo.setInitAll(true);
		//根据QO查询一个订单详情
		GJJPOrder orderDetail = gjjpOrderLocalService.queryUnique(gjjpOrderQo);	
		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute("CANCEL_ORDER_TYPE",YIGJConstant.GJ_CANCEL_ORDER_TYPE);
		return "/airticket/order/gj_payed_order_cancel.html";
	}
	/****
	 * 
	 * @方法功能说明：取消订单，已支付未出票
	 * @修改者名字：guotx
	 * @修改时间：2015年7月31日上午9:28:49
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param params 取消订单参数
	 * @参数：@return
	 * @return:String json数据
	 * @throws
	 */
	@RequestMapping(value="/cancelOrder")
	@ResponseBody
	public String cancelOrder(HttpServletRequest request,
			ApplyCancelGJJPOrderCommand command){
		try {
			gjjpOrderLocalService.applyCancelOrder(command);
		} catch (PLFXJPException e) {
			return DwzJsonResultUtil.createJsonString("300", "退票失败,", "closeCurrent", "order");
		}
		return DwzJsonResultUtil.createJsonString("200", "退票成功", "closeCurrent", "order");
	}
	/****
	 * 
	 * @方法功能说明：跳转到退废票页面，已支付，已出票
	 * @修改者名字：guotx
	 * @修改时间：2015年7月25日 13:11:49
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@param gjjpOrderQo 国际订单号
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/toRefund")
	public String toRefundOrder(
			HttpServletRequest request, 
			Model model,
			@ModelAttribute GJJPOrderQo gjjpOrderQo 
			){
		gjjpOrderQo.setInitAll(true);
		GJJPOrder orderDetail = gjjpOrderLocalService.queryUnique(gjjpOrderQo);	
		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute("REFUND_TYPE_MAP", YIGJConstant.GJ_REFUND_TYPE_MAP);
		return "/airticket/order/gj_order_refund_back.html";
	}
	
	/****
	 * 
	 * @方法功能说明：退废订单
	 * @修改者名字：guotx
	 * @修改时间：2015年7月27日 11:12:41
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
	public String refundOrder(
			HttpServletRequest request, 
			Model model,
			@ModelAttribute ApplyRefundGJJPTicketCommand command){
		//分割乘机人信息
		try {
			gjjpOrderLocalService.applyRefundTicket(command);
		} catch (PLFXJPException e) {
			return DwzJsonResultUtil.createJsonString("300", "操作失败:"+e.getMessage(), "closeCurrent", "order");
		}
		return DwzJsonResultUtil.createJsonString("200", "操作成功", "closeCurrent", "order");
	}	
	
	/**
	 * 
	 * @方法功能说明：定时 扫描国际机票订单，每晚指定时间自动扫描未支付订单，执行取消订单操作
	 * @修改者名字：guotx
	 * @修改时间：2015年8月20日上午10:10:25
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @return:void
	 */
	@RequestMapping("/timeoutCancelGJJPOrderTask")
	public void timeoutCancelGJJPOrderTask(HttpServletRequest request,HttpServletResponse response){
		//定时 扫描国际机票订单，每晚23：50自动扫描未支付订单，执行取消订单操作
		timeOutCancelGJJPService.timeoutCancelGJJPOrderTask();
	}
}

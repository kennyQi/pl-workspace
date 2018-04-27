package lxs.admin.controller.order.mp;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lxs.admin.controller.BaseController;
import lxs.app.service.mp.TicketOrderService;
import lxs.pojo.qo.mp.TicketOrderQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @类功能说明：订单controller
 * @类修改者：
 * @修改日期：2015年5月21日下午12:30:51
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年5月21日下午12:30:51
 */
@Controller
@RequestMapping(value="/ticketOrder")
public class ticketOrderController extends BaseController{

	@Autowired 
	private TicketOrderService ticketOrderService;
	
	/**
	 * @Title: queryLineOrderList 
	 * @author guok
	 * @Description: 门票订单列表
	 * @Time 2016年4月18日上午9:18:23
	 * @param request
	 * @param model
	 * @param ticketOrderQO
	 * @param pageNo
	 * @param pageSize
	 * @param beginTimeStr
	 * @param endTimeStr
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping(value = "/list")
	public String queryLineOrderList(HttpServletRequest request, Model model,
			@ModelAttribute TicketOrderQO ticketOrderQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize
			) {
		
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = (pageNo == null) ? new Integer(1) : pageNo;
		pageSize = (pageSize == null) ? new Integer(20) : pageSize;
		
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(ticketOrderQO);
		pagination = ticketOrderService.queryPagination(pagination);
		
		model.addAttribute("ticketOrderQO", ticketOrderQO);
		model.addAttribute("pagination", pagination);
		
		return "/ticket/order_list.html";
	}
	
	/**
	 * @Title: toCancleLineOrder 
	 * @author guok
	 * @Description: 申请退票
	 * @Time 2016年4月20日上午9:15:03
	 * @param request
	 * @param response
	 * @param model
	 * @param orderID
	 * @return String 设定文件
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/cancle")
	public String toCancleTicketOrder(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "id") String orderID){
		
		boolean flag = ticketOrderService.refundTicketOrder(orderID);
		if (flag) {
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "申请退票成功",null,"ticketOrderList");
		}else {
			return DwzJsonResultUtil.createSimpleJsonString("500", "申请退票失败");
		}
	}

	/**
	 * 
	 * @方法功能说明：取消订单
	 * @修改者名字：cangs
	 * @修改时间：2015年5月21日下午12:31:46
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineOrderID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/toOrder")
	@ResponseBody
	public String toOrder(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "id") String orderID){
		
		boolean flag = ticketOrderService.toOrder(orderID);
		if (flag) {
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "申请出票成功",null,"ticketOrderList");
		}else {
			return DwzJsonResultUtil.createSimpleJsonString("500", "申请出票失败");
		}
	}
		
}
	
	
	
	
	

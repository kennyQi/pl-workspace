package hsl.admin.controller.hotel;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import slfx.jd.pojo.dto.ylclient.order.OrderCancelCommandDTO;
import slfx.jd.pojo.system.HotelOrderStatusConstants;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hsl.admin.controller.BaseController;
import hsl.pojo.command.MPOrderCancelCommand;
import hsl.pojo.command.hotel.JDOrderCancelCommand;
import hsl.pojo.dto.hotel.JDLocalHotelDetailDTO;
import hsl.pojo.dto.hotel.order.HotelOrderDTO;
import hsl.pojo.dto.hotel.order.HotelOrderDetailDTO;
import hsl.pojo.dto.hotel.order.OrderCancelDTO;
import hsl.pojo.dto.hotel.order.OrderCancelResultDTO;
import hsl.pojo.dto.mp.MPOrderDTO;
import hsl.pojo.exception.HotelException;
import hsl.pojo.qo.hotel.HotelOrderDetailQO;
import hsl.pojo.qo.hotel.HotelOrderQO;
import hsl.pojo.qo.hotel.JDHotelListQO;
import hsl.pojo.qo.hotel.JDLocalHotelDetailQO;
import hsl.pojo.qo.hotel.OrderCancelQO;
import hsl.pojo.qo.mp.HslMPOrderQO;
import hsl.spi.common.MpEnumConstants;
import hsl.spi.inter.hotel.HslHotelOrderCancelService;
import hsl.spi.inter.hotel.HslHotelOrderService;
import hsl.spi.inter.hotel.HslHotelService;

@Controller
@RequestMapping(value="/jd")
public class HotelController extends BaseController{
	
	@Autowired
	private HslHotelService hslHotelService;
	
	//默认城市： 杭州
	public static final String cityId="1201";
	
	@Autowired
	private HslHotelOrderService hslHotelOrderService;
	
	@Autowired
	private HslHotelOrderCancelService  hslHotelOrderCancelService;
	
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model,
			@ModelAttribute JDHotelListQO jdHotelListQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo, 
			@RequestParam(value = "numPerPage",required = false) Integer pageSize) {
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 20 : pageSize;
		
		jdHotelListQO.setPageIndex(pageNo);
		jdHotelListQO.setPageSize(pageSize);
		jdHotelListQO.setCityId(cityId);
		jdHotelListQO.setArrivalDate(new Date());
		jdHotelListQO.setDepartureDate(this.getDate("1", 1));
		jdHotelListQO.setCustomerType("None");
		jdHotelListQO.setResultType("1,2,3");
		//添加分页查询条件
		Pagination pagination = new Pagination();
		pagination.setCondition(jdHotelListQO);
		
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		try {
			pagination=this.hslHotelService.queryPagination(pagination);
			
			
		} catch (HotelException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("jdHotelListQO", jdHotelListQO);
		
		return "/jd/list.html";
	}
	
	
	/**
	 * 
	 * @方法功能说明：返回时间
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-1上午11:19:52
	 * @参数：@param msg day
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private Date getDate(String msg,int day){//msg 是否需要返回两个日期 day返回当天和n天日期
		Date date=new Date();
		if(msg.equals("")){
			return date;
		}else{
			long time=date.getTime()+86400000*day;
			return new Date(time);
		}

	}
	
	/**
	 * @方法功能说明：酒店订单列表
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月8日上午11:20:00
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param jpOrderQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/order/list")
	public String orderList(HttpServletRequest request, Model model,
			@ModelAttribute HotelOrderQO hotelOrderQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo, 
			@RequestParam(value = "numPerPage",required = false) Integer pageSize) {
		
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 20 : pageSize;
		
		hotelOrderQO.setPageNo(pageNo);
		hotelOrderQO.setPageSize(pageSize);
		//添加分页查询条件
		Pagination pagination = new Pagination();
		pagination.setCondition(hotelOrderQO);
		
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		try {
			pagination=this.hslHotelOrderService.queryPagination(pagination);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("hotelOrderQO", hotelOrderQO);
		
		
		
		
		
		return "/jd/orderList.html";
	}
	/**
	 * @方法功能说明：
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月10日下午2:47:18
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/hotelOrderDetail")
	public String hotelOrderDetail(HttpServletRequest request, Model model){
		String orderId=request.getParameter("orderId");
		HotelOrderDetailQO detailQo=new HotelOrderDetailQO();
		detailQo.setOrderId(Long.parseLong(orderId));
		HotelOrderDetailDTO order=null;
		try {
			order = this.hslHotelOrderService.queryHotelOrderDetail(detailQo);
			String hotelId=order.getHotelId();
			JDLocalHotelDetailQO qo=new JDLocalHotelDetailQO();
			qo.setHotelId(hotelId);
			
			JDLocalHotelDetailDTO hotelDetail=this.hslHotelService.queryHotelLocalDetail(qo);
			order.setHotelDto(hotelDetail);
			
		} catch (HotelException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("hotelOrder", order);
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderNo", request.getParameter("dealerOrderNo"));
		model.addAttribute("orderStatusMap",HotelOrderStatusConstants.ORDER_STATUS_MAP);
		return "/jd/orderDetail.html";
		
	}
	
	/**
	 * 取消订单页面
	 * @author Caih
	 * @return
	 */
	@RequestMapping("/tocancel")
	public String tocancel(String orderId,Model model)
	{
		model.addAttribute("orderId", orderId);
		//查询取消原因列表
		model.addAttribute("cancelReason", MpEnumConstants.OrderCancelReason.cancelReasonMap);
		return "/jd/order_cancel.html";
	}
	
	/**
	 * 取消订单
	 */

	@RequestMapping(value="/cancel")
	@ResponseBody
	public String adminCancelOrder(HttpServletRequest request, Model model,
			@ModelAttribute OrderCancelCommandDTO ordercancel){
		try {
			JDOrderCancelCommand command = new JDOrderCancelCommand();
			ordercancel.setReason(MpEnumConstants.OrderCancelReason.cancelReasonMap.get(ordercancel.getCancelCode()));
			command.setOrdercancel(ordercancel);
			OrderCancelResultDTO dto = hslHotelOrderService.cancelHotelOrder(command);
			if(!dto.isSuccesss())
			{
				return DwzJsonResultUtil.createJsonString("300", "取消订单失败", "closeCurrent", "");
			}
		} catch (HotelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", "取消订单失败", "closeCurrent", "");
		}
		return DwzJsonResultUtil.createJsonString("200", "取消订单成功", "closeCurrent", "jdOrder");
		
	}
	
	/**
	 * 查看订单取消原因
	 */
	@RequestMapping(value="/reason")
	public String reason(HttpServletRequest request, Model model,
			@ModelAttribute OrderCancelQO qo){
		//根据QO查询一个订单详情
		OrderCancelDTO dto = hslHotelOrderCancelService.queryUnique(qo);
		//查询取消原因列表
		model.addAttribute("dto", dto);
		
		return  "/jd/order_cancel_view.html";
	}

}

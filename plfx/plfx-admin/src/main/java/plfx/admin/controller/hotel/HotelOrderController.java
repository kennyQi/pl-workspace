package plfx.admin.controller.hotel;

import hg.common.component.BaseQo;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;

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
import plfx.jd.pojo.command.plfx.order.OrderCancelAdminCommand;
import plfx.jd.pojo.dto.plfx.order.DealerDTO;
import plfx.jd.pojo.dto.plfx.order.HotelOrderDTO;
import plfx.jd.pojo.dto.plfx.order.SupplierDTO;
import plfx.jd.pojo.qo.HotelOrderQO;
import plfx.jd.pojo.system.HotelOrderConstants;
import plfx.jd.spi.inter.DealerService;
import plfx.jd.spi.inter.HotelOrderService;
import plfx.jd.spi.inter.HotelService;
import plfx.jd.spi.inter.SupplierService;

/**
 * 
 * @类功能说明：酒店订单
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年3月4日上午10:30:55
 * @版本：V1.0
 * 
 */
@Controller
@RequestMapping("/hotel/order")
public class HotelOrderController extends BaseController {

	@Autowired
	private HotelOrderService hotelOrderService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private DealerService dealerService;
//	@Autowired
//	private HotelService hotelService;
	/**
	 * 查询订单列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param qo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public String queryLineList(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute HotelOrderQO hotelOrderQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr) {

		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null ? new Integer(1) : pageNo);
		pagination.setPageSize(pageSize == null ? new Integer(20) : pageSize);
		pagination.setCondition(hotelOrderQO);
		pagination = hotelOrderService.queryPagination(pagination);
		List<SupplierDTO> supplierList = supplierService.queryList(new BaseQo());
		model.addAttribute("supplierList", supplierList);
		List<DealerDTO> dealerList = dealerService.queryList(new BaseQo());
		model.addAttribute("dealerList", dealerList);
		// 订单状态
		model.addAttribute("statusMap", HotelOrderConstants.ORDER_STATUS_MAP);
		model.addAttribute("pagination", pagination);
		model.addAttribute("hotelOrderQO", hotelOrderQO);
		
		return "/hotel/order/order_list.html";
	}

	/**
	 * 查询订单详细
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param qo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/detail")
	public String queryOrderDetail(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "orderNo", required = false) String orderNo) {

		HotelOrderQO qo = new HotelOrderQO();
		qo.setOrderNo(orderNo);
		HotelOrderDTO order = hotelOrderService.queryUnique(qo);
		model.addAttribute("order", order);
		// 订单状态
		model.addAttribute("statusMap", HotelOrderConstants.ORDER_STATUS_MAP);
		model.addAttribute("genderMap",HotelOrderConstants.GENDER_MAP);
		return "/hotel/order/order_detail.html";
	}

	/**
	 * 取消订单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param qo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/toCancel")
	public String toCancel(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "orderNo", required = false) String orderNo,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "supplierOrderNo", required = false) String supplierOrderNo
			) {

		model.addAttribute("orderNo", orderNo);
		model.addAttribute("orderId", orderId);
		model.addAttribute("supplierOrderNo", supplierOrderNo);

		return "/hotel/order/order_cancel.html";
	}

	/**
	 * 取消订单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param qo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/cancel")
	public String cancel(HttpServletRequest request,
			HttpServletResponse response, Model model,
			OrderCancelAdminCommand command) {
		String message = "";
		String statusCode = "";
		try {
			boolean flag = hotelOrderService.OrderCancel(command);
			if (flag) {
				message = "取消成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			} else {
				message = "取消失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
			}
			return DwzJsonResultUtil
					.createJsonString(statusCode, message,
							DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,
							"hotel");

		} catch (Exception e) {
			e.printStackTrace();
			message = "取消失败";
			statusCode = DwzJsonResultUtil.STATUS_CODE_300;
			return DwzJsonResultUtil
					.createSimpleJsonString(statusCode, message
							);
		}

	}

	
	
//	@SuppressWarnings("unchecked")
//	@ResponseBody
//	@RequestMapping("/save")
//	public String save(HttpServletRequest request,
//			HttpServletResponse response, Model model) {
//		String message = "";
//		String statusCode = "";
//		try {
//			boolean flag1 = hotelService.saveHotelListByThread();
//			boolean flag2 = hotelService.saveHotelByThread();
//			if (flag1 && flag2) {
//				message = "保存成功";
//				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
//			} else {
//				message = "保存失败";
//				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
//			}
//			return DwzJsonResultUtil
//					.createJsonString(statusCode, message,
//							DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,
//							"hotel");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			message = "取消失败";
//			statusCode = DwzJsonResultUtil.STATUS_CODE_300;
//			return DwzJsonResultUtil
//					.createSimpleJsonString(statusCode, message
//							);
//		}
//
//	}

}
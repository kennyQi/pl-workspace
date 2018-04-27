package hsl.admin.controller.mp;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.DwzJsonResultUtil;
import hsl.admin.controller.BaseController;
import hsl.pojo.command.MPOrderCancelCommand;
import hsl.pojo.command.MPSyncOrderCommand;
import hsl.pojo.dto.mp.MPOrderDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.qo.mp.DWZPlatdormOrderQO;
import hsl.pojo.qo.mp.HslMPOrderQO;
import hsl.spi.common.MpEnumConstants;
import hsl.spi.inter.mp.MPOrderService;
import hsl.spi.inter.mp.MPScenicSpotService;
@Controller
@RequestMapping(value="/mpOrder")
public class MPOrderController extends BaseController{

	
	@Autowired
	private MPOrderService orderService;
	@Autowired
	private MPScenicSpotService scenicSpotService;

	/**
	 * 订单列表
	 * @param request
	 * @param model
	 * @returns
	 */

	@RequestMapping(value = "/list")
	public String queryOrderList(HttpServletRequest request, Model model,
			@ModelAttribute DWZPlatdormOrderQO dwzPlatformOrderQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize){
		
		//添加分页参数
		Pagination pagination=new Pagination();
		pagination.setPageNo(pageNo == null ? 1 : pageNo);
		pagination.setPageSize(pageSize == null ? 20 : pageSize);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		//添加查询条件
		HslMPOrderQO hslMPOrderQO=BeanMapperUtils.map(dwzPlatformOrderQO, HslMPOrderQO.class);
		hslMPOrderQO.setBeginTime(dwzPlatformOrderQO.getCreateDateBegin());
		hslMPOrderQO.setEndTime(dwzPlatformOrderQO.getCreateDateEnd());
		hslMPOrderQO.setWithPolicy(true);
		hslMPOrderQO.setWithScenicSpot(true);
		hslMPOrderQO.setScenicSpotsNameLike(true);
		//判断注册时间查询条件是否被选择
		if(StringUtils.isBlank(dwzPlatformOrderQO.getCreateDateBegin()) && StringUtils.isBlank(dwzPlatformOrderQO.getCreateDateEnd())){
			hslMPOrderQO.setBeginTime(null);
			hslMPOrderQO.setEndTime(null);
		}else{
			if (StringUtils.isNotBlank(dwzPlatformOrderQO.getCreateDateBegin())
					&& StringUtils.isNotBlank(dwzPlatformOrderQO.getCreateDateEnd())) {

				hslMPOrderQO.setBeginTime(dwzPlatformOrderQO.getCreateDateBegin());
				hslMPOrderQO.setEndTime(dwzPlatformOrderQO	.getCreateDateEnd());
			} else if (StringUtils.isNotBlank(dwzPlatformOrderQO.getCreateDateBegin())	&& StringUtils.isBlank(dwzPlatformOrderQO	.getCreateDateEnd())) {
				hslMPOrderQO.setBeginTime(dwzPlatformOrderQO.getCreateDateBegin());
				hslMPOrderQO.setEndTime(dwzPlatformOrderQO.getCreateDateBegin());
			}
		}
		pagination.setCondition(hslMPOrderQO);
		//分页查询
		pagination=orderService.queryPagination(pagination);
		model.addAttribute("statusMap", MpEnumConstants.OrderStatusEnum.orderStatusMap);
		model.addAttribute("pagination", pagination);
		model.addAttribute("dwzPlatformOrderQO", dwzPlatformOrderQO);
		return "/order/order_list.html";
	}
	
	/**
	 * 订单详情
	 */
	@RequestMapping(value="/detail")
	public String queryOrderDetail(HttpServletRequest request, Model model,
			@ModelAttribute HslMPOrderQO hslMPOrderQO){
		hslMPOrderQO.setWithPolicy(true);
		hslMPOrderQO.setWithScenicSpot(true);
		//根据QO查询一个订单详情
		MPOrderDTO mpOrderDTO=orderService.queryUnique(hslMPOrderQO);
		UserDTO takeTicketUser=orderService.queryTakeTicketUser(mpOrderDTO.getTakeTicketUser().getId());
		mpOrderDTO.setTakeTicketUser(takeTicketUser);
		model.addAttribute("mpOrderDTO", mpOrderDTO);
		return "/order/order_detail.html";
	}
	
	/**
	 * 跳转到取消订单页面
	 */
	@RequestMapping(value="tocancel")
	public String toCancelOrder(HttpServletRequest request, Model model,
			@RequestParam(value="dealerOrderCode",required=false) String dealerOrderCode,
			@RequestParam(value="platformOrderCode",required=false) String platformOrderCode){
		
		model.addAttribute("dealerOrderCode", dealerOrderCode);
		
		
		//查询取消原因列表
		model.addAttribute("cancelReason", MpEnumConstants.OrderCancelReason.cancelReasonMap);
		
		return "/order/order_cancel.html";
	}
	
	/**
	 * 取消订单
	 */

	@RequestMapping(value="/cancel")
	@ResponseBody
	public String adminCancelOrder(HttpServletRequest request, Model model,
			@ModelAttribute MPOrderCancelCommand command){
		
		orderService.cancelMPOrder(command);
		
		return DwzJsonResultUtil.createJsonString("200", "取消订单成功", "closeCurrent", "mporder");
	}
	
	/**
	 * 查看订单取消原因
	 */
	@RequestMapping(value="/reason")
	public String queryCancelOrder(HttpServletRequest request, Model model,
			@ModelAttribute HslMPOrderQO hslMPOrderQO){
		hslMPOrderQO.setWithPolicy(false);
		hslMPOrderQO.setWithScenicSpot(false);
		//根据QO查询一个订单详情
		MPOrderDTO mpOrderDTO=orderService.queryUnique(hslMPOrderQO);
		
		//查询取消原因列表
//		model.addAttribute("cancelReason", MpEnumConstants.OrderCancelReason.cancelReasonMap);
		
		model.addAttribute("mpOrderDTO", mpOrderDTO);
		
		return  "/order/order_cancel_view.html";
	}
	
	/**
	 * 订单同步
	 * @param request
	 * @param model
	 * @param dealerOrderCode
	 * @param platformOrderCode
	 * @return
	 */
	@RequestMapping(value="/sync")
	@ResponseBody
	public String syncOrder(HttpServletRequest request, Model model,
			@RequestParam(value="platformOrderCode",required=false) String platformOrderCode){
		
		MPSyncOrderCommand command = new MPSyncOrderCommand();
		command.setPlatformOrderCode(platformOrderCode);
		orderService.syncOrder(command);
		
		return DwzJsonResultUtil.createSimpleJsonString("200", "订单同步成功");
	}
}

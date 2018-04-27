package plfx.admin.controller.viewspot;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import plfx.admin.controller.BaseController;
import plfx.mp.command.admin.AdminCancelOrderCommand;
import plfx.mp.pojo.dto.order.MPOrderDTO;
import plfx.mp.qo.DWZPlatdormOrderQO;
import plfx.mp.qo.PlatformOrderQO;
import plfx.mp.spi.common.MpEnumConstants;
import plfx.mp.spi.exception.SlfxMpException;
import plfx.mp.spi.inter.DeleteCacheService;
import plfx.mp.spi.inter.PlatformOrderService;


/**
 * 景点门票-订单管理
 * @author lixuanxuan
 *
 */
@Controller
@RequestMapping(value = "/viewspot/order")
public class OrderController extends BaseController{

	@Autowired
	private PlatformOrderService platformOrderService;
	@Autowired
	private DeleteCacheService deleteCacheService;
	
	@Autowired
	private HgLogger hgLogger;
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
		hgLogger.info("wuyg", "admin查询景点订单列表");
		//添加分页参数
		Pagination pagination=new Pagination();
		pageNo=pageNo==null?new Integer(1):pageNo;
		pageSize=pageSize==null?new Integer(20):pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		//添加查询条件
		PlatformOrderQO platformOrderQO=BeanMapperUtils.map(dwzPlatformOrderQO, PlatformOrderQO.class);
		//判断注册时间查询条件是否被选择
		if(StringUtils.isBlank(dwzPlatformOrderQO.getCreateDateBegin()) && StringUtils.isBlank(dwzPlatformOrderQO.getCreateDateEnd())){
			platformOrderQO.setCreateDateFrom(null);
			platformOrderQO.setCreateDateTo(null);
		}else{
			if (StringUtils.isNotBlank(dwzPlatformOrderQO.getCreateDateBegin())
					&& StringUtils.isNotBlank(dwzPlatformOrderQO
							.getCreateDateEnd())) {

				platformOrderQO.setCreateDateFrom(DateUtil
						.dateStr2BeginDate(dwzPlatformOrderQO
								.getCreateDateBegin()));
				platformOrderQO
						.setCreateDateTo(DateUtil
								.dateStr2EndDate(dwzPlatformOrderQO
										.getCreateDateEnd()));
			} else if (StringUtils.isNotBlank(dwzPlatformOrderQO
					.getCreateDateBegin())
					&& StringUtils.isBlank(dwzPlatformOrderQO
							.getCreateDateEnd())) {
				platformOrderQO.setCreateDateFrom(DateUtil
						.dateStr2BeginDate(dwzPlatformOrderQO
								.getCreateDateBegin()));
				platformOrderQO.setCreateDateTo(DateUtil
						.dateStr2EndDate(dwzPlatformOrderQO
								.getCreateDateBegin()));
			}
		}
		pagination.setCondition(platformOrderQO);
		//分页查询
		pagination=platformOrderService.queryPagination(pagination);
		model.addAttribute("statusMap", MpEnumConstants.OrderStatusEnum.orderStatusMap);
		model.addAttribute("pagination", pagination);
		model.addAttribute("dwzPlatformOrderQO", dwzPlatformOrderQO);
		return "/viewspot/order/order_list.html";
	}
	
	/**
	 * 订单详情
	 */
	@RequestMapping(value="/detail")
	public String queryOrderDetail(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO,
			@RequestParam(value="env",required=false) String env){
		hgLogger.info("wuyg", "admin查询订单详情");
		//根据QO查询一个订单详情
		MPOrderDTO mpOrderDTO=platformOrderService.queryUnique(platformOrderQO);
		
		model.addAttribute("mpOrderDTO", mpOrderDTO);
		model.addAttribute("env", env);
		return "/viewspot/order/order_detail.html";
	}
	
	/**
	 * 跳转到取消订单页面
	 */
	@RequestMapping(value="tocancel")
	public String toCancelOrder(HttpServletRequest request, Model model,
			@RequestParam(value="id",required=false) String id){
		hgLogger.info("wuyg", "admin到取消订单页面");
		model.addAttribute("id", id);
		
		//查询取消原因列表
		model.addAttribute("cancelReason", MpEnumConstants.OrderCancelReason.cancelReasonMap);
		
		return "/viewspot/order/order_cancel.html";
	}
	
	/**
	 * 取消订单
	 */
	@RequestMapping(value="/cancel")
	@ResponseBody
	public String adminCancelOrder(HttpServletRequest request, Model model,
			@RequestParam(value="id",required=true) String id, 
			@RequestParam(value="cancelRemark",required=true) String cancelRemark){
		hgLogger.info("wuyg", "admin取消订单"+id);
		AdminCancelOrderCommand adminCancelOrderCommand=new AdminCancelOrderCommand();
		adminCancelOrderCommand.setId(id);
		adminCancelOrderCommand.setCancel(true);
		adminCancelOrderCommand.setCancelRemark(cancelRemark);
		
		try {
			platformOrderService.adminCancelOrder(adminCancelOrderCommand);
		} catch (SlfxMpException e) {
			hgLogger.error("wuyg", "取消订单失败");
			return DwzJsonResultUtil.createJsonString("300", "取消订单失败", "closeCurrent", "mpOrder");
		}
		hgLogger.info("wuyg", "取消订单成功");
		return DwzJsonResultUtil.createJsonString("200", "取消订单成功", "closeCurrent", "mpOrder");
	}
	
	/**
	 * 查看订单取消原因
	 */
	@RequestMapping(value="/reason")
	public String queryCancelOrder(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO){
		hgLogger.info("wuyg", "admin查看取消原因");
		//根据QO查询一个订单详情
		MPOrderDTO mpOrderDTO=platformOrderService.queryUnique(platformOrderQO);
		
		model.addAttribute("mpOrderDTO", mpOrderDTO);

		
		return  "/viewspot/order/order_cancel_view.html";
	}
	
	/**
	 * 同步订单状态
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/sync")
	@ResponseBody
	public String syncOrder(HttpServletRequest request, Model model,
			@RequestParam(value="id",required=true) String id){
		hgLogger.info("wuyg", "admin同步订单状态"+id);
		try {
			platformOrderService.syncOrder(id);
		} catch (SlfxMpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			hgLogger.error("wuyg", "admin同步订单失败");
			return DwzJsonResultUtil.createSimpleJsonString("300", "订单同步失败");
		}
		hgLogger.info("wuyg", "admin订单同步成功");
		return DwzJsonResultUtil.createSimpleJsonString("200", "订单同步成功");
	}
	/**
	 * 清空价格日历缓存和同程政策缓存
	 * @return
	 */
	@RequestMapping(value="/deleteCacheJob")
	@ResponseBody
	public void deleteCacheJob(){
		deleteCacheService.deleteCacheJob();
	}
}

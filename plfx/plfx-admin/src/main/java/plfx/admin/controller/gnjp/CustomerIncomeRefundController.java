package plfx.admin.controller.gnjp;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.log.util.HgLogger;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import plfx.jp.pojo.dto.dealer.DealerDTO;
import plfx.jp.qo.admin.dealer.DealerQO;
import plfx.jp.spi.inter.JPPlatformOrderService;
import plfx.jp.spi.inter.dealer.DealerService;
import plfx.yeexing.pojo.dto.order.JPOrderDTO;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
import plfx.yeexing.pojo.dto.order.PlatformGNOrderDetailDTO;
import plfx.yeexing.qo.admin.PassengerQO;
import plfx.yeexing.qo.admin.PlatformOrderQO;

/*****
 * 
 * @类功能说明：用户收入退款明细
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年8月11日下午3:05:49
 * @版本：V1.0
 *
 */
@Controller
public class CustomerIncomeRefundController {
	
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	
	@Autowired
	private DealerService dealerService;
	
	
	@RequestMapping("/airtkt/gnjp/customer/income_refund/list")
	public ModelAndView list(@RequestParam(value="pageNum", required = false)Integer pageNum,
							 @RequestParam(value="numPerPage", required = false)Integer pageSize,
							 @RequestParam(value="orderNo", required=false)String orderNo,
							 @RequestParam(value="linkerName", required=false)String linkerName,
							 @RequestParam(value="createDateFrom", required=false)String createDateFrom,
							 @RequestParam(value="createDateTo", required=false)String createDateTo,
							 @RequestParam(value="pnr", required=false)String pnr,
							 @RequestParam(value="passengerName", required=false)String passengerName,
							 @RequestParam(value="dealerCode", required=false)String dealerCode){
		
		Pagination pagination = new Pagination();
		PlatformOrderQO qo = new PlatformOrderQO();
		qo.setIsPay(true);
		//平台订单号
		if(StringUtils.isNotBlank(orderNo)){
			qo.setOrderNo(orderNo);
		}
		//下单联系人
		if(StringUtils.isNotBlank(linkerName)){
			qo.setLoginName(linkerName);
		}
		//pnr
		if(StringUtils.isNotBlank(pnr)){
			qo.setPnr(pnr);
		}
		//下单开始时间
		if(StringUtils.isNotBlank(createDateFrom)){
			qo.setCreateDateFrom(DateUtil.dateStr2BeginDate(createDateFrom));
		}
		//下单结束时间
		if(StringUtils.isNotBlank(createDateTo)){
			qo.setCreateDateTo(DateUtil.dateStr2EndDate(createDateTo));
		}
		//乘客姓名
		if(StringUtils.isNotBlank(passengerName)){
			PassengerQO passengerQo = new PassengerQO(); 
			passengerQo.setName(passengerName);
			qo.setPassengerQO(passengerQo);
		}
		
		//经销商订单号
		if(StringUtils.isNotBlank(dealerCode)){
			qo.setDealerCode(dealerCode);
		}
		
		//按创建时间倒序排序
		qo.setCreateDateAsc(false);
		
		pagination.setCondition(qo);
		
		if(pageNum!=null){
			pagination.setPageNo(pageNum);
		}
		if(pageSize!=null){
			pagination.setPageSize(pageSize);
		}
		
		
		pagination = jpPlatformOrderService.queryPagination(pagination);
		//经销商列表
		List<DealerDTO> dealerList = dealerService.queryList(new DealerQO());
		ModelAndView mav = new ModelAndView("/airticket/customer_income_refund/gn_list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("orderNo", orderNo);
		mav.addObject("linkerName", linkerName);
		mav.addObject("pnr", pnr);
		mav.addObject("createDateFrom", createDateFrom);
		mav.addObject("createDateTo", createDateTo);
		mav.addObject("passengerName", passengerName);
		mav.addObject("dealerCode", dealerCode);
		mav.addObject("dealerList", dealerList);
		//证件类型
		mav.addObject("DOCUMENT_TYPE_MAP", JPOrderStatusConstant.DOCUMENT_TYPE_MAP);
		//乘客类型
		mav.addObject("PASSENGER_TYPE_MAP", JPOrderStatusConstant.PASSENGER_TYPE_MAP);
		return mav;
	}
	
	
	/**
	 * 订单明细
	 */
	@RequestMapping("/airtkt/customer/income_refund/detail")
	public ModelAndView detail(
			@RequestParam(value="orderNo", required=false)String orderNo,
			@RequestParam(value="supplierOrderNo", required=false)String supplierOrderNo){
		
		ModelAndView mav = new ModelAndView("/airticket/customer_income_refund/gn_detail.html");
		PlatformOrderQO qo = new PlatformOrderQO();
		qo.setOrderNo(orderNo);
		qo.setSupplierOrderNo(supplierOrderNo);
		JPOrderDTO jpOrder = jpPlatformOrderService.queryUnique(qo);
		PlatformGNOrderDetailDTO dto = new PlatformGNOrderDetailDTO();
		dto.setOrder(jpOrder);
		mav.addObject("detail", dto);
		try {
			mav.addObject("STATUS_MAP", JPOrderStatusConstant.PLFX_JPORDER_STATUS_MAP);
		} catch (Exception e) {
			HgLogger.getInstance().error("tuhualiang", "CustomerIncomeRefundController->detail->exception[订单明细]:" + HgLogger.getStackTrace(e));
		}

		return mav;
	}
	
	
	@RequestMapping("/airtkt/customer/income_refund/cancel_jporder/page")
	public ModelAndView cancelJPOrderPage(@RequestParam(value="orderNo", required=false)String orderNo){
		
		ModelAndView mav = new ModelAndView("/airticket/customer_income_refund/cancel.html");
		mav.addObject("orderNo", orderNo);
		return mav;
	}
	
}

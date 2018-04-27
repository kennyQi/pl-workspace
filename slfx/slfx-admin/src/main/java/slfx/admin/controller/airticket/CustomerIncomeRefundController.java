package slfx.admin.controller.airticket;

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

import slfx.jp.pojo.dto.dealer.DealerDTO;
import slfx.jp.pojo.dto.order.JPOrderDTO;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.pojo.dto.order.PlatformOrderDetailDTO;
import slfx.jp.qo.admin.PassengerQO;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.jp.qo.admin.dealer.DealerQO;
import slfx.jp.spi.inter.JPPlatformOrderService;
import slfx.jp.spi.inter.dealer.DealerService;

@Controller
public class CustomerIncomeRefundController {
	
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	
	@Autowired
	private DealerService dealerService;
	
	
	@RequestMapping("/airtkt/customer/income_refund/list")
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
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		if(StringUtils.isNotBlank(orderNo)){
			qo.setOrderNo(orderNo);//平台订单号Z
		}
		
		if(StringUtils.isNotBlank(linkerName)){
			//下单联系人
			qo.setLoginName(linkerName);
		}
		
		if(StringUtils.isNotBlank(pnr)){
			qo.setPnr(pnr);
		}
		
		if(StringUtils.isNotBlank(createDateFrom)){
			qo.setCreateDateFrom(DateUtil.dateStr2BeginDate(createDateFrom));
		}
		if(StringUtils.isNotBlank(createDateTo)){
			qo.setCreateDateTo(DateUtil.dateStr2EndDate(createDateTo));
		}
		
		if(StringUtils.isNotBlank(passengerName)){
			PassengerQO passengerQo = new PassengerQO(); //乘客姓名
			passengerQo.setName(passengerName);
			qo.setPassengerQO(passengerQo);
		}
		
		if(StringUtils.isNotBlank(dealerCode)){
			qo.setDealerCode(dealerCode);
		}
		
		qo.setCreateDateAsc(false);//按创建时间倒序排序
		
		pagination.setCondition(qo);
		
		if(pageNum!=null){
			pagination.setPageNo(pageNum);
		}
		if(pageSize!=null){
			pagination.setPageSize(pageSize);
		}
		
		//pagination = jpPlatformOrderService.queryOrderList(pagination);
		pagination = jpPlatformOrderService.queryPagination(pagination);
		List<DealerDTO> dealerList = dealerService.queryList(new DealerQO());//经销商列表
		
		ModelAndView mav = new ModelAndView("/airticket/customer_income_refund/list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("orderNo", orderNo);
		mav.addObject("linkerName", linkerName);
		mav.addObject("pnr", pnr);
		mav.addObject("createDateFrom", createDateFrom);
		mav.addObject("createDateTo", createDateTo);
		mav.addObject("passengerName", passengerName);
		mav.addObject("dealerCode", dealerCode);
		mav.addObject("dealerList", dealerList);
		return mav;
	}
	
	
	/**
	 * 订单明细
	 */
	@RequestMapping("/airtkt/customer/income_refund/detail")
	public ModelAndView detail(
			@RequestParam(value="orderNo", required=false)String orderNo,
			@RequestParam(value="supplierOrderNo", required=false)String supplierOrderNo){
		
		ModelAndView mav = new ModelAndView("/airticket/customer_income_refund/detail.html");
		PlatformOrderQO qo = new PlatformOrderQO();
		qo.setOrderNo(orderNo);
		qo.setSupplierOrderNo(supplierOrderNo);
		JPOrderDTO jpOrder = jpPlatformOrderService.queryUnique(qo);
		PlatformOrderDetailDTO dto = new PlatformOrderDetailDTO();
		dto.setOrder(jpOrder);
		mav.addObject("detail", dto);
		try {
			mav.addObject("STATUS_MAP", JPOrderStatusConstant.SLFX_JPORDER_STATUS_MAP);
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
